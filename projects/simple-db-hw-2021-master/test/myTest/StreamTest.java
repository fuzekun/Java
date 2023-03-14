package myTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Zekun Fu
 * @date: 2023/3/14 9:54
 * @Description:
 * 测试最大值最小值使用流获取
 */
public class StreamTest {
    public static void main(String[] args) {
        long[] arr = new long[]{1,2,3,4,5};
        System.out.println(Arrays.stream(arr).max().getAsLong());
        System.out.println(Arrays.stream(arr).max().orElse(0));
        System.out.println(Arrays.stream(arr).reduce(Long::max).orElse(0));
        System.out.println(Arrays.stream(arr).reduce(Long::max).getAsLong());
        List<Long>list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(list.stream().max(Long::compareTo).get());
        HashMap<Long, Long>mp = new HashMap<>();
        for (long i = 1; i <= 5; i++) {
            mp.put(i, i);
        }
        System.out.println(mp.keySet().stream().max(Long::compareTo).get());

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(numbers.stream().max(Integer::compareTo).get());
    }
}
