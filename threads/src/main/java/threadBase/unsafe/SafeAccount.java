package threadBase.unsafe;

import threadBase.model.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 19:18
 * @Description:
 * 不加锁实现安全的account
 */
public class SafeAccount implements Amount {

    AtomicInteger value;
    public SafeAccount(int x) {
        this.value = new AtomicInteger(x);
    }

    @Override
    public boolean withdrwa(int x) {
        while(true) {
            int prev = value.get();
            if (prev < x) return false;
            int nx = prev - x;
            if (value.compareAndSet(prev, nx))
                break;
        }
        return true;
    }

    @Override
    public int getAmout() {
        return 0;
    }

    public AtomicInteger getValue() {
        return value;
    }


    public static void main(String[] args) {
        TestAmount.testAmout(new SafeAccount(1000));
    }
}
