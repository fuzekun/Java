package threadBase.baseKey;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 16:51
 * @Description: 测试Park和unPark
 * 1. 进入等待状态
 * 2. 不用配合锁
 * 3. 实现精准唤醒
 * 4. 可以先unpark后park
 * 5. 如果打断标记为true就没法进行park
 *
 * 底层的实现是一个couter进行计数。
 * park一次就让counter--；如果park == 0就让线程进入等待队列中
 * unpark一次就让      counter++， 但是counter <= 1
 *
 * 所以在没有执行park的情况下，
 * 连续两次unpark和一次unpark的效果是一样的。
 *
 */
public class TestPark {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()-> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("锁住了两次");
            LockSupport.park();
            LockSupport.park();
            System.out.println("继续执行");
        });
        t.start();
        Thread.sleep(1000);
        LockSupport.unpark(t);
        System.out.println("解锁1次完成");
//        Thread.sleep(1000);       // 如果直接进行两次解锁，就相当于解锁了一次
        LockSupport.unpark(t);
        System.out.println("睡眠后在进行第二次解锁");


        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(100);
                Thread.currentThread().interrupt();                                     // 直接设置打断标记为true, 如果不进行后续的处理，没有什么大问题
                LockSupport.park();
                System.out.println("锁不住了... 这里还可以继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

    }
}
