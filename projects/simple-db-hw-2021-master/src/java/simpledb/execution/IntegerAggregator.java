package simpledb.execution;

import simpledb.common.Type;
import simpledb.storage.*;

import java.util.*;

/**
 * Knows how to compute some aggregate over a set of IntFields.
 */
public class IntegerAggregator implements Aggregator {

    private static final long serialVersionUID = 1L;

    /**
     * Aggregate constructor
     * 
     * @param gbfield
     *            the 0-based index of the group-by field in the tuple, or
     *            NO_GROUPING if there is no grouping
     * @param gbfieldtype
     *            the type of the group by field (e.g., Type.INT_TYPE), or null
     *            if there is no grouping
     * @param afield
     *            the 0-based index of the aggregate field in the tuple
     * @param what
     *            the aggregation operator
     */
    private final int afield;
    private final Type gfieldtype;
    private final int gfield;
    private final AggHandler handler;
    private final Op op;

    // 创建不同的聚集函数,实现Handler方法
    // 使用map保存不同分组的结果，如果没分组，使用null保存唯一一组的结果
    private abstract class AggHandler {
        private Map<Field, IntField>ans;
        abstract void handle(Field gfield, IntField afield);            // group by不知道什么类型，但是聚集函数得到的结果一定是int
        public AggHandler() {
            ans = new HashMap<>();
        }
        public Map<Field, IntField> getAns() {
            return this.ans;
        }
    }
    private class MinHandler extends AggHandler {
        @Override
        void handle(Field gfield, IntField afield) {
            int x = afield.getValue();
            Map<Field, IntField>ans = this.getAns();
            if (ans.containsKey(gfield)) {
                ans.put(gfield, new IntField(Math.min(x, ans.get(gfield).getValue())));
            }
            else ans.put(gfield, new IntField(x));
        }
    }
    private class AvgHandler extends AggHandler{
        Map<Field, Integer> sum = new HashMap<>();
        Map<Field, Integer> count = new HashMap<>();
        @Override
        void handle(Field gbField, IntField aggField) {
            int value = aggField.getValue();
            // 求和 + 计数
            if(sum.containsKey(gbField) && count.containsKey(gbField)){
                sum.put(gbField, sum.get(gbField) + value);
                count.put(gbField, count.get(gbField) + 1);
            }
            else{
                sum.put(gbField, value);
                count.put(gbField, 1);
            }
            Map<Field, IntField>ans = this.getAns();
            ans.put(gbField, new IntField(sum.get(gbField) / count.get(gbField)));
        }
    }
    private class MaxHandler extends AggHandler {
        @Override
        void handle(Field gfield, IntField afield) {
            int x = afield.getValue();
            Map<Field, IntField>ans = this.getAns();
            if (ans.containsKey(gfield)) {
                ans.put(gfield, new IntField(Math.max(x, ans.get(gfield).getValue())));
            }
            else ans.put(gfield, new IntField(x));
        }
    }
    private class SumHandler extends AggHandler {
        @Override
        void handle(Field gfield, IntField afield) {
            int x = afield.getValue();
            Map<Field, IntField>ans = this.getAns();
            if (ans.containsKey(gfield)) {
                ans.put(gfield, new IntField(ans.get(gfield).getValue() + x));
            }
            else ans.put(gfield, new IntField(x));
        }
    }
    private class CountHandler extends AggHandler {
        @Override
        void handle(Field gfield, IntField afield) {
            Map<Field, IntField>ans = this.getAns();
            if (ans.containsKey(gfield)) {
                ans.put(gfield, new IntField(ans.get(gfield).getValue() + 1));
            }
            else ans.put(gfield, new IntField(1));
        }
    }


    // 这里的what可以采用工厂模式修改。直接写一个Handler类，替换Op就行了。
    public IntegerAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
        // some code goes here
        this.gfield = gbfield;
        this.afield = afield;
        this.gfieldtype = gbfieldtype;
        this.op = what;
        switch (what) {
            case MIN:
                this.handler = new MinHandler(); break;
            case AVG:
                this.handler = new AvgHandler(); break;
            case SUM:
                this.handler = new SumHandler(); break;
            case COUNT:
                this.handler = new CountHandler(); break;
            case MAX:
                this.handler = new MaxHandler(); break;
            default:
                throw new IllegalArgumentException("聚合器不支持当前运算符");
        }
    }

    /**
     * Merge a new tuple into the aggregate, grouping as indicated in the
     * constructor
     * 
     * @param tup
     *            the Tuple containing an aggregate field and a group-by field
     */
    public void mergeTupleIntoGroup(Tuple tup) {
        // some code goes here
        // 获取对应域的值
        IntField afield = (IntField) tup.getField(this.afield);
        Field gfield = this.gfield == NO_GROUPING ? null : tup.getField(this.gfield);
        handler.handle(gfield, afield);
    }

    /**
     * Create a OpIterator over group aggregate results.
     * 
     * @return a OpIterator whose tuples are the pair (groupVal, aggregateVal)
     *         if using group, or a single (aggregateVal) if no grouping. The
     *         aggregateVal is determined by the type of aggregate specified in
     *         the constructor.
     *
     *         返回结果的迭代器,返回的类型是
     *
     */
    public OpIterator iterator() {
        // some code goes here
//        throw new
//        UnsupportedOperationException("please implement me for lab2");
        Map<Field, IntField> ans = handler.ans;
        List<Tuple>tuples = new ArrayList<>();
        TupleDesc tupleDesc;                                        // 创建迭代器的时候，需要用到
        if (gfield == NO_GROUPING) {                              // 如果没有group
            // 创建tuple
            tupleDesc = new TupleDesc(new Type[]{Type.INT_TYPE}, new String[]{"aggregateVal"});
            Tuple tuple = new Tuple(tupleDesc);
            // 把查询到的结果放入tuple中
            tuple.setField(0, ans.get(null));
            // 不要忘了把创建的tuple放入到结果集中
            tuples.add(tuple);
        }
        else {                                                     // 如果含有group
            tupleDesc = new TupleDesc(new Type[]{this.gfieldtype, Type.INT_TYPE}, new String[]{"groupVal", "aggregateVal"});
            for (Map.Entry<Field, IntField> entry : ans.entrySet()) {
                Tuple tuple = new Tuple(tupleDesc);
                tuple.setField(0, entry.getKey());
                tuple.setField(1, entry.getValue());
                tuples.add(tuple);
            }
        }
        return new TupleIterator(tupleDesc, tuples);
    }

}
