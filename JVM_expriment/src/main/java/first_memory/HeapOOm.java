package first_memory;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/*
*
*   java堆溢出实践
*
*
* * */
public class HeapOOm {

    /*
    *
    *   设置-Xmx6m大小未6m
    *   之后运行程序，让程序出现OOM
    * */

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        long i = 0; // 2B = 2^16
        try {
            while (true) {
                set.add(String.valueOf(i++).intern());
            }
        } catch (Throwable e) {
            System.out.println("i = " + i);
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        List<Integer> list = new ArrayList();
        int num = 3223;
        for (;num != 0; num /= 10) {
            list.add(num % 10);
        }
        list.sort(Collections.reverseOrder());
//        Collections.reverse(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
