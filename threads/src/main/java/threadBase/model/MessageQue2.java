package threadBase.model;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 16:30
 * @Description: 使用Semaphore实现
 *
 *
 *
 */
public class MessageQue2 implements MesQ{

    private Semaphore mutex = new Semaphore(1);
    private Semaphore full;
    private Semaphore empty;
    LinkedList<Message>list = new LinkedList<>();

    public MessageQue2(int capcity) {
        full = new Semaphore(capcity);
        empty = new Semaphore(0);
    }

    @Override
    public void put(Message msg) throws InterruptedException {
        full.acquire();
        mutex.acquire();
        list.addLast(msg);
        mutex.release();
        empty.release();
    }

    @Override
    public Message get() throws InterruptedException {
        empty.acquire();
        mutex.acquire();
        Message ans = list.removeFirst();
        mutex.release();
        full.release();
        return ans;
    }

    public static void main(String[] args) {
        Test.testMesQ(new MessageQue2(2));
    }
}
