package threadBase.threadPool;

import com.sun.corba.se.impl.oa.toa.TOA;
import lombok.extern.slf4j.Slf4j;
import threadBase.model.TimeProxy;

import java.io.File;
import java.io.FileDescriptor;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

/**
 * @author: Zekun Fu
 * @date: 2022/6/10 15:09
 * @Description: 手动实现线程池
 *
 * 1.
 */

@FunctionalInterface // 拒绝策略
interface RejectPolicy<T> {
    public void reject(MyBlockingQueue<T> que, T task) ;
}

@Slf4j(topic =  "c.ThreadPool")
class ThreadPool <T>{
   // 任务队列
    private MyBlockingQueue<Runnable> taskQueue;

    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数目
    private int coresize;

    // 获取任务的超时时间
    private long timeout;

    private TimeUnit unit;

    private RejectPolicy<Runnable> rejectPolicy;


    public ThreadPool(int coresize, long timeout, TimeUnit unit, int queCapcity, RejectPolicy<Runnable>reject) {
        this.coresize = coresize;
        this.timeout = timeout;
        this.unit = unit;
        this.taskQueue = new MyBlockingQueue<>(queCapcity);
        this.rejectPolicy = reject;
    }

    public void execute(Runnable task) {
        //  当前的任务没有超过coreSize时候，直接交给woker对象执行
        // 如果当前任务超过了coreSize的时候，需要加入任务队列缓存
        synchronized (workers) {
            if (workers.size() < coresize) {
                Worker worker = new Worker(task);
                log.debug("新增 worker{}, {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {

                // 如果队列满了
                // 1. 死等
                // 2. 带超时时间
                // 3. 放弃任务执行
                // 4. 抛出异常
                // 5. 让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }



    class Worker extends Thread{
        private Runnable task;
        public Worker (Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 1. 当task不为空，执行任务
            // 2. 当task执行完成后，从任务队列中执行任务

            while(task != null || ((task = taskQueue.poll(timeout, unit)) != null)) {
                try {
                    log.debug("正在执行任务...{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    task = null;
                }
            }

            synchronized (workers) {
                log.debug("{} 被移除", this);
                workers.remove(this);
            }
        }
    }
}

@Slf4j(topic = "c.BlockQueue")
class MyBlockingQueue <T>{


    // 1. 任务队列
    private Deque<T> queue = new ArrayDeque<>();

    // 2. 锁
    private ReentrantLock lock = new ReentrantLock();

    // 3. 消费者条件变量
    private Condition full = lock.newCondition();

    // 4. 生产者条件变量
    private Condition empty = lock.newCondition();

    // 5.容量
    private int capcity;

    public MyBlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T ret = queue.removeFirst();
            full.signalAll();
            return ret;             // 如果不加上catch，那么return就是可以的，加上finally，return就不行了。
        }
        finally {
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T element) {
        lock.lock();
        try {
            while (queue.size() >= capcity) {
                log.debug("等待加入任务队列 {} ...", element);
                full.await();
            }
            log.debug("加入任务队列 {}", element);
            queue.addLast(element);
            empty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    // 获取大小
    public int size() {
        return this.capcity;
    }

    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {

            long nanos =  unit.toNanos(timeout);

            while(queue.isEmpty()) {
                try {
                    if (nanos <= 0) return null;    // 直接返回，不用经过移除的逻辑了
                    // 保护性暂停，内部逻辑是计算当前的时间和等待花费时间的差值
                    nanos = empty.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            full.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }
    public boolean offer(T task, long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.size() >= capcity) {
                log.debug("等待加入任务队列 {} ... {}", task, nanos);
                if (nanos <= 0)
                    return false;
                try {
                    nanos = full.awaitNanos(nanos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}", task);
            queue.addLast(task);
            empty.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }
    public void tryPut(RejectPolicy<T>rejectPolicy, T task) {
        lock.lock();
        try {
            if (queue.size() == capcity) {
                rejectPolicy.reject(this, task);
            } else {
                log.debug("加入任务队列 {}", task);
                queue.addLast(task);
                empty.signalAll();
            }
        }
        finally {
            lock.unlock();
        }
    }
}

@Slf4j(topic = "c.TestPool")
public class ThreadPoolTest<T> {

    public static void main(String[] args) {
        ThreadPool<Runnable> threadPool = new ThreadPool<Runnable>(2, 1000, TimeUnit.MILLISECONDS, 10, (queue, task)->{
            // 1. 死等
//            queue.put(task);
            // 2. 带超时的等待
//            if (!queue.offer(task, 100, TimeUnit.MILLISECONDS)) {
//                log.debug("{} 加入队列等待超时， 任务执行失败!!", task);
//            }
            // 3. 放弃执行
//            log.debug("队列已满，放弃执行任务");


            // 4. 直接抛出异常
//            throw new RuntimeException("现在以及以后的任务都会执行失败" + task);

            // 5. 调用者自己执行任务
            task.run();
        });

        for (int i = 0; i < 15; i++) {
            int j = i;
            threadPool.execute(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }
}
