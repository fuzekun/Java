package threadBase.threadPool;

/*
*
*
*   使用线程池去计算数组的和
* */


import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolToCaculate {
    private static final int MAXN = 100000000;
    private static int[] nums = new int[MAXN];     // 计算数组中100个数字的和
    private AtomicInteger cur = new AtomicInteger();
    static {
        Arrays.fill(nums, 1);
    }

    /*
    *
    *   这段代码说明了，FutureTask是需要线程来进行执行的.
    * FutureTask实现了Runable接口和Future接口。
    * 1. 可以作为一个线程任务来进行执行
    * 2. 可以通过.get()方法获取任务的结果。
    * */
    private void test1() {
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<?>res = pool.submit(new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return new Integer(1);
            }
        }));
        try {
            res.get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

    }

}
