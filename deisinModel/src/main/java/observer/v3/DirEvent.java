package observer.v3;

public class DirEvent extends Event{
    private String key_press;

    public DirEvent(Source source, String key_press) {
        this.key_press = key_press;
        super.setSource(source);
    }
    public void setKey_press(String key_press) {
        this.key_press = key_press;
    }

    public String getKey_press() {
        return key_press;
    }
}
