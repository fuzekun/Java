# Java积累



## 异常、断言与日志

### 1. 异常

1. 区分Throwable和Excetion

![img](https://gss0.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/4034970a304e251fb9f3af9daa86c9177f3e534a.jpg)



```java
package first_memory;

import org.junit.Test;

/*
*
*
*   用来测试虚拟机栈的两种异常
* 1. 如果可以不能进行扩展分配，会出现SOF
* 2. 否则会出现OFM
*
* 注意要使用Throwable而不是Exception,因为栈溢出属于JVM层面的错误，属于Error
* 是不归Excetion管的。
* */
public class JavaVMStackOf {
    private int statckLen = 1;

    public void statckLeak() {
        statckLen++;
//        System.out.println(statckLen);
        statckLeak();
    }

    public int getStatckLen() {
        return statckLen;
    }

    @Test
    public void test1() {
        /*
        *
        *   测试栈溢出
        * */

        JavaVMStackOf oom = new JavaVMStackOf();
        try {
//            int a = 1 / 0;
            oom.statckLeak();
        } catch ( Throwable e) {
            System.out.println("stackLen = " + oom.getStatckLen());
            throw e;
        }
    }

    @Test
    public void test2() {
        /**
         *
         * 测试使用多线程而导致的OOM
         */

//        private void dontStop() {
//
//        }
    }
}

```



- 上述程序如果不`throw e`那么程序是可以执行完成的。
- 但是使用Excetion会导致程序崩溃，直接throw e给虚拟机。





## 线程

### 1. 线程创建的三种方式

[参考文章](https://www.cnblogs.com/3s540/p/7172146.html)

1. 继承Thread类
2. 实现Runable接口
3. 使用Callable接口
4. 使用接口还可以继承其他类，但是使用继承，在Java里面就没法继续继承了。



```java
package com.thread;
 
public class FirstThreadTest extends Thread{
	int i = 0;
	//重写run方法，run方法的方法体就是现场执行体
	public void run()
	{
		for(;i<100;i++){
		System.out.println(getName()+"  "+i);
		
		}
	}
	public static void main(String[] args)
	{
		for(int i = 0;i< 100;i++)
		{
			System.out.println(Thread.currentThread().getName()+"  : "+i);
			if(i==20)
			{
				new FirstThreadTest().start();
				new FirstThreadTest().start();
			}
		}
	}
 
}
```



```java
package com.thread;
 
public class RunnableThreadTest implements Runnable
{
 
	private int i;
	public void run()
	{
		for(i = 0;i <100;i++)
		{
			System.out.println(Thread.currentThread().getName()+" "+i);
		}
	}
	public static void main(String[] args)
	{
		for(int i = 0;i < 100;i++)
		{
			System.out.println(Thread.currentThread().getName()+" "+i);
			if(i==20)
			{
				RunnableThreadTest rtt = new RunnableThreadTest();
				new Thread(rtt,"新线程1").start();
				new Thread(rtt,"新线程2").start();
			}
		}
 
	}
 
}
```



```java
package com.thread;
 
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
 
public class CallableThreadTest implements Callable<Integer>
{
 
	public static void main(String[] args)
	{
		CallableThreadTest ctt = new CallableThreadTest();
		FutureTask<Integer> ft = new FutureTask<>(ctt);
		for(int i = 0;i < 100;i++)
		{
			System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
			if(i==20)
			{
				new Thread(ft,"有返回值的线程").start();
			}
		}
		try
		{
			System.out.println("子线程的返回值："+ft.get());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		}
 
	}
 
	@Override
	public Integer call() throws Exception
	{
		int i = 0;
		for(;i<100;i++)
		{
			System.out.println(Thread.currentThread().getName()+" "+i);
		}
		return i;
	}
 
}
```



## 环境

### 1. 将项目交给maven管理

1. 右键项目

2. add framework support

3. 找到maven确定

4. 配置maven的信息 -> .m 文件夹以及.config文件等。

5. 等待，知道pom.xml文件填充完成

6. ```xml
   <!--需要将版本修改成1.8-->
   <properties>
       <maven.compiler.source>1.8</maven.compiler.source>
       <maven.compiler.target>1.8</maven.compiler.target>
   </properties>
   ```

