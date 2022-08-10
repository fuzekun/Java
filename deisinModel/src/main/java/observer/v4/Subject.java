package observer.v4;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    List<Observer>observers;
    int state = 0;

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
        for (Observer o : observers) {
            o.doit(this);
        }
    }
}
