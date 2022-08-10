package Class;

public class Main {
    public static void main(String[] args) {
        DM dm = new DM();
        DM.Employee e = new DM.Employee();
        /*
        *
        *   从这里不可以访问e.date，因为直接就是不可见的。
        * */
    }
}
