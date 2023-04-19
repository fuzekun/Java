package com.cal;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author: Zekun Fu
 * @date: 2023/4/13 20:44
 * @Description:
 *
 * 计算陡峭数组的数目
 *
 */
public class First {

    private static int ans = 0;
    private static int n;
    private static int[] nums;

    private static void dfs(int cur, int x, int sum) {
        if (cur == n - 1) {
            ans += sum;
//            System.out.println(Arrays.toString(nums));
            return;
        }
//        nums[cur] = x;
        for (int i = 0; i <= 2; i++) {
            if (x != i)
                dfs(cur + 1, i, sum + Math.abs(i - x));
        }
//        dfs(cur + 1, (x + 1) % 3, sum + Math.abs((x + 1) % 3 - x));
//        dfs(cur + 1, (x + 2) % 3, sum + Math.abs((x + 2) % 3 - x));
    }
    /**
     *
     * 这个递推公式可以化简成(2^(n + 1)) * (n - 1)
     *
     *
     * */
    private static int dfs2(int n) {
        if (n <= 2) {
            return 8;
        }
        return 2 * dfs2(n - 1) + (int)Math.pow(2, n + 1);
    }
    /**
     *
     * a ^ b % mod;
     * */
    private static long quick_pow(long a, long b, long mod) {
        long res = 1;
        while (b != 0) {
            if ((b & 1) == 1) res = (res * a) % mod;
            a = a * a % mod;
            b >>= 1;
        }
        return res;
    }
    public static void main(String[] args) {
        int mod = (int)1e9 + 7;
        for (int i = 2; i <= 10; i++) {
            n = i;
            ans = 0;
            dfs(0, 0, 0);
            dfs(0, 1, 0);
            dfs(0, 2, 0);
            long ans = quick_pow(2, n + 1, mod) * (n - 1) % mod;
            System.out.println(ans + " " + dfs2(i) +" " + ans);
        }
        // 综上所述，答案为(n - 1) * 2^(n + 1)
    }
}
