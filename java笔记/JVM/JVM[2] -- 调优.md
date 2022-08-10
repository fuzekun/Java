# JVM调优

## 常见的面试问题

## 调优描述



![image-20220521093956258](D:\blgs\source\imgs\image-20220521093956258.png)

![image-20220517151054255](D:\blgs\source\imgs\image-20220517151054255.png)

### 1. 背景

- **硬件层面**
- **软件层面**
- **JVM部署层面**

### 2. JVM调优的过程

- **部署之前**
  - GC的选择
  - 运行时参数的设定
- **部署之后**
  - 性能的监控
  - 性能分析
  - 性能的调优



### 3. 性能监控复盘

- **性能对比**
- **性能监控、分析、调优的经验总结**
- **对软件设计和硬件要求的总结**





## 1. 性能监控工具

- jps [-v] [-l] [-q] [-m]
  - -v : 打印日志信息
  - -l : 打印详细信息
  - -q : 打印简略信息
  - -m：输出传给main类的参数
  - root:node2:支持远程连接 [主机名:端口号]
- jstat -option :产看JVM的统计信息 
  - option参数和普通的参数
  - 普通参数
    - inteval
    - count
    - -t : 程序运行的时间
    - -h
  - 顺序 `jstat -option [-普通参数] 进程号 inteval count`
  - 比如 `jstat -class [-t] 9000 1000 10`

![image-20220516110126047](D:\blgs\source\imgs\image-20220516110126047.png)

- option的日志内容

![image-20220516112348291](D:\blgs\source\imgs\image-20220516112348291.png)



- 使用-gc或者-gcutil进行内存泄漏或者内存占比的分析
  - OU一直上升，说明内存有可能泄漏了
  - 用gc时间/运行时间占比20%说明内存压力比较大。占比90%说明随时可能溢出内存。

- jinfo的使用
  - 一些可以动态修改的参数 `java -XX:+PrintFlagsFinal -version | findstr "manageable"`

![image-20220517153805962](D:\blgs\source\imgs\image-20220517153805962.png)



- `java -XX:+PrintFlagsInitial`:查看所有JVM参数启动的初始值
- `java -XX:+PrintFlagsFinal`：产看所有JVM参数的最终值
- `java -XX:+PrintCommandLineFlags` :产看被用户或者JVM设置过的详细的XX参数的名称和值



![image-20220517154622641](D:\blgs\source\imgs\image-20220517154622641.png)





- jmap:
  - -heap pid :显示堆的配置信息和使用信息。可以和`jstat`对比使用
  - -dump 导出内存镜像文件 `jmap -dump:live,format=b, filename="d:\1.txt" pid`  # 导出存活的对象
  - -histo：类的大小和对象实例的个数
  - 自动方式导出 `-XX:+HeapDumpOnOutMemoryError` 或者 `-XX:HeapDumpPath=文件名`

![image-20220517160623224](D:\blgs\source\imgs\image-20220517160623224.png)

![image-20220517155333007](D:\blgs\source\imgs\image-20220517155333007.png)

![image-20220517160000990](D:\blgs\source\imgs\image-20220517160000990.png)



![image-20220517160418102](D:\blgs\source\imgs\image-20220517160418102.png)

和jstat的对比



![image-20220517161252148](D:\blgs\source\imgs\image-20220517161252148.png)





- jhat ，再7000端口进行分析

![image-20220517161450236](D:\blgs\source\imgs\image-20220517161450236.png)

![image-20220517161517754](D:\blgs\source\imgs\image-20220517161517754.png)



- jstack -l ： 加上锁的信息

![image-20220519144245578](D:\blgs\source\imgs\image-20220519144245578.png)

![image-20220521102940717](D:\blgs\source\imgs\image-20220521102940717.png)



> 对于死锁，**jstack只可以检测到由于竞争锁**而引起的死锁问题。检测由于**循环等待**而导致的死锁信息。
>
> 也就是说：如果两个线程永久处于**blok状态，才会被检测为死锁**；而**永久处于wait状态**是**不会**被检测到死锁的。





- jcmd : 可以执行stat的基本所有指令。
  - jmap的替换
  - jinfo的替换
  - jps替换
  - PrintFlagsFinal的替换

![image-20220521101546826](D:\blgs\source\imgs\image-20220521101546826.png)





****

****

****



**图形化的界面**























## 2. 性能指标

- GC的时间
- 吞吐量
- 延迟，STW的时间
- 并发数
- 内存占比

注意吞吐量和延迟的区别：

- 对于web应用程序来说，吞吐量越高，延迟越低。 此处的延迟是用户体验到的延迟。
- 对于GC来说，吞吐量越高，延迟越高。



**一些占用时间的过程**

- 垃圾回收
- 热点代码编译
- 类加载





## 3. 性能分析

- 无监控，不调优；无分析，不调优。
- 监控和分析的一句
  - 运行日志 ： 异常堆栈的日志，以及垃圾回收日志，还有就是热点代码编译的时间，类加载时间
  - 线程快照：时间上的快照
  - 堆存储快照: 空间上的快照





## 4. 性能调优









