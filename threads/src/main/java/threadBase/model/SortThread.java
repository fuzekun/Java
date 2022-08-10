package threadBase.model;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Zekun Fu
 * @date: 2022/5/21 14:41
 * @Description: 分治排序的主线程
 *
 * 对于自动排序: 需要指定线程数量和每个线程处理数组的大小
 * 对于自动排序的2：需要执行线程数量和.. 以及分配一个和原数组一样大的tmp数组。
 *
 */
public class SortThread {


    private ExecutorService pool;           // 线程池

    public SortThread() {
        pool = Executors.newCachedThreadPool();        // 因为8核心就只有16个线程
    }

    private int[][] div(int[] arr, int d) {  // 数组划分成多少份
        int [][] ans = new int[d][];
        int n = arr.length;
        int len = n / d;
        for (int i = 0; i < d; i++) {
            int tmp[];
            if(i != d - 1) tmp = new int[len];
            else tmp = new int[n - (d - 1) * len];
            for (int j = i * len; j < (i + 1) * len; j++) {
                tmp[j - (i * len)] = arr[j];
            }
            ans[i] = tmp;
        }
        for (int i = d * len, j = len; i < n; i++, j++) {
            ans[d - 1][j] = arr[i];
        }
        return ans;
    }


    /*
    *   使用分治进行，每次分成两个线程进行排序，然后吧排好序的进行合并。
    * 这里进行数组的划分，进行分支。
    * */
    private int[] sortByAuto(int[] arr, int l, int r, int len) throws Exception{
        if (r - l + 1 <= len) {
            int []tmp = Arrays.copyOfRange(arr, l, r + 1);
            Arrays.sort(tmp);
            return tmp;
        }
        int mid = (l + r) >> 1;

        FutureTask<int[]> task1 = new FutureTask<int[]>(()->{
            int[] ans = sortByAuto(arr, l, mid, len);
            return ans;
        });
        FutureTask<int[]>task2 = new FutureTask<int[]>(()->{
            int[] ans = sortByAuto(arr, mid + 1, r, len);
            return ans;
        });
        new Thread(task1).start();
        new Thread(task2).start();

        // 由于是阻塞式的，所以再返回之前一定是已经完成了这两组的排序了
        int[] tmpl = task1.get();
        int[] tmpr = task2.get();
        int[] ans = new int[r - l + 1];
        int i = 0, j = 0, k = 0;
        while(i < tmpl.length && j < tmpr.length) {
            ans[k++] = tmpl[i] <= tmpr[j] ? tmpl[i++] : tmpr[j++];
        }
        while(i < tmpl.length) {
            ans[k++] = tmpl[i++];
        }
        while(j < tmpr.length) {
            ans[k++] = tmpr[j++];
        }
        return ans;
    }
    /*
     *
     * 不进行复制粘贴，直接使用原数组进行排序
     * */
    private void sortByAuto2(int[] arr, int l, int r, int len, int[] tmp) {
        if (r - l + 1 <= len) {
            Arrays.sort(arr, l, r + 1);
            return;
        }
        int mid = (l + r) >> 1;
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(()->{
            sortByAuto2(arr, l, mid, len, tmp);
            latch.countDown();
        }).start();
        new Thread(()->{
            sortByAuto2(arr, mid + 1, r, len, tmp);
            latch.countDown();
        }).start();

        try{
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = l, j = l, k = mid + 1;
        while(j <= mid && k <= r) {
            tmp[i++] = arr[j] <= arr[k] ? arr[j++] : arr[k++];
        }
        while(j <= mid) {
            tmp[i++] = arr[j++];
        }
        while(k <= r) {
            tmp[i++] = arr[k++];
        }
        for (i = l; i <= r; i++) arr[i] = tmp[i];
    }

        /*
    *
    *   直接采用分治的方式进行
    * 是一个错误的方法不知道怎么实现同步
    * */
    @Deprecated                 // 错误方法，别用了
    private int[] sortByAuto3(int[] arr, int l, int r, int len) {


        // 1. 递归基
        int[] tmp = new int[r - l + 1];
        for (int i = l, j = 0; i <= r; i++, j++) {
            tmp[j] = arr[i];
        }
        if (r - l + 1 <= len) {
            new Thread(()->{
                Arrays.sort(tmp);
            }).start();
            return tmp;
        }
        // 2.进行左右划分
        int mid = (l + r) >> 1;
        int[] tmpl = sortByAuto3(arr, l, mid, len);
        int[] tmpr = sortByAuto3(arr, mid + 1, r, len);

        // 3. 进行合并, 必须等待所有的子线程排序完成。
        int[] merge = new int[r - l + 1];
        int i = l, j = 0, k = 0;
        while(j < tmpl.length && k < tmpr.length) {
            merge[i - l] = tmpl[j] <= tmpr[k] ? tmpl[j++] : tmpr[k++];
            i++;
        }
        while(j < tmpl.length) {
            merge[i - l] = tmpl[j++];
            i++;
        }
        while(k < tmpr.length) {
            merge[i - l] = tmpr[k++];
            i++;
        }
        return merge;
    }



    /*
    *
    *   使用线程池进行数组的排序
    * */
    private void sortByPool(int[] arr, int l, int r, int len, int[] tmp) {
        if (r - l <= len) {
            Arrays.sort(arr, l, r + 1);
            return ;
        }
        // 1. 进行划分
        int mid = (l + r) >> 1;
        Future<?> f1 = pool.submit(() -> sortByPool(arr, l, mid, len, tmp));
        Future<?> f2 = pool.submit(()-> sortByPool(arr, mid + 1, r, len, tmp));
        // 2. 等待两个线程执行完成
        try {
            f1.get();
            f2.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3. 进行合并即可。
        int i = l, j = l, k = mid + 1;
        while(j <= mid && k <= r) tmp[i++] = arr[j] <= arr[k] ? arr[j++] : arr[k++];
        while(j <= mid) tmp[i++] = arr[j++];
        while(k <= r) tmp[i++] = arr[k++];
        for (i = l; i <= r; i++) arr[i] = tmp[i];
    }
    /*
    *
    *   使用手动线程进行数组的排序
    * */
    private int[] collect(int[][]sortedArr, int size) {

        class Node implements Comparable<Node>{
            int num;
            int ground;             // 第几组
            int id;                 // 第几个

            public Node(int num, int group, int id) {
                this.num = num;
                this.ground = group;
                this.id = id;
            }

            @Override
            public int compareTo(Node o) {
                return Integer.compare(this.num, o.num);
            }
        }

        int[] ans = new int[size];
        Queue<Node> heap = new PriorityQueue<>();
        int d = sortedArr.length;
        // 1.建立初始堆
        for (int i = 0; i < d; i++) {
            Node p = new Node(sortedArr[i][0], i, 0);
            heap.add(p);
        }
        // 2.取顶，放入答案，重新建堆。
        int idx = 0;
        while(!heap.isEmpty()) {
            Node top = heap.poll();
            ans[idx ++] = top.num;
            int g = top.ground, id = top.id;
            if (id  + 1 < sortedArr[g].length) {     // 看是否为空
                heap.add(new Node(sortedArr[g][id + 1], g, id + 1));
            }
        }
        return ans;
    }
    public int[] threadSortByFutT(int[] arr) {
        int n = 8;      // 线程数量, 小于等于数组的长度。
        int[][] divArr = div(arr, n);


        AtomicInteger id = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            new Thread(()->{
                Arrays.sort(divArr[id.getAndIncrement()]);  // 给数组进行排序
                latch.countDown();
            }).start();
        }
        // 这里必须等所有数组排好序才能继续执行
        try {
            latch.await();
        }catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("拍好序之后的数组");
//        SortUtils.printArr(divArr);

        int[] ans = collect(divArr, arr.length);            // 得到一个新的数组
        return ans;
    }

    public void sortBySingle(int[] arr) {
        Arrays.sort(arr);
    }

    public int[] sortByAuto(SortThread s, int[] arr, int tn) {
        int n = arr.length;
        int len = n / tn;           // 每个线程执需要排序的长度
        int[] ans = null;
        try {
            ans = s.sortByAuto(arr, 0, n - 1, len); // 调用s的,防止递归调用打印。
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return ans;
        }
    }

    public void sortByAuto2(SortThread s, int[] arr, int tn) {
        int n = arr.length;
        int len = n / tn;
        int[] tmp =  new int[n];
        s.sortByAuto2(arr, 0, n - 1, len, tmp);     // 调用s的
    }

    public void sortByPool(SortThread s, int[] arr, int tn) {
        int n = arr.length;
        int len = n / tn;
        int[] tmp = new int[n];
        s.sortByPool(arr, 0, n - 1, len, tmp); // 调用s的
        s.pool.shutdown();
    }

    /*
     *
     * 为了方便代理的使用，
     * 这里写一个方法调用被代理对象的方法，
     * 然后执行的时间就是被代理对象的执行时间
     *
     * 另外生成了一个非代理对象，作为参数传递给所有方法，
     * 在调用实际函数的时候，调用的是非代理对象的工作方法。
     * 但是统计时间的时候，调用的是代理对象的方法。
     *
     * */
    // 进行辅助函数的测试
    public static void test() {
        int n = 16, m = 100;
        int[] arr = SortUtils.getArr(16, 100);
        int[] sortedArr = Arrays.copyOf(arr, n);
        Arrays.sort(sortedArr);
        SortThread s = new SortThread();
        SortThread st = (SortThread) new TimeProxy().getProxy(SortThread.class);
        System.out.println("原数组为:");
        SortUtils.printArr(arr);
        System.out.println("排好序的数组为:");
        SortUtils.printArr(sortedArr);

        // 测试划分是否正确
        System.out.println("划分之后的数组为:");
        int[][] divArr = s.div(arr, 3);
        SortUtils.printArr(divArr);

        // 进行sortByauto的正确性测试
        int tn = 8;
        int[] ans1 = st.sortByAuto(s, arr, tn);
        SortUtils.printArr(ans1);
        System.out.println("sortByAuto是否正确：" + SortUtils.check(sortedArr, ans1));


        // 进行sortByAuto2的正确性测试
        tn = 8;
        int[] ans2 = Arrays.copyOf(arr, n);
        st.sortByAuto2(s, ans2, tn);
        SortUtils.printArr(ans2);
        System.out.println("sortByAuto2是否正确:" + SortUtils.check(sortedArr, ans2));

        // 进行线程池排序的测试
        tn = 8;
        int[] ans3 = Arrays.copyOf(arr, n);
        st.sortByPool(s, ans3, tn);
        SortUtils.printArr(ans3);
        System.out.println("线程池排序是否正确:" + SortUtils.check(sortedArr, ans3));

    }
    public static void runMethod() {
        int n = (int)1e8, m = (int)1e9;
        int[] arr = SortUtils.getArr(n, m);       // 生成数组
        SortThread s = new SortThread();
        SortThread st = (SortThread) new TimeProxy().getProxy(SortThread.class);

        int[] sortArr = Arrays.copyOf(arr, n);
        st.sortBySingle(sortArr);                    // 进行一次标准排序，判断正误 + 统计时间

        // 测试a手动划分，然后使用堆进行合并的运行时间，这里使用了代理进行时间统计。
        int[] ans1 = st.threadSortByFutT(arr);     // 这里不对arr1打乱顺序
        System.out.println("threadSortByFutT答案是否正确:" + SortUtils.check(sortArr, ans1));

        //  测试方法1的正确性和运行时间
        int tn = 16;                      // 开启线程的个数
        int[] ans = st.sortByAuto(s, arr, tn);   // 不对arr改变顺序
        System.out.println("sortByAuto是否正确:" + SortUtils.check(ans, sortArr));

        // 测试方法2的正确性和运行的时间
        int[] ans2 = Arrays.copyOf(arr, n);       // 不改变原来的数组
        tn = 16;
        st.sortByAuto2(s, ans2, tn);
        System.out.println("sortByAuto2是否正确:" + SortUtils.check(ans2, sortArr));

        // 测试方法3的正确定和运行时间
        int[] ans3 = Arrays.copyOf(arr, n);
        tn = 16;
        st.sortByPool(s, ans3, tn);
        System.out.println("线程池的最大大小为;" + ((ThreadPoolExecutor)s.pool).getLargestPoolSize());
        System.out.println("sortByPool是否正确:" + SortUtils.check(ans3, sortArr));
    }
    public static void main(String[] args) {
//        test();
        runMethod();
    }
}
