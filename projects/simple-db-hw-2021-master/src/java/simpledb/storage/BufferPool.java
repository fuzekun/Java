package simpledb.storage;

import simpledb.common.Database;
import simpledb.common.Permissions;
import simpledb.common.DbException;
import simpledb.common.DeadlockException;
import simpledb.transaction.Transaction;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;

import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * BufferPool manages the reading and writing of pages into memory from
 * disk. Access methods call into it to retrieve pages, and it fetches
 * pages from the appropriate location.
 * <p>
 * The BufferPool is also responsible for locking;  when a transaction fetches
 * a page, BufferPool checks that the transaction has the appropriate
 * locks to read/write the page.
 *
 * @Threadsafe, all fields are final
 */
public class BufferPool {
    /** Bytes per page, including header. */
    private static final int DEFAULT_PAGE_SIZE = 4096;

    private static int pageSize = DEFAULT_PAGE_SIZE;

    /** Default number of pages passed to the constructor. This is used by
     other classes. BufferPool should use the numPages argument to the
     constructor instead. */
    public static final int DEFAULT_PAGES = 50;

    // 锁
    class PageLock{
        private static final int SHARE = 0;
        private static final int EXCLUSIVE = 1;
        private TransactionId tid;
        private int type;
        public PageLock(TransactionId tid, int type){
            this.tid = tid;
            this.type = type;
        }
        public TransactionId getTid(){
            return tid;
        }
        public int getType(){
            return type;
        }
        public void setType(int type){
            this.type = type;
        }
    }

    class LockManager {
        ConcurrentHashMap<PageId, ConcurrentHashMap<TransactionId, PageLock>> lockMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<TransactionId, ConcurrentHashMap<PageId, PageLock>>pageMap = new ConcurrentHashMap<>();
        /**
         * 获取锁
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
            ConcurrentHashMap<PageId, PageLock> pages = pageMap.getOrDefault(tid, new ConcurrentHashMap<>());
            // tid 没有 Page 上的锁
            if (!pageLocks.containsKey(tid)) {
                // 如果申请排他锁一定不行
                if (requiredType == PageLock.EXCLUSIVE) return false;
                // 多个事务
                if (pageLocks.size() > 1) {
                    // 申请共享锁
                    PageLock pageLock = new PageLock(tid, PageLock.SHARE);
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
                    for (PageLock lock : pageLocks.values()) {
                        curLock = lock;
                    }
                    // 如果页面上的锁是读锁
                    if (curLock.getType() == PageLock.SHARE) {
                        // 申请共享锁
                        PageLock pageLock = new PageLock(tid, PageLock.SHARE);
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
                if (requiredType == PageLock.SHARE) {
                    return true;
                }
                // 新请求是独占锁
                else if (requiredType == PageLock.EXCLUSIVE) {
                    // 如果该页面上只有一个锁，就是该事务的读锁
                    if (pageLocks.size() == 1) {
                        // 进行锁升级(升级为写锁)
                        pageLock.setType(PageLock.EXCLUSIVE);
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
                ConcurrentHashMap<TransactionId, PageLock> locks = lockMap.get(pid);
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
        public synchronized ConcurrentHashMap<PageId, PageLock> getAllPages(TransactionId tid){
            return pageMap.get(tid);
        }
    }

    // 锁管理
    private LockManager lockManager;
    // 页面的最大数量
    private final int numPages;
    // 储存的页面
    // key 为 PageId
    private final ConcurrentHashMap<PageId, LinkedNode> pageStore;
    // 头节点
    private LinkedNode head;
    // 尾节点
    private LinkedNode tail;

    // 页面的访问顺序
    private static class LinkedNode{
        PageId pageId;
        Page page;
        LinkedNode prev;
        LinkedNode next;
        public LinkedNode(PageId pageId, Page page){
            this.pageId = pageId;
            this.page = page;
        }
    }

    private void addToHead(LinkedNode node){
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(LinkedNode node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(LinkedNode node){
        remove(node);
        addToHead(node);
    }

    private LinkedNode removeTail(){
        LinkedNode node = tail.prev;
        remove(node);
        return node;
    }



    /**
     * Creates a BufferPool that caches up to numPages pages.
     *
     * @param numPages maximum number of pages in this buffer pool.
     */
    public BufferPool(int numPages) {
        // some code goes here
        this.numPages = numPages;
        pageStore = new ConcurrentHashMap<>();

        // lab2
        head = new LinkedNode(new HeapPageId(-1, -1), null);
        tail = new LinkedNode(new HeapPageId(-1, -1), null);
        head.next = tail;
        tail.prev = head;

        // lab4
        lockManager = new LockManager();
    }

    public static int getPageSize() {
        return pageSize;
    }

    // THIS FUNCTION SHOULD ONLY BE USED FOR TESTING!!
    public static void setPageSize(int pageSize) {
        BufferPool.pageSize = pageSize;
    }

