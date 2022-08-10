package StringTest;

import org.junit.Test;

/*
*
*   字符串的不可变性
*
* 使用+花费时间特别长的原因：因为+ 创建了StringBuilder对象调用了toString 方法
* 而toString 方法会使用new String 创建一个对象
*
* */
public class NotChange {


    @Test
    public void test1() {
        String a = "ab" + "c";
        String b = "abc";
        String c = a + "c";
//        System.out.println(c);
//        System.out.println(a + c == b);
        System.out.println(b == c);
        // 如果都是常量，属于编译期优化，都放在常量池中。
        System.out.println("ab" + "c" == b);
        // 如果都是变量，则调用了StringBuilder方法，创建了新的对象
        System.out.println(a == b);
    }

    @Test
    public void test2() {
        StringBuilder a = new StringBuilder("abc");
        StringBuilder b = new StringBuilder("abc");
        String c = "abc";
        System.out.println(a == b);
        System.out.println(a);
//        System.out.println(a == c);
    }

    @Test
    public void test3() {
        final String a = "ab";
        final String b = "c";
        String c = "abc";
        System.out.println(a + b == c);
    }

    @Test
    public void test4() {
        int t = 1000000;
        String b = "";

        long bg = System.currentTimeMillis();
//        for (int i = 0 ; i < t; i++) {
//            b += "a";
//        }
        long ed = System.currentTimeMillis();
        System.out.println("使用 + 号连接花费的时间是:" + (ed - bg));


        StringBuilder builder = new StringBuilder("");

        bg = System.currentTimeMillis();
        for (int i = 0; i < t; i++) {
            builder.append("a");
        }
        ed = System.currentTimeMillis();
        System.out.println("使用append连接花费的时间是:" + (ed - bg));

    }
    @Test
    public void test5() {
        int t = 1000000;
        StringBuilder builder2 = new StringBuilder(t + 1);
        long bg = System.currentTimeMillis();
        for (int i = 0; i < t; i++) {
            builder2.append("a");
        }
        long ed = System.currentTimeMillis();
        System.out.println("使用预定义容量的方式:" + (ed - bg));
    }


    @Test
    public void test6() {
//        String a = new String("ab" );
        String t = new String("a") + new String("b");
//        a.intern();
        t.intern();
        String b = "ab";
        System.out.println(t == b);
    }

    @Test
    public void test7() {
        String t = (new StringBuilder("a").toString() + new StringBuilder("b").toString()).toString();
        String s2 = t.intern();
        System.out.println(s2 == t);
        System.out.println(s2 == "ab");
        System.out.println(t == "ab");
    }

    @Test
    public void test8() {
        // 加上append会从堆中引用，不加上append就不会从堆中进行引用
        String t = new StringBuilder("a").append("b").toString();
        // 如果不加上append就不会从堆中引用
//        String t = new StringBuilder("A").append("b").toString();

        String s2 = t.intern(); //如果没有直接从堆区域返回，有就有了
        String s1 = "a";
        String s3 = "ab"; // 在常量池中创建了一个
        System.out.println(s2 == t);
        System.out.println(s3 == t);
        System.out.println(s2 == s1);
    }

    @Test
    public void test9() {
        String s1 = new StringBuilder("ada").toString();
        String s2 = "ada";
        String s3 = "ada";
        System.out.println(s1 == s2);  // false
        System.out.println(s2 == s3); // true
    }

}
