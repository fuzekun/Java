package threadBase.model;

import leetcode.Philosophiers;

import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/1 14:59
 * @Description: 哲学家进餐问题,
 * 破坏不可剥夺 -- 使用tryLock
 * 破坏请求保持 -- 静态分配
 * 破坏循环等待 -- 规定顺序。可能会导致某一个线程饥饿。
 *      因为有的线程和两个人竞争，而有的线程在一个时刻只和一个人竞争
 *
 *      实现细节：
 *      1. 筷子可以继承锁变量
 *      2. 可以使用Semphore实现
 *      3. 可以使用ReentrantLock 实现的可以说是无锁的，因为线程一直处于就绪和执行装态。
 *
 *      4. 为什么不进入等待队列中呢？
 *          因为这不是一个同步问题，没有线程之间的协作，没有一个线程通知另外一个线程这种事情。
 *          也就是说，不会有人通知他醒过来。
 *          所以他需要不断的死循环去尝试，去抢筷子。
 *
 */

class Chopstic extends ReentrantLock {

}
public class Philosopher extends Thread {


    Chopstic left;
    Chopstic right;
    public Philosopher(Chopstic left, Chopstic right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        while(true) {

            if (t.isInterrupted()) {
                System.out.println(t.getName() + "嗝屁了");
                break;
            }

            if (left.tryLock()) {       // 如果拿到了左筷子
                try {
                    if (right.tryLock()) {  // 尝试拿右筷子， 没拿到
                        try {
                            eat();
                        } finally {
                            right.unlock();     // 如果拿到了，吃饭，放下锁
                        }
                    }
                }finally {
                    left.unlock();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                t.interrupt(); // 重新设置打断标记
            }
        }
    }

    public void eat() {
        Thread t = Thread.currentThread();
        System.out.println(t.getName() + "正在吃饭...");
    }

    public static void main(String[] args) throws InterruptedException {

        Chopstic[] chopstics = new Chopstic[5];
        for (int i = 0; i < 5; i++) chopstics[i] = new Chopstic();
        String[] names = {
                "阿基米德",
                "柏拉图",
                "牛顿",
                "柯西",
                "亚里士多德"
        };

        Philosopher[] ps = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            Philosopher p = new Philosopher(chopstics[i], chopstics[(i + 1) % 5]);
            p.setName(names[i]);
            p.start();
            ps[i] = p;
        }

        Thread.sleep(10000);

        for (int i = 0; i < 5; i++) {
            ps[i].interrupt();
        }
    }
}