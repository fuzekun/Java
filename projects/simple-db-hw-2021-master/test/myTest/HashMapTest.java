package myTest;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Zekun Fu
 * @date: 2023/3/12 15:01
 * @Description: 测试hashMap和concurrenHashMap
 *  1. 测试遍历的时候，删除的情况
 *  2. 测试遍历的时候，增加的情况
 *
 */
public class HashMapTest {

    /**
     *
     * hashMap遍历的时候插入会报并发错误
     * concurrentHashMap不会出错
     * */
    @Test
    public void testInsertWhileScan() {
        ConcurrentHashMap<Integer, Integer>mp = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            mp.put(i, i);
        }
        int cnt = 11;
        for (int x : mp.keySet()) {
            if (x < 10) mp.put(cnt, cnt);
            cnt++;
        }
        for (int x : mp.keySet()) {
            System.out.println(x + " " + mp.get(x));
        }
    }
    public void testDeleteWhileScan() {
    }
}
