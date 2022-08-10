package threadBase.unsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 18:34
 * @Description:
 * Amout的测试类，里面就只有一个静态的测试方法
 */
public class TestAmount {

    // 这个测试类是线程不安全的， 但是实现安全的逻辑应该放在withdraw中
    /*
    *   由于get和set方法一起进行了使用，
    *   所以会导致线程不安全的问题.
    *
    *
    * 更新...................
    *
    * 测试不应该写线程安全的逻辑
    * 应该在withdraw中写线程安全的逻辑
    * */

    /*
    *
    *   如果只是为了测试Integer的不安全，那么是不用加上锁的。
    * 只要最后的值为0，就是线程安全的。
    *
    *
    * */
    // 初始设置成1000， 10个线程，每个线程取钱100， 最后应该是输出0
    public static void testAmout(Amount t) {
        long startTime = System.currentTimeMillis();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ts.add(new Thread(()->{
                if (t.withdrwa(100)) {
                    System.out.println("success!");
                } else {
                    System.out.println("fail: your account is not enough!");
                }
            }));
        }
        ts.forEach(Thread::start);
        for (Thread thread : ts) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(t.getAmout());
        System.out.println("time:" + (endTime - startTime) + "ms");
    }

    /*
    *
    *
    *   自己写的cas操作，在面对这种情况时，
    *  10个线程: 10ms以内
    *  1000个线程 70ms左右
    *
    *  使用AtomicInteger的cas操作，以及使用synchornized实现情况也是一样的。
    * 基本上都是这个时间。
    * 这是因为如果不成功基本就直接返回了。
    *
    * */
}
