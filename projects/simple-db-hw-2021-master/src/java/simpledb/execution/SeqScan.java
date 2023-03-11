package simpledb.execution;

import simpledb.common.Database;
import simpledb.storage.*;
import simpledb.transaction.Transaction;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;
import simpledb.common.Type;
import simpledb.common.DbException;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * SeqScan is an implementation of a sequential scan access method that reads
 * each tuple of a table in no particular order (e.g., as they are laid out on
 * disk).
 */
public class SeqScan implements OpIterator {

    private static final long serialVersionUID = 1L;
    private final TransactionId tid;                //一个操作对应一个事务，一个事务可以操作多张表
    private String tablAlias;
    private int tableid;
    private DbFileIterator iterator;

    /**
     * Creates a sequential scan over the specified table as a part of the
     * specified transaction.
     *
     * @param tid
     *            The transaction this scan is running as a part of.
     * @param tableid
     *            the table to scan.
     * @param tableAlias
     *            the alias of this table (needed by the parser); the returned
     *            tupleDesc should have fields with name tableAlias.fieldName
     *            (note: this class is not responsible for handling a case where
     *            tableAlias or fieldName are null. It shouldn't crash if they
     *            are, but the resulting name can be null.fieldName,
     *            tableAlias.null, or null.null).
     */
    public SeqScan(TransactionId tid, int tableid, String tableAlias) {
        // some code goes here
        this.tid = tid;
        this.tablAlias = tableAlias;
        this.tableid = tableid;
    }

    /**
     * @return
     *       return the table name of the table the operator scans. This should
     *       be the actual name of the table in the catalog of the database
     *       获取表的方法：
     *
     * */
    public String getTableName() {
        return Database.getCatalog().getTableName(this.tableid);
    }

    /**
     * @return Return the alias of the table this operator scans.
     * */
    public String getAlias()
    {
        // some code goes here
        return this.tablAlias;
    }

    /**
     * Reset the tableid, and tableAlias of this operator.
     * @param tableid
     *            the table to scan.
     * @param tableAlias
     *            the alias of this table (needed by the parser); the returned
     *            tupleDesc should have fields with name tableAlias.fieldName
     *            (note: this class is not responsible for handling a case where
     *            tableAlias or fieldName are null. It shouldn't crash if they
     *            are, but the resulting name can be null.fieldName,
     *            tableAlias.null, or null.null).
     */
    public void reset(int tableid, String tableAlias) {
        // some code goes here
        this.tableid = tableid;
        this.tablAlias = tableAlias;

    }

    public SeqScan(TransactionId tid, int tableId) {
        this(tid, tableId, Database.getCatalog().getTableName(tableId));
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
        // 打开表，就是初始化迭代器。
        DbFile dbFile = Database.getCatalog().getDatabaseFile(this.tableid);
        this.iterator = dbFile.iterator(this.tid);
        iterator.open();                                    // 别忘了打开迭代器
    }

    /**
     * Returns the TupleDesc with field names from the underlying HeapFile,
     * prefixed with the tableAlias string from the constructor. This prefix
     * becomes useful when joining tables containing a field(s) with the same
     * name.  The alias and name should be separated with a "." character
     * (e.g., "alias.fieldName").
     *
     * @return the TupleDesc with field names from the underlying HeapFile,
     *         prefixed with the tableAlias string from the constructor.
     *         获取tupleDesc, 这里的tupleDesc需要使用表的别名作为前缀进行包装
     *         tupleDesc从HeapFile中进行获取
     *
     */
    private String getItemName(String name) {
        String prefix = tablAlias == null ? "null" : tablAlias;         // 如果没有别名
        return  prefix + "." + name;
    }
    public TupleDesc getTupleDesc() {
        // some code goes here
        TupleDesc tupleDesc = Database.getCatalog().getTupleDesc(this.tableid);
        Iterator<TupleDesc.TDItem>it = tupleDesc.iterator();
        int n = tupleDesc.numFields();
//        System.out.println(String.format("域的个数%d\n", n));
        TupleDesc.TDItem[] items = new TupleDesc.TDItem[n];
        for (int i = 0; i < n; i++) {
            items[i] = new TupleDesc.TDItem(tupleDesc.getFieldType(i), this.getItemName(tupleDesc.getFieldName(i)));
        }
        return new TupleDesc(items);
    }

    public boolean hasNext() throws TransactionAbortedException, DbException {
        // some code goes here
        if (iterator == null) return false;                             // 如果没有打开
        return iterator.hasNext();
    }

    public Tuple next() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        // some code goes here
        if (iterator == null || !iterator.hasNext())
            throw new NoSuchElementException("数据库中没有该元素!");
        return iterator.next();
    }

    public void close() {
        // some code goes here
        iterator.close();
    }

    public void rewind() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        // some code goes here
        iterator.rewind();
    }
}
