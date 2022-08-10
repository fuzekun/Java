package threadBase.unsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author: Zekun Fu
 * @date: 2022/6/1 16:33
 * @Description:
 * 测试LongAdder和Integer的差距
 */
public class LongAdderTest {


    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            LongAdderTest.demo(
                    ()->new AtomicLong(0),
                    (adder)->adder.getAndIncrement());

            LongAdderTest.demo(
                    ()->new LongAdder(),
                    (adder)->adder.increment()
            );
        }
    }

    private static <T>void demo(Supplier<T> adderSupplier, Consumer<T>action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        // 4个线程
        for (int i = 0; i < 32; i++) {
            ts.add(new Thread(()->{
                for (int j = 0; j < 500000; j++)
                    action.accept(adder);
            }));
        }

        long start = System.nanoTime();
        ts.forEach(t->t.start());
        ts.forEach(t->{             // 等待所有的线程执行完成
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });             // 等待所有的线程执行完成之后进行输出
        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end - start) / 1000_000);
    }
}
