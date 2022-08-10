package geneticParadigm;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Constraint {

    /*
    *
    *   使用泛型的一些限制
    *
    * */
    // 3.不可以创建泛型数组
    @Test
    public void Tarray() {
        ArrayList [] list = new ArrayList[10];
        ArrayList<String>[] slist;
        slist = list;

        ArrayList<Integer>inlist = new ArrayList<>();
        inlist.add(100);
        list[0] = inlist;

        String s = slist[0].get(0);     // 转换异常， 但是如果改成String就不会出现了
        System.out.println(s);
    }
    @Test
    public void Tarray2() {
        ArrayList<String>[] slist = new ArrayList[10];
        ArrayList<String>sli = new ArrayList<>();
        slist[0] = sli;
        sli.add("1");
        System.out.println(slist[0].get(0));
    }
    @Test
    @SuppressWarnings("unchecked")      // 加上这个注解，就不会得到泛型数组的警告了。
    public void Tarray3() {

        ArrayList<String>[] table = (ArrayList<String>[]) new ArrayList<?>[10];
        ArrayList<String>list1 = new ArrayList<>();
        ArrayList<Integer>inlist = new ArrayList<>();
        table[0] = list1;
//        table[1] = inlist;            // 会报编译错误，所以是安全的。
        list1.add("rw");
        System.out.println(table[0].get(0));
    }


    /**
     *
     * 不能实例化类型变量
     * 也不使用new构造泛型数组
     *
     */

    class MyList<T> {
        T [] array;
        T attr;
        public MyList(Class<T>clz) {
            try {
                attr = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MyList(IntFunction<T[]> constr, int len) {
            array = constr.apply(len);
        }
        @SuppressWarnings("unchecked")
        MyList(Class<T>clz, int len) {
            array = (T[])Array.newInstance(clz, len);
            try{
                attr = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*
            *   此处没有抛出异常的原因，被Array内部消化了。
            * */
        }

        void put(int index, T t) {
            array[index] = t;
        }

        T get(int index) {
            return array[index];
        }
        T[] getInstance() {
            return array;
        }
    }

    @Test
    public void testArray() {
        MyList<String>list = new MyList<>(String.class, 10);
        list.put(1, "100");
        list.put(2, "101");
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        String[]array = list.getInstance();
        for (String s : array) {
            System.out.println(s);
        }
        list.attr = "fff";
        System.out.println("属性是:" + list.attr);


        System.out.println("使用lambda表达式进行数组的创建");
        MyList<Integer> list1 = new MyList<>(Integer[]::new, 2);
        list1.put(0, 323);
        list1.put(1, 323);

    }

    class MyList2<T> {
        T attr;

        public MyList2(Function<T, T>constr, T x) {
            attr = constr.apply(x);        // 有参构造器,参数为x，返回类型为T
        }

        public MyList2(Supplier<T>s) {      // 无参构造器，返回类型为T
            attr = s.get();
        }


        public void setAttr(T attr) {
            this.attr = attr;
        }

        public T getAttr() {
            return attr;
        }
    }

    @Test
    public void testArray2() {
        MyList2<Integer>myList21 = new MyList2<Integer>(Integer::new, 1);
        System.out.println(myList21.getAttr());


    }




}
