package simpledb.optimizer;

import simpledb.common.Database;
import simpledb.common.Type;
import simpledb.execution.Predicate;
import simpledb.execution.SeqScan;
import simpledb.storage.*;
import simpledb.transaction.TransactionId;

import java.sql.DataTruncation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TableStats represents statistics (e.g., histograms) about base tables in a
 * query.
 *
 * This class is not needed in implementing lab1 and lab2.
 */
public class TableStats {

    // 每个表直方图的缓存
    private static final ConcurrentMap<String, TableStats> statsMap = new ConcurrentHashMap<>();

    static final int IOCOSTPERPAGE = 1000;

    public static TableStats getTableStats(String tablename) {
        return statsMap.get(tablename);
    }

    public static void setTableStats(String tablename, TableStats stats) {
        statsMap.put(tablename, stats);
    }

    public static void setStatsMap(Map<String,TableStats> s)
    {
        try {
            java.lang.reflect.Field statsMapF = TableStats.class.getDeclaredField("statsMap");
            statsMapF.setAccessible(true);
            statsMapF.set(null, s);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, TableStats> getStatsMap() {
        return statsMap;
    }

    public static void computeStatistics() {
        Iterator<Integer> tableIt = Database.getCatalog().tableIdIterator();

        System.out.println("Computing table stats.");
        while (tableIt.hasNext()) {
            int tableid = tableIt.next();
            TableStats s = new TableStats(tableid, IOCOSTPERPAGE);
            setTableStats(Database.getCatalog().getTableName(tableid), s);
        }
        System.out.println("Done.");
    }

    /**
     * number of bins for the histogram. feel free to increase this value over
     * 100, though our tests assume that you have at least 100 bins in your
     * histograms.
     */
    // 每个直方图应该分的段数
    static final int NUM_HIST_BINS = 100;
    // 读取每个页的 IO 成本
    private int ioCostPerPage;
    // 需要进行数据统计的表
    private DbFile dbFile;
    // 表的属性行
    private TupleDesc tupleDesc;
    // 表id
    private int tableid;
    // 一个表中页的数量
    private int pagesNum;
    // 一页中行的数量
    private int tupleNum;
    // 一行中段的数量
    private int fieldNum;
    // 第 i 个整型字段 和 第 i 个直方图的映射
    private HashMap<Integer, IntHistogram> integerHashMap;
    // 第 i 个字符串字段 和 第 i 个直方图的映射
    private HashMap<Integer, StringHistogram> stringHashMap;

    /**
     * Create a new TableStats object, that keeps track of statistics on each
     * column of a table
     *
     * @param tableid
     *            The table over which to compute statistics
     * @param ioCostPerPage
     *            The cost per page of IO. This doesn't differentiate between
     *            sequential-scan IO and disk seeks.
     */
    public TableStats(int tableid, int ioCostPerPage) {
        // For this function, you'll have to get the
        // DbFile for the table in question,
        // then scan through its tuples and calculate
        // the values that you need.
        // You should try to do this reasonably efficiently, but you don't
        // necessarily have to (for example) do everything
        // in a single scan of the table.
        // some code goes here

        // 基本的设置
        tupleNum = 0;
        this.tableid = tableid;
        this.ioCostPerPage = ioCostPerPage;
        this.dbFile = Database.getCatalog().getDatabaseFile(tableid);
        this.pagesNum = ((HeapFile) dbFile).numPages();
        integerHashMap = new HashMap<>();
        stringHashMap = new HashMap<>();

        // 获取字段数
        this.tupleDesc = dbFile.getTupleDesc();
        this.fieldNum = tupleDesc.numFields();

        // 获得行数
        Type[] types = getTypes(tupleDesc);
        int[] mins = new int[fieldNum];
        int[] maxs = new int[fieldNum];

        // 根据表查询行
        TransactionId tid = new TransactionId();
        // 查询每一行
        SeqScan scan = new SeqScan(tid, tableid);
        // 统计数字字段的最大值和最小值
        try{
            // 打开迭代器
            scan.open();
            // 获取每个字段的最小值和最大值，一共有 fieldNum个字段
            for (int i = 0; i < fieldNum; i++) {
                // 如果是字符串，跳过
                if(types[i] == Type.STRING_TYPE){
                    continue;
                }
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                while(scan.hasNext()){
                    if(i == 0){
                        tupleNum++;
                    }
                    Tuple tuple = scan.next();
                    IntField field = (IntField)tuple.getField(i);
                    int val = field.getValue();
                    max = Math.max(val, max);
                    min = Math.min(val, min);
                }
                // 迭代器重置
                scan.rewind();
                mins[i] = min;
                maxs[i] = max;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            scan.close();
        }

        // 写入缓存map中
        for (int i = 0; i < fieldNum; i++) {
            // 如果是整数，创立整数直方图
            if(types[i] == Type.INT_TYPE){
                integerHashMap.put(i, new IntHistogram(NUM_HIST_BINS, mins[i], maxs[i]));
            }
            else{
                stringHashMap.put(i, new StringHistogram(NUM_HIST_BINS));
            }
        }

        // 把相应的值填入直方图
        addValueToHist();
    }

    /**
     * 获取相应的类型
     * */
    private Type[] getTypes(TupleDesc td){
        int numField = td.numFields();
        Type[] types = new Type[numField];
        for (int i = 0; i < numField; i++) {
            Type t = td.getFieldType(i);
            types[i] = t;
        }
        return types;
    }

    /**
     * 把相应的值填入直方图
     * */
    private void addValueToHist(){
        TransactionId tid = new TransactionId();
        SeqScan scan = new SeqScan(tid, tableid);
        try{
            // 打开迭代器
            scan.open();
            // 获取每行的各个字段值写入
            while(scan.hasNext()){
                Tuple tuple = scan.next();
                // 遍历每个字段
                for (int i = 0; i < fieldNum; i++) {
                    Field field = tuple.getField(i);
                    // 判断类型
                    if(field.getType() == Type.INT_TYPE){
                        integerHashMap.get(i).addValue(((IntField) field).getValue());
                    }
                    else{
                        stringHashMap.get(i).addValue(((StringField) field).getValue());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            scan.close();
        }
    }

    /**
     * Estimates the cost of sequentially scanning the file, given that the cost
     * to read a page is costPerPageIO. You can assume that there are no seeks
     * and that no pages are in the buffer pool.
     *
     * Also, assume that your hard drive can only read entire pages at once, so
     * if the last page of the table only has one tuple on it, it's just as
     * expensive to read as a full page. (Most real hard drives can't
     * efficiently address regions smaller than a page at a time.)
     *
     * @return The estimated cost of scanning the table.
     */
    public double estimateScanCost() {
        // some code goes here
        return pagesNum * ioCostPerPage;
    }

    /**
     * This method returns the number of tuples in the relation, given that a
     * predicate with selectivity selectivityFactor is applied.
     *
     * @param selectivityFactor
     *            The selectivity of any predicates over the table
     * @return The estimated cardinality of the scan with the specified
     *         selectivityFactor
     */
    public int estimateTableCardinality(double selectivityFactor) {
        // some code goes here
        // 选择因子 * 行的数量，得出来应该选择多少行
        double cardinality = selectivityFactor * tupleNum;
        return (int) cardinality ;
    }

    /**
     * The average selectivity of the field under op.
     * @param field
     *        the index of the field
     * @param op
     *        the operator in the predicate
     * The semantic of the method is that, given the table, and then given a
     * tuple, of which we do not know the value of the field, return the
     * expected selectivity. You may estimate this value from the histograms.
     * */
    public double avgSelectivity(int field, Predicate.Op op) {
        // some code goes here
        // 判断类型
        if(tupleDesc.getFieldType(field) == Type.INT_TYPE){
            return integerHashMap.get(field).avgSelectivity();
        }
        else{
            return stringHashMap.get(field).avgSelectivity();
        }
    }

    /**
     * Estimate the selectivity of predicate <tt>field op constant</tt> on the
     * table.
     *
     * @param field
     *            The field over which the predicate ranges
     * @param op
     *            The logical operation in the predicate
     * @param constant
     *            The value against which the field is compared
     * @return The estimated selectivity (fraction of tuples that satisfy) the
     *         predicate
     */
    public double estimateSelectivity(int field, Predicate.Op op, Field constant) {
        // some code goes here
        // 判断类型
        if(tupleDesc.getFieldType(field) == Type.INT_TYPE){
            return integerHashMap.get(field).estimateSelectivity(op, ((IntField)constant).getValue());
        }
        else{
            return stringHashMap.get(field).estimateSelectivity(op, ((StringField)constant).getValue());
        }
    }

    /**
     * return the total number of tuples in this table
     * */
    public int totalTuples() {
        // some code goes here
        return tupleNum;
    }

}
