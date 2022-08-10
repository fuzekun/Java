package fanxing;

import javafx.util.Pair;


/*
*
*   设计泛型类的时候要注意的点
* */


public class Employee <T, E>{

    public static class BMOne {
        public Object getT() {
            return null;
        }
    }

    public static class BMTwo extends BMOne {
        public String getT() {      // 重写了父方法？
            return "Nihao";
        }
    }

    T name;
    E id;

    public Employee(T name, E id) {
        this.name = name;
        this.id = id;
    }

    public E getId() {
        return id;
    }

    public void setId(E id) {
        this.id = id;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        Manage<String, Integer>mng = new Manage<>("fda", 1);
        Employee<Manage, Integer>employee = new Employee<>(mng, 2);
        employee.setName(mng);
        Employee<? extends Employee, ? extends Integer> e = employee;
        Employee t= e.getName(); // 返回值是他的一个子类型，所以可以被接收
        /*
        *   1. 调用返回
        *   2. 编译器帮忙强转
        *   3. 如果类型不行，就报编译错误。
        *   4. 就是说，编译器知道那是错误
        *
        *
        *    父类可以接收子类，子类无法接收父类
        *    所以Employee可以接收? extends Employee
        *    但是? extends Employee 无法接收  任何类型。
        *
        *
        *
        *   为什么内部可以赋值？即 this.name = name怎么进行的？
        *   因为name和this的name都是Object类型。
        *   只不过到最后用的时候返回成了Employee类型罢了。
        *
        *
        *  */

        System.out.println(e.getName().getId());
        System.out.println(employee.getId());

        /*
            ? extends Employee name;
        *   ? extends Employee getName() {
        *       return name;
        *   } 所以说name和返回值是同类型的。
        *
        *
        *   访问的时候，get返回的是一个 ： ？ extends Employee
        *   修改的时候, set无法判断是
        *

                1. e是一个对象指针，类型是
                2. e有get和set方法，
                3. 为什么能判断返回类型呢，为什么不能判断修改类型呢？
                原理是强制转换，那么强制转换成了某一个子类型，你需要知道
                通配类型是怎么实现的。

                1. 转换成裸类型
                2. 遇到使用的地方，强转换成 E 的子类
                3. 设计的真的是佶屈聱牙。
        */

    }
}

class Manage <T, E>extends Employee<T, E> {

    public Manage(T name, E id) {
        super(name, id);
    }

    @Override
    public void setName(T name) {
        super.setName(name);
    }

    @Override
    public T getName() {
        return super.getName();
    }

    @Override
    public void setId(E id) {
        super.setId(id);
    }

    @Override
    public E getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    Pair<String, String>pr[] = (Pair<String, String>[]) new Pair<?, ?>[10];
}

class Employees <T, E>extends Employee<T, E> {


    Employees(T name, E id) {
        super(name, id);
    }
    @Override
    public void setName(T name) {
        super.setName(name);
    }

    @Override
    public T getName() {
        return super.getName();
    }

    @Override
    public void setId(E id) {
        super.setId(id);
    }

    @Override
    public E getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return super.toString();
    }



}