package com.mr.test;

//ͨ���ʵ��ʹ�õ�ʱ���õģ�T extends���ڶ����ʱ��ʹ�õ�
public class Pair<T> {
    private T first;         //? extends employ
    public static void print(Pair<Employee>p) {

    }

    public void setPair(T x) { //? extends employee first��ĳһ�����࣬xҲ��ĳһ�����࣬��������������ֵܣ���û����ֵ
        first = x;
    }

    public T getFirst() {       //? extends employee����ֵ��ĳһ�����࣬���Ը�����˵ĸ�ֵ�������ж�����
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
