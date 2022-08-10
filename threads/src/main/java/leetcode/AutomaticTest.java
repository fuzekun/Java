package leetcode;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class AutomaticTest {
    class Foo {
        
        private boolean firstJobDone = false;
        private boolean secondJboDone = false;
        private Object lock = new Object();

        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {

//             printFirst.run() outputs "first". Do not change or remove this line.
            synchronized (lock) {
                printFirst.run();
                firstJobDone = true;
                lock.notify();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {

            synchronized (lock) {

                if (!firstJobDone) lock.wait();
                // printSecond.run() outputs "second". Do not change or remove this line.
                printSecond.run();
                secondJboDone = true;
                lock.notify();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {

            synchronized (lock) {
                // printThird.run() outputs "third". Do not change or remove this line.
                if (!secondJboDone) lock.wait();
                printThird.run();
            }
        }
    }

    public static void main(String[] args) {
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("first");
            }
        };
        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("second");
            }
        };

        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                System.out.println("third");
            }
        };

        Foo f = new AutomaticTest().new Foo();

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        f.first(run1);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        f.third(run3);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        f.second(run2);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
