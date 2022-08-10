package threadBase.model;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 19:01
 * @Description:
 * 保护性暂停，
 * Future 中get方法的实现原理
 *
 * 写信者和收信者1：1实现模型。
 * 写信的人和收信的人是一一对应的关系。
 * 两个线程通过一个唯一的id确定彼此的身份。
 *
 * 1. 解耦了收信人线程和写信人线程
 * 2. 防止写信人线程用完对象不销毁。
 * 3. MailBoxes获取之后应该销毁对象。
 *
 *
 * 多个线程之间速度不匹配，所以使用消息队列。也就是生产者消费者模型。
 * 这里的停止等待也可以看作是一种一对一的解决速度不匹配问题的机制。
 *
 * 加上MailBox并没有改变这个的本质，只是方便编码了而已，就是把future
 * 放入了一个公共的消息队列，然后消费者进行取出。
 *
 * 可以看作是弱鸡版的生产者消费者模型。
 * 具有缓解速度不匹配问题的机制，但是必须要实现一对一的模型。
 *
 *
 */

class MailBoxes {           // 消息队列机制

    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;
    // 产生唯一的id
    private static synchronized int generateId() {
        return id++;
    }

    // boxes类是线程安全的, 没有调用boxes的不同方法，所以是线程安全的
    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    // 如果有一个线程返回了keySet
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    public static GuardedObject getGuardedObject(int id) {
        return boxes.remove(id);        // 使用remove方法，防止内存泄漏
    }
}

/*
*
*   消费者线程消费特定的future也就是GuardObject。
* */

class ReadPeople extends Thread{    // 生产者线程
    @Override
    public void run() {
        //  收信
        // 1. 创建信箱等待收信
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        System.out.println(Thread.currentThread().getName() + "等待收信 id:" + guardedObject.getId());
        Object mail = guardedObject.get(5000);
        System.out.println(Thread.currentThread().getName() + "受到信:" + guardedObject.getId() + " 内容:" + mail);
    }
}

/*
*
* 生产者线程，生产特定的Future
**/
class WriteMan extends Thread{          // 消费者线程
    private int id;
    private String mail;
    WriteMan(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(id);
        System.out.println(Thread.currentThread().getName() + "写信 id = " + id +" 内容:" + mail);
        guardedObject.complete(mail);
    }
}

public class GuardedObject {        // future任务机制



    private int id;

    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Object get(long timeout) {
        synchronized (this) {
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while(response == null) {
                long waitTime = timeout - passedTime;   // 这里是为了防止虚假唤醒
                if (waitTime <= 0) {
                    System.out.println("超时等待!!");
                    break;
                }
                try {
//                    System.out.println("等待中..");  // 如果被虚假唤醒
                    this.wait(waitTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                System.out.println("被唤醒了"); // 如果被虚假唤醒
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }
    // 获取结果
    public Object get() {
        synchronized (this) {
            // 等待结果
            while (response == null) {
                try {
                    this.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;    // 这里自然释放锁
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        GuardedObject go = new GuardedObject(1);
//        new Thread(()-> {
//            // 等待两秒
//            Object response = go.get(2000);
//            System.out.println("结果是" + response);
//        }).start();
//
//        new Thread(()-> {
//            try {
//                // 3s才进行返回
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            go.complete(new Object());
//        }).start();
//
//        // 虚假唤醒
//        new Thread(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            go.complete(null);
//
//        }).start();



        // 模拟写信和收信的过程
        // 1. 三个收信的人
        for (int i = 0; i < 3; i++) {
            ReadPeople p = new ReadPeople();
            p.start();
        }
        Thread.sleep(100);
        Vector<Integer>ids = new Vector<>();
        ids.addAll(MailBoxes.getIds());
        for (int idx: ids) {
            GuardedObject guardedObject = MailBoxes.getGuardedObject(idx);
            guardedObject.complete(new String("内容" + idx));
//            new WriteMan(idx, "内容" + idx).start();
        }


    }
}
