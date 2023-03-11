package simpledb;

import org.junit.Test;
import simpledb.common.Database;
import simpledb.common.Type;
import simpledb.execution.SeqScan;
import simpledb.storage.*;
import simpledb.transaction.TransactionId;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2023/2/22 10:04
 * @Description:
 */
public class Lab1Test {

    // 看当前的类路径
    @Test
    public void testPath() {
        System.out.println(String.format("当前的类路径是%s", System.getProperty("user.dir")));
    }

    // 测试文件转化
    @Test
    public void convert()  {
        File infile = new File("data.txt");
        File outFile = new File("some_data_file.dat");
        try {
            HeapFileEncoder.convert(infile, outFile, BufferPool.getPageSize(), 3);      // 默认3个int类型
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws IOException{
        // construct a 3-column table schema
        // 构建表头
        Type types[] = new Type[]{Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE};
        String names[] = new String[]{"field0", "field1", "field2"};
        TupleDesc descriptor = new TupleDesc(types, names);

        // create the table, associate it with some_data_file.dat
        // and tell the catalog about the schema of this table.

        // 创建表文件映射
        File file = new File("test.txt");
        if (!file.exists()) {
            throw new IOException(".dat文件不存在!");
        }
        File heapFile = new File("some_data_file.dat");
        HeapFileEncoder.convert(file, heapFile, BufferPool.getPageSize(), 3);
        HeapFile table1 = new HeapFile(heapFile, descriptor);
        Database.getCatalog().addTable(table1, "test");

        // construct the query: we use a simple SeqScan, which spoonfeeds
        // tuples via its iterator.
        // 创建查询
        TransactionId tid = new TransactionId();
        SeqScan f = new SeqScan(tid, table1.getId());

        try {
            // and run it
            f.open();
            while (f.hasNext()) {
                Tuple tup = f.next();
                System.out.print(tup);
            }
            f.close();
            Database.getBufferPool().transactionComplete(tid);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
        boolean end = heapFile.delete();
        System.out.println("临时数据库文件是否删除成功:" + end);
    }

    @Test
    public void testMap() {
        // 如果键是null，那么能不能放东西呢？
        Map<Field, Integer> mp = new HashMap<>();
        Field gfield = null;
        mp.put(gfield, 1);
        int ans = mp.get(gfield);
        System.out.println("ans = " + ans);         // 如果键未空
    }

}
