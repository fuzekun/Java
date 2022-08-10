

# JVM 编译器与前后端编译优化

把java文件中的代码翻译成.class文件，就是说翻译成类文件。

1. 即时编译器，前端编译器，提前编译器的区别。
   1. 前两种编译器是顺序使用，分工合作的，并不是编译器的两种分类。可以说，前端编译器 + 即时编译器 == 传统意义的编译器。
   2. 提前编译器是直接把程序翻译成与目标机器指令集相关的二进制代码的过程。
   3. 前端编译器就是把.java文件翻译成.class文件的一步。
   4. 即时编译器就是把class文件编译成本地代码的过程。

# 模块化系统





# JVM 执行引擎

## 0.运行时栈帧

### 0.1包含内容

- 局部变量表
- 操作数栈
- 动态链接
- 返回地址
- 其他附加信息

### 0.2 使用参数

`javap -v hello.class`打印所有的栈帧信息，以及字节码的反汇编信息



### 0.3 局部变量表对垃圾回收的影响-懒回收

- 这么写，是不会回收原来的区域的，因为在没有复用之前，原来的区域仍旧作为垃圾GC的一部分保持着关联。
- 和删除磁盘的时候，磁盘的东西不会改变，只有，复用了地址，里面的东西才会改变一样，撤销删除也没什么问题一样。

```java
package exeSystem;

import org.junit.Test;

/*
*
* -verbose:gc -XX:+PrintGCDetails
* */
public class LazyDelete {


    public static void main(String[] args) {
        {
            byte[] placehodler = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }
}

```



![image-20220510153704670](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220510153704670.png)

- 有两种方式可以使得垃圾被回收：

```java
/*
*
* -verbose:gc -XX:+PrintGCDetails
* */
public class LazyDelete {


    public static void main(String[] args) {
        {
            byte[] placehodler = new byte[64 * 1024 * 1024];
            placehodler = null; // 方法一:大对象手动赋值为null，不建议。
        }
        int a = 0;          // 方法二：手动占用
        System.gc();
    }
}
```

![image-20220510154251452](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220510154251452.png)

## 1. 执行子系统三个作用

1. 将类加载入内存 ，相当于连接的过程
2. 执行字节码文件，相当于**编译 + 执行**的过程，这里是广义的编译，包括将字节码文件变成机器码的过程。
3. 编译代码，将源文件编译成 **字节码文件**，这里是狭义的编译。

## 2. 调用方法

### 2.1 调用方法的字节码指令

- invokeSpecial
- invokeStatic
- invokeVirtual
- invokeInterface
- invokeDynamic

**前四条的分派逻辑固化在虚拟机内部，最后一条的分派逻辑由用户自己指定。**，比如儿子调用祖父的方法，如果只是虚拟机，无法进行这样的分派。

### 2.2 方法调用逻辑

**静态解析**（**非虚方法的解析**）**，剩下的是虚方法的解析**

- 调用父类方法
- 调用私有方法
- 调用final修饰的方法
- 调用static方法
- 构造函数

**静态分派(重载)**

- 下面方法可以只是根据不同的静态类型，在编译期间决定，调用哪一个方法。

```java
public void test1(Inter inter1) {
    
}
public void test1(Inter1 inter) {
    
}
```



**动态分派(重写)**

- 下面代码可以看到，参数是一个接口，编译器无法只根据形参进行判断调用哪一个方法，所以必须等到运行时候才能决定。

```java
public void test(Inter inter1) {
    //...
    inter1.method1();			// 这个需要使用invokeVirtual指令进行分派
    //...
}
```

**用户自定义分派(invokedynamic)**

- Reflection包
- java.lang.invoke包
- 实战：用户自定义的分派规则，在子类中调用祖父的方法。

```java
package exeSystem;

import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
/*
*
* IMPL_LOOKUP这个Field本身就是一个Lookup对象。这个对象在MethodHandlers类里面。
* static final Lookup IMPL_LOOKUP = new Lookup(Object.class, TRUSTED);
*   所以可以通过, Class对象的一个Field，来获取到，并且设置它的可见性。
* 而Field中有get()方法，返回的是这个默认值。
* */


public class Test {
    class GrandFa {
        void thinking() {
            System.out.println("GrandFa is thinking.");
        }
    }

    class Fa extends GrandFa {
        @Override
        void thinking() {
            System.out.println("Father is thinking.");
        }
    }

    class SSon extends Fa {
        @Override
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);
                Field lookupImp = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                lookupImp.setAccessible(true);
                ((MethodHandles.Lookup) lookupImp.get(null))
                        .findSpecial(GrandFa.class, "thinking", mt, GrandFa.class)
                        .bindTo(this)
                        .invoke();
            } catch (Throwable e) {

            }
        }
    }

    public static void main(String[] args) {
        (new Test().new SSon()).thinking();
    }
}

```







