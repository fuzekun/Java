package threadBase.unsafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 17:06
 * @Description:    测试原子类数组
 */
public class TestAtomicArray {

    /*
    *   参数的含义
    *   1. 创建数组
    *   2. 获取长度
    *   3. 操作结果
    *   4. 输出数组
    * */



    public static void main(String[] args) {
        demo(
                ()->new int[10],
                (array)-> array.length,
                (array, index) -> array[index] ++,
                (array) -> System.out.println(Arrays.toString(array))
        );

        demo(
                ()->new AtomicIntegerArray(10),
                (array) ->array.length(),
                (array, index)->array.incrementAndGet(index),
                (array)-> System.out.println(array.toString())

        );
    }
    public static <T> void demo (
            Supplier<T>s,
            Function<T, Integer> lenFunc,
            BiConsumer<T, Integer>putCon,
            Consumer<T> printCon
    ) {
        T array = s.get();
        int len = lenFunc.apply(array);

        List<Thread>list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            Thread t = new Thread(()-> {
                for (int j = 0; j < 10000; j++) {
                    putCon.accept(array, j % len);
                }
            });
            t.start();
            list.add(t);
        }
        /*
        *   这里不可以放在上面的for循环，
        * 因为一旦开启就进行等待，结果就同task.get放进了for循环，
        * 必须等线程执行完成才进行后面线程的开启
        *
        *
        *
        * */
        for (int i = 0; i < len; i++) {
            try {
                list.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printCon.accept(array);
    }

}
