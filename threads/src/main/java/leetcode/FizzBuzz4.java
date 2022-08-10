package leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class FizzBuzz4 extends FizzBuzz{
    private int n;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    int state = 0;

    public FizzBuzz4(int n) {
        super(n);
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            try {
                lock.lock();
                if (i % 3 == 0 && i % 5 == 0) continue;
                while (state != 3) {
                    condition.await();
                }
                printFizz.run();
                state = 0;
                condition.signalAll();
            } finally {
                lock.unlock();      // 如果没有获得锁，就直接释放
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            try {
                lock.lock();
                if (i % 3 == 0 && i % 5 == 0) continue;
                while (state != 5) {
                    condition.await();
                }
                printBuzz.run();
                state = 0;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            try {
                lock.lock();
                while (state != 15) {
                    condition.await();
                }
                printFizzBuzz.run();
                state = 0;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            try {
                lock.lock();
                while (state != 0) {
                    condition.await();
                }
                if (i % 3 != 0 && i % 5 != 0) {
                    printNumber.accept(i);
                } else {
                    if (i % 3 == 0 && i % 5 == 0) state = 15;
                    else if (i % 3 == 0) state = 3;
                    else if (i % 5 == 0) state = 5;
                    condition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception{

        String[] className = {"leetcode.FizzBuzz4", "20"};

        FuzzBuzzMain.main(className);
    }
}
