package observer.v3;

public class Event {
    private Source source;


    public Event() {

    }
    public Event(Source source) {
        this.source = source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Source getSoce() {
        return this.source;
    }
}
