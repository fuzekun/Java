package command.v1;

import javafx.scene.effect.Light;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 11:29
 * @Description:
 */
public class LightOnCommand implements Command{

    MyLight light;

    public LightOnCommand(MyLight light) {
        this.light = light;
    }

    @Override
    public Object excute() {
        light.on();
        return null;
    }
}
