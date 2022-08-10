# Java反射与代理模式



## 需求分析：
给每一个类调用方法的时候，打印一个日志，打印出来输入输出。
这样就是说，需要给每一个类，生成一个新的增强类，这个增强类的作用就是在调用对象
方法的时候，增加输入输出的打印。

## 一、Java反射

### 1. 反射能做什么

​		我们知道反射机制允许程序在运行时取得任何一个已知名称的class的内部信息，包括包括其modifiers(修饰符)，fields(属性)，methods(方法)等，并可于运行时改变fields内容或调用methods。那么我们便可以更灵活的编写代码，代码可以在运行时装配，无需在组件之间进行源代码链接，降低代码的耦合度；还有动态代理的实现等等；但是需要注意的是反射使用不当会造成很高的资源消耗！

​		**可以使用反射去调用对象的方法。**

​		灵活使用反射能让我们代码更加灵活，这里比如JDBC原生代码注册驱动，hibernate 的实体类，Spring 的 AOP等等都有反射的实现。但是凡事都有两面性，反射也会消耗系统的性能，增加复杂性等，合理使用才是真！

### 2. 获取类Class对象的三种方式

```java
//1、通过对象调用 getClass() 方法来获取,通常应用在：比如你传过来一个 Object
//  类型的对象，而我不知道你具体是什么类，用这种方法
　　Person p1 = new Person();
　　Class c1 = p1.getClass();

//2、直接通过 类名.class 的方式得到,该方法最为安全可靠，程序性能更高
//  这说明任何一个类都有一个隐含的静态成员变量 class
　　Class c2 = Person.class;

//3、通过 Class 对象的 forName() 静态方法来获取，用的最多，
//   但可能抛出 ClassNotFoundException 异常
　　Class c3 = Class.forName("com.ys.reflex.Person");
```



### 3. 示例

```java
//获得类完整的名字
String className = c2.getName();
System.out.println(className);//输出com.ys.reflex.Person

//获得类的public类型的属性。
Field[] fields = c2.getFields();
for(Field field : fields){
   System.out.println(field.getName());//age
}

//获得类的所有属性。包括私有的
Field [] allFields = c2.getDeclaredFields();
for(Field field : allFields){
    System.out.println(field.getName());//name    age
}

//获得类的public类型的方法。这里包括 Object 类的一些方法
Method [] methods = c2.getMethods();
for(Method method : methods){
    System.out.println(method.getName());//work waid equls toString hashCode等
}

//获得类的所有方法。
Method [] allMethods = c2.getDeclaredMethods();
for(Method method : allMethods){
    System.out.println(method.getName());//work say
}

//获得指定的属性
Field f1 = c2.getField("age");
System.out.println(f1);
//获得指定的私有属性
Field f2 = c2.getDeclaredField("name");
//启用和禁用访问安全检查的开关，值为 true，则表示反射的对象在使用时应该取消 java 语言的访问检查；反之不取消
f2.setAccessible(true);
System.out.println(f2);

//创建这个类的一个对象
Object p2 =  c2.newInstance();
//将 p2 对象的  f2 属性赋值为 Bob，f2 属性即为 私有属性 name
f2.set(p2,"Bob");
//使用反射机制可以打破封装性，导致了java对象的属性不安全。
System.out.println(f2.get(p2)); //Bob

//获取构造方法
Constructor [] constructors = c2.getConstructors();
for(Constructor constructor : constructors){
    System.out.println(constructor.toString());//public com.ys.reflex.Person()
}
```





## 二、Java代理

代理的使用时机：
有一个表示接口的Class对象，在编译的时候不知道确切的类型，但是
需要在运行的时候生成一个接口的实现类。

### java代理的实现方式

#### 1. 静态代理

类似于装饰者模式，就是直接给每一个类生成一个对应的代理类。然后组合被引用类，直接调用被包装类的方法即可。

#### 2. 动态代理

代理的使用步骤：

1. 原料准备：

 -  类加载器
 -  Class对象数组，就是接口对象数组
 -  调用处理器

2. 过程 Proxy.newProxyInstance的过程

- 获取被代理的类的类型信息
- 由类型信息，生成一个带有InvokeHandler类的构造器，这个构造器就是代理类的构造器，有一个实例字段就是invokeHandler。
- 由构造器生成一个被代理的对象，这个对象需要有InvokHandler的对象，里面有一个invoke()，每次调用的方法的时候，都会调用这个invoke。



> 准确来说，准备一个处理器和一个被代理的类就足够了。
>
> 因为由被代理的类可以获得类加载器以及类的接口信息。
>
> 用第二种方式，可以由类的接口信息获得一个构造器，从而获得一个新的对象。



#### 3. 动态代理补充

**1. invoke方法中第一个参数proxy的用处:**

1. 充当this指针，可以递归调用proxy，this指的不是proxy是handler。
2. 可以获取proxy的信息。

**2. 接口.class的返回值是一个接口Class**

**3. 如果方法被调用，那么对应的类，就一定要实现**

比如如果调用了compareTo函数，那么Comparable接口就一定要实现。