## 5. 垃圾回收器选择

## 6. 运行时参数的设定

## 7. 实战案例

### 1.单体应用在大内存硬件上部署

- 通过单独的Java虚拟机实现管理大量的Java堆内存
- 同时使用若干个JVM，建立逻辑集群来应用硬件资源

### 2. 集群同步导致内存溢出

### 3. 堆外内存导致溢出的错误

- 没有明显的堆存储快照
- 大量使用NIO

- 经验总结：管理32应用或者小内存时，应该注意下面的内存
  - 直接内存
  - 线程堆栈
  - Socket缓存区
  - JNI代码
  - 虚拟机和垃圾收集器



### 4. 外部命令导致系统缓慢

- 由于进程调用导致的系统缓慢，处理器资源占用率很高。
- 自己的毕业设计里面 -- 加载的照片很慢的原因就是存在系统调用。没法通过Django框架直接进行图片的处理，还需要进程调用。
- 问题，找不到自己电脑的Pytorch环境和tensorflow环境。
  - 把下载好的tenflow的包和pytorch的包，放在项目的model里面进行引用。
  - 然后调试环境就行了。
- 问题2：图片的数据库存储，否则的话会查询很慢，因为没法建立索引。应该按照小时建立索引。
  - 增加、删除都没什么问题
  - 查找会很慢，也不支持修改。
- 问题3：页面的问题很大，一些功能没有实现，加上自己对前端的不熟悉，导致前端很丑，并且修改很困难。



### 5. 服务器虚拟机进程崩溃

- 出现了Connect Reset异常。

- 把异步调用修改成消息队列。

- 写的发送消息的那个阻塞I/O就是这样的问题。

### 6. 不恰当的数据结构导致内存占用过大

- 使用Hahs<Long, Long>封装的对象，使用的存储效率只有18%。

- 治标，让对象立马进入老年代。
- 治本，修改代码

### 7. 由Windows虚拟机导致长时间停顿(存在问题)

- 情景描述
  - 桌面应用程序，占用内存不大，所以认为不是垃圾回收的原因。最后打印了日志信息`-XX+PrintGCDate-Stamps -Xloggc:gclong.log`后，发现是由于gc准备收集到真正开始收集的的时间消耗了很长时间。
  - 最小化的时候，资源监视器中内存占用大幅度减小，但是JVM的内存却没有变化。

- 原因：最小化导致工作内存被自动交换到磁盘内存当中。
- 解决方案：加入`-Dsun.awt.keepWorkingSetOnMinimize=tre`，不让它最小化的时候写入内存。

### 8. 由安全点导致的长时间停顿

- 现象：
  - 用户线程停顿时间特别长 2.26s,但是垃圾收集时间很短，也只有0.14s
  - 使用`-XX:+PrintSafepointStatisticsCount=1`看到waited_to_block有两个。也就是等待两个线程，并且spin时间超过了2s。
- 解决：
  - 找到最这两个线程`-XX:safepointTimeoutDelay=2000`
  - 使用了int类型的可数循环
  - 改成long类型的循环，这样每次循环就会检查安全点，不用等到跑完才会进行垃圾回收。



## 实战：Eclipse运行速度调优

### 0. 使用工具

- VisualVM
- Ecplipse
- jdk5，6，7

### 1. 初始状态

- 启动时间15s
- GC ：4.149s
  - full GC 19次，3s
  - Minor GC , 278次，1s
- 类加载9115个:4s
- 及时编译:2s
- 内存分布：40M新生代，472M老年代

### 2. 解决方案

- 升级硬件或者软件
  - 升级到jdk6
- 永久代溢出，加上`-XX:MaxPermSize=256M`
  - JDK5中已经默认加上了
  - Eclipse不认识Oracle公司的虚拟机，认识sun公司的虚拟机，所以参数就不能传进去，就需要手动设置。
  - 概述 > JVM参数。
- 编译时间和类加载时间优化
  - 去掉验证阶段: `-Xverify:none`
  - ~~去掉编译时间`-Xint`~~

- 调整内存设置控制垃圾收集频率，主要是Full GC
  - 分析每次回收老年代都会**扩容**，(日志中括号的内容是老年代的总大小)
  - 直接设定堆的大小为最大值。
  - 调整初始新生代的大小为128m, 初始堆区大小为512m,老年代384m。
  - 禁用System.gc()。`-XX:+DisableExplicitGC`。
- 选择收集器降低延迟
  - `-XX:+UseConcMarkSweepGC` + `-XX:+UseParNewGC`。第二个可以不加，因为是默认的新生代收集器。使用CMS的时候。



### 3. 总结

- `-Xverify:none`
- `-Xmx512m`
- `-Xms512m`
- `-Xmn128m`
- `-XX:PermSize=256m`
- `-XX:MaxPermSize=256m`
- `-XX:+DisableExplicitGC"`
- `-XX:+UseParNew`
- `-XX:+UseConcMarkSweepGC`
- ``-XX:CMSInitialtingOccupanyFrction=85`			#VMS的初始并发占内存用率为15%。
- `-Xnoclassgc`
