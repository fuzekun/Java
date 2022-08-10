package leetcode;

import threadBase.model.TimeProxy;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 16:05
 * @Description:
 * 写业务代码
 */

 class IntConsumer {
    public void accept(int x) {
        System.out.print(x);
    }
}
public final class PrintOujiTest {  // 加上final,防止被继承重写, 从而导致线程的安全问题

     // 私有构造方法，只进行方法的提供
     private PrintOujiTest() {

     }

    public static void run(PrintOuJiInt print) {

        IntConsumer consumer = new IntConsumer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print.zero(consumer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print.odd(consumer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print.even(consumer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
