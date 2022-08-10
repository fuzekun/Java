package observer.v3;

public class NumEvent extends Event{
    private int key_press;

    public NumEvent(Source source, int key_press) {
        this.key_press = key_press;
        super.setSource(source);
    }

    public void setKey_press(int key_press) {
        this.key_press = key_press;
    }

    public int getKey_press() {
        return key_press;
    }
}
