package threadBase.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 15:48
 * @Description: 生产者消费者，使用自己加锁实现
 */
public class MessageQue implements MesQ{

    LinkedList<Message>que = new LinkedList<>();
    int capcity;

    public MessageQue(int capcity) {
        this.capcity = capcity;
    }

    public void put(Message x) throws InterruptedException{
        synchronized (que) {
            while (que.size() >= capcity) {
                System.out.println("队列已满，" + Thread.currentThread().getName() + "等待");
                que.wait();
            }
            que.addLast(x);         // 尾部添加
            que.notifyAll();
        }
    }

    public Message get() throws InterruptedException{
        synchronized (que) {
            while (que.size() == 0) {
                que.wait();
            }
            Message msg = que.removeFirst();        // 头部取出
            que.notifyAll();
            return msg;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test.testMesQ(new MessageQue(2));
    }
}

class Message {
    private String msg;
    public Message(String mgs) {
        this.msg = mgs;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
