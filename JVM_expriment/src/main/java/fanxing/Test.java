package fanxing;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        Integer[] a = new Integer[5];
        min(a);
    }
    public static <T extends Comparable> T min(T[] a) {
        ArrayList<? extends Comparable> b ;
        ArrayList<Integer> c = new ArrayList<Integer>();
        c.add(5);
        c.add(6);
        b = c;
        System.out.println(b.get(0));
        return a[0];
    }
}
