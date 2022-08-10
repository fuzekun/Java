package command.v1;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 11:28
 * @Description:
 *
 * 抽象的Command类，只有一个excute方法
 */
public interface Command {
    public Object excute();

}
