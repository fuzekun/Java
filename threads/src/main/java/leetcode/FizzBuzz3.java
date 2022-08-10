package leetcode;

import java.util.function.IntConsumer;


/*
*
*   这里是一个错误的同步
* */
public class FizzBuzz3 extends FizzBuzz{
    // 使用synchornize + state的方式来实现
    private int state = 0;     // 0 : 数字， 1 ：fizz, 2:buzz, 3
    private Object obj = new Object();
    private int n;

    public FizzBuzz3(int n) {
        super(n);
        this.n = n;
    }
    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            if (i % 5 == 0) continue;
            synchronized (obj) {
                while (state != 1) {
                    obj.wait();
                }
                printFizz.run();
                state = 0;
                obj.notifyAll();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            if (i % 3 == 0) continue;       // 如果不行，就不该状态,否则最后一个可能输出不了
            synchronized (obj) {
                while (state != 2) {
                    obj.wait();
                }
                printBuzz.run();
                state = 0;
                obj.notifyAll();       // 可能会死锁，如果都wait就死锁了。
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {     // 只要等于3，就可以直接输出了
            synchronized (obj) {
                while (state != 3) {
                    obj.wait();
                }
//                System.out.println("\nstate = " + state + " i = " + i);
                printFizzBuzz.run();
                state = 0;          // 只有输出才有资格改，否则自己也能竞争
                obj.notifyAll();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized (obj) {
                while (state != 0) {
                    obj.wait();
                }
                if (i % 3 != 0 && i % 5 != 0) {
                    printNumber.accept(i);
                } else {
                    if (i % 3 == 0 && i % 5 == 0) {
                        state = 3;
                    } else if (i % 5 == 0) {
                        state = 2;
                    } else state = 1;
                }
//                System.out.println(" state = " + state);
                obj.notifyAll();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        String[] className = {"leetcode.FizzBuzz3", "20"};
        FuzzBuzzMain.main(className);
    }
}
