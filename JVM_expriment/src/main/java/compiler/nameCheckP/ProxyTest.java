package compiler.nameCheckP;


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




public class ProxyTest {
    interface  IHello {
        void sayHello();
    }
    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("你好\n");
        }

        public void sayGoodBay() {
            System.out.println("拜拜\n");
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

