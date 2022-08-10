package leetcode;

import threadBase.model.TimeProxy;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 15:56
 * @Description:
 * 使用volatile + state实现
 */
public class PrintOuJi3 extends PrintOuJiInt{

    private int n;

    private volatile int state = 0;


    public PrintOuJi3() {
        this.n = 10;
    }

    public PrintOuJi3(int n) {
        this.n = n;
    }

    public  void zero(IntConsumer printNumber) throws InterruptedException {
        for(int i = 0; i < n; i++) {
            while(state != 0) {Thread.yield();}
            printNumber.accept(0);
            if ((i & 1) == 0) {
                state = 1;
            } else {
                state = 2;
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while(state != 2) {
                Thread.yield();
            }
            printNumber.accept(i);
            state = 0;
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            while(state != 1) {
                Thread.yield();
            }
            printNumber.accept(i);
            state = 0;
        }
    }

    public static void main(String[] args) {
        TimeProxy t = new TimeProxy();
        PrintOuJiInt tProxy = (PrintOuJiInt) t.getProxy(PrintOuJi3.class);
        PrintOujiTest.run(tProxy);
    }
}
