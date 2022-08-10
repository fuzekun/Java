# JUC并发包



![image-20220605201524589](https://fuzekun.oss-cn-hangzhou.aliyuncs.com/picGoimage-20220605201524589.png)



![image-20220605201600061](D:\blgs\source\imgs\image-20220605201600061.png)

## AQS

![image-20220622152115229](D:\blgs\source\imgs\image-20220622152115229.png)





对应的等待队列

```java
final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
```





## Lock





## Disrupt

## guava



## RentryLock的原理



[ReentryLock原理](https://blog.csdn.net/weixin_38568503/article/details/123292702)

[ReentryLock与Synchornized的区别](https://blog.csdn.net/weixin_38568503/article/details/123292702)



### 1.加锁竞争失败的流程

1. 竞争失败之后，进入等待队列，使用双向链表进行实现。

```java
final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {		// 1. 首先尝试获取， 成功返回
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) && // 2. 在进行一次尝试获取锁，如果失败，就Thread.interrupt，清除打断标记，然后可以重新park。
                    parkAndCheckInterrupt())			// 3. 如果失败了就直接上锁了	
                    interrupted = true;					// 只是设置打断标记，并没有抛出异常，所以是不可以打断的。
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }



    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             */
            return true;
        if (ws > 0) {
            /*
            所有的前驱结点都被取消了，就应该执行自己了。
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }
```

- 等待队列中state = -1，表示唤醒下一个元素。等于0表示末尾结点。x

![image-20220623164946255](D:\blgs\source\imgs\image-20220623164946255.png)



![image-20220623165237280](D:\blgs\source\imgs\image-20220623165237280.png)



![image-20220623165217620](D:\blgs\source\imgs\image-20220623165217620.png)



![image-20220623164810369](D:\blgs\source\imgs\image-20220623164810369.png)

















### 2. 唤醒阻塞线程的流程





```java
public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);     // 唤醒后记结点
            return true;
        }
        return false;
    }



// 如何进行tryRelease呢？
// 
 protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
```



![image-20220624103653849](D:\blgs\source\imgs\image-20220624103653849.png)





![image-20220624104326808](D:\blgs\source\imgs\image-20220624104326808.png)

### 3. 可重入的流程



```java
 final boolean nonfairTryAcquire(int acquires) {									
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {// 如果是当前线程,就多加上一个
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }


 protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {	// 只有当c == 0的时候才进行释放
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }



// 解锁
private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         */
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0);

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
    
    	// 解锁，让线程的unpark。
        if (s != null)		
            LockSupport.unpark(s.thread);
    }

```



### 4. 不可打断原理



```java
final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;								// 只是设置了打断标记，并没有进行真正的打断
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
```



```java
private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);											// 如果打断标记为true，就没法进行park了.
        return Thread.interrupted();
    }
```



### 5. 可打断原理



```java
 private void doAcquireInterruptibly(int arg)
        throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    throw new InterruptedException();					// 如果已经被打断了，直接抛出异常。
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
```





### 6. 非公平锁的实现

```java
  final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
      		// 如果状态为0，直接去进行锁的抢占。
            if (c == 0) {														
                if (compareAndSetState(0, acquires)) {								
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
```



### 7. 公平锁的实现

```java
 static final class FairSync extends Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        final void lock() {
            acquire(1);
        }

        /**
         * Fair version of tryAcquire.  Don't grant access unless
         * recursive call or no waiters or is first.
         */
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                // 看是否有前驱节点
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

```



```java
 public final boolean hasQueuedPredecessors() {
        // The correctness of this depends on head being initialized
        // before tail and on head.next being accurate if the current
        // thread is first in queue.
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s;
     // 如果队列不空，并且头节点是自己才需要执行。
        return h != t &&
            ((s = h.next) == null || s.thread != Thread.currentThread());
    }
```





### 8. 条件变量的原理

**仍旧是AQS实现的await**

- await流程
- notify流程





await的作用

- 线程进入等待队列
- 线程状态变成等待状态
- **释放线程上的锁。**

```java
        public final void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();						// 创建新的结点
            int savedState = fullyRelease(node);					// 释放线程上的锁，如果线程是可重入的，那么就需要把所有的锁都进行释放。
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }



// 创建
 private Node addConditionWaiter() {
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
     		// -2 代表的是等待状态
            if (t != null && t.waitStatus != Node.CONDITION) {  
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

// 完全释放
 final int fullyRelease(Node node) {
        boolean failed = true;
        try {
            int savedState = getState();
            if (release(savedState)) {
                failed = false;
                return savedState;
            } else {
                throw new IllegalMonitorStateException();
            }
        } finally {
            if (failed)
                node.waitStatus = Node.CANCELLED;
        }
    }

// 具体释放
    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }
```









![image-20220623173807769](D:\blgs\source\imgs\image-20220623173807769.png)

![image-20220623175703624](D:\blgs\source\imgs\image-20220623175703624.png)





```java
public final void signal() {
    // 首先做检查
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException(); 
   // 找队列头的元素         
   Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }


	// 实际工作
     private void doSignal(Node first) {
            do {
                
                if ( (firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            }
         // 转移到队列中
         	while (!transferForSignal(first) &&          // 打断或者超时，线程结点就会失效
                     (first = firstWaiter) != null);   // waitState为0
        }
```



![image-20220623180100329](D:\blgs\source\imgs\image-20220623180100329.png)





## 读写锁的应用





### 0. 体验代码



```java
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

```



![image-20220623183536928](D:\blgs\source\imgs\image-20220623183536928.png)

### 1. 注意事项

 



- 读写锁不支持条件变量
- 重入时不支持升级：持有读锁情况下去获取写锁,导致写锁永久等待
- 支持降级：持有写锁的情况下，获取读锁，可以。





![image-20220623183727823](D:\blgs\source\imgs\image-20220623183727823.png)

















### 2. 应用缓存

- 版本1：并发高危版本。
- 没有保护机制
- 清空缓存顺序不行。



```java
package threadBase.JUC;
import com.mysql.cj.Session;
import com.sun.javafx.image.impl.ByteIndexed;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import threadBase.JUC.model.Student;
import threadBase.model.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 11:22
 * @Description:
 */
public class TestGenericDao {



    public static void main(String[] args) throws Exception{
        GenericDao g = new GeneritDaoCached();

        // 查询三次
        Student stu = g.getById(1);
        System.out.println(stu);
        stu = g.getById(1);
        System.out.println(stu);
        stu = g.getById(1);
        System.out.println(stu);
        // 更新一次
        Student updateS = new Student(1, "fzk", "177");
        g.update(updateS);
    }


}
@Slf4j(topic = "c.GeCached")
class GeneritDaoCached extends GenericDao {
    private GenericDao dao = new GenericDao();
    private Map<Integer, Object>sqlMap = new Hashtable<>(); // 使用id作为缓存

    @Override
    public Student getById(int id) throws IOException {

        // 先从缓存中找，找到了直接返回
        if (sqlMap.containsKey(id)) {
            log.debug("缓存中存在，不用查询数据库了");
            return (Student) sqlMap.get(id);
        }
        Student stu = super.getById(id);
        sqlMap.put(id, stu);
        // 如果没有，查询数据库
        return stu;
    }

    @Override
    public void update(Student student) throws IOException {
        // 清空缓存， 之后再进行修改
        sqlMap.remove(student.getId());
        super.update(student);
    }
}

```







![image-20220624152837685](D:\blgs\source\imgs\image-20220624152837685.png)



- 集合类使用的不好
- 没有保护机制。
- 缓存没起到作用，策略问题。



**使用读写锁改进**



- 跟新
  - 直接加上写锁
- 读取
  - 访问mp，加上读锁
  - **如果没有，释放读锁，之后加上写锁**
  - 双重检查，如果已经有人更新过了，不用再进行更新mp了。



```java

@Slf4j(topic = "c.GeCached")
class GeneritDaoCached extends GenericDao {
    private GenericDao dao = new GenericDao();
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private Map<Integer, Object>sqlMap = new Hashtable<>(); // 使用id作为缓存

    @Override
    public Student getById(int id) throws IOException {
        rw.readLock().lock();
        try {
            // 先从缓存中找，找到了直接返回
            if (sqlMap.containsKey(id)) {
                log.debug("缓存中存在，不用查询数据库了");
                return (Student) sqlMap.get(id);
            }
        }finally {
            rw.readLock().unlock();
        }

        rw.writeLock().lock();
        try {
            // 多个线程，使用双重检查, 节约了资源
            if (sqlMap.containsKey(id))
                return (Student) sqlMap.get(id);

            Student stu = super.getById(id);
            sqlMap.put(id, stu);
            // 如果没有，查询数据库
            return stu;
        }
        finally {
            rw.writeLock().unlock();
        }
    }

    @Override
    public void update(Student student) throws IOException {
        rw.writeLock().lock();
        try {
            // 先更新后移除，加上锁就无所为了
            super.update(student);
            sqlMap.remove(student.getId());
        } finally {
            rw.writeLock().unlock();
        }
    }
}

```



- 适合都多写少的场景
- 没有考虑容量和缓存的过期问题。
- 适合单机
- 并发性还是低，只用了一把锁，锁的粒度太大。**对表的读写锁**





### 3. 读写锁的原理

 

![image-20220624154413429](D:\blgs\source\imgs\image-20220624154413429.png)











## 一些问题



1. 为什么要尝试差不多4次获取，才进行加锁？

```java
为了节约资源。
    防止前驱节点被取消了，如果所有的前驱节点都被取消了，那么就应该直接让自己成为第一个结点。
    不应该加上锁。
```





1. LockSupport的park如果线程处于被打断状态，是否就没法进行？

```java
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
```

- 先解锁，只能解锁一次，因为只有一个帐篷，一个大小的队列可以存放对应的内容。
- 上锁，可以上锁多次
- 必须线程的打断标记为false的时候才可以进行上锁。



3. 怎么看非公平和公平最后调用的是哪一个方法呢？这个判断逻辑是从哪里实现的呢？

```java
public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }
```

- 在构造方法这里，如果是采用的公平锁，直接使用公平的对象。算是工厂模式的一种。解决了类型选择问题。

4. 为什么加上空结点，但是条件变量不进行哨兵结点的创建
5. 为什么在释放锁的时候，需要先进行线程的置空，然后进行状态改变？

```java
考虑这个顺序
    1. 改变状态
    2. 另一个修改了持有者为自己
    3. 然后这个线程把持有者设置成null
    
```



6. 为什么读写锁的cacheData中的cacheValid需要加上volatile，并且需要双重检测。





```java


上面的双锁检测的，是因为需要获取读锁，所以就算重新获取写锁之后，相当于进行重新检查。加上volatile关键字的意义在于保证可见性。不用自己的缓存而是从内存中进行读取。 



双锁检测的核心是什么？
    
    1. 核心是节约synchornized的资源。如果直接synchornied浪费，所以需要进行检验。
    2. 
    
    
    
    public class Singleton {  
    private volatile static Singleton singleton;  
    private Singleton (){}  
    public static Singleton getSingleton() {  
    if (singleton == null) {  
        synchronized (Singleton.class) {  
            if (singleton == null) {  
                singleton = new Singleton();  
            }  
        }  
    }  
    return singleton;  
    }  
}
```



7. 操作系统的双锁检测是什么东西？操作系统的软件互斥的几种方式。



8. 为什么tryRelease是使用的ReentryLock的tryRelease。

- 如下面的类图所示。Sync继承了AQS。所以可以说是一个自定的AQS，其中的tryRelase和tryAquire是抽象方法。由其进行了实现。
- 所以说调用的是ReentryLock中的公平或者不公平的实现。



![image-20220624104943151](D:\blgs\source\imgs\image-20220624104943151.png)





9. state的取值以及表示的含义

```java
0:最后一个结点
-1 : 表示唤醒后面的结点
-2 : 表示加入到了condition的wait队列
1: 代表本结点被取消了。
```





