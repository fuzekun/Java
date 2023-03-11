package simpledb.storage;

import simpledb.common.Database;
import simpledb.common.Permissions;
import simpledb.common.DbException;
import simpledb.transaction.Transaction;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;

import java.io.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    private final int numPage;
    private final ConcurrentHashMap<PageId, LinkedNode>pageStore;
    private final LockManage lockManage;
    // 双向链表
    private class LinkedNode {
//        PageId pageId;
        Page page;
        LinkedNode pre;
        LinkedNode next;
        public LinkedNode(Page page) {
            this.page = page;
        }
    }
    LinkedNode head;            // 头节点
    LinkedNode tail;            // 尾结点

    private void addToHead(LinkedNode node) {
        if (head == null) throw new IllegalArgumentException("head还未初始化！");
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }
    private void remove(LinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void moveToHead(LinkedNode node) {
        remove(node);
        addToHead(node);
    }
    private LinkedNode removeTail() {
        if (tail == null) throw new IllegalArgumentException("tail还未初始化");
        LinkedNode node = tail.pre;
        remove(node);                           // 删除结点，尾指针自动前移，就不用在移动了
        return node;
    }

    /**
     *
     * 锁的内存结果
     * 1. 类型
     * 2. 事务的id
     *
     * mysql中还有
     * 1. 是否在等待isWaiting : true false
     *
     * */
    private class PageLock {
        // 共享锁和排他锁
        private static final int SHARE = 0;
        private static final int EXCLUSIVE = 1;
        private TransactionId tid;
        // 锁的类型，值为上面的两种
        private int type;
        public PageLock(TransactionId tid, int type) {
            this.tid = tid;
            this.type = type;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
    }

    /**
     * 锁管理
     * */
    private class LockManage {
        // 从页面，可以得到锁它的事务
        private final ConcurrentHashMap<PageId, ConcurrentHashMap<TransactionId, PageLock>> lockMap = new ConcurrentHashMap<>();
        // 对于每一个事务，直接得到锁住的页面。解锁的时候，就不用遍历所有的上锁页面了。不用再保存一遍锁了
        private final ConcurrentHashMap<TransactionId, ConcurrentLinkedQueue<PageId>>pageMap = new ConcurrentHashMap<>();
        /**
         * 获取锁
         * @param pid 页面id
         * @param tid 事务id
         * @param lockType 锁类型
         * @return 成功返回true, 否则false
         * */
        public synchronized boolean acquireLock(PageId pid, TransactionId tid, int lockType) {
            // 判断当前页是否当前有锁, 没锁直接返回就行了
            if (!lockMap.containsKey(pid)) {
                // 创建锁
                PageLock pageLock = new PageLock(tid, lockType);
                ConcurrentHashMap<TransactionId, PageLock> pageLocks = new ConcurrentHashMap<>();
                ConcurrentLinkedQueue<PageId> pages = pageMap.getOrDefault(tid, new ConcurrentLinkedQueue<>());
                pageLocks.put(tid, pageLock);
                pages.add(pid);
                lockMap.put(pid, pageLocks);
                pageMap.put(tid, pages);
                return true;
            }
            // 有锁，获取当前锁的等待队列
            ConcurrentHashMap<TransactionId, PageLock> pageLocks = lockMap.get(pid);
            // 这里为什么会有lockMaps中有，但是pageMap中没有的情况呢?
            ConcurrentLinkedQueue<PageId>pages = pageMap.getOrDefault(tid, new ConcurrentLinkedQueue<>());
            // tid 没有 Page 上的锁
            if (!pageLocks.containsKey(tid)) {
                // 多个事务,一定是读锁
                if (pageLocks.size() > 1) {
                    // 申请的锁是共享锁
                    if (lockType == PageLock.SHARE) {
                        // tid 请求锁
                        PageLock pageLock = new PageLock(tid, PageLock.SHARE);
                        pageLocks.put(tid, pageLock);
                        pages.add(pid);
                        lockMap.put(pid, pageLocks);
                        pageMap.put(tid, pages);
                        return true;
                    }
                    // 申请排他锁
                    else if (lockType == PageLock.EXCLUSIVE) {
                        // tid 需要获取写锁，拒绝
                        return false;
                    }
                }
                // 如果只有一个事务，page上可能是读锁，也可能是写锁
                if (pageLocks.size() == 1) {
                    // 获取唯一的锁
                    PageLock curLock = null;
                    for (PageLock lock : pageLocks.values()) {
                        curLock = lock;
                    }
                    // 如果页面上的锁是读锁
                    if (curLock.getType() == PageLock.SHARE) {
                        // 如果请求的锁也是读锁
                        if (lockType == PageLock.SHARE) {
                            // tid 请求锁
                            PageLock pageLock = new PageLock(tid, PageLock.SHARE);
                            pageLocks.put(tid, pageLock);
                            pages.add(pid);
                            lockMap.put(pid, pageLocks);
                            pageMap.put(tid, pages);
                            return true;
                        }
                        // 如果是独占锁
                        else if (lockType == PageLock.EXCLUSIVE) {
                            // tid 需要获取写锁，拒绝
                            return false;
                        }
                    }
                    // 如果是独占锁, 这里再加上一次判断，为了防止以后有新的锁
                    else if (curLock.getType() == PageLock.EXCLUSIVE) {
                        // tid 需要获取写锁，拒绝
                        return false;
                    }
                }
            }
            // 当前事务持有 Page 锁
            else {
//            else if (pageLocks.get(tid) != null) {
                PageLock pageLock = pageLocks.get(tid);
                // 事务上是读锁
                if (pageLock.getType() == PageLock.SHARE) {
                    // 新请求的是读锁
                    if (lockType == PageLock.SHARE) {
                        return true;
                    }
                    // 新请求是写锁
                    else if (lockType == PageLock.EXCLUSIVE) {
                        // 如果该页面上只有一个锁，就是该事务的读锁
                        if (pageLocks.size() == 1) {
                            // 进行锁升级(升级为写锁)
                            pageLock.setType(PageLock.EXCLUSIVE);
                            // 这一个更新其实没有必要，因为上面的pageLock本来就是引用
                            pageLocks.put(tid, pageLock);
                            return true;
                        }
                        // 大于一个锁，说明有其他事务有共享锁
                        else if (pageLocks.size() > 1) {
                            // 不能进行锁的升级
                            return false;
                        }
                    }
                }
                // 事务上是写锁
                // 无论请求的是读锁还是写锁，都可以直接返回获取
                return pageLock.getType() == PageLock.EXCLUSIVE;
            }
            return false;
        }

        /**
         *
         * @param pid 页面
         * @param tid 事务
         *
         * @return 是否释放锁成功，如果事务不持有页面锁，就返回false
         */
        public synchronized boolean releaseLock(PageId pid, TransactionId tid) {
            // 其实，只要保证上面的两个双向hash同步更新，就不会出现，tid不持有pid锁的情况，这个判断可以认为一直是true的。
            if (isHoldLock(tid, pid)) {
                ConcurrentHashMap<TransactionId, PageLock> locks = lockMap.get(pid);
                locks.remove(tid);
                // 页面上没有锁了，移除页面
                if (locks.size() == 0) {
                    // 源代码，在遍历map的时候移除map的元素，会不会有问题？
                    lockMap.remove(pid);
                }
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
        public synchronized void completeTransaltion(TransactionId tid) {
            // 事务完成后，释放事务。这里采用的是，遍历所有已上锁的页面，看事务是否已经锁上了它，如果是，就释放。
            // 可以写双向,事务锁上的页面有哪些。
            for (PageId pageId : pageMap.get(tid)) {
                releaseLock(pageId, tid);
            }
            // 移除已经完成的事务
            pageMap.remove(tid);
        }

    }

    /**
     * Creates a BufferPool that caches up to numPages pages.
     * 创建一个初始化大小为numPages的缓冲池，运行过程中大小不能改变。
     * @param numPages maximum number of pages in this buffer pool.
     */
    public BufferPool(int numPages) {
        // some code goes here
        this.numPage = numPages;
        pageStore = new ConcurrentHashMap<>();
        // 实验3创建双向链表实现LRU
        head = new LinkedNode(null);
        tail = new LinkedNode(null);
        head.next = tail;
        tail.pre = head;
        // 实验4创建新的锁
        this.lockManage = new LockManage();
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
     * 如果已经大于了buffer pool的大小，应该使用lru算法进行页面置换
     *
     * @param tid the ID of the transaction requesting the page
     * @param pid the ID of the requested page
     * @param perm the requested permissions on the page
     */
    public  Page getPage(TransactionId tid, PageId pid, Permissions perm)
        throws TransactionAbortedException, DbException {
        // some code goes here
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
            isAcquired = lockManage.acquireLock(pid, tid, lockType);
            long now = System.currentTimeMillis();
            // 如果超过 500 ms没有获取就抛出异常
            if(now - startTime > 500){
                // 放弃当前事务
                throw new TransactionAbortedException();
            }
        }
        //- ---------------  lab 3 ------------------------
        if (!pageStore.containsKey(pid)) {   // 缓冲池中没有需要从DBFile中进行读取
            DbFile dbFile = Database.getCatalog().getDatabaseFile(pid.getTableId());    // 获取表
            Page page = dbFile.readPage(pid);                                           // 获取表的某一页

            // 缓存满了，直接删除缓存
            if (pageStore.size() >= numPage) {
                evictPage();
            }

            LinkedNode node = new LinkedNode(page);
            addToHead(node);                                                        // 创建新页面之后，需要加入页面链表
//            System.out.println(String.format("页面%d加入缓存", pid.getPageNumber()));
            pageStore.put(pid, node);                                               // 页面存入缓存
        }
        else moveToHead(pageStore.get(pid));                                        // 否则直接移动到头结点
        return pageStore.get(pid).page;                                             // 缓冲池中有直接返回
    }

    /**
     * Releases the lock on a page.
     * Calling this is very risky, and may result in wrong behavior. Think hard
     * about who needs to call this and why, and why they can run the risk of
     * calling it.
     * 直接释放锁，很危险。
     * 因为事务一旦出现了错误。
     *
     * @param tid the ID of the transaction requesting the unlock
     * @param pid the ID of the page to unlock
     */
    public  void unsafeReleasePage(TransactionId tid, PageId pid) {
        // some code goes here
        // not necessary for lab1|lab2
        // lab 4
        lockManage.releaseLock(pid, tid);
    }

    /**
     * Release all locks associated with a given transaction.
     * 对于给的事务，释放所有的锁
     *
     * @param tid the ID of the transaction requesting the unlock
     */
    public void transactionComplete(TransactionId tid) {
        // some code goes here
        // not necessary for lab1|lab2
    }

    /** Return true if the specified transaction has a lock on the specified page
     *  判断一个事务是否获取了页面了锁
     * @param tid : 事务id
     * @param p : 页面的id
     *
     *
     * */
    public boolean holdsLock(TransactionId tid, PageId p) {
        // some code goes here
        // not necessary for lab1|lab2
        return false;
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
    }

    /**
     * Add a tuple to the specified table on behalf of transaction tid.  Will
     * acquire a write lock on the page the tuple is added to and any other
     * pages that are updated (Lock acquisition is not needed for lab2).
     * May block if the lock(s) cannot be acquired.
     * 需要写锁
     * Marks any pages that were dirtied by the operation as dirty by calling
     * their markDirty bit, and adds versions of any pages that have
     * been dirtied to the cache (replacing any existing versions of those pages) so
     * that future requests see up-to-date pages.
     * 标记页面是否是脏页，通过调用markDirty来标记
     * @param tid the transaction adding the tuple
     * @param tableId the table to add the tuple to
     * @param t the tuple to add
     */
    public void insertTuple(TransactionId tid, int tableId, Tuple t)
        throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        // 1. 找到对应的表
        // 2. 插入元组
        // 3. 标记脏页
        // 4. 更新缓存, 将刚操作的页放入到缓存中
        HeapFile heapFile = (HeapFile) Database.getCatalog().getDatabaseFile(tableId);
        List<Page>list = heapFile.insertTuple(tid, t);
        updateBufferPool(list, tid);
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
        // 1. 获取表
        // 2. 删除元组
        // 3. 更新缓存，将删除的页面从缓存中删除
        int tableId = t.getRecordId().getPageId().getTableId();
        HeapFile file = (HeapFile) Database.getCatalog().getDatabaseFile(tableId);
        updateBufferPool(file.deleteTuple(tid, t), tid);
    }

    private void updateBufferPool(List<Page>pagelist, TransactionId tid) throws DbException{
        for (Page page : pagelist) {
            page.markDirty(true, tid);
            // 如果缓存池已经满了，执行淘汰策略, 注意如果是脏页，需要写回
            if (pageStore.size() > numPage) {
                evictPage();
            }
            // 此时的页面一定在缓存，因为刚才被修改的时候已经放入缓存了, 更新缓存就行了
//            System.out.println(String.format("更新缓存中的页面%d", page.getId().getPageNumber()));
            // 测试的时候，页面不放入buffer pool 中，直接创建一个新的页面
            if (!pageStore.containsKey(page.getId())) {
                try {
//                    System.out.println(String.format("页面%d不存在", page.getId().getPageNumber()));
                    page = getPage(tid, page.getId(), Permissions.READ_WRITE);
                } catch (TransactionAbortedException e) {
                    e.printStackTrace();
                }
            }
            LinkedNode node = pageStore.get(page.getId());
            node.page = page;
            pageStore.put(page.getId(), node);
        }
    }

    /**
     * Flush all dirty pages to disk.
     * NB: Be careful using this routine -- it writes dirty data to disk so will
     *     break simpledb if running in NO STEAL mode.
     *    //
     */
    public synchronized void flushAllPages() throws IOException {
        // some code goes here
        // not necessary for lab1
        for (PageId pageId: pageStore.keySet()) {
            flushPage(pageId);
        }

    }

    /** Remove the specific page id from the buffer pool.
        Needed by the recovery manager to ensure that the
        buffer pool doesn't keep a rolled back page in its
        cache.
     淘汰页面, 从链表中去掉，然后从缓存中删除。
     应该看一下是否是脏页，如果是应该


        Also used by B+ tree files to ensure that deleted pages
        are removed from the cache so they can be reused safely
    */
    public synchronized void discardPage(PageId pid) {
        // some code goes here
        // not necessary for lab1
        remove(pageStore.get(pid));
        pageStore.remove(pid);
    }

    /**
     * Flushes a certain page to disk
     * @param pid an ID indicating the page to flush
     */
    private synchronized  void flushPage(PageId pid) throws IOException {
        // some code goes here
        // not necessary for lab1
        // 1.如果是脏页，刷盘
        // 2.脏页标记制空
        Page page = pageStore.get(pid).page;
        if (page.isDirty() != null) {
            Database.getCatalog().getDatabaseFile(pid.getTableId()).writePage(page);
            page.markDirty(false, null);            // 事务标记制空
        }
    }

    /** Write all pages of the specified transaction to disk.
     */
    public synchronized  void flushPages(TransactionId tid) throws IOException {
        // some code goes here
        // not necessary for lab1|lab2
    }

    /**
     * Discards a page from the buffer pool.
     * Flushes the page to disk to ensure dirty pages are updated on disk.
     * 保证脏页被刷新到磁盘
     * 1. 从链表中删除，得到待淘汰的页面
     * 2. 从pageStore中删除
     * 3. 刷新链表
     */
    private synchronized  void evictPage() throws DbException {
        LinkedNode node = removeTail();             // 得到待淘汰的页面
        try {
            flushPage(node.page.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        pageStore.remove(node.page.getId());
    }

}