```java
Class[] interfaces = date.getClass().getInterfaces(); // 可以直接使用这个获取对象实现的所有接口。
```

**4. 对代理类的认识**

- 代理类是一个类
- 代理类只有一个实例字段invokHandler
- 同一个接口数组和类加载器，只有一个代理类，但是会生成多个代理对象，所以类是 **“单例类”**
- 代理类覆盖了Object中的`toString, equals 和 hashCode`但是没有覆盖`getClass和clone`
- 代理类是public和final的，也就是说，不允许继承，一定允许访问。

> 总结来说：是一个**有一个字段**的，**final** 、**单例** 类。

**5. 重要的方法**

- construct.newInstance(new InvokeHandler)
- static getProxyClass
- isProxyClass
- invoke

**6.三种获取代理类的方式**

- 使用newInstance

```java
package proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Calculator {
    public int add(int a, int b);
    public int sub(int a, int b);
}
class CalculatorImpl implements Calculator{

    public int add(int a, int b) {
        return a + b;
    }

    public int sub(int a, int b) {
        return a - b;
    }
}
public class ProxyTest {
    public static void main(String[] args) throws Throwable {
        CalculatorImpl target = new CalculatorImpl();
        //传入目标对象
        //目的：1.根据它实现的接口生成代理对象 2.代理对象调用目标对象方法
        Calculator calculatorProxy = (Calculator) getProxy(target);
        calculatorProxy.add(1, 2);
        calculatorProxy.sub(2, 1);
    }

    public static Object getProxy(final Object target) throws Exception {
        //参数1：随便找个类加载器给它， 参数2：目标对象实现的接口，让代理对象实现相同接口
        Class proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        Object prox = constructor.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + "方法开始执行...");

                // invoke，需要知道对象，方法，以及参数，就可以用代理类进行实现了。
                Object result = method.invoke(target, args);


                System.out.println(result);
                System.out.println(method.getName() + "方法执行结束...");
                return result;
            }
        });
        return prox;
    }
}
```



- 使用newProxyInstace()

```java
package proxy;

import javafx.util.Pair;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyTest2 {

    public static void main(String[] args) throws Exception{
        Object[] elements = new Object[1000];

        for (int i = 0; i < elements.length; i++) {
            Integer val = i + 1;
            InvocationHandler handler = new TraceHadler(val);
//            Class proxyClazz = Proxy.getProxyClass(val.getClass().getClassLoader(), val.getClass().getInterfaces());
            Class[] interfaces = val.getClass().getInterfaces();
            Object proxy = Proxy.newProxyInstance(null, interfaces, handler);
//            Object proxy =  ProxyTest.getProxy(val);
            elements[i] = proxy;
        }

        Integer key = new Random().nextInt(elements.length) + 1;

        int result = Arrays.binarySearch(elements, key);

        if (result > 0) System.out.println(elements[result]);
    }

}

class TraceHadler implements InvocationHandler {
    private Object target;

    public TraceHadler(Object t) {
        this.target = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.print(target);
        System.out.print("." + method.getName() + "(");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) System.out.print(",");
            }
        }
        System.out.println(")");

        return method.invoke(target, args);
    }
}
```



- 实现InvokeHandler接口直接返回

```java
package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 *  使用java虚拟机上面的创建方式进行创建
 *
 *  1. 系统实现一个InvokeHandler类
 *  2. 调用者只需要给一个目标对象，就可以得到一个代理对象了
 *
 *  这个方法和方法一的实现有着异曲同工之妙。都不需要用户自己写invokerHandler
 * 就可以得到一个代理对象。
 *
 * 那么哪个方法耦合更低呢？其实都挺高的，还是原来的方法，耦合更低，也就是设计者的更低。
 *
 *
 *
 * */




public class ProxyTest3 {
    interface  IHello {
        void sayHello();
    }
    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("你好\n");
        }
    }

    static class DynamicProxy implements InvocationHandler {
        Object originalObject;

        Object bind(Object o) {
            this.originalObject = o;
            return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), this);
        } // 自己就是一个Handler，可以使用自己来生成一个动态的代理对象，就不用用户自己写handler类了。

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("欢迎调用");
            return method.invoke(originalObject, args);
        }
    }

    public static void main(String[] args) {
        IHello hello = (IHello)new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }
}


```





**7. 动态代理的原理是什么**

原理是类加载过程中，会生成一个Class对象，这个对象可以通过反射获取。可以根据这个Class对象构造一个新的代理类，这个代理类实现了传入接口的所有方法。

- 怎么在调用sayHello的时候，调用代理类的方法的？

​		由于调用的是代理类生成的对象，这个对象中有**传入接口**的每一个方法的实现。以及从Object类中继承来的equals()、hashCode()、toString()方法等。然后调用了InvokeHandler类中的invoke方法。

```java
m3 = Class.forName("").getMethod("", new Class[0]); // 类中的方法
this.h.invoke(this, m3, null);					// 调用自定义InvokeHandler中的invoke()
```



> 注意实现的是**传入接口中的方法**，而**不是代理对象的方法**，一般中对象中的方法，在接口中没有，也应该声明为private，因为没法实现多态，这个方法也没法被其他类感知。



