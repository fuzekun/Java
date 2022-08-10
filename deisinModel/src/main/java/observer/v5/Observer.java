package observer.v5;

public abstract class Observer {

    public abstract boolean interst(Event e);       // 判断事件是否感兴趣,不感兴趣就不接受了
    public abstract void doit(Event e) ;
}
