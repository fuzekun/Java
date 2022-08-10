# Java异常机制







## 异常机制的背景

1. c++程序出错直接返回-1，或者直接报一个Segment fault，不知是什么意思。
2. 程序员使用类库的时候，发现出错了，却不知道自己传的数据到底有什么问题。
3. 系统使用人员使用系统的时候，系统突然崩溃了，不知道自己什么操作有什么问题。



## 异常机制实现

![](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220430182240211.png)



[JVM如何处理异常](https://cloud.tencent.com/developer/article/1786449)

### 1. 代码层面

**实际代码**

```java
try
{
    //就算这里return了,也会调用finally语句，如果finally中也有return，就会抑制这个return。
}
catch (Exception e) 
{
    
}
finally 
{
    
}
```

**类型**

![img](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/yddr2i34j.png)

### 2. 虚拟机层面

**异常抛出机制**

1. 显式抛出：由用户程序写代码进行抛出，必须显式捕获。	通常是Exception。
2. 隐式抛出：由虚拟机的安全检查进行抛出，比如数组越界检查、除以0错误等等。不用用户程序捕获。通常是RunTimeError或者Error

**异常捕获机制**

- 首先JVM为**每一个方法**生成一个异常表。包括`{监控起始语句 监控终止语句 异常类型}`

```java
public static void main(String[] args) {
  try {
    mayThrowException();
  } catch (Exception e) {
    e.printStackTrace();
  }
}
// 对应的 Java 字节码
public static void main(java.lang.String[]);
  Code:
    0: invokestatic mayThrowException:()V
    3: goto 11
    6: astore_1
    7: aload_1
    8: invokevirtual java.lang.Exception.printStackTrace
   11: return
  Exception table:									// 异常表条目
    from  to target type
      0   3   6  Class java/lang/Exception  
12345678910111213141516171819
```

其中的from和to分别对应着监控的字节码的代码。类型就是捕获的Exception类型。





![preload](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/fwf54ovasi.png)

- 把finally中的代码“内联”到try和catch中各一份，**或者**只在catch中放一份，在try中放入跳转指令。
- 然后如果**Exception就直接进入catch语句**，**如果遇见Error，直接执行了finally**。

```java
public class Foo {
  private int tryBlock;
  private int catchBlock;
  private int finallyBlock;
  private int methodExit;
 
  public void test() {
    try {
      tryBlock = 0;
    } catch (Exception e) {
      catchBlock = 1;
    } finally {
      finallyBlock = 2;
    }
    methodExit = 3;
  }
}
 
 
$ javap -c Foo
...
  public void test();
    Code:
       0: aload_0
       1: iconst_0
       2: putfield      #20                 // Field tryBlock:I
       5: goto          30
       8: astore_1
       9: aload_0
      10: iconst_1
      11: putfield      #22                 // Field catchBlock:I
      14: aload_0
      15: iconst_2
      16: putfield      #24                 // Field finallyBlock:I
      19: goto          35
      22: astore_2
      23: aload_0
      24: iconst_2
      25: putfield      #24                 // Field finallyBlock:I
      28: aload_2
      29: athrow
      30: aload_0
      31: iconst_2
      32: putfield      #24                 // Field finallyBlock:I
      35: aload_0
      36: iconst_3
      37: putfield      #26                 // Field methodExit:I
      40: return
    Exception table:
       from    to  target type
           0     5     8   Class java/lang/Exception
           0    14    22   any
 
  ...
```

可以看到，编译结果包含三份 finally 代码块。其中，前两份分别位于 try 代码块和 catch 代码块的正常执行路径出口。最后一份则作为异常处理器，监控 try 代码块以及 catch 代码块。它将捕获 try 代码块触发的、未被 catch 代码块捕获的异常，以及 catch 代码块触发的异常。



### 3. jdk7的 try-with-resource语句的使用

**背景**

finally中的语句也有可能抛出错误，但是关闭资源这个错误的根本原因，可能式第一个被抛出的错误引起的，而被抛出的错误确实finally语句中的错误，从而导致了根本错误被supress。

**代码层面实现**

所以为了得到第一个错误，加入了Surpress机制，反映到代码层面就是try-with-resource语句。使用try-with-resource语句，会自动关闭资源，从而避免了**内存泄漏**的问题。另外使用try-with-resource语句，会抑制finally语句中的抛出的错误。可以使用addSupress和getSupress获取全部的Exception。

**实例**

```java

@Test
public void testJdk7TryWith() {

    try(InputStream inputStream = new FileInputStream(new File("test"))) {

    } catch(Exception e) {
        e.printStackTrace();

    }
}
/*
*
*   由于第一个异常可能是根本原因，那么怎么进行捕获呢？
* */
public static void main(String[] args) throws Exception {

    InputStream in = null;
    Exception ex = null;

    try {
        in = new FileInputStream(new File("tes"));
    } catch (FileNotFoundException e) {
        ex = e;
        throw e;
    } finally {
        try {
            in.close();
        } catch (IOException e) {
            if (ex == null) {
                System.out.println("有这个文件就不抛出了");
                throw e;
            }
        } catch (NullPointerException e) { // 因为in会出现空指针，抑制第一个文件的抛出
            if (ex == null) {
                System.out.println("有这个文件就不抛出了");
            }
        }
    }
}

```



**最后注意**

使用锁，不能使用try-with语句，必须显式的调用unlock方法。代码如下

```java
@Test
public void testLock() {
    Lock myLock = new ReentrantLock();
    try {
        myLock.lock();
    } catch (Exception e) {
        throw e;
    } finally {
        myLock.unlock();
    }
}

```





## 异常机制的有优缺点

### 1. 优点

- 方便定位错误，对程序员更加友好
- 方便定位错误，方便日志处理，方便更快的开发
- JVM虚拟机安全检查，运行时错误机制，使得程序更加安全。

### 2. 缺点

- 安全检查影响速度
- 使用try..catch也会影响效率。



## 异常机制的使用场景

- 写一个系统的时候，要让使用人员知道是什么错误，而不是直接返回一个不知道错误。比如用户名，密码不为空这种。
- 写类库的使用，要让程序员知道，传递的数据需要有什么限制。
- **总结来说，如果对传入的数据，有具体的要求时候使用。**



### 使用案例

1. http的一些默认错误，比如404错误。
2. 写系统的时候，使用的验证传入的数据是否为空。
3. 写类库的时候，比如读取文件的时候，保证文件是否存在。