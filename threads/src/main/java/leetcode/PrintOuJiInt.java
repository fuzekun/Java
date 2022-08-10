package leetcode;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 16:05
 * @Description:
 *
 * 写一个父类，方便后面的进行多态
 */
public abstract class PrintOuJiInt {

    abstract public void zero(IntConsumer printNumber) throws InterruptedException ;

    abstract public void even(IntConsumer printNumber) throws InterruptedException;

    abstract public void odd(IntConsumer printNumber) throws InterruptedException;
}
