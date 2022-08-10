package com.mr.test;

//通配符实在使用的时候用的，T extends是在定义的时候使用的
public class Pair<T> {
    private T first;         //? extends employ
    public static void print(Pair<Employee>p) {

    }

    public void setPair(T x) { //? extends employee first是某一个子类，x也是某一个子类，两个子类如果是兄弟，就没法赋值
        first = x;
    }

    public T getFirst() {       //? extends employee返回值是某一个子类，可以根据左端的赋值来进行判断类型
        return first;
    }

    public static void main(String[] args) {
        Pair<Employee> manager = new Pair<>();
        Manager newE = new Manager();
        Pair<? super Employee> p = manager;
        p.setPair(newE);
        Employee x = (Employee) p.getFirst();
    }

}
