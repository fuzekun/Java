package threadBase.model;

import java.util.Random;

/**
 * @author: Zekun Fu
 * @date: 2022/5/21 11:07
 * @Description: 使用线程池进行归并排序
 *
 *
 * 设计：
 * 1. 开启16个线程进行排序。
 * 2. 进行划分
 * 3. 使用快速排序进行排序。
 * 4. 把16个结果放到堆里面，取出一个，然后维护堆和原本的堆就行了。
 *
 *
 * 会快多少呢？
 * 1. 首先16个线程并行运行，快16倍
 * 2. 其次，每一个线程计算的时间复杂度是O(n/16 * log(n/16))。
 * 3. 最后最进行合并的时候，需要O(nlog16)的时间复杂度。
 * 4. 运行时间基本上可以看作是线性的。
 *
 *
 *
 *
 */
public class SortUtils {
    private int[] tmp;

    void merge_sort(int[] arr, int l, int r) {
        if (l >= r) return ;
        int mid = (l + r) >> 1;
        merge_sort(arr, l, mid);            // [l, mid]进行排序
        merge_sort(arr, mid + 1, r);      // [mid + 1, r]进行排序

        int i = l, j = mid + 1, k = l;
        while(i <= mid && j <= r) {         // 选择较小的
            tmp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        while(i <= mid) tmp[k++] = arr[i++];
        while(j <= r) tmp[k++] = arr[j++];

        for (int t = l; t <= r; t++) arr[t] = tmp[t];
    }

    public static void printArr(int[] a) {
        for (int x: a) System.out.print(x + " ");
        System.out.println();
    }
    public static void printArr(int[][]arr) {
        for (int[] tmp : arr) {
            printArr(tmp);
        }
    }

    public static int[] getArr(int n, int m) {
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = random.nextInt(m);
        return arr;
    }

    /*'
    * 判断两个数组是否相等
    * */
    public static boolean check(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 10;
        SortUtils th = new SortUtils();
        th.tmp = new int[n];
        int[] arr = getArr(n, 100);
        printArr(arr);
        th.merge_sort(arr, 0, n - 1);
        printArr(arr);


    }

}
