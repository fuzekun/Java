package geneticParadigm;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
*
*   通配符类型捕获
*
* */
public class TestSwap {
    static class Employ implements Comparable<Employ>{
        private String name;
        @Override
        public int compareTo(Employ o) {
            return this.name.compareTo(o.getName());
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    static class  Employee extends Employ{
        Employee(String name) {
            setName(name);
        }
    }
    static class Manager extends Employ {
        Manager(String name) {
            setName(name);
        }
    }
    static class MyPair<T> {
        T first;
        T second;

        public void setFirst(T first) {
            this.first = first;
        }

        public void setSecond(T second) {
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public T getSecond() {
            return second;
        }
    }

    public static void swap(MyPair<?>p) {
        swapHandler(p);
    }
    public static <V> void swapHandler(MyPair<V> p) {
        V tmp = p.first;
        p.first = p.second;
        p.second = tmp;
    }

    public static<T> void minmaxBounus(MyPair<? super T>p) {
        ArrayList<Employ>list = new ArrayList<>();
        Collections.sort(list);
    }

    public static<T> void set(MyPair<? super T>p) {
        p.setSecond((T)p.getSecond());
        p.setSecond((T)p.getFirst());
    }


    public static void main(String[] args) {
        MyPair<Employ> myPair = new MyPair<>();
        myPair.setFirst(new Manager("fuzekun"));
        myPair.setSecond(new Employee("qita"));
        minmaxBounus(myPair);
        System.out.println(myPair.getFirst().getName());
    }

}





