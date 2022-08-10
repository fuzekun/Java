package leetcode;

import threadBase.model.TimeProxy;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
*
* leetcode上面的线程题，打印0和奇偶数字
*
* */
public class Print0AndJi extends PrintOuJiInt{
    private int n = 10;

    private CyclicBarrier cybr1 = new CyclicBarrier(2);

    private CyclicBarrier cybr2 = new CyclicBarrier(2);

    private CyclicBarrier cybr3 = new CyclicBarrier(2);

    private CyclicBarrier cybr4 = new CyclicBarrier(2);

    public Print0AndJi() {

    }

    public Print0AndJi(int n) {

        this.n = n;
    }
    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            printNumber.accept(0);
            if (i % 2 == 1) {
                try {
                    cybr1.await();      // 使用Semphore或者门栓这种不需要进行while判断
                    cybr2.await();      // 等待1进行输出
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    cybr3.await();      // 通知2进行输出
                    cybr4.await();      // 等待2进行输出
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i = i + 2) {
            try {
                cybr1.await();          // 输出奇数
                printNumber.accept(i);
                cybr2.await();

            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i = i + 2) {

            try {
                cybr3.await();
                printNumber.accept(i);
                cybr4.await();

            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        PrintOuJiInt print = (PrintOuJiInt) new TimeProxy().getProxy(Print0AndJi.class);
        PrintOujiTest.run(print);
    }

}
