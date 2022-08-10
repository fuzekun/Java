package StringTest;


import java.lang.reflect.Field;
import java.util.Map;
/*
*
*
*   对象的创建过程
* 1. 检查有无类
* 2. 父类的创建
* 3. 分配内存
* 4. 进行初始化
* 5. invokesepecial调用<init>方法
* */

public class MethodInvokeTest {

    static class Father {
        public int x = 10;
        static {
            System.out.println("调用父类的静态部分了");
        }
        Father() {
            System.out.println("调用父类的构造器了");
            System.out.println("father.x = " + x);
            this.print();          // 因为new的是一个son，所以会调用son的print函数
        }
        void print() {
            System.out.println("Father.x = " + x);
        }
    }
    static class Son extends Father {
        private int x = 30;
        static {

            System.out.println("调用子类的静态部分了");
        }
        Son() {
            System.out.println("调用子类的构造器了");
            this.print();

        }
        void print() {
            System.out.println("Son.x = " + x); // 重写之后,Son.x还没来得及赋初始值
        }

        /*
         *   调用顺序
         * 1. 父类的静态部分
         * 2. 调用子类的静态部分
         * 3. 调用父类的构造方法
         * 4. 调用子类的构造方法
         *
         *
         * 1. 首先是类加载：父类和子类的字面量和静态变量被赋初值
         * 2. 其次是对象初始化，首先分配内存进行一步初始化
         *
         * 这个顺序是由于类加载所导致的 ： 加载 -> 验证 - > 准备 -> 解析 ->  初始化
         * 变量在静态代码中赋初值是在初始化阶段进行的，还有给所有的变量进行赋值为0。
         * 而经过类的初始化之后，才能够正常的"被使用"，也就是给Java程序员创建对象。
         *
         * */
    }

    static class Gf {
        private int x = 30;

        Gf() {
            this.print();
            x = 40;
        }
        public void print() {
            System.out.println("X = " + x);
        }
    }

    public static void main(String[] args) {
        Father f = new Son();
        System.out.println(f.x);
    }
}
