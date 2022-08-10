package observer.v5;




import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Subject {

    List<Observer>observers;
    int state = 0;
    String dir;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Subject() {
        observers = new ArrayList<>();
    }
    public void add(Observer o) {
        observers.add(o);
    }
    public void notifyAllObservers() {
        Random random = new Random();
        String[] dirs = {"u", "d", "l", "r"};
        for (Observer o : observers) {
            Event e = random.nextInt(2) % 2 == 0 ?
                    new DirEvent(this, dirs[random.nextInt(4)]) :
                    new NumEvent(this, random.nextInt(4));
            if (o.interst(e)) o.doit(e);
        }
    }


    public void setDir(String dir) {
        this.dir = dir;
    }

    public String  getDir() {
        return dir;
    }
}
