package threadBase.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/23 18:22
 * @Description:
 * 读写锁的测试
 * 读者写者问题
 */
@Slf4j(topic = "c.WriteLockTest")
public class WriteLockTets {


    // 需要保护的数据
    private Object data;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    public Object read() throws InterruptedException{
        Thread t = Thread.currentThread();
        log.debug("{}获取读锁", t);
        readLock.lock();
        try {
            log.debug("{}读取", t);
            Thread.sleep(1000);
            return data;                    // 因为不会进入catch，所以就算有问题也一定会返回
        } finally {
            log.debug("{}释放读锁", t);
            readLock.unlock();
        }
    }

    public void write() throws InterruptedException{
        Thread t = Thread.currentThread();
        writeLock.lock();
        log.debug("{}获取写锁", t);
        try {
            log.debug("{}写入", t);
            Thread.sleep(1000);
        } finally {
            log.debug("{}释放写锁", t);
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        WriteLockTets test = new WriteLockTets();
        new Thread(()-> {
            try {
                test.write();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                test.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()-> {
            try {
                test.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
