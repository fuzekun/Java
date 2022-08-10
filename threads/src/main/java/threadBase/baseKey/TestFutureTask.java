package threadBase.baseKey;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/*
*
*
*   工作流程：
* 1. 线程的start方法调用了回调函数，并且修改了FutureTask中的结果，以及状态
* 2. 之后调用FutureTask方法就可以得到结果了。
*
*   如果类比观察者模式
* 1. 观察者是 : FutureTask
* 2. 被观察者是 : Thread中
* 3. 和传统的不一样的是：Thread中的状态是由Call来决定的。执行完成后修改FutureTask
* */
public class TestFutureTask {

    public static void main(String[] args) {
        FutureTask<Integer>f = new FutureTask<Integer>(new Callable<Integer>() {

            public Integer call() throws Exception {
                return null;
            }
        });
        new Thread(f).start();
        try {
            f.get();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
