package compiler.nameCheckP;


import fanxing.Test;
import sun.nio.cs.ext.IBM037;

/*
*
*   测试自动装箱和拆箱子
*
*   1. equals比较的是值，而 == 比较的是地址, 但是遇到了运算符，就进行自动拆箱和装箱了。
*  2. valueOf有一个[-128, 127]的缓存，如果没超过，就直接是返回原来的对象
*   3. equals首先判断是否是同类型，如果不是同类型，就不进行值判断了，也就是说不处理不同类型数据的关系
*  4. == 在 不遇到运算符的情况下，不进行自动拆箱和装箱。但是遇到了，就进行了。
*
* */
public class TestInteger {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 128;        // 大于127就是false，因为有个cache，小于的直接返回
        Integer f = 128;
        Long g = 3L;
        Integer h = 321;
        Integer i = 100;
        Integer j = 221;
        System.out.println(c == d); // true, 地址也相同
        System.out.println(e == f); // false, 创建新对象了
        System.out.println(c == (a + b)); // true a + b自动拆 + 装,没有新对象
        System.out.println(c.equals(a + b)); // true
        System.out.println(g == (a + b));   // true
        System.out.println(h == (i + j)); // true;遇到了
        System.out.println(g.equals(a + b)); // false; 看下面的myEquals



    }
    private static boolean myEquals (Object object){
        if (object instanceof Long) {
            System.out.println("对的，是Long");
            return true;
        }
        System.out.println("不是Long,直接返回false了");
        return false;
    }
}
