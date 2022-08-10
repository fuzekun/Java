package adapter;


import java.util.TreeMap;

/*
*
*   客户端
*   进行测试
* */
public class DuckTestDriver {
    public  static void main(String args[]) {
        Duck duck = new MallarDuck();       //必须符合里氏替换原则

        Turkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey); //火鸡转换成鸭子

        System.out.println("The turkey says...");
        turkey.quack();
        turkey.fly();

        System.out.println("The duck says");
        duck.quack();
        duck.fly();

        System.out.println("The TurkeyAdapter says");
        turkeyAdapter.quack();
        turkeyAdapter.fly();

    }
}
