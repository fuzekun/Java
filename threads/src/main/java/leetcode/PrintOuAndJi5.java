package leetcode;

import threadBase.model.TimeProxy;

import java.util.concurrent.Semaphore;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 17:35
 * @Description:
 * 使用Semphore进行实现
 *
 * PV操作YYDS, 操作的时间很短。
 * 代码更短，不用自己手动枷锁
 */
public class PrintOuAndJi5 extends PrintOuJiInt {


    private Semaphore s0 = new Semaphore(1);
    private Semaphore s1 =  new Semaphore(0);
    private Semaphore s2 = new Semaphore(0);
    private int n;

    public PrintOuAndJi5() {
        this.n = 10;
    }

    public PrintOuAndJi5(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            s0.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) s1.release();
            else s2.release();
        }
    }
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            s2.acquire();
            printNumber.accept(i);
            s0.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {

        for (int i = 1; i <= n; i += 2) {
            s1.acquire();
            printNumber.accept(i);
            s0.release();
        }
    }

    public static void main(String[] args) {
        // 这里也可以使用这种多态进行代码的复用。
        PrintOuJiInt print = (PrintOuAndJi5) new TimeProxy().getProxy(PrintOuAndJi5.class);
        PrintOujiTest.run(print);
    }

}
