package observer.v5;

public class NumEvent extends Event{
    private int key_press;

    public NumEvent(Subject source, int key_press) {
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
