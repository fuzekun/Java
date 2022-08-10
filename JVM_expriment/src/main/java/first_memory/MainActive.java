package first_memory;

import org.junit.Test;
import sun.rmi.runtime.Log;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.TreeMap;

public class MainActive{

    private static final String TAG = "PhantomReferenceDemo";

    // 1、定义为成员变量 防止PhantomReference被回收
    private ReferenceQueue<String> mQueue = new ReferenceQueue<>();
    private PhantomReference<String> mReference;

    // 2、定义为成员变量 方便手动控制回收
    private String mTest;

    @Test
    public void test() {
        // 4、开启线程 监控回收情况
//        new Thread(() -> {
//            while (true) {
//                System.out.println("监听线程上线");
//                Reference<? extends String> poll = mQueue.poll();
//                if (poll != null) {
//                    System.out.println("引用被回收");
//                }
//                try {
//                    System.out.println("监听线程等待");
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }).start();


        // 3、
        // 直接用双引号定义的 存储在方法区
        // new出来的 存储在JVM堆
        // 由于GC回收主要针对堆 所以这儿使用new String才能看到效果
        mTest = new String("test");
        mReference = new PhantomReference<>(mTest, mQueue);

        try {
            mTest = null;
            System.gc();
            System.out.println("手动制空完成");

            Reference<? extends String> poll = mQueue.poll();
            if (poll != null) {
                System.out.println("引用被回收");
            }
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
    }

}