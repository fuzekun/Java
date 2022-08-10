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
                Object result = method.invoke(target, args); // 这里使用invoke就是反射


                System.out.println(result);
                System.out.println(method.getName() + "方法执行结束...");
                return result;
            }
        });
        return prox;
    }
}