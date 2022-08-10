package threadBase.JUC;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;
import java.util.concurrent.locks.StampedLock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/26 16:14
 * @Description:
 * 对JDK8加入的StampedLock进行测试
 *
 *
 * 1. 测试乐观读的时候，写来了怎么办？
 *      1, 可以直接加锁
 *      2. 如果加完了锁之后，仍旧没法读完，那么就可能读到新写入的数据。
 */
public class StampLockTest {
    public static void main(String[] args) throws InterruptedException {
        DataContainerStamped stamped = new DataContainerStamped(1);
        new Thread(()-> {
            try {
                int ans = stamped.read();
                System.out.println(ans);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        new Thread(()-> {
            try {
                stamped.write(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

@Slf4j(topic = "c.DataContainerStamped")
class DataContainerStamped {
    private int data;
    private final StampedLock lock = new StampedLock();
    public DataContainerStamped(int data) {
        this.data = data;
    }
    public int read() throws InterruptedException{
        long stamp = lock.tryOptimisticRead();  // 乐观读取
        Thread.sleep(2000);
        if (lock.validate(stamp)) {
            log.debug("read finish ... {}" , stamp);
            return data;
        }
        // 锁升级 - 读锁
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            Thread.sleep(2);
            log.debug("read finish {}", stamp);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) throws InterruptedException{
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            Thread.sleep(2);
            this.data = newData;
        }
        finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}