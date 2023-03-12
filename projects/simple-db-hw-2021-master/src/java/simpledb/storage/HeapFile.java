package simpledb.storage;

import simpledb.common.Database;
import simpledb.common.DbException;
import simpledb.common.Debug;
import simpledb.common.Permissions;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples
 * in no particular order. Tuples are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 *
 * @see HeapPage#HeapPage
 * @author Sam Madden
 *
 * DBFile的实现
 * HeapFile和HeapPage的工作原理相似。
 *
 */
public class HeapFile implements DbFile {


    /**
     * Constructs a heap file backed by the specified file.
     *
     * @param f
     *            the file that stores the on-disk backing store for this heap
     *            file.
     */
    private final File file;                                           // 定义成常量，数据库文件
    private final TupleDesc tupleDesc;                                // 表的属性描述

    public HeapFile(File f, TupleDesc td) {
        // some code goes here
        this.file = f;
        this.tupleDesc = td;
    }

    /**
     * Returns the File backing this HeapFile on disk.
     * 返回磁盘上的一张表
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        // some code goes here

        return this.file;
    }

    /**
     * Returns an ID uniquely identifying this HeapFile. Implementation note:
     * you will need to generate this tableid somewhere to ensure that each
     * HeapFile has a "unique id," and that you always return the same value for
     * a particular HeapFile. We suggest hashing the absolute file name of the
     * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
     * 返回唯一性id
     * 我们见以使用文件绝对路径的hash值作文唯一性id
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId() {
        // some code goes here
        return file.getAbsolutePath().hashCode();              // 绝对路径是一个字符串，由这个字符串的hash值代表该对象的id
//        throw new UnsupportedOperationException("implement this");
    }

    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     *
     * 返回表的描述
     *
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
        return this.tupleDesc;
//        throw new UnsupportedOperationException("implement this");
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        // some code goes here
//        Page page = new HeapPage();
        int tableId = pid.getTableId();
        int pgNo = pid.getPageNumber();

        try (RandomAccessFile f = new RandomAccessFile(file, "r")) {         // 随机读取
            // 判断是否越界
//            System.out.println(String.format("文件的大小:%d, 第 %d 页, 结尾的字节数 %d", file.length() ,pgNo, (pgNo + 1) * BufferPool.getPageSize()));
            if ((pgNo + 1) * BufferPool.getPageSize() > file.length()) {            // 必须整页，不满不让读。
                f.close();
                throw new IllegalArgumentException(String.format("表 %d 页 %d 不存在", tableId, pgNo));
            }
            byte[] bytes = new byte[BufferPool.getPageSize()];
            f.seek(pgNo * BufferPool.getPageSize());                        // 获取页的初始位置
            int read = f.read(bytes, 0, BufferPool.getPageSize());          // 从文件中读取一页，返回读取到的字节数目
            // 如果读取到的不满足一页，那么就不能读取了吗？最后一页应该也是可以读取的
            if (read != BufferPool.getPageSize()) {
                throw new IllegalArgumentException(String.format("表 %d 页 %d 不存在", tableId, pgNo));
            }
            return new HeapPage(new HeapPageId(pid.getTableId(), pid.getPageNumber()), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(String.format("表 %d 页 %d 不存在", tableId, pgNo));
    }

    /**
     * Push the specified page to disk.
     * p 是需要写的页，通过id中的pageno来决定需要插入的页的偏移量。
     * IO错误，如果写失败的话
     * @param page The page to write.  page.getId().pageno() specifies the offset into the file where the page should be written.
     * @throws IOException if the write fails
     *
     */
    public void writePage(Page page) throws IOException {
        // 1. 获取页面的偏移量，判断是否越界
        // 2. 通过随机读写文件，来读写
        int x = numPages();
        int pgNo = page.getId().getPageNumber();
        if (pgNo > numPages()) throw new IllegalArgumentException("页面no不应该超过总页数");
        try (RandomAccessFile f = new RandomAccessFile(file, "rw")) {         // 随机读取
            // 判断是否越界
//            System.out.println(String.format("文件的大小:%d, 第 %d 页, 结尾的字节数 %d", file.length() ,pgNo, (pgNo + 1) * BufferPool.getPageSize()));
            byte[] bytes = page.getPageData();
            f.seek(pgNo * BufferPool.getPageSize());                        // 获取页的初始位置
            f.write(bytes);                     // 从初始位置开始写, 写一个page的大小、
            // 如果写的不满足一页
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the number of pages in this HeapFile.
     *
     * 返回表中页的数量
     *
     * 计算表的大小，
     *
     */
    public int numPages() {
        // some code goes here
        // 返回小于等于的最大值。ceil:返回大于等于的最小值
        return (int)file.length() / BufferPool.getPageSize();
    }

    // see DbFile.java for javadocs
    /**
     * Inserts the specified tuple to the file on behalf of transaction.
     * This method will acquire a lock on the affected pages of the file, and
     * may block until the lock can be acquired.
     * 需要加上锁，直到事务完成。
     *
     * @param tid The transaction performing the update
     * @param t The tuple to add.  This tuple should be updated to reflect that
     *          it is now stored in this file. 待插入的tuple
     * @return An ArrayList contain the pages that were modified 返回被更新的页面。
     * @throws DbException if the tuple cannot be added 如果插入失败
     * @throws IOException if the needed file can't be read/written 如果文件无法读写
     *
     * 不用立刻写文件，只打上脏页的标记
     */
    // see DbFile.java for javadocs

    public List<Page> insertTuple(TransactionId tid, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        List<Page> list = new ArrayList<>();
        // 1. 如果有空闲的页面可以插入元组
//        System.out.println(String.format("页面%d插入元组", t.getRecordId().getPageId().getPageNumber()));
        for (int pageNo = 0; pageNo < numPages(); pageNo++) {
            // 查询页
            HeapPageId pageId = new HeapPageId(getId(), pageNo);        // 表 + 页确定一个id
            // 使用读写方式获取一页， 如果没有需要读入缓冲池
            HeapPage page = (HeapPage) Database.getBufferPool().getPage(tid, pageId, Permissions.READ_WRITE);
            // 查看当前的页是否有空闲
            if (page.getNumEmptySlots() != 0) {
                page.insertTuple(t);
                list.add(page);
                return list;
            }
            //----------------- lab 4 ------------------------
//             当该 page 上没有空闲空 slot 的时候，提前释放该 page 上的锁，节约释放锁的时间
            else{
                Database.getBufferPool().unsafeReleasePage(tid, pageId);
            }
        }
        // 2. 如果都已经写满了，创建新的页面，追加方式写入页面
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, true));
        // 创建一个空页面， 插入文件
        byte[] emptyPage = HeapPage.createEmptyPageData();
        out.write(emptyPage);
        out.close();

        // 获取最后一个页面，刚刚插入的页面
        HeapPageId pageId = new HeapPageId(getId(), numPages() - 1);
        HeapPage page = (HeapPage)Database.getBufferPool().getPage(tid, pageId, Permissions.READ_WRITE);
        page.insertTuple(t);
        list.add(page);
        return list;
    }

