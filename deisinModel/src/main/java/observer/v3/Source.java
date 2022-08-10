package observer.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Source {
    List<Observer>observers;
    int state;
    String Dir;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Source() {
        observers = new ArrayList<>();
    }

    public Source addObserver(Observer o) {     // 链式添加
        observers.add(o);
        return this;
    }

    public void setDir(String dir) {
        Dir = dir;
    }

    public String getDir() {
        return Dir;
    }

    public void notifyAllOberver() {
        Random random = new Random();
        String[] dirs = {"u", "d", "l", "r"};
        for (Observer o : observers) {
            // 随机创建一个事件，不知道是哪个具体事件
            Event e = random.nextInt(2) % 2 == 0 ?
                    new DirEvent(this, dirs[random.nextInt(4)]) :
                    new NumEvent(this, random.nextInt(4));
            o.doit(e);   // 创建一个事件，把自己作为源对象
        }
    }

}
