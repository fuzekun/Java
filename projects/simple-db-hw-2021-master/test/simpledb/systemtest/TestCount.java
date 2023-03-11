package simpledb.systemtest;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author: Zekun Fu
 * @date: 2023/3/6 17:01
 * @Description: 测试一下计数
 */
public class TestCount {

    @Test
    public void test() {
        System.out.println(hammingWeight(9));
        System.out.println(hammingWeight(7));
        System.out.println(hammingWeight(8));
    }
    public int hammingWeight(int n) {
        n = (n & 0x55555555) + ((n >>> 1)  & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2)  & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >>> 4)  & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >>> 8)  & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
        return n;
    }
    @Test
    public void testdfs() {
        int size = 2;
        Set<Set<Integer>>els = new HashSet<>();
        List<Integer> v = new ArrayList<>();
        for (int i = 0; i < 7; i++) v.add(i);
        int n = v.size();
        for (int i = 0; i < 1<<n; i++) {
            if (hammingWeight(i) != size) continue;
            Set<Integer> tmp = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if ((i >> j & 1) == 1) tmp.add(v.get(j));
            }
            els.add(tmp);
        }
        for (Set<Integer> tmp : els) {
            System.out.println(Arrays.toString(tmp.toArray()));
        }
        System.out.println(els.size());
//        dfs(v, size, 0, els, new ArrayDeque<>());
//        return els;
        Set<Set<Integer>> res = new HashSet<>();
        dfs(v, size, 0, res, new ArrayDeque<>());
        for (Set<Integer> tmp : res ) {
            System.out.println(Arrays.toString(tmp.toArray()));
        }
        System.out.println(res.size());
    }
    private <T> void dfs(List<T>list, int size, int begin, Set<Set<T>> res, Deque<T> path) {
        if (path.size() == size) {
            res.add(new HashSet<>(path));
        }
        for (int i = begin; i < list.size(); i++) {     // 每次有两种方案，选择或者不选择
            path.addLast(list.get(i));
            dfs(list, size, i + 1, res, path);
            path.removeLast();                          // 回溯
        }
    }
}
