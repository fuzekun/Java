package first_memory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/*
*
*   -XX:PermSize=10M -XX:MaxPermSize=10M
* 方法区的大小限制10M已经不管用了，jdk1.8已经变成元空间了。
* -XX:MaxMetaspaceSize=1m -XX:MetaspaceSize=1m
* */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OOMObject.class);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return methodProxy.invokeSuper(o, args);
            }
        });
        enhancer.create();

    }

    static class OOMObject {

    }
}
