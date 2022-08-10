package threadBase.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Zekun Fu
 * @date: 2022/6/28 20:49
 * @Description: 如类名
 */

@Slf4j(topic = "c.TestCountDownLatchWithThread")
public class TestCountDownLatchWithThread {


    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.submit(()->{
            try {
                log.debug("begin...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end...");
            latch.countDown();
        });

        service.submit(()->{
            try {
                log.debug("begin...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end...");
            latch.countDown();
        });

        service.submit(()->{
            try {
                log.debug("begin...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end...");
            latch.countDown();
        });

        service.submit(()->{
            log.debug("waiting...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("waitting end...");
        });

        service.shutdown();
    }
}
