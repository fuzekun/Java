package Thread;

public class ProduceAndConsume {
//    class FooBar {
//
//        /**
//
//         没有缓冲区的生产者消费者模型 -->简单协作模型
//         foo进行生产，
//         bar进行消费.
//         使用while会超时，所以使用Notify和lock进行
//
//         */
//        private int n;
//
//        private volatile boolean fooFinish = false;
//        private volatile boolean barFinish = true;
//        private Object lock = new Object();
//
//        public FooBar(int n) {
//            this.n = n;
//        }
//
//        public void foo(Runnable printFoo) throws InterruptedException {
//
//            for (int i = 0; i < n; i++) {
//                synchronized (lock) {
//                    if (!barFinish) lock.wait();
//                    barFinish = false;
//                    // printFoo.run() outputs "foo". Do not change or remove this line.
//                    printFoo.run();
//                    fooFinish = true;
//                    lock.notify();
//                }
//            }
//        }
//
//        public void bar(Runnable printBar) throws InterruptedException {
//
//            for (int i = 0; i < n; i++) {
//                synchronized (lock) {
//                    if (!fooFinish) lock.wait();
//                    fooFinish = false;
//                    // printBar.run() outputs "bar". Do not change or remove this line.
//                    printBar.run();
//                    barFinish = true;
//                    lock.notify();
//                }
//
//            }
//        }
//    }
class FooBar {

    /**

     生产者消费者模型
     foo进行生产，
     bar进行消费.
     mutex


     */
    private int n;

    private volatile boolean fooW = false;
    private volatile boolean barW = true;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!barW){Thread.sleep(1);}
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            barW = false;       // 必须放在后面
            fooW = true;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while(!fooW){Thread.sleep(1);}
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            fooW = false;      // 必须放在后面
            barW = true;
        }
    }
}


    public static void main(String[] args) {
        FooBar fooBar = new ProduceAndConsume().new FooBar(2);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            fooBar.foo(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(100);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    System.out.print("foo");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            fooBar.bar(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("bar");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}
