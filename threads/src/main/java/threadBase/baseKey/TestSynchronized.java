package threadBase.baseKey;


/*
*
*
*   synchronized this和
*   synchronized 是相同的
* 因为锁的都是这个对象,不管别的方法怎么进来都一样
* 但是使用Lock就不同
* */
public class TestSynchronized {

    Object o = new Object();

    public synchronized void m1() {
        System.out.println("M1 正在执行");
        try {
            //wait();                     // 可以执行m2了
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m1 运行完成");
    }
    public synchronized void m2() {
        System.out.println("m2 正在运行,锁上了方法也无法运行，但是如果wait就可以了");
        notifyAll();
    }

    public void m3() {
        synchronized (o) {
            System.out.println("m3执行完成，不受this对象被锁的影响");
        }
    }

    public static void main(String[] args) {
        TestSynchronized t = new TestSynchronized();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        }).start();
        new Thread(()->{
            System.out.println("m2线程创建完成");
            t.m2();
        }).start();

        new Thread(()->{
            System.out.println("m3创建完成");
            t.m3();
        }).start();
    }

}