![image-20220415174957437](D:\blgs\source\imgs\image-20220415174957437.png)



#### 4. cglib代理



**1. 实例**

```java
package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibTest {


    public static class Dog {
        final public void run(String name) {
            System.out.println("够" + name + "---- run");
        }

        public void eat() {
            System.out.println("狗----eat");
        }

    }
    // 拦截器采用了代理，也就是面向切面编程
    public static class MyMethodIntercepter implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("这里是对目标类进行代理！！！");
            System.out.println("代理类:" + obj.getClass().getTypeName());
            Object object = proxy.invokeSuper(obj, args);
            return object;
        }
    }


    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();

        // 设置需要代理的类，可以不是接口，直接是类
        enhancer.setSuperclass(Dog.class);

        // 设置回调函数
        enhancer.setCallback(new MyMethodIntercepter());

        Dog proxyDog = (Dog)enhancer.create();

        proxyDog.eat();
        // final 修饰的run，所以没法重写
        proxyDog.run("jj");
    }
}

```

**2. 实现的方式**

- 采用了代理 + fastClass的方式
- fastClass采用方法映射的方式，直接调用被代理的方法，从而加快了速度。
- 原来调用原类方法采用的是`method.invoke`的方式进行调用，并没有实际生成被代理的对象。

```java
public class test10 {
    public static void main(String[] args){
        Test tt = new Test();
        Test2 fc = new Test2();
        int index = fc.getIndex("f()V");
        fc.invoke(index, tt, null);
    }
}

class Test{
    public void f(){
        System.out.println("f method");
    }
    
    public void g(){
        System.out.println("g method");
    }
}
class Test2{
    public Object invoke(int index, Object o, Object[] ol){
        Test t = (Test) o;										// 需要知道目标对象的类型。
        switch(index){
        case 1:
            t.f();
            return null;
        case 2:
            t.g();
            return null;
        }
        return null;
    }
    
    public int getIndex(String signature){
        switch(signature.hashCode()){
        case 3078479:
            return 1;
        case 3108270:
            return 2;
        }
        return -1;
    }
}
```

**3. 对比**

![image-20220415180648983](D:\blgs\source\imgs\image-20220415180648983.png)

```java
 @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("这里是对目标类进行代理！！！");
            System.out.println("代理类:" + obj.getClass().getTypeName());
            Object object = proxy.invokeSuper(obj, args); // 被代理对象和参数
            
            // 以前是使用 Object result = method.invoke(原对象， args);进行反射调用
            return object;
        }
    }


可以看到，这里的是采用代理方法的.invokeSuper(obj, args)进行方法调用的。其中的invoke方法调用了被保存对象的引用。
    而直接使用method.invoke(object, args);进行调用的动态代理，就是直接使用的反射。这句话的消耗很大。
```



可以看到这个invoke并不是反射的invoke，而是被代理类的index方法被调用。



![img](https://img2018.cnblogs.com/blog/1368608/201906/1368608-20190602190800764-1093977192.png)

而这个方法的简单实现就是上面的2.





## 三、什么时候使用代理

-  路由对远程服务器的方法访问 。（方法不想理这个访问，所以交给代理类去处理这个访问）。 
- 程序运行期间，将用户接口事件与动作关联起来。（用户事件event只想记录，不想处理，所以交给代理类eventHadler去做）。
- 为调试、跟踪方法调用。（类本身没有写日志打印语句，也不想打印，交给代理类去做）。



## 四、自己动手实现远程执行功能





## 五、代理模式

1. 是一种结构行为的设计模式，代理类和委托类都实现了同一个接口，这样客户在调用委托类的时候，直接调用代理类。可以增强委托类的功能，并且不用修改该类。

![img](https://bkimg.cdn.bcebos.com/pic/8c1001e93901213f7531cb1154e736d12e2e95f9?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_auto)





2. 和装饰器模式的区别
   - 让别人做你不喜欢做的事情，叫做代理。代理的目的是为了控制。
   - 装饰器是为了增强自己的功能。



## 六、观察者模式

1. 回调函数的设计
2. 异步模型



## 七、面试的问题

1. 为什么反射会慢，cglib会快？
   - 因为涉及到反射的代码，JIT编译器无法进行代码的优化，所以反射会慢。
   - 而cglib代理并没有使用反射进行代码的调用，只是持有目标对象的方法引用。所以可以根据方法名称进行目标对象的方法调用。可以进行代码的优化，所以会快。
   - 原来的代理使用的**反射**调用某一个类的方法，也就是说，需要
     - 先根据目标对象，获取类
     - 根据这个类的方法进行执行。
   - 现在的代理使用的是if...else的判断方法



## 参考文章

[Java反射详解](https://blog.csdn.net/qq_44715943/article/details/120587716)

[Java 反射详解 - YSOcean - 博客园 (cnblogs.com)](https://www.cnblogs.com/ysocean/p/6516248.html#_label1)

[CGLib动态代理 - java小新人 - 博客园 (cnblogs.com)](https://www.cnblogs.com/wyq1995/p/10945034.html)

JVM虚拟机

Java核心技术卷I
