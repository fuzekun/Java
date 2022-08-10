package threadBase.changeState;

/**
 * @author: Zekun Fu
 * @date: 2022/5/23 9:24
 * @Description: 使用volatile进行
 */
public class StopThread2 {

    private volatile boolean stopFlag;
    private Thread t;

    public StopThread2() {
        this.stopFlag = false;
        t = new Thread(()-> {
            Thread current = Thread.currentThread(); // 获取当前线程
            while(true) {
                if (this.stopFlag) {
                    System.out.println("进行资源关闭的后续处理");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    task();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("加上interrupt,减少一次监控");
//                    current.interrupt();    // 不管进行这个设置了
                }
            }
        });
    }

    public void task() {
        System.out.println("执行监控中...");
    }
    public void run() {
        t.start();
    }
    public void stop() {
        this.stopFlag = true;
        t.interrupt();                      // 防止在睡眠中还得等好长时间。
    }
    public static void main(String[] args) throws InterruptedException{
        StopThread2 st = new StopThread2();
        st.run();
        Thread.sleep(3000);
        st.stop();
    }
}
