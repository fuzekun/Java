package threadBase.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Zekun Fu
 * @date: 2022/6/28 20:56
 * @Description:
 * 模拟王者荣耀的等待所有玩家的机制
 */
@Slf4j(topic = "c.TestCountLatch3")
public class TestCountDownLatch3 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        String[] all = new String[10];
        Random r = new Random();
        CountDownLatch latch = new CountDownLatch(10);
        for (int j = 0; j < 10; j++) {
            int k = j;
            service.submit(()->{
                for (int i = 0; i <= 100; i++) {
                    try{
                        Thread.sleep(r.nextInt(100));
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[k] = i + "%";
                    System.out.print("\r" + Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println();
        log.debug("游戏开始");
        service.shutdown();
    }
}

