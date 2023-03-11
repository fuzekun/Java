package simpledb.optimizer;

import simpledb.execution.Predicate;

/** A class to represent a fixed-width histogram over a single integer-based field.
 */
public class IntHistogram {

    // 直方图
    private int[] buckets;
    // 边界最小值
    private int min;
    // 边界最大值
    private int max;
    // 长度
    private double width;
    // 行数
    private int tuplesCount = 0;

    /**
     * Create a new IntHistogram.
     *
     * This IntHistogram should maintain a histogram of integer values that it receives.
     * It should split the histogram into "buckets" buckets.
     *
     * The values that are being histogrammed will be provided one-at-a-time through the "addValue()" function.
     *
     * Your implementation should use space and have execution time that are both
     * constant with respect to the number of values being histogrammed.  For example, you shouldn't
     * simply store every value that you see in a sorted list.
     *
     * @param buckets The number of buckets to split the input value into.
     * @param min The minimum integer value that will ever be passed to this class for histogramming
     * @param max The maximum integer value that will ever be passed to this class for histogramming
     */
    public IntHistogram(int buckets, int min, int max) {
        // some code goes here
        this.buckets = new int[buckets];
        this.min = min;
        this.max = max;
        this.width = (max - min + 1.0) / buckets;
        this.tuplesCount = 0;
    }

    /**
     * Add a value to the set of values that you are keeping a histogram of.
     * @param v Value to add to the histogram
     */
    private int getIndex(int v){
        return (int) ((v - min) / width);
    }
    public void addValue(int v) {
        // some code goes here
        if (v >= min && v < max) {
            buckets[getIndex(v)]++;
            tuplesCount++;
        }
    }

    /**
     * Estimate the selectivity of a particular predicate and operand on this table.
     *估计选择率
     * For example, if "op" is "GREATER_THAN" and "v" is 5,
     * return your estimate of the fraction of elements that are greater than 5.
     *
     * @param op Operator
     * @param v Value
     * @return Predicted selectivity of this particular operator and value
     */
    public double estimateSelectivity(Predicate.Op op, int v) {
        // some code goes here
        switch (op){
            case LESS_THAN:
                if(v <= min){
                    return 0.0;
                }
                else if(v >= max){
                    return 1.0;
                }
                else{
                    int index = getIndex(v);
                    double tuples = 0;
                    for (int i = 0; i < index; i++) {
                        tuples += buckets[i];
                    }
                    // 索引所在柱的高度 * （当前值 - 该柱前的宽度）<这个也就是当前柱所占的宽度>
                    tuples += (1.0 * buckets[index] / width) * (v - index * width - min);
                    return tuples / tuplesCount;
                }
            case GREATER_THAN:
                return 1 - estimateSelectivity(Predicate.Op.LESS_THAN_OR_EQ, v);
            case EQUALS:
                return estimateSelectivity(Predicate.Op.LESS_THAN_OR_EQ, v) - estimateSelectivity(Predicate.Op.LESS_THAN, v);
            case NOT_EQUALS:
                return 1 - estimateSelectivity(Predicate.Op.EQUALS, v);
            case GREATER_THAN_OR_EQ:
                return estimateSelectivity(Predicate.Op.GREATER_THAN, v - 1);
            case LESS_THAN_OR_EQ:
                return estimateSelectivity(Predicate.Op.LESS_THAN, v + 1);
            default:
                throw new IllegalArgumentException("Operation is illegal");
        }
    }

    /**
     * @return
     *     the average selectivity of this histogram.
     *    统计直方图的平均选择率
     *     This is not an indispensable method to implement the basic
     *     join optimization. It may be needed if you want to
     *     implement a more efficient optimization
     * */
    public double avgSelectivity()
    {
        // some code goes here
        int cnt = 0;
        for(int bucket : buckets){
            cnt += bucket;
        }
        if(cnt == 0) return 0;
        return (cnt * 1.0 / tuplesCount);
    }

    /**
     * @return A string describing this histogram, for debugging purposes
     */
    public String toString() {
        // some code goes here
        return String.format("IntHistogram(buckets = %d, min = %d, max = %d)", buckets.length, min, max);
    }
}
