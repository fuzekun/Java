package command.v1;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 11:32
 * @Description:
 *
 * 具体的invoker类，也就是菜单类，负责接收命令，设置命令和调用命令。
 */
public class SimpleRemoteControl {

    Command slot;
    public void setCommand(Command command) {
        slot = command;
    }
    public void pressButton() {
        slot.excute();
    }

    public static void main(String[] args) {
        SimpleRemoteControl s = new SimpleRemoteControl();
        MyLight light = new MyLight();
        s.setCommand(new LightOnCommand(light));
        s.pressButton();


    }
}
