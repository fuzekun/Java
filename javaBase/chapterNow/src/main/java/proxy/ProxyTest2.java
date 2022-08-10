package proxy;

import javafx.util.Pair;


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

        return method.invoke(target, args); // 这里使用invoke,进行反射处理
    }
}
