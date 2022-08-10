package exeSystem;

import org.junit.Test;

/*
*
* -verbose:gc -XX:+PrintGCDetails
* */
public class LazyDelete {


    public static void main(String[] args) {
        {
            byte[] placehodler = new byte[64 * 1024 * 1024];
//            placehodler = null; //方法1：大对象手动赋值为null，不建议。
        }
        int a = 0;          // 方法2： 建议这么做
        System.gc();
    }
}
