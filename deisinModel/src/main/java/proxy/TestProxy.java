package proxy;

import chainOfResponsibility.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;



public class TestProxy {


    private interface IHello {
        void sayHello();
        void sayGoodBye();
    }

    private class DogHello implements IHello
    {
        @Override
        public void sayHello() {
            System.out.println("汪汪汪");
        }

        @Override
        final public void sayGoodBye() {
            System.out.println("呜呜呜");
        }
    }

    public static void main(String[] args) {
        System.getProperties().setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        TestProxy t = new TestProxy();
        IHello dog = t.new DogHello();
        IHello dogp = (IHello) Proxy.newProxyInstance(t.getClass().getClassLoader(),
                new Class[]{IHello.class}, new InvocationHandler() {
                    Object target = dog;            // 一般需要放入一个被代理对象，然后构造函数
                    int x = 0;
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        IHello idog2 = (IHello) proxy;  // 递归调用10次，每次都打印日志
                        if (x++ >= 10) return null;
                        System.out.println("第" + x + "次调用了被代理对象的方法");
                        Object res = method.invoke(target, args);  // 调用目标dog的方法
                        idog2.sayHello();               // 有了这个可以修改代理对象的状态
                        return res;        // 调用后的结果
                    }
                });

        dogp.sayGoodBye();
        dogp.sayHello();

   }
}
