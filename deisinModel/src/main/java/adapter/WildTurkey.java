package adapter;

public class WildTurkey implements Turkey{
    @Override
    public void quack() {
        System.out.println("野火鸡正在叫唤");
    }

    @Override
    public void fly() {
        System.out.println("野生火鸡正在飞翔");
    }
}
