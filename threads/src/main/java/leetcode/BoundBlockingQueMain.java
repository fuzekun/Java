package leetcode;

import java.util.concurrent.ThreadLocalRandom;

public class BoundBlockingQueMain {

    public static void main(String[] args) throws Exception{
        BoundedBlockingQueue que = (BoundedBlockingQueue) Class.forName(args[0]).getDeclaredConstructor(int.class).newInstance(Integer.valueOf(args[1]));

        for (int i = 0; i < 3; i++) {
            // 3个生产者
            String name = "producer_" + i;
            new Thread(()->{
                // 使用线程本地变量进行随机数的生产。
                ThreadLocalRandom t = ThreadLocalRandom.current();

                try {
                    while(true) {
                        if (que.enSub()) {
                            int x = t.nextInt(19);
                            que.enqueue(x);
                            System.out.println(Thread.currentThread().getName() + "生产了数字:" + x);

                        }
                        else break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, name).start();
        }

        for (int i = 0; i < 4; i++) {
            // 4个消费者
            String name = "customer_" + i;
            new Thread(()->{
                try {
                    while(true) {
                        if (que.deSub()) {
                            int x = que.dequeue();
                            System.out.println(Thread.currentThread().getName() + "消费数字:" + x);
                        }
                        else break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, name).start();
        }
    }
}
