package threadBase.model;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: Zekun Fu
 * @date: 2022/5/21 15:39
 * @Description: 为了测试运行时间，写一个拦截器
 */
public class TimeProxy implements MethodInterceptor {
    /*
    *
    *
    *   使用cglib代理计算使用线程与不适用线程排序所用的时间
    * 无法把final方法重写
    * 这个方法可以实现任意类的时间统计，很方便的面向切面编程
    * 还可以根据具体的类生成单独的，那种建议使用内部的()->{}方法编写
    * 拦截器。
    * */
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class<?>clz) {
        enhancer.setSuperclass(clz);
        enhancer.setCallback(this);
        return enhancer.create();               // 创建这个代理
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long startTime = System.currentTimeMillis();
        System.out.println();
        Object ans = methodProxy.invokeSuper(o, objects);   // 调用o的方法
        long endTime = System.currentTimeMillis();
        System.out.println();
        // 指定为某个方法生成代理怎么指定呢？
        System.out.println("--------" + method.getName() + "的运行时间为:" + (endTime - startTime) + "ms" + "-------------");
        return ans;
    }
}
