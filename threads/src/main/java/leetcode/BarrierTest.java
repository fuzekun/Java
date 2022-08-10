package leetcode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/*
*
*
*   方法1：回环 + 信号量锁
*   方法2：两个信号量
*   方法3：自己手动实现三把锁，不用提供的同步机制
* 1. 生成
* */
public class BarrierTest {

    class H2OIn {
        private Semaphore hSema = new Semaphore(2);
        private Semaphore oSema = new Semaphore(1);
        private CyclicBarrier cb = new CyclicBarrier(3);

        public H2OIn() {

        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            hSema.acquire();
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            releaseHydrogen.run();
            hSema.release();
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            oSema.acquire();
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            releaseOxygen.run();
            oSema.release();
        }
    }

}
