# 不可变与flyWeight



## 连接池的实现，注意不是线程池



```java
package threadBase.threadPool;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import threadBase.unsafe.MyAtoInteger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author: Zekun Fu
 * @date: 2022/6/6 9:40
 * @Description: 手动实现线程池
 */

@Slf4j(topic = "c.Pool")
public class MyPool {

	
    
    // 进行测试
    public static void main(String[] args) {
        final MyPool pool = new MyPool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Connection conn = pool.borrow();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    pool.free(conn);            // 保证一定会被释放, 防止catch之后，线程不释放
                }

            }).start();
        }
    }

    private final int poolSize;             // 大小
    private Connection[] connections;       // 连接对象数组
    private AtomicIntegerArray state;       // 状态 0:空闲， 1：繁忙

    public MyPool(int poolSize) {
        this.poolSize = poolSize;
        this.connections = new Connection[poolSize];
        this.state = new AtomicIntegerArray(new int[poolSize]);
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnectoin("conn" + i);
        }
    }

    // 借连接
    public Connection borrow() {
        while (true) {
            for (int i = 0; i < poolSize; i++) {
                // 如果有空闲连接
                if (state.get(i) == 0) {
                    if (state.compareAndSet(i, 0, 1)) {
                        log.debug("borrow {}", connections[i]);
                        return connections[i];
                    }
                }
            }
            // 如果没有空闲连接, 让当前线程等待
            synchronized (this) {
                try {
                    log.debug("wait...");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        // 2. 归还连接
    public void free (Connection conn) {
        for (int i = 0; i < poolSize; i++) {
            if (connections[i] == conn) {
                state.set(i, 0);
                synchronized (this) {
                    log.debug("free {}", conn);
                    this.notifyAll();
                }
            }
        }
    }
}

class MockConnectoin implements Connection {

    private String name;
    MockConnectoin(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "connection:" + name;
    }
}

```



- 等待超时
  - 保护性暂停
  - waitTime = waitTime - spendTime = waitTime - (endTime - startTime)
- 连接的动态增长 1<<n, 在没有超过cpu核心数目的情况下
- **为什么使用synchornized**
  - 不用逻辑上没有错误
  - 但是由于数据库的增删改查一般比较耗时，所以使用cas操作和让cpu空转，从而浪费时间。

![image-20220606101227140](D:\blgs\source\imgs\image-20220606101227140.png)



## 不可变类的分析



1. 共享与缓存
2. final防重写
3. 保护性复制



## final原理



### 设置原理



![image-20220606101954298](D:\blgs\source\imgs\image-20220606101954298.png)



### 获取原理



速度：

栈 > 运行时常量池 > 元空间



堆：

- 元空间：方法区域的一部分，存放类信息
- 运行时常量池：常量和静态变量， 符号引用。
- 对象的分配

![image-20220606103531038](D:\blgs\source\imgs\image-20220606103531038.png)
