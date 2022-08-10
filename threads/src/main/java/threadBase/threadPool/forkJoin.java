package threadBase.threadPool;


import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: Zekun Fu
 * @date: 2022/6/20 19:39
 * @Description:
 *
 * 使用fork-join进行代码的求和
 *
 * 拆分的手段：
 *
 *
 * 使用fork-join很慢。
 * 如果使用newFixexCache进行拆分计算，
 * 很可能会导致饥饿。因为线程数量不足以支撑到递归到底
 *
 *
 *
 * */



@Slf4j(topic = "c.MyTask")
class  MyTask extends RecursiveTask<Long> {
    private int l, r;
    public MyTask(int l, int r) {
        this.l = l;
        this.r = r;
    }

    @Override
    protected Long compute() {
        if (l == r) return (long)l;
        int mid = l + r >> 1;
        MyTask t1 = new MyTask(l, mid);
        t1.fork();          // 让线程执行任务
        MyTask t2 = new MyTask(mid + 1, r);
        t2.fork();
//        log.debug("{}->{},{}", Thread.currentThread(), l, r);
        return t1.join() + t2.join();   // 合并任务
    }
}

class Task implements Callable<Long> {

    int l, r;
    ExecutorService pool;
    public Task(int l, int r, ExecutorService pool) {
        this.l = l;
        this.r = r;
        this.pool = pool;
    }
    @Override
    public Long call() throws Exception {
        if (l == r) return (long)l;
        int mid = l + r >> 1;
        Task t1 = new Task(l, mid, pool);
        Task t2 = new Task(mid + 1, r, pool);
        Future<Long> f1 = pool.submit(t1);
        Future<Long> f2 = pool.submit(t2);
        return f1.get() + f2.get();
    }
}

public class forkJoin {

    // 使用递归线程池
    private static ExecutorService cpool = Executors.newFixedThreadPool(16);
    public static Long testPool2(int l, int r) throws Exception{
        if (l == r) return (long)l;
        System.out.println(l + " " + r);
        int mid = l + r >> 1;
        Future<Long> f1 = cpool.submit(()-> testPool2(l, mid));
        Future<Long> f2 = cpool.submit(()->testPool2(mid + 1, r));
        return f1.get() + f2.get();
//        Task t = new Task(l, r, cpool);
//        Future<Long> f = cpool.submit(t);
//        return f.get();
    }

    public static void testPool(int n) {
        int tn = 16;
        ExecutorService pool = Executors.newFixedThreadPool(tn); //17个线程，效率比较高

        List<Future<Long>>list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        int sz = n / tn;        // 每一段需要计算的大小
        for (int i = 0; i < tn; i++) {
            int bg = sz * i, ed = i == tn - 1 ? n + 1 : sz * (i + 1) ;
            Future<Long> f = pool.submit(()->{
                long ans = 0;
                for (int j = bg; j < ed; j++)
                    ans += j;
                return ans;
            });
            list.add(f);
        }
        long ans = 0;
        for (Future<Long> f : list) {
            try {
                ans += f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(ans);
        System.out.println("使用普通线程池时间" + (endTime - startTime) + "ms");
        pool.shutdown();
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(16);
        System.out.println("线程数目为:" + pool.getPoolSize());
        int n = (int)1e9;
        long startTime = System.currentTimeMillis();
        //System.out.println(pool.invoke(new MyTask(0, n)));
//        pool.shutdown();
        long endTime = System.currentTimeMillis();
//        System.out.println("fork-join执行时间为:" + (endTime - startTime) + "ms");
        long ans = 0;
        for (int i = 0; i <= n; i++) {
            ans += i;
        }
        startTime = System.currentTimeMillis();
        System.out.println(ans);
        System.out.println("单线程执行时间为:" + (startTime - endTime) + "ms");

        testPool(n);

//        try {
//            startTime = System.currentTimeMillis();
//            ans = testPool2(0, n);
//            endTime = System.currentTimeMillis();
//            System.out.println("cpool线程池大小" + (((ThreadPoolExecutor)cpool).getLargestPoolSize()));
//            cpool.shutdown();
//            System.out.println(ans);
//            System.out.println("递归线程池运行时间:" + (endTime - startTime) + "ms");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
