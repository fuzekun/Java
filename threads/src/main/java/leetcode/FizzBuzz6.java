package leetcode;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.IntConsumer;

/*
*
*   使用fizz和buzz
* */
public class FizzBuzz6 extends FizzBuzz {
    private Semaphore semaphore = new Semaphore(1);
    private int state = 0;
    private int n;
    FizzBuzz6(int n) {
        super(n);
        this.n = n;
    }
    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            semaphore.acquire(1);               // 获取state的锁
            while (state != 3) {
                Thread.yield();                         // 等待等于3的时候
                semaphore.release();
            }

            if (i % 3 == 0 && i % 5 != 0) {
                printFizz.run();
                state = 0;
            }
            semaphore.release(1);
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5){
            semaphore.acquire(1);
            while(state != 5) {
                semaphore.release();
                Thread.yield();                 // 不执行
            }
            if (i % 3 != 0) {
                printBuzz.run();
                state = 0;
            }
            semaphore.release(1);
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            semaphore.acquire(1);
            while(state != 15) {
                semaphore.release();
                Thread.yield();
            }
            printFizzBuzz.run();
            state = 0;
            semaphore.release();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++)  {
            semaphore.acquire(1);
            while (state != 0) {
                Thread.yield();
                semaphore.release();
            }
            if (i % 3 != 0 && i % 5 != 0) {
                printNumber.accept(i);
            } else  {
                if (i % 3 == 0 && i % 5 == 0) state = 15;
                else if (i % 3 == 0) state = 3;
                else state = 5;
            }
            semaphore.release(1);
        }
    }

    public static void main(String[] args)throws Exception {
        String[]s = {"leetcode.FizzBuzz6", "20"} ;
        FuzzBuzzMain.main(s);
    }
}
