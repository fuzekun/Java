package threadBase.safe;

/**
 * @author: Zekun Fu
 * @date: 2022/5/25 20:08
 * @Description: 测试7条happen-before
 *              重点是线程Interrupted规则
 */
public class Happen_Before {

    static int x;
    public static void testInterruptRule() {

        Thread t2 = new Thread(()->{
            while(true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程中断了" + x);
                    break;
                }
            }
        },"t2");
        t2.start();
        new Thread(() -> {
            try{
                Thread.sleep(1);
            }catch (Exception e) {
                e.printStackTrace();
            }
            x = 10;                         // 这个写，对于t2的读可见
            t2.interrupt();
            }, "t1").start();

        System.out.println(x);              // 这个是主线程的x，
    }

    public static void main(String[] args) {
        testInterruptRule();
    }


}
