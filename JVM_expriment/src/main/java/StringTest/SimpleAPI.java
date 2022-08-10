package StringTest;

import org.junit.Test;

public class SimpleAPI {

    /*
    *
    *   简单的使用API文档做一些算法题目
    * */

    String a = String.valueOf(12);
    int b = Integer.valueOf(a);

    @Test
    public void test1() {
        System.out.println(a);
//        System.out.println(Integer.valueOf(a.substring(0, 0)));
        System.out.println(b);
        StringBuilder a = new StringBuilder("fda").append("+").append(")");
        System.out.println(a.toString());
    }

    public static void main(String[] args) {

    }
}
