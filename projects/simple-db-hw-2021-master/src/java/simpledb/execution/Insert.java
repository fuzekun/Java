package simpledb.execution;

import simpledb.common.Database;
import simpledb.common.DbException;
import simpledb.common.Type;
import simpledb.storage.BufferPool;
import simpledb.storage.IntField;
import simpledb.storage.Tuple;
import simpledb.storage.TupleDesc;
import simpledb.transaction.TransactionAbortedException;
import simpledb.transaction.TransactionId;

import java.io.IOException;

/**
 * Inserts tuples read from the child operator into the tableId specified in the
 * constructor
 *
 * 将从子操作中读取的元组曹汝到构造器给定的表中
 */
public class Insert extends Operator {

    private static final long serialVersionUID = 1L;

    private final int tableId;
    private OpIterator child;
    private final TransactionId tid;
    private final TupleDesc ansDesc;
    private boolean inserted;
    /**
     * Constructor.
     *
     * @param tid
     *            The transaction running the insert.
     * @param child
     *            The child operator from which to read tuples to be inserted.
     * @param tableId
     *            The table in which to insert tuples.
     * @throws DbException
     *             if TupleDesc of child differs from table into which we are to
     *             insert.
     *
     */
    public Insert(TransactionId tid, OpIterator child, int tableId)
            throws DbException {
        // some code goes here
        if(!child.getTupleDesc().equals(Database.getCatalog().getDatabaseFile(tableId).getTupleDesc())){
            throw new DbException("插入的类型错误");
        }
        this.tid = tid;
        this.child = child;
        this.tableId = tableId;
        this.ansDesc = new TupleDesc(new Type[]{Type.INT_TYPE}, new String[]{"插入的行数"});
        this.inserted = false;
    }

    public TupleDesc getTupleDesc() {
        // some code goes here
        return this.ansDesc;

    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
        super.open();
        child.open();
    }

    public void close() {
        // some code goes here
        child.close();
        super.close();
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
        child.rewind();
        this.inserted = false;
    }

    /**
     * Inserts tuples read from child into the tableId specified by the
     * constructor. It returns a one field tuple containing the number of
     * inserted records. Inserts should be passed through BufferPool. An
     * instances of BufferPool is available via Database.getBufferPool(). Note
     * that insert DOES NOT need check to see if a particular tuple is a
     * duplicate before inserting it.
     * 插入不用检查元组是否已经插入过了。
     * 插入的元组应该放入到缓冲池中。
     *
     *
     *返回一个一个域的tuple，包含了插入数据的数量，或则返回null，如果该方法被调用了多次
     * @return A 1-field tuple containing the number of inserted records, or
     *         null if called more than once.
     * @see Database#getBufferPool
     * @see BufferPool#insertTuple
     */
    protected Tuple fetchNext() throws TransactionAbortedException, DbException {
        // some code goes here
        int cnt = 0;
//        System.out.println("调用fetchNext");
//        if (!inserted) {
            while (child.hasNext()) {
                Tuple t = child.next();
                try {
                    Database.getBufferPool().insertTuple(tid, tableId, t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cnt++;
            }
            Tuple tuple = new Tuple(ansDesc);
            tuple.setField(0, new IntField(cnt));
            inserted = true;
            return tuple;
//        }
//        return null;
    }

    @Override
    public OpIterator[] getChildren() {
        // some code goes here
        return new OpIterator[]{this.child};
    }

    @Override
    public void setChildren(OpIterator[] children) {
        // some code goes here
        this.child = children[0];
    }
}