    /**
     * Removes the specified tuple from the file on behalf of the specified
     * transaction.
     * This method will acquire a lock on the affected pages of the file, and
     * may block until the lock can be acquired.
     *
     * @param tid The transaction performing the update
     * @param t The tuple to delete.  This tuple should be updated to reflect that
     *          it is no longer stored on any page.
     * @return An ArrayList contain the pages that were modified
     * @throws DbException if the tuple cannot be deleted or is not a member
     *   of the file 如果不包含元组或者元组删除失败
     */
    public List<Page> deleteTuple(TransactionId tid, Tuple t) throws DbException, TransactionAbortedException{
//        System.out.println(String.format("页面%d删除元组", t.getRecordId().getPageId().getPageNumber()));
        // 找到对应的页
        PageId pid = t.getRecordId().getPageId();
        HeapPage page = (HeapPage) Database.getBufferPool().getPage(tid, pid, Permissions.READ_WRITE);
        // 从页中进行删除
        List<Page>list = new ArrayList<>();
        page.deleteTuple(t);
        list.add(page);
        // 更新到写入到文件中
        return list;
    }

    // see DbFile.java for javadocs
    public DbFileIterator iterator(TransactionId tid) {
        // some code goes here
        return new HeapFileIterator(this, tid);
    }

    private static final class HeapFileIterator implements DbFileIterator {

        private final HeapFile heapFile;                    // 需要读取的文件
        private final TransactionId tid;                    // 事务的id
        // 元组迭代器
        private Iterator<Tuple> iterator;
        private int curPageNo;

        public HeapFileIterator(HeapFile heapFile, TransactionId tid) {
            this.heapFile = heapFile;
            this.tid = tid;
        }

        @Override
        public void open() throws DbException, TransactionAbortedException {
            // 获取第一页的全部元组
            curPageNo = 0;
            iterator = getPageTuple(curPageNo);     // 如果迭代器未空，说明是空页面
        }

        private Iterator<Tuple> getPageTuple(int pageNumber) throws TransactionAbortedException, DbException{
            if (pageNumber >= 0 && pageNumber < heapFile.numPages()) {
                HeapPageId pid = new HeapPageId(heapFile.getId(), pageNumber);
                // 这里的页面从缓冲池中获取，缓冲池中的逻辑是，如果没有
                HeapPage page = (HeapPage)Database.getBufferPool().getPage(tid, pid, Permissions.READ_ONLY);
                return page.iterator();
            }
            throw new DbException(String.format("对文件 %d 不包含 页 %", pageNumber, heapFile.getId()));
        }


        @Override
        public boolean hasNext() throws DbException, TransactionAbortedException {
            // 1. 如果本页中还有
            if (iterator == null) {                 // 没打开的时候返回错误
                return false;
            }
            if (!iterator.hasNext()) {                                   // 如果本页已经读取完成了, 获取下一页
                while (curPageNo + 1 < heapFile.numPages()) {           // 页面大小固定，所以有的页面是不满的
                    curPageNo++;
//                    System.out.println(String.format("当前页面是%d\n", curPageNo));
                    iterator = getPageTuple(curPageNo);
                    if (iterator.hasNext()) return true;              // 如果还有
                }
                return false;                                           // 没有页有了
            }
            return true;                                                // 本页中还有
        }

        @Override
        public Tuple next() throws DbException, TransactionAbortedException, NoSuchElementException {
            // 如果没有元组了，抛出异常
            if(iterator == null || !iterator.hasNext()){
                throw new NoSuchElementException();
            }
            // 返回下一个元组
            return iterator.next();
        }

        @Override
        public void rewind() throws DbException, TransactionAbortedException {
            // 清除上一个迭代器
            close();
            // 重新开始
            open();
        }

        @Override
        public void close() {
            iterator = null;
        }
    }


}

