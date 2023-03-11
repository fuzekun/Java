package simpledb.execution;

import simpledb.common.Type;
import simpledb.storage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Knows how to compute some aggregate over a set of StringFields.
 */
public class StringAggregator implements Aggregator {

    private static final long serialVersionUID = 1L;

    /**
     * Aggregate constructor
     * @param gbfield the 0-based index of the group-by field in the tuple, or NO_GROUPING if there is no grouping
     * @param gbfieldtype the type of the group by field (e.g., Type.INT_TYPE), or null if there is no grouping
     * @param afield the 0-based index of the aggregate field in the tuple
     * @param what aggregation operator to use -- only supports COUNT
     * @throws IllegalArgumentException if what != COUNT
     */
    private final int gbfeild;
    private final Type gbfildtype ;
    private final int afeild;
    private Map<Field, IntField> aggResult;

    public StringAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
        // some code goes here
        if (!what.equals(Op.COUNT))
        {
            throw new IllegalArgumentException("String类型只支持计数类型的聚集函数");
        }
        this.gbfeild = gbfield;
        this.gbfildtype = gbfieldtype;
        this.afeild = afield;
        aggResult = new HashMap<>();
    }

    /**
     * Merge a new tuple into the aggregate, grouping as indicated in the constructor
     * @param tup the Tuple containing an aggregate field and a group-by field
     *            把tup放入到聚集函数中
     */
    public void mergeTupleIntoGroup(Tuple tup) {
        // some code goes here
        Field gbField = this.gbfeild == NO_GROUPING ? null : tup.getField(gbfeild);
        if (aggResult.containsKey(gbField))
            aggResult.put(gbField, new IntField(aggResult.get(gbField).getValue() + 1));
        else aggResult.put(gbField, new IntField(1));
    }

    /**
     * Create a OpIterator over group aggregate results.
     *
     * @return a OpIterator whose tuples are the pair (groupVal,
     *   aggregateVal) if using group, or a single (aggregateVal) if no
     *   grouping. The aggregateVal is determined by the type of
     *   aggregate specified in the constructor.
     */
    public OpIterator iterator() {
        // some code goes here
//
//        throw new UnsupportedOperationException("please implement me for lab2");

        TupleDesc tupleDesc;
        List<Tuple>tuples = new ArrayList<>();
        if (this.gbfeild == NO_GROUPING) {
            tupleDesc = new TupleDesc(new Type[]{this.gbfildtype}, new String[]{"aggregateVal"});
            Tuple tuple = new Tuple(tupleDesc);
            tuple.setField(0, aggResult.get(null));
        }else {
            tupleDesc = new TupleDesc(new Type[]{this.gbfildtype, Type.INT_TYPE}, new String[]{"grounpVal", "aggregateVal"});
            for (Map.Entry<Field, IntField> entry : aggResult.entrySet()) {
                Tuple tuple = new Tuple(tupleDesc);
                tuple.setField(0, entry.getKey());
                tuple.setField(1, entry.getValue());
                tuples.add(tuple);
            }
        }
        return new TupleIterator(tupleDesc, tuples);
    }

}
