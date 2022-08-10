package leetcode;

import threadBase.model.TimeProxy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 15:58
 * @Description:
 * 使用Lock进行实现。
 *
 * 一个Semaphore好像实现不了这个东西。
 */
public class PrintOuJi4 extends PrintOuJiInt{
    private int n;

    private int state = 0;

    private boolean turn = false;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public PrintOuJi4() {
        this.n = 10;
    }

    public PrintOuJi4(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            /*
            *
            *   如果这里不是while，那么被唤醒之后，
            *   就算state != 0，也会执行后面的代码
            * */
            while (state != 0)
                condition.await();
            printNumber.accept(0);
            if (turn) {
                state = 1;          // 打印偶数
            } else state = 2;       // 打印奇数
            condition.signalAll();
            lock.unlock();
        }
    }
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            while (state != 1) condition.await();
            printNumber.accept(i);
            state = 0;
            turn = false;
            condition.signalAll();
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            while (state != 2) condition.await();
            printNumber.accept(i);
            state = 0;
            turn = true;
            condition.signalAll();
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        TimeProxy t = new TimeProxy();
        PrintOuJiInt tProxy = (PrintOuJiInt) t.getProxy(PrintOuJi4.class);
        PrintOujiTest.run(tProxy);
    }
}
