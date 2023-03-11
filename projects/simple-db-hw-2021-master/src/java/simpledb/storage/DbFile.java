
package simpledb.storage;

import simpledb.common.DbException;
import simpledb.common.Catalog;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;

import java.util.*;
import java.io.*;

/**
 * The interface for database files on disk. Each table is represented by a
 * single DbFile. DbFiles can fetch pages and iterate through tuples. Each
 * file has a unique id used to store metadata about the table in the Catalog.
 * DbFiles are generally accessed through the buffer pool, rather than directly
 * by operators.
 * 数据库存储在磁盘上的文件。一张表对应着一个DBFile
 * DBFile可以获取页和tuple的迭代器。
 * 每一个文件有位唯一的id，存储在catalog中
 * DBFile通常首先从缓存池中获取，如果没有再从磁盘读取。
 *
 */
public interface DbFile {
    /**
     * Read the specified page from disk.
     * 从磁盘中读取一页
     * @throws IllegalArgumentException if the page does not exist in this file.
     */
    Page readPage(PageId id);

    /**
     * Push the specified page to disk.
     * p 是需要写的页，通过id中的pageno来决定需要插入的页的偏移量。
     * IO错误，如果写失败的话
     * @param p The page to write.  page.getId().pageno() specifies the offset into the file where the page should be written.
     * @throws IOException if the write fails
     *
     */
    void writePage(Page p) throws IOException;

    /**
     * Inserts the specified tuple to the file on behalf of transaction.
     * This method will acquire a lock on the affected pages of the file, and
     * may block until the lock can be acquired.
     *
     * @param tid The transaction performing the update
     * @param t The tuple to add.  This tuple should be updated to reflect that
     *          it is now stored in this file.
     * @return An ArrayList contain the pages that were modified
     * @throws DbException if the tuple cannot be added
     * @throws IOException if the needed file can't be read/written
     */
    List<Page> insertTuple(TransactionId tid, Tuple t)
        throws DbException, IOException, TransactionAbortedException;

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
     *   of the file
     */
    List<Page> deleteTuple(TransactionId tid, Tuple t)
        throws DbException, IOException, TransactionAbortedException;

    /**
     * Returns an iterator over all the tuples stored in this DbFile. The
     * iterator must use {@link BufferPool#getPage}, rather than
     * {@link #readPage} to iterate through the pages.
     *
     * @return an iterator over all the tuples stored in this DbFile.
     */
    DbFileIterator iterator(TransactionId tid);

    /**
     * Returns a unique ID used to identify this DbFile in the Catalog. This id
     * can be used to look up the table via {@link Catalog#getDatabaseFile} and
     * {@link Catalog#getTupleDesc}.
     * <p>
     * Implementation note:  you will need to generate this tableid somewhere,
     * ensure that each HeapFile has a "unique id," and that you always
     * return the same value for a particular HeapFile. A simple implementation
     * is to use the hash code of the absolute path of the file underlying
     * the HeapFile, i.e. <code>f.getAbsoluteFile().hashCode()</code>.
     *
     * @return an ID uniquely identifying this HeapFile.
     */
    int getId();
    
    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * @return TupleDesc of this DbFile.
     */
    TupleDesc getTupleDesc();
}
