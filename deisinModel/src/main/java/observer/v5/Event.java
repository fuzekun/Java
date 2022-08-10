package observer.v5;

public class Event {
    private Subject source;


    public Event() {

    }
    public Event(Subject source) {
        this.source = source;
    }

    public void setSource(Subject source) {
        this.source = source;
    }

    public Subject getSoce() {
        return this.source;
    }
}