    // THIS FUNCTION SHOULD ONLY BE USED FOR TESTING!!
    public static void resetPageSize() {
        BufferPool.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * Retrieve the specified page with the associated permissions.
     * Will acquire a lock and may block if that lock is held by another
     * transaction.
     * <p>
     * The retrieved page should be looked up in the buffer pool.  If it
     * is present, it should be returned.  If it is not present, it should
     * be added to the buffer pool and returned.  If there is insufficient
     * space in the buffer pool, a page should be evicted and the new page
     * should be added in its place.
     *
     * @param tid the ID of the transaction requesting the page
     * @param pid the ID of the requested page
     * @param perm the requested permissions on the page
     */
    public Page getPage(TransactionId tid, PageId pid, Permissions perm)
            throws TransactionAbortedException, DbException {
        // some code goes here
        // ------------------- lab 4 -----------------------------
        // 判断需要获取的锁的类型
        int lockType = perm == Permissions.READ_ONLY ? PageLock.SHARE : PageLock.EXCLUSIVE;
        // 计算超时时间（设置为 500 ms）
        long startTime = System.currentTimeMillis();
        boolean isAcquired = false;
        // 循环获取锁
        while(!isAcquired){
            // 尝试获取锁
            isAcquired = lockManager.acquiredLock(pid, tid, lockType);
            long now = System.currentTimeMillis();
            // 如果超过 500 ms没有获取就抛出异常
            if(now - startTime > 500){
                // 放弃当前事务
                throw new TransactionAbortedException();
            }
        }

        // ------------------- lab 2------------------------------
        // 如果缓存池中没有
        if(!pageStore.containsKey(pid)){
            // 获取
            DbFile dbFile = Database.getCatalog().getDatabaseFile(pid.getTableId());
            Page page = dbFile.readPage(pid);
            // 是否超过大小
            if(pageStore.size() >= numPages){
                // 使用 LRU 算法进行淘汰最近最久未使用
                evictPage();
            }
            LinkedNode node = new LinkedNode(pid, page);
            // 放入缓存
            pageStore.put(pid, node);
            // 插入头节点
            addToHead(node);
        }
        // 移动到头部
        moveToHead(pageStore.get(pid));
        // 从 缓存池 中获取
        return pageStore.get(pid).page;
    }

    /**
     * Releases the lock on a page.
     * Calling this is very risky, and may result in wrong behavior. Think hard
     * about who needs to call this and why, and why they can run the risk of
     * calling it.
     *
     * @param tid the ID of the transaction requesting the unlock
     * @param pid the ID of the page to unlock
     */
    public  void unsafeReleasePage(TransactionId tid, PageId pid) {
        // some code goes here
        // not necessary for lab1|lab2
        lockManager.releaseLock(pid, tid);
    }

    /**
     * Release all locks associated with a given transaction.
     * 保证事务一定提交成功，所以直接调用true的就行了
     * @param tid the ID of the transaction requesting the unlock
     */
    public void transactionComplete(TransactionId tid) {
        // some code goes here
        // not necessary for lab1|lab2
        transactionComplete(tid, true);
    }

    /** Return true if the specified transaction has a lock on the specified page */
    public boolean holdsLock(TransactionId tid, PageId p) {
        // some code goes here
        // not necessary for lab1|lab2
        return lockManager.isHoldLock(tid, p);
    }

    /**
     * Commit or abort a given transaction; release all locks associated to
     * the transaction.
     *
     * @param tid the ID of the transaction requesting the unlock
     * @param commit a flag indicating whether we should commit or abort
     */
    public void transactionComplete(TransactionId tid, boolean commit) {
        // some code goes here
        // not necessary for lab1|lab2
        // 如果成功提交
        if(commit){
            // 刷新页面
            try{
                flushPages(tid);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        // 如果提交失败，回滚
        else{
            restorePages(tid);
        }
        // 事务完成
        lockManager.completeTransaction(tid);
    }
    public synchronized void restorePages(TransactionId tid){

        // 遍历当前事务修改的页面，看是否在缓存中
        for (PageId pid : lockManager.getAllPages(tid).keySet()) {
            LinkedNode node = pageStore.get(pid);
            // 事务持有的页面在内存中，并且脏页的tid就是自己
            if (node != null && tid.equals(node.page.isDirty())) {
                Page pageInDisk = Database.getCatalog().getDatabaseFile(pid.getTableId()).readPage(pid);
                node.page = pageInDisk;
                // 为了安全可以写，但是没必要。更新node
                pageStore.put(pid, node);
                moveToHead(node);
            }

        }
    }



    /**
     * Add a tuple to the specified table on behalf of transaction tid.  Will
     * acquire a write lock on the page the tuple is added to and any other
     * pages that are updated (Lock acquisition is not needed for lab2).
     * May block if the lock(s) cannot be acquired.
     *
     * Marks any pages that were dirtied by the operation as dirty by calling
     * their markDirty bit, and adds versions of any pages that have
     * been dirtied to the cache (replacing any existing versions of those pages) so
     * that future requests see up-to-date pages.
     *
     * @param tid the transaction adding the tuple
     * @param tableId the table to add the tuple to
     * @param t the tuple to add
     */
    public void insertTuple(TransactionId tid, int tableId, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        // not necessary for lab1
        // 获取 数据库文件 DBfile
        HeapFile heapFile = (HeapFile) Database.getCatalog().getDatabaseFile(tableId);
        // 将页面刷新到缓存中
        updateBufferPoll(heapFile.insertTuple(tid, t), tid);
    }

    /**
     * Remove the specified tuple from the buffer pool.
     * Will acquire a write lock on the page the tuple is removed from and any
     * other pages that are updated. May block if the lock(s) cannot be acquired.
     *
     * Marks any pages that were dirtied by the operation as dirty by calling
     * their markDirty bit, and adds versions of any pages that have
     * been dirtied to the cache (replacing any existing versions of those pages) so
     * that future requests see up-to-date pages.
     *
     * @param tid the transaction deleting the tuple.
     * @param t the tuple to delete
     */
    public  void deleteTuple(TransactionId tid, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        // not necessary for lab1
        // 查询所属表对应的文件
        HeapFile heapFile = (HeapFile)Database.getCatalog().getDatabaseFile(t.getRecordId().getPageId().getTableId());
        // 将页面刷新到缓存中
        updateBufferPoll(heapFile.deleteTuple(tid, t), tid);
    }

    /**
     * 更新缓存
     * @param pageList 需要更新的页面
     * @param tid 事务id
     * */
    private void updateBufferPoll(List<Page> pageList, TransactionId tid) throws DbException {
        for (Page page : pageList){
            // 页面改成脏页
            page.markDirty(true, tid);
        }
    }

    /**
     * Flush all dirty pages to disk.
     * NB: Be careful using this routine -- it writes dirty data to disk so will
     *     break simpledb if running in NO STEAL mode.
     */
    public synchronized void flushAllPages() throws IOException {
        // some code goes here
        // not necessary for lab1
        for(PageId pageId : pageStore.keySet()){
            flushPage(pageId);
        }
    }

    /** Remove the specific page id from the buffer pool.
     Needed by the recovery manager to ensure that the
     buffer pool doesn't keep a rolled back page in its
     cache.

     Also used by B+ tree files to ensure that deleted pages
     are removed from the cache so they can be reused safely
     */
    public synchronized void discardPage(PageId pid) throws IOException {
        // some code goes here
        // not necessary for lab1
        // 删除使用记录
        remove(pageStore.get(pid));
        // 删除缓存
        pageStore.remove(pid);
    }

    /**
     * Flushes a certain page to disk
     * @param pid an ID indicating the page to flush
     */
    private synchronized  void flushPage(PageId pid) throws IOException {
        // some code goes here
        // not necessary for lab1
        Page page = pageStore.get(pid).page;
        // 如果是是脏页
        if(page.isDirty() != null){
            // 写入脏页
            Database.getCatalog().getDatabaseFile(pid.getTableId()).writePage(page);
            // 移除脏页标签 和 事务标签
            page.markDirty(false, null);
        }
    }

    /** Write all pages of the specified transaction to disk.
     */
    public synchronized  void flushPages(TransactionId tid) throws IOException {
        // some code goes here
        // not necessary for lab1|lab2

        if (lockManager.getAllPages(tid) == null) return ;
        // 遍历所有事务持有的页面
        for (PageId pid : lockManager.getAllPages(tid).keySet()) {
            LinkedNode node = pageStore.get(pid);
//            if (node == null) System.out.println(tid.getId() + "不存在" + pid.getTableId() + " " + pid.getPageNumber());
            // 如果页面没有被换出内存，并且是脏页的情况下才需要
            if (node != null && tid.equals(node.page.isDirty())) {
                flushPage(pid);
            }
        }
    }


    /**
     * Discards a page from the buffer pool.
     * Flushes the page to disk to ensure dirty pages are updated on disk.
     */
    private synchronized  void evictPage() throws DbException {
        // some code goes here
        // not necessary for lab1
        // 遍历全部页面，如果是脏页需要跳过，因为还没提交
        for (int i = 0; i < numPages; i++) {
            // 淘汰尾部节点
            LinkedNode node = removeTail();
            Page evictPage = node.page;
            // 如果当前有事务持有，也就是脏页，需要跳过
            if(evictPage.isDirty() != null){
                addToHead(node);
            }
            // 如果不是，刷盘
            else{
                // 更新页面
                try{
                    flushPage(node.pageId);
                }catch (IOException e){
                    e.printStackTrace();
                }
//                if (lockManager.lockMap.get(evictPage.getId()) != null) System.out.println("正在交换带锁的页面, 没有脏标记，但是上锁了!");
                pageStore.remove(node.pageId);
                return ;
            }
        }
        // 如果没有就是所有页面都是未提交的脏页
        throw new DbException("All Page Are Dirty Page");
    }


}

