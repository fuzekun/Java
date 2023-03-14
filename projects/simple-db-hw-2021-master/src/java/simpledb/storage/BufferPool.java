package simpledb.storage;

import simpledb.common.Database;
import simpledb.common.Permissions;
import simpledb.common.DbException;
import simpledb.transaction.LockManager;
import simpledb.transaction.PageLock;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;

import java.io.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

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
            // 如果超过 500 ms没有获取就抛出异常, 防止死锁问题的
            if(now - startTime > 500){
                // 获取id最小的事务
                long minId = lockManager.acquireMinTid();
                // 如果当前事务是最小id的事务，不放锁
                if (tid.getId() == minId) {
                    continue;
                }
                // 事务失败，回滚，放锁。
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
            // 刷盘
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
        // 释放锁
        lockManager.completeTransaction(tid);
    }
    public synchronized void restorePages(TransactionId tid){

        // 在这之前会进行事务回滚，已经实现并且被调用了。
        // --------------- lab 4 ----------------------
        // 如果tid没有页面
        if (lockManager.getAllPages(tid) == null) return ;
        // 遍历当前事务修改的页面，看是否在缓存中
        for (PageId pid : lockManager.getAllPages(tid).keySet()) {
            LinkedNode node = pageStore.get(pid);
            // 事务持有的页面在内存中，并且脏页的tid就是自己
            if (node != null && tid.equals(node.page.isDirty())) {
                // 重新读取，覆盖page
                Page pageInDisk = Database.getCatalog().getDatabaseFile(pid.getTableId()).readPage(pid);
                node.page = pageInDisk;
                // 为了安全可以写，但是没必要。更新node
                pageStore.put(pid, node);
                // lru策略
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
        DbFile file =  Database.getCatalog().getDatabaseFile(tableId);
        // 将页面刷新到缓存中
        updateBufferPoll(file.insertTuple(tid, t), tid);
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
        DbFile file = Database.getCatalog().getDatabaseFile(t.getRecordId().getPageId().getTableId());
        // 将页面刷新到缓存中
        updateBufferPoll(file.deleteTuple(tid, t), tid);
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
            // 页面重新写入内存
            LinkedNode node = pageStore.get(page.getId());
            // 这里不知道为什么插入的时候页面可能不在内存中
            if (node == null) {
                if (pageStore.size() == numPages) {
                    evictPage();
                }
                // 创建新结点
                node = new LinkedNode(page.getId(), page);
                // 重新放入内存
                pageStore.put(page.getId(), node);
                // 插入头节点
                addToHead(node);
            } else {
                // 更新内存
                node.page = page;
                // 移动到头部
                addToHead(node);
            }
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
        if (!pageStore.containsKey(pid)) return ;
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
        // 如果不在内存中，直接不用刷新了
        if (pageStore.get(pid) == null) return ;
        Page page = pageStore.get(pid).page;
        // 如果是是脏页, 淘汰前需要刷盘
        if(page.isDirty() != null){
            DbFile file = Database.getCatalog().getDatabaseFile(pid.getTableId());
            // ---------------- lab6 -------------------------
            // 刷盘前将脏页保存下来，可以为了redo和undo日志。
            Database.getLogFile().logWrite(page.isDirty(), page.getBeforeImage(), page);
            Database.getLogFile().force();
            //--------------- lab2 ------------------------
            // 刷盘
            file.writePage(page);
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
            if (node != null) {
                //-------------- lab 6 ---------------
                // 事务的所有页面都需要保存镜像
                node.page.setBeforeImage();
//            if (node == null) System.out.println(tid.getId() + "不存在" + pid.getTableId() + " " + pid.getPageNumber());
                // 如果页面没有被换出内存，并且是脏页的情况下才需要刷盘
                if (tid.equals(node.page.isDirty())) {
                    // ---------------- lab 2 --------------------
                    flushPage(pid);
                }
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
        // ------------ lab 4 no-steal ---------------------
        for (int i = 0; i < numPages; i++) {
            // 淘汰尾部节点
            LinkedNode node = removeTail();
            Page evictPage = node.page;
            // 如果当前有事务持有，也就是脏页，需要跳过
            if(evictPage.isDirty() != null && false){
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

