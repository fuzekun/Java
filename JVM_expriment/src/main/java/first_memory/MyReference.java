package first_memory;

import java.lang.ref.*;

/*
*
*   java中的强软弱虚引用
* */
public class MyReference {

    public static void main(String[] args) {
        ReferenceQueue referenceQueue = new ReferenceQueue();
        String s = new String("软引用，你好华为\n");
        SoftReference<String> sr = new SoftReference<String>(s, referenceQueue);
        System.out.printf(sr.get());
        s = null;
        System.gc();
        System.out.printf(sr.get());
        /*
        *  可以看出，虽然引用强引用已经指向了null, 但是内存仍旧没有释放，这是由于有
        * 弱引用的存在。弱引用只有在内存不够的时候在进行回收。
        * 为了防止内存泄漏，可以配合引用队列进行
        * */
        try{
//              referenceQueue.remove();


            if (referenceQueue.poll() != null) {
                System.out.printf("1:被回收\n");
            } else System.out.printf("0：没被回收\n");
            if (referenceQueue.poll() != null) {
                System.out.println("poll 不弹出");
            } else {
                System.out.println("poll 弹出");
            }
        }catch ( Exception e) {
            System.out.println("你好,出错了");
            e.printStackTrace();
        }
        System.out.printf(sr.get());


        ReferenceQueue reference2 = new ReferenceQueue();
        WeakReference weakReference = new WeakReference(new String("weakReference hello 科兴"), reference2);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
        try {
            if (reference2.poll() != null) { //使用Poll 如果使用remove会进入阻塞态
                System.out.printf("弱引用失去引用\n");
            } else System.out.printf("弱引用仍旧在被引用\n");
        }catch ( Exception e) {
            e.printStackTrace();
        }

        ReferenceQueue<String> queue = new ReferenceQueue();
        String ss = new String("必须使用new，否则没效果");
        PhantomReference<String> p = new PhantomReference<String>(ss, queue);
        // get都get不到
        System.out.println(p.get());
        // 1. 手动制空
        ss = null;
        // 2. 垃圾回收
        System.gc();
        try {
            if (queue.poll() != null) {
                System.out.println("虚引用结束了");
            } else {
                System.out.println("虚引用还没结束啊");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
