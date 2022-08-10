package threadBase.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/23 16:29
 * @Description: 用来阅读ReentryLock的源码
 */
public class ReentryLockRead {
    private static int state;
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        ReentrantLock fairLock = new ReentrantLock(true);
        Condition condition = lock.newCondition();
        state = 1;
        int n = 10;
        new Thread(()->{
            try {
                lock.lock();
                for (int i = 0; i < n; i++) {
//                fairLock.lock();
                    while (state == 0) {
                        condition.await();
                    }
                    state = 0;
                    System.out.print("1");
                    condition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
//                fairLock.unlock();
            }
        }).start();
        new Thread(()->{
            try {
                lock.lock();
                for (int i = 0; i < n; i++) {
//                fairLock.lock();
                    while (state == 1) {
                        condition.await();
                    }
                    state = 1;
                    System.out.print("0");
                    condition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
//                fairLock.unlock();
            }

        }).start();
    }
}
