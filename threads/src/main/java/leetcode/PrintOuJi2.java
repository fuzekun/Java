package leetcode;

import threadBase.model.TimeProxy;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 15:20
 * @Description:
 * 输出奇数偶数，第二个版本。
 * 使用锁的机制
 */
public class PrintOuJi2 extends PrintOuJiInt {

    private int n;

    private int state = 0;

    private boolean turn = false;

    public PrintOuJi2() {
        this.n = 10;
    }

    public PrintOuJi2(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {       // 打印n个0
                while (state != 0) this.wait();
                printNumber.accept(0);
                if (turn) {
                    state = 2;          // 打印偶数
                } else state = 1;       // 打印奇数
                notifyAll();
            }
        }
    }
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            synchronized (this) {
                while (state != 1) this.wait();
                printNumber.accept(i);
                state = 0;
                turn = true;
                notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            synchronized (this) {
                while (state != 2) this.wait();
                printNumber.accept(i);
                state = 0;
                turn = false;
                notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        TimeProxy t = new TimeProxy();
        PrintOuJiInt tProxy = (PrintOuJiInt) t.getProxy(PrintOuJi2.class);
        PrintOujiTest.run(tProxy);
    }
}
