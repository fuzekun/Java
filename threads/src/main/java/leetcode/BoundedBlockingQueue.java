package leetcode;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/*
*
*   首先获得一个队列的锁，其次对队列进行操作
*
* 典型的生产者消费者问题
* */
public class BoundedBlockingQueue {

    private int enNum = 10;
    private int deNum = 10;
    private final int n;
    private Object obj;

    // 为了方便使用线程，直接锁住，不用手动上锁了
    synchronized boolean enSub() {
        return enNum-- > 0;
    }
    synchronized boolean deSub() {
        return deNum-- > 0;
    }

    Queue<Integer> list = new ArrayDeque<>();
    public BoundedBlockingQueue(int capacity) {
        this.n = capacity;
        obj = new Object(); // 每一个实例创建一个对象
    }

    public void enqueue(int element) throws InterruptedException {
            synchronized (obj) {
                while (list.size() == n) {
                    obj.wait();
                }
                list.add(element);
                obj.notifyAll();
        }
    }

    public int dequeue() throws InterruptedException {
        int ele;
        synchronized (obj) {

            while (list.isEmpty()) {
                obj.wait();
            }
            ele = list.poll();      // 保证不空
            obj.notifyAll();
        }
        return ele;
    }

    public int size() {
        int sz;
        synchronized (obj) {
            sz = list.size();
            obj.notifyAll();
        }
        return sz;
    }

    public static void main(String[] args) {
        String[] list = {"leetcode.BoundedBlockingQueue", "10"}; // 初始容量为10
        BoundedBlockingQueue.main(list);
    }
}
