package threadBase.unsafe;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 18:49
 * @Description: 不安全的int类型，直接进行减去，最后会得不到正确结果
 */
public class UnsafeAmount implements Amount{

    int value = 0;

    public UnsafeAmount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getAmout() {
        return getValue();
    }

    @Override
    public boolean withdrwa(int x) {
        if (getValue() >= x) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value -= x;
            return true;
        }
        else return false;
    }


    public static void main(String[] args) {
        TestAmount.testAmout(new UnsafeAmount(1000));
    }
}
