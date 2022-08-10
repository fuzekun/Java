package threadBase.model;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Zekun Fu
 * @date: 2022/5/22 20:10
 * @Description: 生产者消费者模型, 使用PV操作实现
 */
public class PC {
    int n = 10;
    private Semaphore mutex = new Semaphore(1);     // 互斥访问队列
    private Semaphore full = new Semaphore(n);    // 初始可以放入10
    private Semaphore empty = new Semaphore(0);
    int[] buffer = new int[n];
    int idx = 0;
    private ThreadLocalRandom random;

    public static void main(String[] args) {
        PC pc = new PC();
        // 5个生产者
        for (int i = 0; i < 15; i++) {
            new Thread(()->{
                pc.random = ThreadLocalRandom.current();
                while(true) {
                    try {
                        pc.full.acquire();
                        pc.mutex.acquire();
                        int x = pc.random.nextInt(100);
                        System.out.println(Thread.currentThread().getName() + "生产了" + x);
                        pc.buffer[pc.idx++] = x;
                        pc.mutex.release();
                        pc.empty.release();
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "生产者线程" + i).start();
        }

        // 5个消费者线程
        for (int i = 0; i < 5; i++) {
            while(true) {
                new Thread(()-> {
                    try {
                        pc.empty.acquire();
                        pc.mutex.acquire();
                        System.out.println(Thread.currentThread().getName() + "消费了" + pc.buffer[--pc.idx]);
                        pc.mutex.release();
                        pc.full.release();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },"消费者线程" + i).start();
            }
        }
    }
}
