package threadBase.unsafe;

/**
 * @author: Zekun Fu
 * @date: 2022/6/6 8:37
 * @Description: 使用Synchornized进行时间的测试
 */
public class SynchorenizedAccount implements Amount{


    int value;
    Object obj = new Object();
    public SynchorenizedAccount(int x) {
        this.value = x;
    }

    @Override
    public boolean withdrwa(int x) {
        synchronized (obj) {
            int prev = getValue();
            if (prev >= x) {
                this.value -= x;
            } else {
                return false;
            }
        }
        return true;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getAmout() {
        return getValue();
    }

    public static void main(String[] args) {
        TestAmount.testAmout(new SynchorenizedAccount(100000));
    }
}
