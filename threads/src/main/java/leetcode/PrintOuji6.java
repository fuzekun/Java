package leetcode;

import net.sf.cglib.proxy.Enhancer;
import threadBase.model.TimeProxy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Zekun Fu
 * @date: 2022/6/6 8:57
 * @Description:
 * 使用cas锁来进行实现
 *
 *
 * 逻辑是
 * 奇数线程和0线程共享一把锁
 * 偶数线程和0线程共享一把锁
 *
 *
 *
 */

public class PrintOuji6 extends PrintOuJiInt{

    AtomicInteger state = new AtomicInteger(0);

    int n;
    public PrintOuji6() {
        this.n = 10;
    }
    public PrintOuji6(int n) {
        this.n = n;
    }
    // 打印0
    @Override
    public void zero(IntConsumer printNumber) throws InterruptedException {
        // 上锁

        for (int i = 0; i < n; i++) {
            while(state.get() != 0) {Thread.yield();}
            printNumber.accept(0);
            if ((i & 1) == 0) {
                state.set(1);
            } else {
                state.set(2);
            }
        }
    }

    // 打印奇数
    @Override
    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            while(state.get() != 1) {Thread.yield();}
            printNumber.accept(i);
            state.set(0);
        }
    }

    // 打印偶数
    @Override
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while(state.get() != 2) { Thread.yield();}
            printNumber.accept(i);
            state.set(0);
        }
    }


    public static void main(String[] args) {
        TimeProxy t = new TimeProxy();
        PrintOuJiInt tProxy = (PrintOuJiInt) t.getProxy(PrintOuji6.class);
        PrintOujiTest.run(tProxy);
    }




}
