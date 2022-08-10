package adapter;

public class MallarDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("mall鸭子正在叫唤");
    }

    @Override
    public void fly() {
        System.out.println("mall鸭子正在飞翔");
    }
}
