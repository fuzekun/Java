package simpledb.execution;

import simpledb.transaction.TransactionAbortedException;
import simpledb.common.DbException;
import simpledb.storage.Tuple;
import simpledb.storage.TupleDesc;

import java.util.*;

/**
 * Filter is an operator that implements a relational select.
 */
public class Filter extends Operator {

    private static final long serialVersionUID = 1L;
    private final Predicate p;                                    // 一个过滤器只能有一种操作吗?
    private OpIterator child;
    /**
     * Constructor accepts a predicate to apply and a child operator to read
     * tuples to filter from.
     *
     * @param p
     *            The predicate to filter tuples with
     * @param child
     *            The child operator
     *
     *            child告诉你从哪张表的哪个位置,开始进行操作。
     *            为什么迭代器不是final的？
     *
     *            如果需要过滤：
     *            1. 给一张表的入口。可以知道从哪张表中的哪个地方进行遍历。
     *            2. 给条件predicate
     */
    public Filter(Predicate p, OpIterator child) {
        // some code goes here
        this.p = p;
        this.child = child;

    }

    public Predicate getPredicate() {
        // some code goes here
        return this.p;
    }

    public TupleDesc getTupleDesc() {
        // some code goes here
        return child.getTupleDesc();
    }

    public void open() throws DbException, NoSuchElementException,
            TransactionAbortedException {
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

    }

    /**
     * AbstractDbIterator.readNext implementation. Iterates over tuples from the
     * child operator, applying the predicate to them and returning those that
     * pass the predicate (i.e. for which the Predicate.filter() returns true.)
     *
     * @return The next tuple that passes the filter, or null if there are no
     *         more tuples
     * @see Predicate#filter
     */
    protected Tuple fetchNext() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        // some code goes here
        while(child.hasNext()) {
            Tuple tuple = child.next();
            if (this.p.filter(tuple))                           // 通过过滤器看看是否满足条件
                return tuple;
        }
        return null;
    }

    @Override
    public OpIterator[] getChildren() {
        // some code goes here
        // 获取表的入口。
        return new OpIterator[]{this.child};
    }

    @Override
    public void setChildren(OpIterator[] children) {
        // some code goes here
        // 修改表的入口
        this.child = children[0];
    }

}
