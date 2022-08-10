package observer.v7;

public interface Subject {

    void addObserver(Observer o);
    void delObserver(Observer o);
    void notifyAllObservers();
}
