package proxy;

import org.junit.Test;

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

        // 方法使用invoke，就是采用了反射的方式，返回的是方法执行结果的对象。
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("欢迎调用");
            // 第一个就是为了方便递归调用才进行的，如果有多个拦截器
            // 可以先到头就转成不同的拦截器进行拦截。
//            x--;
//            if (x == 0) return null;
//            IHello hello = (IHello) proxy;
//            hello.sayHello();
            return method.invoke(originalObject, args); // 也是使用了反射的方式
        }
    }

    @Test
    public void test() {
        IHello hello = (IHello)new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }

    public static void main(String[] args) {
        IHello hello = (IHello)new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }
}

