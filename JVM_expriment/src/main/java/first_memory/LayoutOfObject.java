package first_memory;


/*
*   对象内存布局的探索
*   1. 对象的内存分成几部分
*   2. 每一部分有几个字节组成
*   3. 每一部分的作用
*
* */


import org.openjdk.jol.info.ClassLayout;

class A {
    long l;
    int i;
}

class B extends A {
    long l;
}

public class LayoutOfObject {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        System.out.print(ClassLayout.parseClass(A.class).toPrintable());
        System.out.print(ClassLayout.parseClass(B.class).toPrintable());

    }
}
