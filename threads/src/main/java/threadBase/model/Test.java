package threadBase.model;

/**
 * @author: Zekun Fu
 * @date: 2022/5/31 16:32
 * @Description: 测试类
 * 使用继承的方式，这样就不用赋值粘贴测试代码的逻辑了。
 * 实现了代码的复用。
 */
public class Test {

    public static void testMesQ(MesQ mq) {

        for (int i = 0; i < 3; i++) {
            int idx = i;
            new Thread(()->{
                System.out.println("生产者线程" + idx + "生产消息" + idx);
                try {
                    mq.put(new Message("消息" + idx));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"生产者线程" + i).start();
        }

        Thread t = new Thread(()-> {

            Thread cur = Thread.currentThread();
            while (true) {
                if (cur.isInterrupted()) {
                    break;
                }
                try {
                    Thread.sleep(1000);     // 每隔1s消费一次
                    Message msg = mq.get();
                    System.out.println("消费" + msg.getMsg());
                } catch (InterruptedException e) {
                    cur.interrupt();
                    System.out.println("停止消费");
                }
            }
        }, "消费者线程");
        t.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}
