package threadBase.changeState;

/**
 * @author: Zekun Fu
 * @date: 2022/5/23 9:03
 * @Description: 如何优雅的关闭线程？
 *                  - stop, suspend会破坏同步块，从而可能产生死锁
 *                  - 使用interrupt方法
 */
public class StopThread {

    Thread t;

    public void task() {
        System.out.println("正在监控");
    }
    public StopThread() {
        t = new Thread(()-> {
            Thread current = Thread.currentThread(); // 获取当前线程
            while(true) {
                if (current.isInterrupted()) {
                    System.out.println("进行资源关闭的后续处理");
                    break;
                }
                try {
                    Thread.sleep(2000);
                    task();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("睡眠中被打断, 必须重新设置打断标记");
                    current.interrupt();
                }
            }
        });
    }

    public void run() {
        t.start();
    }
    public void stop() {
        t.interrupt();
    }
    public static void main(String[] args) throws InterruptedException{
        StopThread st = new StopThread();
        st.run();
        Thread.sleep(1000);
        st.stop();
    }
}