# 类文件结构以及类的加载

1. 符号引用与直接引用的区别
   1. 解析阶段就是把符号引用变成直接引用。准备阶段是为变量分配内存，并进行初始化的阶段。
   
2. 反射与代理

3. 运行时指定实现类是怎么实现的？有什么应用？

4. 类加载器什么时候使用？

   ​	首先需要知道什么是类加载器：通过一个**类的全限定名称**来获取描述该类的二进制字节流，用来实现这个这个动作的**代码**，叫类加载器。总结来说，就是从给定文件名中读取文件这个动作，用来获取字节流的代码叫做类加载器。

   ​	第一：只是读取，并没有实现，没有

   1. 加密
   2. 从非标准的来源加载代码

5. 类加载器怎么自定义？

6. 双亲委派机制是什么 

   - 在loadClass()方法中实现。

   - 类加载器形成一个树形结构，由启动加载器，扩展加载器，以及用户自定义的加载器等，每一个加载器是一个树结点。

   - 双亲委派就是一个递归调用模型。对于每一个结点来说，先递归调用父类加载器，如果父类加载器可以加载就交给父类加载，否则退出递归的时候，在自己加载。
   - 双亲委派机制使得Java虚拟机中的一些重要的类比如Java.lang包下的类，不管在什么类加载器中都是一个类。
   - 因为**类的全类名，是类在类加载器中的唯一标志，所以一个类加载器中不允许有两个同名的类**。虽然在不同的类加载器中可以有重名的类。

7. 什么破坏了双亲委派机制

   - jdk1.2以前，没有这个机制，如果用户自定义的类加载器没有调用findClass()方法，就破坏了这个机制。

   - JNDI，父类加载器调用了子类加载器。因为基础类型需要调用用户的代码
   - 代码的热替换，模型的热部署。OSGi

8. OSGI的热插拔是如何实现的？

   热部署可以有效地重启应用。为了恢复应用的状态，这些**状态必须提前被保存并被重新应用或加载到“新”的应用或模块中**。

   当前基于组件的框架如 Grails 或 RIFE **都自己负责处理组件状态**。这就是为什么用新 classloader 重新装载的组件在这些框架中不存在问题的原因。新组件的状态在框架中已经存在。组件的颗粒性也可以让很小的 bundle 被瞬时重新加载。

   简单理解下: 就是JVM也可以实现热部署，但是JVM实现热部署非常困难，因为很难把所有的与之相关的引用逐一更新。而springboot使用了不同的类加载器，用于加载不同的类，一旦更新了代码，就直接把代码少的类加载器卸载了重新更新就行了。

9. JVM如何热部署

   1. 销毁自定义的ClassLoader类

   2. 更新Class文件

   3. 创建新的classLoader去加载更新后的class类文件。

      ```java
      package com.csair.soc.hotswap;
      
      import java.io.IOException;
      import java.io.InputStream;
      /**
       * 自定义类加载器，并override findClass方法
       */
      public class MyClassLoader extends ClassLoader{
           @Override
           public Class<?> findClass(String name) throws ClassNotFoundException{
                  try{
                      String fileName = name.substring(name.lastIndexOf("." )+1) + ".class" ;
                      InputStream is = this.getClass().getResourceAsStream(fileName);
                       byte[] b = new byte[is.available()];
                      is.read(b);
                       return defineClass(name, b, 0, b. length);
                 } catch(IOException e){
                       throw new ClassNotFoundException(name);
                 }
           }
      }
      
      
      public class HotSwap {
          public static void main(String[] args) throws Exception{
              loadHelloWorld();
              // 回收资源,释放HelloWorld.class文件，使之可以被替换
              System.gc();
              Thread.sleep(1000);// 等待资源被回收
              File fileV2 = new File( "HelloWorld.class");
              File fileV1 = new File(
                  "D:\\projects\\java\\JVM_expriment\\out\\production\\JVM_expriment\\classLoader\\HelloWorld.class" );
              fileV1.delete(); //删除V1版本
              fileV2.renameTo(fileV1); //更新V2版本
              System. out.println( "Update success!");
              loadHelloWorld();
      }
      
          public static void loadHelloWorld() throws Exception {
              MyClassLoader myLoader = new MyClassLoader(); //自定义类加载器
              Class<?> class1 = myLoader
                      .findClass( "classLoader.HelloWorld");//类实例
              Object obj1 = class1.newInstance(); //生成新的对象
              Method method = class1.getMethod( "say");
              method.invoke(obj1); //执行方法say
              System. out.println(obj1.getClass()); //对象
              System. out.println(obj1.getClass().getClassLoader()); //对象的类加载器
          }
      }
      
      
      /*
      
      	上述代码，需要创建一个HelloWorld类
      	在src同级目录下放入相应2版本的.class文件
      	在原来的类路径目录下放入1版本的class文件
      	还能说明使用类加载器加载之后的对象可以通过gc进行回收。
      */
      ```

      

