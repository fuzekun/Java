package thread;

import org.omg.CORBA.TRANSACTION_MODE;

import java.util.TreeMap;

public class VolatileTest {
    public static volatile int race = 0;

    synchronized public static void increase2() {       // 使用synchronized可以得到正确结果
        race++;
    }

    public static void increase(){
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args){

        Thread[] threads = new Thread[THREADS_COUNT];

        for(int i = 0; i < THREADS_COUNT; i++){
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run(){
                    for(int i = 0; i < 10000; i++){     // 不能太小要不然还下cpu就执行完了
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        //等待所有累加线程都结束
        while(Thread.activeCount() > 2) { // 从IDEA上跑最少有两个线程，所以应该大于2
//            System.out.println(Thread.activeCount());
            Thread.yield();
        }
        System.out.println(race);
    }
}
