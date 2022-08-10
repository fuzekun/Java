package threadBase.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/22 16:48
 * @Description: 不可重入锁。
 */


@Slf4j(topic = "c.TestAqs")
public class MyLock implements Lock {


    private MySync mySync = new MySync();

    @Override
    public void lock() {
        mySync.acquire(1); // 1. 尝试加锁，加锁不成功，放入等待队列
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        mySync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return mySync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mySync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        mySync.release(1);
    }

    @Override
    public Condition newCondition() {
        return mySync.newCondition();
    }


    // 大部分方法已经写完了，直接重写一小部分就行了
    class MySync extends AbstractQueuedLongSynchronizer {
        @Override
        protected boolean tryAcquire(long arg) {

            /*
            *
            *   内部有一个volatile修饰的state方法
            * */
            if (compareAndSetState(0, 1)) {         // 1. 加上锁
                setExclusiveOwnerThread(Thread.currentThread());    // 2. 设置线程为当前线程
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(long arg) {
            setExclusiveOwnerThread(null);              // 1. 先释放
            setState(0);                                // 2. 然后进行状态的改变,不能提刚换顺序
            return true;
        }

        @Override // 是否持有独占锁
        protected boolean isHeldExclusively() {
            return  getState() == 1;
        }

        // 返回条件变量, 直接返回内部的ConditinObject
        public Condition newCondition (){
            return new ConditionObject();
        }
    }


    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(()-> {
            lock.lock();
            try {
                log.debug("locking...");
                Thread.sleep(1000);
                lock.lock();
                log.debug("relock...");
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t1").start();


        new Thread(()-> {
            lock.lock();
            try {
                log.debug("locking...");
                Thread.sleep(1);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t2").start();
    }

}
