package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TraceHandler implements InvocationHandler {

    private Object target;
    TraceHandler(Object t) {        // 被代理的对象
        target = t;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("这里进行输出程序的名称和方法");
        return method.invoke(target, args);
    }
}
