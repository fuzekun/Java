package threadBase.unsafe;

import leetcode.AutomaticTest;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 8:49
 * @Description:
 *  线程不安全的实现模型，卖票模型
 *
 *  1. 加上sleep增加出错概率。
 *  2. 线程加上一个引用，方便后续对线程的操作。
 *  3. 使用CountDown和latchDown，以及使用join的等价性
 */
public class SaleTickey {

    private int tickets = 1000;

    // 应该是在递归调用的时候才有用
    /*
    *
    *   两个线程合作
    * 1. 第一个递归计算二叉树的最大值
    * 2. 第二个业绩算二叉树的最大值
    * 3. 每次都需要定义的全局变量，这个全局变量定义在类中。
    * 4. 每个线程调用就是线程私有的。
    * */



    // 出售
    public synchronized int sale(int cnt) throws InterruptedException {
        if (cnt <= tickets) {
            Thread.sleep(1);
            tickets -= cnt;
            return cnt;
        }
        return 0;
    }

    public int getCount() {
        return tickets;
    }

    public static void main(String[] args) throws InterruptedException {
        SaleTickey s = new SaleTickey();
        CountDownLatch cd = new CountDownLatch(1000);
        AtomicInteger saleCnt = new AtomicInteger();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int cnt = s.sale(1);
                        saleCnt.getAndAdd(cnt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cd.countDown();
                }
            });
            t.start();
        }

        cd.await();

        System.out.println("卖出:" + saleCnt);
        System.out.println("还剩:" + s.getCount());
    }

}
