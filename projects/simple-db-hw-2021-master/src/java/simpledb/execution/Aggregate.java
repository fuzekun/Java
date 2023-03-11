package simpledb.execution;

import simpledb.common.DbException;
import simpledb.common.Type;
import simpledb.storage.Field;
import simpledb.storage.Tuple;
import simpledb.storage.TupleDesc;
import simpledb.transaction.TransactionAbortedException;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;

import static simpledb.execution.Aggregator.NO_GROUPING;


/**
 * The Aggregation operator that computes an aggregate (e.g., sum, avg, max,
 * min). Note that we only support aggregates over a single column, grouped by a
 * single column.
 */
public class Aggregate extends Operator {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * <p>
     * Implementation hint: depending on the type of a field, you will want to
     * construct an {@link IntegerAggregator} or {@link StringAggregator} to help
     * you with your implementation of readNext().
     * 根据域的类型，需要实现两种类型的Aggregator，从而实现readNext()方法
     * @param child  The OpIterator that is feeding us tuples. 迭代器，表入口
     * @param afield The column over which we are computing an aggregate. 选择的域
     * @param gfield The column over which we are grouping the result, or -1 if
     *               there is no grouping                               group by的域
     * @param aop    The aggregation operator to use  具体使用的aggregator， max, min, avg, sum, count
     */
    private OpIterator child;
    private final int afield;
    private final int gfield;
    private final Aggregator.Op aop;
    private final Aggregator aggregator;                    // 聚合器
//    private final TupleDesc desc;                           // 所有的tuple公用一份tupleDes就行了，没必要重新创建
    private OpIterator opIterator;                          // 聚合结果迭代器
    // 这里可以使用工厂模式进行改写
    public Aggregate(OpIterator child, int afield, int gfield, Aggregator.Op aop) {
        // some code goes here
        this.child = child;
        this.afield = afield;
        this.gfield = gfield;
        this.aop = aop;
        Type gfieldtype;
        if (gfield == NO_GROUPING) gfieldtype = null;
        else gfieldtype = this.child.getTupleDesc().getFieldType(this.gfield);
        if (this.child.getTupleDesc().getFieldType(afield).equals(Type.STRING_TYPE))
            this.aggregator = new StringAggregator(gfield, this.child.getTupleDesc().getFieldType(this.gfield), afield, aop);
        else this.aggregator = new IntegerAggregator(gfield, gfieldtype, afield, aop);
//        desc = this.getTupleDesc();
    }

    /**
     * @return If this aggregate is accompanied by a groupby, return the groupby
     * field index in the <b>INPUT</b> tuples. If not, return
     * {@link Aggregator#NO_GROUPING}
     *
     * 如果没有返回-1，如果有返回
     */
    public int groupField() {
        // some code goes here
        return this.gfield;
    }

    /**
     * @return If this aggregate is accompanied by a group by, return the name
     * of the groupby field in the <b>OUTPUT</b> tuples. If not, return
     * null;
     * 如果有gfield返回名称，否则，返回空
     */
    public String groupFieldName() {
        // some code goes here
        if (this.gfield == -1) return null;
        return child.getTupleDesc().getFieldName(this.gfield);
    }

    /**
     * @return the aggregate field
     */
    public int aggregateField() {
        // some code goes here
        return this.afield;
    }

    /**
     * @return return the name of the aggregate field in the <b>OUTPUT</b>
     * tuples
     */
    public String aggregateFieldName() {
        // some code goes here
        return child.getTupleDesc().getFieldName(this.afield);
    }

    /**
     * @return return the aggregate operator
     */
    public Aggregator.Op aggregateOp() {
        // some code goes here
        return this.aop;
    }

    public static String nameOfAggregatorOp(Aggregator.Op aop) {
        return aop.toString();
    }

    public void open() throws NoSuchElementException, DbException,
            TransactionAbortedException {
        // some code goes here
        child.open();
        // 计算聚合结果
        while (child.hasNext()) {
            this.aggregator.mergeTupleIntoGroup(child.next());
        }
        this.opIterator = aggregator.iterator();
        this.opIterator.open();                         // 这个没有调用，导致出现了空指针错误
//        if (this.opIterator == null) throw new NoSuchElementException("聚合结果为空");
        super.open();
    }

    /**
     * Returns the next tuple. If there is a group by field, then the first
     * field is the field by which we are grouping, and the second field is the
     * result of computing the aggregate. If there is no group by field, then
     * the result tuple should contain one field representing the result of the
     * aggregate. Should return null if there are no more tuples.
     *
     * 返回的第一个域是group的域
     * 第二个域是聚集函数得出的结果
     * 如果没有group
     * 那么结果应该包含代表聚集函数的域
     * 如果没有元组了，返回Null
     */
    protected Tuple fetchNext() throws TransactionAbortedException, DbException {
        // some code goes here
        if (this.opIterator == null || !this.opIterator.hasNext()) return null;
        return this.opIterator.next();

    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
//        this.close();
//        this.open();      // 不用重新计算结果
        child.rewind();    // 如果children一旦换了，那么结果就会出错。因为使用的是原来的结果。
        opIterator.rewind();
    }

    /**
     * Returns the TupleDesc of this Aggregate. If there is no group by field,
     * this will have one field - the aggregate column. If there is a group by
     * field, the first field will be the group by field, and the second will be
     * the aggregate value column.
     * <p>
     * The name of an aggregate column should be informative. For example:
     * "aggName(aop) (child_td.getFieldName(afield))" where aop and afield are
     * given in the constructor, and child_td is the TupleDesc of the child
     * iterator.
     * 假设聚集函数得到的类型一定是int类型
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
//        String afiledName = this.aop.name() + "("  + this.aggregateFieldName() + ")";       // max(id);
//        Type aType = this.child.getTupleDesc().getFieldType(this.afield);
//        if (this.groupFieldName() != null) {
//            String gfiledName = this.groupFieldName();
//            Type gType = this.child.getTupleDesc().getFieldType(this.gfield);
//            return new TupleDesc(new Type[]{gType, aType},new String[]{gfiledName, afiledName});
//        }
//        else {
//            return new TupleDesc(new Type[]{aType}, new String[]{afiledName});
//        }
        List<Type>types = new ArrayList<>();
        List<String>names = new ArrayList<>();
        if (groupFieldName() != null) {
            types.add(this.child.getTupleDesc().getFieldType(this.gfield));
            names.add(groupFieldName());
        }
        types.add(this.child.getTupleDesc().getFieldType(this.afield));
        names.add(this.aop.name() + "(" + aggregateFieldName() + ")");          // 类似于max(id), AVG(score)这种
        if (this.aop.equals(Aggregator.Op.SUM_COUNT)) {                         // 如果有
            types.add(Type.INT_TYPE);
            names.add("COUNT");
        }
        return new TupleDesc(types.toArray(new Type[types.size()]), names.toArray(new String[names.size()]));
    }

    public void close() {
        // some code goes here
        super.close();
        opIterator.close();
        child.close();
    }

    @Override
    public OpIterator[] getChildren() {
        // some code goes here
        return new OpIterator[]{child};
    }

    @Override
    public void setChildren(OpIterator[] children) {
        // some code goes here
        this.child = children[0];
    }

}
