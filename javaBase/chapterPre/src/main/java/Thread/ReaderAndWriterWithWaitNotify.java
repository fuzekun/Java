package Thread;


import java.util.concurrent.CountDownLatch;  
  
public class ReaderAndWriterWithWaitNotify {  
  
    private static Object w = new Object();  
  
    static int readcnt = 0;  
    static int writecnt = 0;  
      
    static int data = 0;  
  
    static CountDownLatch latch = new CountDownLatch(150);  
  
  class Reader extends Thread {  
  
        int quantity;  
  
        Reader(int quantity) {  
            this.quantity = quantity;  
        }  
  
        @Override  
        public void run() {  
            synchronized (w) {  
                try {  
                    while (writecnt > 0) {  
                        w.wait();  
                    }  
                    readcnt++;  
                    while (quantity > 0) {  
                        System.out.println(getName() + " is reading...¡¾data=" + data + "¡¿" );  
                        quantity--;  
                    }  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
                readcnt--;  
                w.notify();  
                latch.countDown();  
            }  
        }  
    }  
  
    class Writer extends Thread {  
        int quantity;  
  
        Writer(int quantity) {  
            this.quantity = quantity;  
        }  
  
        @Override  
        public void run() {  
            synchronized (w) {  
                while (readcnt > 0 || writecnt > 0) {  
                    try {  
                        w.wait();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                writecnt++;  
                while (quantity > 0) {  
                    data++;  
                    System.out.println(getName() + " is writing...¡¾data=" + data + "¡¿" );  
                    quantity--;  
                }  
                writecnt--;  
                w.notify();  
                latch.countDown();  
            }  
        }  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        long startTime = System.nanoTime();  
        ReaderAndWriterWithWaitNotify demo = new ReaderAndWriterWithWaitNotify();  
        for (int i = 0; i < 100; ++i) {  
            demo.new Reader(10).start();  
        }  
        for (int i = 0; i < 50; ++i) {  
            demo.new Writer(1).start();  
        }  
  
        latch.await();  
        long endTime = System.nanoTime();  
        System.out.println(endTime - startTime + "ns");  
  
    }  
  
} 