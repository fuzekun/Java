package leetcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class BoundedBlockingQueue2 extends BoundedBlockingQueue{
    Queue<Integer> queue = new LinkedList<>();
    Semaphore full;
    Semaphore empty;
    Semaphore mutex;

    public BoundedBlockingQueue2(int capacity) {
        super(capacity);
        full = new Semaphore(capacity);
        empty = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    /*
    *
    *   死锁的执行顺序
    * 1. 队列初始为满
    * 2. 消费者首先执行，获取队列锁，但是empty锁已经锁住了
    * 3. 生产者在执行，队列锁锁住了。
    *
    * 死锁执行顺序2
    * 1. 队列初始为空
    * 2. 生产者首先执行，获取队列锁，但是full锁已经上了
    * 3. 消费者执行，队列锁拿不到。
    * */
    public void enqueue(int element) throws InterruptedException {
            full.acquire();
            mutex.acquire(1);
            queue.add(element);
            empty.release();
            mutex.release();
    }

    public int dequeue() throws InterruptedException {
        int x = 0;
        empty.acquire();
        mutex.acquire();
        x = queue.poll();
        full.release();
        mutex.release();
        return x;
    }

    public int size() {
        int sz = 0;
        try {
            mutex.acquire();
            sz = queue.size();
            mutex.release();
        } catch (Exception e){
            e.printStackTrace();
        }
        return sz;
    }

    public void TestMutex() throws Exception{
        mutex.acquire(1);
        System.out.println("可以获得一把锁");
        System.out.println("执行完成");
        mutex.release();
    }

    public static void main(String[] args) {
        String[] list = {"leetcode.BoundedBlockingQueue2", "10"};       // 初始容量为10
        try {
            BoundBlockingQueMain.main(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
