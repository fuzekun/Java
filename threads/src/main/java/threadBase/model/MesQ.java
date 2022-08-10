package threadBase.model;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 16:33
 * @Description: 统一接口，方便测试
 */
public interface MesQ {
    public void put(Message msg) throws InterruptedException;
    public Message get() throws InterruptedException;
}
