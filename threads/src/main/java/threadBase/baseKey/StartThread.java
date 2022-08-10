package threadBase.baseKey;


import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/*
*
*
*   线程启动的三种方式
* 1. implement
* 2. extends
* 3. FutureT
* 4. 线程方式
*
* 其中FutureTask实现了Runnable和Future接口可以算作Runnble的一种实现方式。
* 线程池可以算作开启线程的新的一种方式。
*
* */
public class StartThread {
    private static final int MAXN = 100000000;
    private static int[] nums = new int[MAXN];     // 计算数组中100个数字的和
    private AtomicInteger cur = new AtomicInteger();
    static {
        Arrays.fill(nums, 1);
    }

    private static long testSingle() throws Exception {
        long startTime = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < MAXN; i++) {
            for (int j = 0; j < 100; j++) {
                sum += nums[i];
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("单线程: ");
        System.out.println("sum = " + sum + " t = " + (endTime - startTime) + "ms");

        return endTime - startTime;
    }
    private static long test1() throws Exception{


        long startTime = System.currentTimeMillis();
        FutureTask<Long> task1 = new FutureTask<Long>(() -> {
            long tsum = 0;
            for (int i = 0; i < 25000000; i++) {
                for (int j = 0; j < 100; j++) {
                    tsum += nums[i];
                }
            }
            return tsum;
        });
        FutureTask<Long> task2 = new FutureTask<Long>(() -> {
            long tsum = 0;
            for (int i = 25000000; i < 50000000; i++) {
                for (int j = 0; j < 100; j++) {
                    tsum += nums[i];
                }
            }
            return tsum;
        });
        FutureTask<Long> task3 = new FutureTask<Long>(() -> {
            long tsum = 0;
            for (int i = 50000000; i < 75000000; i++) {
                for (int j = 0; j < 100; j++) {
                    tsum += nums[i];
                }
            }
            return tsum;
        });
        FutureTask<Long> task4 = new FutureTask<Long>(() -> {
            long tsum = 0;
            for (int i = 75000000; i < 100000000; i++) {
                for (int j = 0; j < 100; j++) {
                    tsum += nums[i];
                }
            }
            return tsum;
        });
        new Thread(task1).start();
        new Thread(task2).start();
        new Thread(task3).start();
        new Thread(task4).start();
        long sum = task1.get() + task2.get() + task3.get() + task4.get();
        long endTime = System.currentTimeMillis();


        System.out.println("4线程:");
        System.out.println("sum = " + sum + " t = " + (endTime - startTime) + "ms");

        return endTime - startTime;

    }

    private static long test2(int t) throws Exception{
        /*
         *
         *   首先需要一个线程进行任务的划分。
         *  然后由这个划分线程生成划分任务的线程数目。
         * 在有这个线程进程组装。
         * 在这使用主线程进行划分。
         * */
        int len = MAXN / t;
        long sum = 0;
        long startTime = System.currentTimeMillis();
        FutureTask<Long>[]tasks = new FutureTask[t];
        for (int i = 0; i < t; i++) {           // 进行任务划分
            int[] numt = new int[len];
            for (int j = i * len; j < (i + 1) * len; j++) {
                numt[j - (i * len)] = nums[j];
            }
            // 线程执行
            FutureTask<Long>task = new FutureTask<Long>(()->{
                long ans = 0;
                for (int x: numt) {
                    for (int j = 0; j < 100; j++) {
                        ans += x;
                    }
                }
                return ans;
            });
            new Thread(task).start();
            tasks[i] = task;
//            sum += task.get();            // 这个会阻塞线程，所以会慢
        }
        for (int i = 0; i < 4; i++) {
            sum += tasks[i].get();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("使用分治进行" + t + "线程划分执行:");
        System.out.println("sum = " + sum + " t = " + (endTime - startTime) + "ms");
        return endTime - startTime;
    }

    private static long test3() throws Exception {
        StartThread startThread = new StartThread();
        int cnt = 4;                    // 控制线程的个数
        int sz = MAXN / cnt;            // 每一个线程计算的数量是多少
        long sum = 0;                        // 计算和是多少
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < cnt; i++) {
            FutureTask<Long> task = new FutureTask<Long>(()->
            {
                long ans = 0L;
                int bg = startThread.cur.getAndIncrement();
                for (int j = bg * sz; j < (bg + 1) * sz; j++) {
                    for (int k = 0; k <100; k++) {
                        ans += nums[j];
                    }
                }
                return ans;
            });
            new Thread(task).start();
            sum += task.get();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(cnt + "个线程:");
        System.out.println("sum = " + sum + " t = " + (endTime - startTime) + "ms");

        return endTime - startTime;
    }

    public static long test4(int cnt) throws Exception{
        StartThread startThread = new StartThread();

        int sz = MAXN / cnt;            // 每一个线程计算的数量是多少
        long sum = 0;                        // 计算和是多少
        long startTime = System.currentTimeMillis();
        FutureTask<Long>[]tasks = new FutureTask[cnt];
        for (int i = 0; i < cnt; i++) {
            FutureTask<Long> task = new FutureTask<Long>(()->
            {
                long ans = 0L;
                int bg = startThread.cur.getAndIncrement();
                for (int j = bg * sz; j < (bg + 1) * sz; j++) {
                    for (int k = 0; k <100; k++) {
                        ans += nums[j];
                    }
                }
                return ans;
            });
            new Thread(task).start();
            tasks[i] = task;
        }
        for (int i = 0; i < cnt; i++) {
            sum += tasks[i].get();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(cnt + "个线程:");
        System.out.println("sum = " + sum + " t = " + (endTime - startTime) + "ms");

        return endTime - startTime;
    }


    private static void testDiff() throws Exception{
        long t1 = 0, t2 = 0, t3 = 0, t4 = 0;
        int t = 8, cnt = 8;                 // 分治多线程和手动多线程
        for (int i = 0; i < 11; i++) {      // 后面的8轮次进行统计
            System.out.println("-----------第" + i + "轮----------");
            if (i >= 3) {
//                t1 += testSingle();
                t2 += test1();
                t3 += test2(t);
                t4 += test4(cnt);
            } else {
//                testSingle();
                test1();
                test2(t);
                test4(cnt);
            }
        }
        System.out.println("平均时间:");
//        System.out.println("单线程:" + t1 / 8 + "ms");
        System.out.println("4个手动多线程:" + t2 / 8 + "ms");
        System.out.println(t + "个分治多线程:" + t3 / 8 + "ms");
        System.out.println(cnt + "for循环多线程:" + t4 / 8 + "ms");
    }

    public static void testNumOfThread() throws Exception{
        int cnt = 32;
        long []times =  new long[cnt];
        for (int i = 1; i < cnt; i++) {
            times[i] = test4(i);
        }
        for (int i = 1; i < cnt; i++) {
            System.out.println(i + "个线程:" + times[i] + "ms");
        }
    }
    // 可以从第三遍开始，统计一个平均的时间
    public static void main(String[] args)throws Exception {
        testNumOfThread();
    }
}
