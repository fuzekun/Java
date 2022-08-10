package StringTest;

/*
*
*   测试Integer的一些东西
* 1. 让x == y需要经过那些步骤？
* */
public class IntegerTest {

    public static void main(String[] args) {
        Integer x1 = 100;
        int x2 = 100;
        Integer y1 = 258;
        int y2 = 258;
        Integer z1 = 128;
        Integer z2 = 128;
        System.out.println(x1 == x2);
        System.out.println(y2 == y2);   // 涉及到了自动拆箱
        System.out.println((int)z1 == z2);
    }


}
