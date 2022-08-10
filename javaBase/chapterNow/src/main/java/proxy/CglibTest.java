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
