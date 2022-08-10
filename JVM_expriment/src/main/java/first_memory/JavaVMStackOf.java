package first_memory;

import org.junit.Test;

/*
*
*
*   用来测试虚拟机栈的两种异常
* 1. 如果可以不能进行扩展分配，会出现SOF
* 2. 否则会出现OFM
*
* 注意要使用Throwable而不是Exception,因为栈溢出属于JVM层面的错误，属于Error
* 是不归Excetion管的。
* */
public class JavaVMStackOf {
    private int statckLen = 1;

    public void statckLeak() {
        statckLen++;
//        System.out.println(statckLen);
        statckLeak();
    }

    public int getStatckLen() {
        return statckLen;
    }

    @Test
    public void test1() {
        /*
        *
        *   测试栈溢出
        * */

        JavaVMStackOf oom = new JavaVMStackOf();
        try {
//            int a = 1 / 0;
            oom.statckLeak();
        } catch ( Throwable e) {
            System.out.println("stackLen = " + oom.getStatckLen());
            throw e;
        }
    }
    private void dontStop() {
        while(true){}
    }

    public void stackLeakByThread() {

        while(true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }
//    @Test                                 // 别测试，否则会死机
    public void test2() {
        /**
         *
         * 测试使用多线程而导致的OOM
         * ,直接导致电脑死机了，没必要测试了
         */

        JavaVMStackOf oom = new JavaVMStackOf();
        oom.statckLeak();
    }


    @Test
    public void test3() {
        /*
        *   看一下单纯的while(true)会不会死机
        *   结果是不会死机，也不会终止，那么AC网站上的就是超时出错。
        *   所以不会出现那种死机的情况。也防止了恶意的攻击。
        * */

        while(true){}
    }
}
