# JVM [1] -- 自动内存管理 

![image-20220419210619339](D:\blgs\source\imgs\image-20220419210619339.png)

## 0. Java的内存区域与溢出异常

### 0.0 Java内存区域划分

![image-20220419200223728](D:\blgs\source\imgs\image-20220419200223728.png)

**1. 方法区**

![image-20220419195855340](D:\blgs\source\imgs\image-20220419195855340.png)

- [参考方法区的变迁](https://blog.csdn.net/weixin_39860201/article/details/111122434?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2.pc_relevant_paycolumn_v3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2.pc_relevant_paycolumn_v3&utm_relevant_index=4)

- 变成了在直接内存中的元空间,大小不受限制，只受物理内存的限制。本来是受`-XX:MaxPermSize`的影响的。

  ```java
  -XX:MetaspaceSize
      class metadata的初始空间配额，以bytes为单位，达到该值就会触发垃圾收集进行类型卸载，
      同时GC会对该值进行调整：如果释放了大量的空间，就适当的降低该值；如果释放了很少的空间，
      那么在不超过MaxMetaspaceSize（如果设置了的话），适当的提高该值。
  -XX：MaxMetaspaceSize
      可以为class metadata分配的最大空间。默认是没有限制的。
  -XX：MinMetaspaceFreeRatio
      在GC之后，最小的Metaspace剩余空间容量的百分比，减少为class metadata分配空间导致的垃圾收集。
  -XX:MaxMetaspaceFreeRatio
      在GC之后，最大的Metaspace剩余空间容量的百分比，减少为class metadata释放空间导致的垃圾收集。
  ```

- 总结：方法区已经是一个**逻辑上的概念**了，功能已经被**堆和直接内存**实现了。

- 类型信息放在直接内存中，常量和静态变量放在了堆里面。

**2. 直接内存**

- 元空间在这实现。

- 使用-XX:MaxDirectMemorySize指定直接内存的大小,如果不指定和-Xmx一致。

**3. 堆**



![image-20220419201343686](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220419201343686.png)

![image-20220419195053174](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220419195053174.png)

- 对象分配、运行时常量池、静态字段。（或者说是常量和静态变量）。
- 垃圾回收的重点区域
- 要求连续的内存空间

**4. 栈**

- 栈上分配
- 标量替换

**5. 程序计数器**

- 无用
- 记住就行了
- 线程私有



### 0.1 Java对象

1. **对象的整体概述**

![image-20220408213140817](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220408213140817.png)









**2. 对象访问定位**

![image-20220408193646136](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220408193646136.png)

![image-20220408193458424](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220408193458424.png)



- 使用句柄池 -> 快速
- 使用直接指针访问 -> 对象移动的的时候，不用移动对象，直接移动实例的指针， 用户(栈中)的reference不用修改。
- 句柄池中存了两个：**类型和引用**









### 0.2 实战: OutOfMemoryError异常

![image-20220408213200708](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220408213200708.png)

1. 本地方法栈的两种，首先区分throwable和Exception的区别。使用Excetion接收不到JVM层面的异常

   ![img](https://gss0.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/4034970a304e251fb9f3af9daa86c9177f3e534a.jpg)

   



## 1.对象问题

### 1.1 对象的判亡

1. 使用标记(可达性分析)算法，从GC root中找到有引用的对象和没有被引用的对象。
2. 对被引用的对象进行一次标记，之后判断是否有必要执行finalize()方法
3. 如果finalize()**被调用过**，或者**没有被重写**，就没有必要执行。
4. 所以说一个对象到达死亡最多会进行两次标记，一般来说只会进行一次标记。

```java
package gcAndDistribution;

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes i am still alive");
    }

          /*调用这个方法之后把引用重新给挂到自己身上，
        那么这个对象就又有了强引用，
        就可达，
        就不会被垃圾回收器回收。
        */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
  
    }

    public static void main(String[] args) throws Throwable{
        SAVE_HOOK = new FinalizeEscapeGC();

        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead");
        }

        // 只能调用一个finalize方法，所以就算再次把这个对象指向也没有什么用了
        SAVE_HOOK = null;

        System.gc();
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            System.out.println("I am still alive");
        } else {
            System.out.println("no i am dead");
        }

    }
}

```



- 可以作为GC root的
  - 静态变量
  - 常量池中内容
  - 栈
  - 锁
  - 跨区域的引用。比如进行Eden收集的时候，把老年代。

![image-20220429112256653](D:\blgs\source\imgs\image-20220429112256653.png)



### 1.2 对象的内存布局

-  对象头
  - markword

![image-20220419175024129](D:\blgs\source\imgs\image-20220419175024129.png)

![image-20220419175040917](D:\blgs\source\imgs\image-20220419175040917.png)

- 对象头->类型指针
  - 指针压缩4B
  - 不压缩8B

- 其他变量

![image-20220419181355064](D:\blgs\source\imgs\image-20220419181355064.png)

![image-20220419181413766](D:\blgs\source\imgs\image-20220419181413766.png)

### 1.3 对象引用

看上面对象内存布局。

![image-20220419194625617](D:\blgs\source\imgs\image-20220419194625617.png)



- 强引用

  就是普通的应用

- 软引用：可以实现缓存机制和引用队列一起使用

  ```java
          ReferenceQueue referenceQueue = new ReferenceQueue();
          String s = new String("软引用，你好华为\n");
          SoftReference<String> sr = new SoftReference<String>(s, referenceQueue);
          System.out.printf(sr.get());
          s = null;
          System.gc();
          System.out.printf(sr.get());
          /*
          *  可以看出，虽然引用强引用已经指向了null, 但是内存仍旧没有释放，这是由于有
          * 弱引用的存在。弱引用只有在内存不够的时候在进行回收。
          * 为了防止内存泄漏，可以配合引用队列进行
          * */
          try{
  //              referenceQueue.remove();
  
              if (referenceQueue.poll() != null) {
                  System.out.printf("1:被回收\n");
              } else System.out.printf("0：没被回收\n");
          }catch ( Exception e) {
              System.out.println("你好,出错了");
              e.printStackTrace();
          }
          System.out.printf(sr.get());
  ```

  

  ![image-20220425163608739](D:\blgs\source\imgs\image-20220425163608739.png)

- 弱应用,

  ```java
          ReferenceQueue reference2 = new ReferenceQueue();
          WeakReference weakReference = new WeakReference(new String("weakReference hello 科兴"), reference2);
          System.out.println(weakReference.get());
          System.gc();
          System.out.println(weakReference.get());
          try {
              if (reference2.poll() != null) { //使用Poll 如果使用remove会进入阻塞态
                  System.out.printf("弱引用失去引用\n");
              } else System.out.printf("弱引用仍旧在被引用\n");
          }catch ( Exception e) {
              e.printStackTrace();
          }
  ```

  

- 虚引用: 用于管理堆外内存，netty中的zerocopy

  ```java
      private static final String TAG = "PhantomReferenceDemo";
  
      // 1、定义为成员变量 防止PhantomReference被回收
      private ReferenceQueue<String> mQueue = new ReferenceQueue<>();
      private PhantomReference<String> mReference;
  
      // 2、定义为成员变量 方便手动控制回收
      private String mTest;
  
      @Test
      public void test() {
          // 4、开启线程 监控回收情况
          new Thread(() -> {
              while (true) {
                  System.out.println("线程开启");
                  Reference<? extends String> poll = mQueue.poll();
                  if (poll != null) {
                      System.out.println("引用被回收");
                  }
                  try {
                      Thread.sleep(1000);
  
                  } catch (Exception e) {
                      System.out.println(e.getMessage());
                  }
              }
          }).start();
  
  
          // 3、
          // 直接用双引号定义的 存储在方法区
          // new出来的 存储在JVM堆
          // 使用System.gc进行强制垃圾回收，之后内存被回收，就可以看到
          mTest = new String("test");
          mReference = new PhantomReference<>(mTest, mQueue);
  
          try {
              Thread.sleep(1000);
              mTest = null;
              System.gc();
              System.out.println("手动制空完成");
              Thread.sleep(2000);
          } catch ( Exception e) {
              System.out.println(e.getMessage());
          }
      }
  ```

  ![image-20220425165754121](D:\blgs\source\imgs\image-20220425165754121.png)





### 1.4 对象创建

![image-20220419194615074](D:\blgs\source\imgs\image-20220419194615074.png)

1. 首先去看能否在常量池种定位到一个**类符号引用**，并判断是否经过了**加载、解析和初始化**的过程。(**首先创建类**)
2. 分配内存，内存的大小在类加载完成之后就可以确定了。
   - 指针碰撞法
   - 空闲链表发
   - 是否有内存压缩(compact) 决定的(parNew和serial是使用指针碰撞)。（CMS理论上是空闲链表，但是可以在拿到的大区域中进行指针碰撞）。
3. 设置对象头，(类型指针，hash, 对象的分带年龄)
4. 执行<init>方法

考虑并发

- CAS失败重试
- 线程本地分配缓冲TLAB（Thread Local Allocation Buffer） -XX +/-UseTLAB

考虑是否执行了<init>方法。

- new和 invokespecial是两条字节码指令。
- 如果字节码中在<init>之后生成了invokespecial的命令，就会，就是说invokespecial调用的是<init>方法。
- Java编译器编译的字节码文件一般都会。



**注意**

- 首先类加载：父类的静态和字面量加载进去
- 其次类分配内存
- 然后父类的域和初始化块进行初始化，调用父类的构造函数
- 然后子类的域和初始化块进行初始化，调用子类的构造函数。

![image-20220426180727654](D:\blgs\source\imgs\image-20220426180727654.png)

![image-20220426180631010](D:\blgs\source\imgs\image-20220426180631010.png)



## 2. 垃圾回收器

**关注点**

![image-20220428212812959](D:\blgs\source\imgs\image-20220428212812959.png)



**经典垃圾回收器**



![image-20220428215941360](D:\blgs\source\imgs\image-20220428215941360.png)

![image-20220428215958260](D:\blgs\source\imgs\image-20220428215958260.png)











### 2.1 CMS收集器

![image-20220428192705489](D:\blgs\source\imgs\image-20220428192705489.png)



**参数设置**

- `-XX:+UseConcMarkSweepGC`

![image-20220428192633986](D:\blgs\source\imgs\image-20220428192633986.png)

![image-20220428215040808](D:\blgs\source\imgs\image-20220428215040808.png)



### 2.2 G1收集器



![image-20220428185658685](D:\blgs\source\imgs\image-20220428185658685.png)



**G1收集器的参数**

- `-XX: +UseG1GC`
- 设置默认停顿时间，一般设置在200-300ms左右，如果小于这个量级，就很容易出现FullGc从而影响性能。



![image-20220428185609946](D:\blgs\source\imgs\image-20220428185609946.png)





**设计细节**

1. 年轻代的GC

![image-20220429095417561](D:\blgs\source\imgs\image-20220429095417561.png)



2. 老年代的GC

​	![image-20220429095445843](D:\blgs\source\imgs\image-20220429095445843.png)



- 此处的重点是采用了**原始快照**的方式
- 使用了**写前屏障**，进行记忆集和卡表的更新。
- 筛选回收，也就是独占清理需要**STW**



3. 混合回收

   ![image-20220429095459622](D:\blgs\source\imgs\image-20220429095459622.png)



### 2.3 **ZGC**







### 2.4 **shenadoah**

**重要的点**

- 并发整理，区别于G1的**并行整理**
- 不进行分代收集
- 连接矩阵，放弃了记忆集，不用维护，从而也没有伪共享。**使用连接矩阵**



****





### 2.4 性能指标

- 吞吐量: 用户线程的时间/用户线程的时间 + GC的时间
- 延迟：就是垃圾回收停顿用户线程的时间
- 内存占用情况。
- 其他：回收频率等

> 其中延迟和吞吐量还有内存是矛盾三角。随着硬件的发展，吞吐量和内存可以解决，但是延迟却会随之升高。

### 2.5 参数设置

- 产看默认的垃圾回收器
  - -XX:+PrintCommandLineFlags
  - jinof -flag **UseParNewGC 2343**
  - verbose:gc

- 各种收集器的使用设置
  - -XX:+UseSerialGC
  - -XX:+UseParNewGC            // -XX:ParalleGCThreads  限制线程数目
  - Parllel S和O是JDK8种默认的收集器
  - -XX: +UseG1GC
- 不同收集器的参数设置，具体看各种收集器
- 一般的配置
  - 老年代配置启动的阈值
  - 多线程配置线程的数目
  - 加上是否进行压缩，整理

### 2.6 日志分析

**1. 关于日志的参数**

![image-20220428190336763](D:\blgs\source\imgs\image-20220428190336763.png)



**2. Minor的回收日志**

![image-20220428191023402](D:\blgs\source\imgs\image-20220428191023402.png)



**3. Full Gc的日志**



![image-20220428191535134](D:\blgs\source\imgs\image-20220428191535134.png)



总结来说，可以得到下面的信息

- 年轻代的回收情况
- 老年代的回收情况
- 元空间/永久代的回收情况
- 堆总体的回收请款
- 回收花费的时间信息

**不同回收时间对比**

- user -- GC运行时间
- sys -- 阻塞等待系统调用时间
- real -- 运行的真实时间

- **User + sys >= real**

![image-20220517162207094](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220517162207094.png)





### 2.7 G1于CMS的对比



- 工作流程对比

![image-20220428213226797](D:\blgs\source\imgs\image-20220428213226797.png)

- 性能对比
  - CMS适合4-6G内存，G1适合6G+内存
  - CMS的最好性能和G1差不多,但是平均性能不如，最坏性能远远低于G1
  - G1可以进行延迟预测，CMS不能
- 算法对比

|          | CMS       |         G1          |
| :------- | --------- | :-----------------: |
| 并发标记 | 增量跟新  |      原始快照       |
| 回收算法 | 标记-清除 | 标记复制 + 标记压缩 |
| 写屏障   | 写后屏障  |      写前屏障       |

- 并发于并行
  - CMS只有在初始标记和重新标记阶段STW，所以延迟较小
  - G1在初始标记，重新标记，**并行回收**阶段都STW，延迟较小，但是仍旧有延迟。





## 3. HotSpot实现细节

<font size=4.7>**1.AOP和环形通知**</font>

- 将日志记录，性能统计，安全控制，事务处理，[异常处理](https://baike.baidu.com/item/异常处理)等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改变这些行为的时候不影响业务逻辑的代码。

<font size=4.5>**2. 函数式编程**</font>

<font size=4.6>**3.计组中的cache的映射方式**</font> 

- 全相联映射
- 直接映射
- 组相联映射

<font size=4.7>**4.并发的可达性分析**</font>

- 对象消失：**同时满足**
  - 插入了**一条或者多条**黑色到白色的引用
  - 同时，删除了**所有**灰色到白色的引用
- 增量更新：**记录插入**，重新扫描，黑色变成灰色。
- 原始快照：**记录删除**，重新扫描。

都是在并发结束阶段，冻结了用户的线程之后，进行这样的操作的。

**5. 写屏障与伪共享**

1. 写屏障，就是在进行赋值操作的时候，虚拟机插入一条指令，用来修改卡表，让卡表变脏
2. 伪共享:  并发操作中，如果需要修改的卡表标志位，在同一个cache行中，就可能需要同步，或者写回，或者无效化。

### 1. OopMap

1. 类加载完成之后，会把把所有的引用记录下来。
2. 即时编译器会记录栈中的内容,java线程栈中的对象。

> 类静态变量是否是活跃的？当然，要不然就不会有所谓的饿汉式的单例了。

### 2. 安全点

- 修改普通对象指针的点。在这个点，线程停下来，虚拟机修改一下普通对象指针。

- 通过**插入一条语句**实现。`test 0xef`。这条指令会检测是否需要中断。

### 3. 安全区域

在这个区域中，没有对对象指针的修改。

### 4. 并发可达性分析

三色染色法

增量更新 + 原始快照

### 5. 记忆集与卡表

1. 跨代引用
2. 跨区域引用

### 6. 写屏障

1. 判断是否需要增加卡表中的内容。也就是修改卡表的前置或者后置操作。通过插入一个方法生成。也就是所谓的AOP。
2. 可能导致伪并发（缓存无效化，写回，同步）：解决办法是先判断，经典的读者写者问题。
   - 因为读是同步的，所以不需要加锁。 可以加上volatile关键字进行解决，这种没有其他变量参与决策，并且当前值不受初值影响。直接就是判断。
   - 写是不同步的，所以需要枷锁。
   - **这里不需要同步，是因为都是为了变脏的。就算多写一次也是无所谓的。**

3. CMS以及之前的都采用写后屏障。G1和ZGC采用写前屏障。



## 4. 垃圾回收算法





![image-20220419211834827](D:\blgs\source\imgs\image-20220419211834827.png)



**1. 垃圾回收算法以及对应的收集器**

1. 标记复制算法
   - 分配一个Eden和两个Survivor区域，一般时8：1，也就是90%的内存利用率。
   - 在一个Eden和Survivor中进行对象的分配。
   - 收集的时候，把存活对象复制到保留的Suvivor区域中。
   - 如果分配失败，让老年代进行担保
2. 标记清除算法
3. 标记整理算法

**2. Full GC**

1. 老年代不够，或者有数组，不够连续
2. 方法区不够
3. 调用了System.gc
4. 没有配置CMS GC的前提下

**两个参数**

```java
/*

	-XX:+CMSFullGCsBeforeCompaction : 设置执行多少次不压缩的FullGc后来一次压缩的
	-XX:+UseCMSCompactAtFullCollection: 开关参数，用于在“享受”完Full GC服务之后额外免费赠送一个碎片整理的过程
*/
```





## 5. 内存分配策略

### 5.1 优先分配Eden区域

```java
package gcAndDistribution;


import org.junit.Test;

import java.lang.management.ManagementFactory;

/**
 *JVM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * 1. 优先在Eden区域进行分配              测试这个东西。
 * 2. 大对象直接进入老年代
 * 3. 长期存活的对象直接进入老年代
 * 4. 动态对象年龄判定
 * 5. 空间分配担保                       以及空间分配担保。
 */

public class TestAllocation {
    private static final int _1MB = 1024 * 1024;
    public static void testAllocation() {
        byte[] a1, a2, a3, a4, a5;
        a1 = new byte[_1MB * 2] ;
        a2 = new byte[_1MB * 2];
        a3 = new byte[_1MB * 2];
        a4 = new byte[_1MB * 2];
        a5 = new byte[_1MB * 4];

    }
    public static void main(String[] args) {
        testAllocation();
//        System.out.println(ManagementFactory.getRuntimeMXBean().getInputArguments());
    }
}

```

**运行结果如下**



![image-20220419202714056](D:\blgs\source\imgs\image-20220419202714056.png)



**结果分析以及全回收的情况**



![image-20220419210822503](D:\blgs\source\imgs\image-20220419210822503.png)



**两个参数**

```java
/*

	-XX:+CMSFullGCsBeforeCompaction : 设置执行多少次不压缩的FullGc后来一次压缩的
	-XX:+UseCMSCompactAtFullCollection: 开关参数，用于在“享受”完Full GC服务之后额外免费赠送一个碎片整理的过程
*/
```



### 5.2 大对象直接进入老年代

```java
public static void testAllocation2() {

        /*JVM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseG1GC
                    -XX:PretenureSizeThreshold=3145728


        */
        byte[] a1, a2, a3, a4, a5, a6;

        a1 = new byte[_1MB * 4] ;

    }
```



- PretenureSizeThreshold不能使用M作为单位，是按照字节进行计算的
- 另外只能在Serial 和 ParNew收集器上进行，Parallel收集器不支持

![image-20220428195047602](D:\blgs\source\imgs\image-20220428195047602.png)



### 5.3长期存活的对象进入老年代

![image-20220428200556109](D:\blgs\source\imgs\image-20220428200556109.png)



![image-20220428200636077](D:\blgs\source\imgs\image-20220428200636077.png)



### 5.4 动态对象年龄判定

​	可以根据第一个看出来，如果suvivor区域的对象年龄小于x的超过了总suvivor区域的50%，大于x的就可以直接不管年龄划分到老对象里面了。

1. 是否超过50%
2. 是否超过的都小于某一年龄x

同时满足以上两条，进入老年代。







**那么能否从from区域直接进入老年代呢？**

- 当然可以，看是否超过了年龄，或者满足动态年龄的判断。

- 因为from和to都是survivor区域的一部分啊。







### 5.5 空间担保策略

- 标记复制算法里面
- 如果eden区域不够
- 直接分配到老年代中





## 6. 内存的一些JVM参数的总结

### 6.1 垃圾回收器参数

### 6.2 内存大小的设置

### 6.3 其他





## 7. 面试问题

1. 举例栈溢出的情况，以及内存超出限制的情况?
2. 方法中的局部变量是否有线程安全问题？

如果是基本的数据类型，就不会有线程安全问题, 但是如果是引用类型的变量，就可能会出现线程安全问题。如果这个引用类型的变量指向了共享数据，并且共享数据没有实现同步机制，那么就是线程不安全的。

3. 什么是逃逸分析，举一个逃逸分析的例子

逃逸分析就是，线程内部的变量被其他变量所引用了，导致出现了线程不安全问题。

4. 内存泄漏
   - 对象不会被程序用到，但是GC回收不了
   - 对象的生命周期特别长，也是广义上的。
5. 内存泄漏的列子
   - 单例模式，应用大对象
   - 提供close 的资源未关闭，导致内存泄漏，比如数据库连接，网络连接和文件的I/O等

6. 内存泄漏的4种情况:

![image-20220526114305940](D:\blgs\source\imgs\image-20220526114305940.png)

## 参考文章

[throwable和Exception区别](https://blog.csdn.net/apple1414/article/details/101683528)
