package simpledb.transaction;

import simpledb.storage.BufferPool;
import simpledb.storage.PageId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author: Zekun Fu
 * @date: 2023/3/14 15:26
 * @Description: https://github.com/CreatorsStack/CreatorDB/blob/master/src/main/java/simpledb/transaction/LockManager.java
 * 它的锁，试试效果
 */

public class LockManager {
    ConcurrentHashMap<PageId, ConcurrentHashMap<TransactionId, PageLock>> lockMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<TransactionId, ConcurrentHashMap<PageId, PageLock>>pageMap = new ConcurrentHashMap<>();
    /**
     * 获取最小的事务id，
     * min(Integer::min)应该是min(Integer::compareTo)
     * */
    public synchronized long acquireMinTid() {
        return  pageMap.keySet().stream().mapToLong(TransactionId::getId).min().getAsLong();
    }
    /**
     * 获取锁
     * @param pid 页面id
     * @param tid 事务id
     * @param requiredType 需要锁的类型 共享，排他
     */
    public synchronized boolean acquiredLock(PageId pid, TransactionId tid, int requiredType) {
        // 当前页面没有锁
        if (lockMap.get(pid) == null) {
            // 创建锁
            PageLock pageLock = new PageLock(tid, requiredType);
            ConcurrentHashMap<TransactionId, PageLock> pageLocks = new ConcurrentHashMap<>();
            // 这里不能直接new啊，页面没有锁，不代表事务没有锁
            ConcurrentHashMap<PageId, PageLock> pages = pageMap.getOrDefault(tid, new ConcurrentHashMap<>());
            pageLocks.put(tid, pageLock);
            pages.put(pid, pageLock);
            lockMap.put(pid, pageLocks);
            pageMap.put(tid, pages);
            return true;
        }
        // 有锁，获取当前锁的等待队列
        ConcurrentHashMap<TransactionId, PageLock> pageLocks = lockMap.get(pid);
        // 这里为什么会有lockMaps中有，但是pageMap中没有的情况呢?
        ConcurrentHashMap<PageId,  PageLock> pages = pageMap.getOrDefault(tid, new ConcurrentHashMap<>());
        // tid 没有 Page 上的锁
        if (!pageLocks.containsKey(tid)) {
            // 如果申请排他锁一定不行
            if (requiredType ==  PageLock.EXCLUSIVE) return false;
            // 多个事务
            if (pageLocks.size() > 1) {
                // 申请共享锁
                 PageLock pageLock = new  PageLock(tid,  PageLock.SHARE);
                pageLocks.put(tid, pageLock);
                pages.put(pid, pageLock);
                lockMap.put(pid, pageLocks);
                pageMap.put(tid, pages);
                return true;
            }
            // 如果只有一个事务，page上可能是读锁，也可能是写锁
            else {
                // 获取唯一的锁
                 PageLock curLock = null;
                for ( PageLock lock : pageLocks.values()) {
                    curLock = lock;
                }
                // 如果页面上的锁是读锁
                if (curLock.getType() ==  PageLock.SHARE) {
                    // 申请共享锁
                     PageLock pageLock = new  PageLock(tid,  PageLock.SHARE);
                    pageLocks.put(tid, pageLock);
                    pages.put(pid, pageLock);
                    lockMap.put(pid, pageLocks);
                    pageMap.put(tid, pages);
                    return true;
                }
                // 页面上的是写锁
                else return false;
            }
        }
        // 当前事务持有 Page 锁
        else {
             PageLock pageLock = pageLocks.get(tid);
            // 新请求的是读锁
            if (requiredType ==  PageLock.SHARE) {
                return true;
            }
            // 新请求是独占锁
            else if (requiredType ==  PageLock.EXCLUSIVE) {
                // 如果该页面上只有一个锁，就是该事务的读锁
                if (pageLocks.size() == 1) {
                    // 进行锁升级(升级为写锁)
                    pageLock.setType( PageLock.EXCLUSIVE);
                    return true;
                }
                // 别的事务也有锁
                else return false;
            }
        }
        return false;
    }

    /**
     *
     * @param pid 页面
     * @param tid 事务
     * 释放tid对pid的页面锁
     * @return 是否释放锁成功，如果事务不持有页面锁，就返回false
     */
    public synchronized boolean releaseLock(PageId pid, TransactionId tid) {
        // 其实，只要保证上面的两个双向hash同步更新，就不会出现，tid不持有pid锁的情况，这个判断可以认为一直是true的。
        // 但是别的地方可能会调用这个方法，所以就需要判断了。
        if (isHoldLock(tid, pid)) {
            ConcurrentHashMap<TransactionId,  PageLock> locks = lockMap.get(pid);
            // 将事务从页面表中去掉
            locks.remove(tid);
            // 页面上没有锁了，移除页面
            if (locks.size() == 0) {
                // 源代码，在遍历map的时候移除map的元素，会不会有问题？
                lockMap.remove(pid);
            }
//                System.out.println(tid.getId() + "移除" + pid.getTableId() + " " + pid.getPageNumber());
            // 可以看到，不只是pageMap的不在内存中。释放锁的时候，锁所在的页面都可能不在内存中。那说明这些页面被交换出去了
            /**
             * 这个是因为，读锁并不会将页面变脏。
             * 所以说，读锁所在的页面可能会被换出内存。
             * 所以，每次事务完成，flush的时候，需要判断内存中是否有页面。如果没有，就不需要在进行flush操作了，因为已经换出去了。
             */
//                if (pageStore.get(pid) == null) System.out.println("不在内存中");
            // 将页面从事务表中去掉
            pageMap.get(tid).remove(pid);
            return true;
        }
        return false;
    }

    /**
     * @param tid 事务id
     * @param pid 页面id
     * @return 是否持有锁。事务申请了锁，并且锁在了这个页面上。
     * */
    public synchronized boolean isHoldLock(TransactionId tid, PageId pid) {
        // 页面中没有锁 || 锁住页面的事务中没有tid
        return lockMap.containsKey(pid) && lockMap.get(pid).containsKey(tid);
    }
    /**
     * @param tid
     * 事务完成：释放锁
     * 这里是锁管理中的事务完成，只负责进行锁的释放，不进行是否commit的判断
     * */
    public synchronized void completeTransaction(TransactionId tid) {
        if (pageMap.get(tid) == null) return ;
        // 遍历事务持有锁的页，然后释放
        for (PageId pageId : pageMap.get(tid).keySet()) {
            releaseLock(pageId, tid);
        }
        // 删除事务
        pageMap.remove(tid);
    }
    /**
     * 返回事务持有的所有页面
     * @param tid 事务id
     * */
    public synchronized ConcurrentHashMap<PageId,  PageLock> getAllPages(TransactionId tid){
        return pageMap.get(tid);
    }
}