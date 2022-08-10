package leetcode;

import java.util.concurrent.Semaphore;

public class TestSynize {
    class H2O {
        private Semaphore h = new Semaphore(2); // 先生成一个h，然后生成一个
        private Semaphore o = new Semaphore(0); // 多一把也无所谓，反正

        //    private AtomicInteger cnt = new AtomicInteger(0);
        public H2O() {

        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

            h.acquire();
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseHydrogen.run();
//            System.out.println("cnt = " + cnt.get());
            o.release();
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {

            // releaseOxygen.run() outputs "O". Do not change or remove this line.

            o.acquire(2);
            releaseOxygen.run();
            h.release();
            h.release();
        }
    }
    public static void main(String[] args) {
        H2O h = new TestSynize().new H2O();

        for (int i = 0; i < 2; i++) {    // 先生成1个o，就有4个H可以生成了
            new Thread(() -> {            // 5个线程用来生成O
                try {
                    h.oxygen(() -> {
                        System.out.print("O");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {            // 10个线程用来生成H
                try {
                    h.hydrogen(() -> {
                        System.out.print("H");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
