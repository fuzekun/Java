package gcAndDistribution;


import org.junit.Test;

import java.lang.management.ManagementFactory;

/**
 *
 * 最后一个是否使用G1,可以不用
 *
 * 1. 优先在Eden区域进行分配
 * 2. 大对象直接进入老年代
 * 3. 长期存活的对象直接进入老年代
 * 4. 动态对象年龄判定
 * 5. 空间分配担保
 */

public class TestAllocation {
  //JVM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseG1GC
    private static final int _1MB = 1024 * 1024;
    public static void testAllocation1() {
        byte[] a1, a2, a3, a4, a5, a6;
        a1 = new byte[_1MB * 2] ;
        a2 = new byte[_1MB * 2];
        a3 = new byte[_1MB * 2];
//        System.gc();                      // 进行一次全回收
        a4 = new byte[_1MB * 2];
        a5 = new byte[_1MB * 4];

    }

    public static void testAllocation2() {

        /*JVM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseG1GC
                    -XX:PretenureSizeThreshold=3145728


        */
        byte[] a1, a2, a3, a4, a5, a6;

        a1 = new byte[_1MB * 4] ;

    }

    public static void testTenuringThreshold() {
        /*
        *
        * VM参数-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
        * -XX:MaxTenuringThreshold=1
        * -XX:+PrintTenuringDistribution
        * */
        byte[] a1, a2, a3, a4;
        a1 = new byte[_1MB / 4];
        a2 = new byte[_1MB * 4];
        a3 = new byte[_1MB * 4];
        a3 = null;
        a4 = new byte[_1MB * 4];
    }


    public static void main(String[] args) {
//        testAllocation2();
        testTenuringThreshold();
//        System.out.println(ManagementFactory.getRuntimeMXBean().getInputArguments());
    }
}