10. springboot和spring中的热部署是如何实现的？就是说更新了代码，直接保存，就可以更新相应的类了。

    深层原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间。

11. springboot实现热部署的三种方式：

12. java的模块化系统是什么，怎么用，为什么需要模块化系统？

13. Integer x = 128, y = 128; int z = 128; x == y; x == z经历了哪些步骤？最后的答案是什么？

![](D:\blgs\source\imgs\image-20220317193921766-16475174964751.png)

```java
经历的步骤如上图所示：
    1. 调用了valueOf方法，该方法如果值大于了128就会创建一个新的对象，从而导致==的对象不相等。
    2. 如果使用x 和int 进行比较的话就会是true，因为调用了intValue的拆箱方法，返回一个int值，比较的就是值。
    3. 如果强制转化成(int)x == y,那么输出的也会是true,因为这也是涉及到了自动拆箱。
    
    总结来说，如果没有自动拆箱，比较的就是两个对象的引用，如果涉及到了自动拆箱，比较的就是两个int值。
```



# 一些问题



1. 虚拟机的类加载器是编译器吗?
   1. 不是，类加载相等于操作系统中的连接过程。编译过程相当于将.java文件编译成.class文件。也就是说，编译的结果从本地机码变成字节码是存储格式发展的一小步，却是编译语言发展的一大步。
   2. 类加载器是读取限定文件的代码，就是说把文件中的类的二进制流读入到内存中。
   3. 编译器是把本地代码编译成目标机器码的过程。
   4. 从这个角度来说，前端编译 + 及时编译 == 传统意义的编译。类加载可以说是一个装入的过程。
2. 前后端优化优化的是啥？
3. 方法调用的过程是什么？
   1. 
4. 组成原理与体系结构的区别

   - 体系结构是能够被程序员看到的属性
   - 组成原理是是指如何实现计算机体系结构能够表现出来的属性。
   - 比如有没有乘法指令是计算机体系结构需要处理的问题，而如何实现乘法指令，是通过专用的乘法器，还是通过加法器和移位器进行实现。这个是组成原理的问题。



5. 大问题，怎么一步一步的执行的？
   1. 编译成.class文件(二进制的字节码文件)
   2. 将.class文件中的类加载如内存。如果类加载器没有卸载，那么这个类就不会被卸载。类加载器，就是把字节码中类信息装入内存中的代码。
   3. 后端编译将其翻译成机器指令进行执行，或者使用解释器，将代码进行解释执行。
   4. 机器指令就可以一步一步执行了。



内存是怎么分配的？代码是怎么执行的？把类中的信息加载进方法区和堆空间中。存储的是代码和数据，执行的是代码，然后将数据进行加工计算。



6. 为什么要有class文件
   - 方便开发，直接把java翻译成各个机器上的源代码太难。class文件相当于汇编语言。
   - 更加快速，少了一步此法分析和语法分析的过程。
7. java是如何保证源码安全的，对比c++没有反汇编
   - 类加载部分保证了安全。可以给文件加密，然后自己写类加载器，读取.class文件的时候进行解密。





# 参考文献



[线程模型的描述](https://blog.csdn.net/qq877728715/article/details/101547608?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-2-101547608.pc_agg_new_rank&utm_term=%E4%B8%BA%E4%BB%80%E4%B9%88%E6%AF%8F%E4%B8%AA%E7%BA%BF%E7%A8%8B%E9%9C%80%E8%A6%81%E4%B8%80%E4%B8%AA%E5%8D%95%E7%8B%AC%E7%9A%84%E5%86%85%E5%AD%98&spm=1000.2123.3001.4430)

