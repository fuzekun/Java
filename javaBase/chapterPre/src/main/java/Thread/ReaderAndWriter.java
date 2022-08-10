package Thread;


import java.util.concurrent.CountDownLatch;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.Semaphore;  

public class ReaderAndWriter {  
    
  static Semaphore mutex = new Semaphore(1);  
  static Semaphore w = new Semaphore(1);  
  static int readcnt = 0 ;  
  static CountDownLatch latch = new CountDownLatch(150);  
  static int data = 0;  
    
  class Reader extends Thread{  
        
      int quantity;  
        
      Reader(int quantity){  
          this.quantity = quantity;  
      }  
        
      @Override  
      public void run(){  
          while(quantity > 0){  
              try {  
                  mutex.acquire();  
                  readcnt++;  
                  if (readcnt == 1)  
                      w.acquire();  
                  mutex.release();  
                  //read something  
                  System.out.println(getName() + " is reading ...¡¾data=" + data + "¡¿");  
                  mutex.acquire();  
                  readcnt--;  
                  if (readcnt == 0)  
                      w.release();  
                  mutex.release();  
                  quantity--;  
              } catch (InterruptedException e) {  
                  e.printStackTrace();  
              }  
          }  
          latch.countDown();  
      }  
  }  
    
  class Writer extends Thread{  
      int quantity;  
        
      Writer(int quantity){  
          this.quantity = quantity;  
      }  
        
      @Override  
      public void run(){  
          while(quantity>0){  
              try {  
                  w.acquire();  
                  data++;  
                  System.out.println(getName() + " is writing ...¡¾data=" + data + "¡¿");  
                  w.release();  
                  quantity--;  
              } catch (InterruptedException e) {  
                  e.printStackTrace();  
              }  
          }  
          latch.countDown();  
      }  
  }  
    
    
  public static void main(String[] args) throws InterruptedException {  
      long startTime = System.nanoTime();  
      ReaderAndWriter demo = new ReaderAndWriter();  
      ExecutorService service = Executors.newFixedThreadPool(150);  
      for(int i=0; i< 100; ++i){  
           service.execute(demo.new Reader(10));  
      }  
      for(int i=0 ; i< 50; ++i){  
          service.execute(demo.new Writer(1));  
      }  
      latch.await();  
      service.shutdown();  
      long endTime = System.nanoTime();  
      System.out.println(endTime - startTime + "ns");  
        
  }  
}  