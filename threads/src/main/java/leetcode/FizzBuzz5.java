package leetcode;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class FizzBuzz5 extends FizzBuzz {
    private int n;

    private Semaphore semaphore = new Semaphore(1);
    private int cur = 1;

    public FizzBuzz5(int n) {
        super(n);
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (true) {
            semaphore.acquire(1);
            try {
                // 原因就在这里，循环过程中如果打印的字符串个数已经满足要求，那么会使用return来返回，终止该方法的执行。
                // 但是咱们已经获取了信号量，那么在方法返回前需要释放该信号量，否则会导致其它线程一直等待，整个程序一直不结束。
                // Java语言中try-finally可以做到这一点，try-finally代码块也是常用的一种释放资源（IO流、数据库连接等）的方式。
                // 不是程序死循环，而是其它线程在wait，导致无法退出。
                if (cur > n) return;
                if (cur % 3 == 0 && cur % 5 != 0) {
                    cur++;
                    printFizz.run();
                }
            } finally {
                semaphore.release(1);
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (true) {
            semaphore.acquire(1);
            try {
                if (cur > n) return;
                if (cur % 3 != 0 && cur % 5 == 0) {
                    cur++;
                    printBuzz.run();
                }
            } finally {
                semaphore.release(1);
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (true) {
            semaphore.acquire(1);
            try {
                if (cur > n) return;
                if (cur % 3 == 0 && cur % 5 == 0) {
                    cur++;
                    printFizzBuzz.run();
                }
            } finally {
                semaphore.release(1);
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            semaphore.acquire(1);
            try {
                if (cur > n) return;
                if (cur % 3 != 0 && cur % 5 != 0) {
                    printNumber.accept(cur);
                    cur++;
                }
            } finally {
                semaphore.release(1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String[] s = {"leetcode.FizzBuzz5", "20"};
        FuzzBuzzMain.main(s);
    }

}
