package threadBase.unsafe;

import sun.invoke.util.VerifyAccess;
import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 18:20
 * @Description: 原子整数手动实现
 */
public class MyAtoInteger implements Amount{
    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe UNSAFE;


    public MyAtoInteger() {
    }

    public MyAtoInteger(int x) {
        this.value = x;
    }

    // 需要抛出运行时异常，否则会报错
    static {
        UNSAFE =  UnsafeAccecor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtoInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw  new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decreement(int amount) {
        while(true) {
            int prev = this.value;
            int next = prev - amount;
            if(UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    public boolean compareAndSet(int prev, int nx) {
        return UNSAFE.compareAndSwapInt(this, valueOffset, prev, nx);
    }


    @Override
    public boolean withdrwa(int x) {
        while(true) {
            int prev = getValue();
            if (prev < x) return false;
            int nx = prev - x;
            if(this.compareAndSet(prev, nx)) {
                break;
            }
        }
        return true;
    }

    @Override
    public int getAmout() {
        return getValue();
    }

    public static void main(String[] args) {
//        MyAtoInteger t = new MyAtoInteger(1000);
//        t.decreement(1000);
//        System.out.println(t.getValue());
        // 取款1000元，最后剩下对就行
        TestAmount.testAmout(new MyAtoInteger(10000));
    }
}

