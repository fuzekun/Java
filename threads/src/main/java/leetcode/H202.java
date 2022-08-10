package leetcode;

public class H202 {     // 这种方法的缺点很明显就是说特别的慢，因为一直在就绪队列中，可能刚获得cpu就得切换了。
    // 这种适合少线程的进行

    private volatile int state = 0;
    private int hcnt = 0;


    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while(state == 1) {
            Thread.yield();
        }
        releaseHydrogen.run();
        if (++hcnt == 2) {
            state = 1;
            hcnt = 0;
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while(state == 0) {
            Thread.yield();
        }
        releaseOxygen.run();
        state = 0;
    }

    public static void main(String[] args) {
        H2O.main(args);
    }
}
