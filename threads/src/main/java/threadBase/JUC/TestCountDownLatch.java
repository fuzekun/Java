package threadBase.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author: Zekun Fu
 * @date: 2022/6/28 20:42
 * @Description:    如类名
 */
@Slf4j(topic = "c.TestCountDownLatch")
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                log.debug("begin...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end...");
            latch.countDown();
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end...");
            latch.countDown();
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end...");
            latch.countDown();
        }).start();
        log.debug("waiting...");
        latch.await();
        log.debug("waitting end...");
    }
}
