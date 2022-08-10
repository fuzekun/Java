package exeSystem;

/*
*
*   由于这是“静态分派”，所以编译器在重载时是根据参数的“静态类型”而不是“实际类型”作为判定依据的
*   这也是为什么说重载经常被放在方法解析阶段进行讲解，因为都是由编译器对方法进行选择的。
* */
public class StaticDispatch {

    static abstract class Human {}
    static class Man extends Human{}
    static class Women extends Human{}

    public void sayHello(Human human) {
        System.out.println("Hello guys");
    }

    public void sayHello(Women women) {
        System.out.println("Hello women");
    }

    public void sayHello(Man man) {
        System.out.println("Hello man");
    }

    public static void main(String[] args) {
        Human women = new Women();
        Human men = new Women();
        StaticDispatch sd = new StaticDispatch();
        sd.sayHello(men);
        sd.sayHello(women);
    }
}
