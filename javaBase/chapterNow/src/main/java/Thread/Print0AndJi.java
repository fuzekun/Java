package Thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
*
* leetcode上面的线程题，打印0和奇偶数字
* */
public class Print0AndJi {

    class IntConsumer {
        public void accept(int x) {
            System.out.print(x);
        }
    }

    class ZeroEvenOdd {
        private int n;

        private CyclicBarrier cybr1 = new CyclicBarrier(2); // 有了两个线程才能继续执行
        private CyclicBarrier cybr2 = new CyclicBarrier(2);
        private CyclicBarrier cybr3 = new CyclicBarrier(2);
        private CyclicBarrier cybr4 = new CyclicBarrier(2);

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {

                printNumber.accept(0);  // 输出一个0
                if (i % 2 == 1) {         // 该输出奇数了
                    try {
                        cybr1.await();    // 0开始等待在奇数后面
                        cybr2.await();    //

                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        cybr3.await();      //
                        cybr4.await();

                    } catch (BrokenBarrierException e) {
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
    }

}
