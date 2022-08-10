package threadBase.model;

import java.util.concurrent.Semaphore;

/**
 * @author: Zekun Fu
 * @date: 2022/5/21 9:48
 * @Description: 死锁问题
 */
public class LockDead {

    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(1);

    private static Object o1 = new Object();
    private static Object o2 = new Object();


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    s1.acquire(1);
                    Thread.sleep(100);
                    s2.acquire();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "线程2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    s2.acquire();               // 使用semphore线程进入等待状态
                    Thread.sleep(100);
                    s1.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "线程1").start();


        new Thread(()->{
            synchronized (o1) {         // 获取o1锁
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {     // 获取o2锁

                }
            }

        }, "线程3").start();


        new Thread(()->{
            synchronized (o2) {
                try{
                    Thread.sleep(100);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {

                }
            }
        },"线程4").start();
    }
}
