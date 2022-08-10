package threadBase.baseKey;

import java.sql.Connection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    Lock lock = new ReentrantLock();
    public void m1() {

        System.out.println("m1正在执行");

        try {
            lock.wait();
            Thread.sleep(1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m1执行完成");
        lock.unlock();
    }

    public void m2() {
        lock.lock();
        System.out.println("m2方法执行完成");
        lock.unlock();
    }

    public static void main(String[] args) {

    }
}
