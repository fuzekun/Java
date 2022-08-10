package threadBase.threadPool;

/*
*
*
*   java核心技术卷上面的线程池
* 使用线程池统计文件中含有关键字的文件个数
*  默认一个文件夹开启一个线程进行处理

 */


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Test1 {
    public static void main(String[] args) {
        String dir = "D:\\projects\\java\\javaBase\\threads\\data";
        System.out.println("文件夹的绝对路径是: " + dir);
        ExecutorService pool = Executors.newCachedThreadPool();
        String keyWord = "this";
        System.out.println("关键词是: " + keyWord);
        MatchCounter counter = new MatchCounter(pool, keyWord, new File(dir));

        Future<Integer> result = pool.submit(counter);
        try {
            System.out.println("含有关键词的文件个数为:" + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int largestPoolSize = ((ThreadPoolExecutor)pool).getLargestPoolSize();
        System.out.println("线程池的最大数量是:" + largestPoolSize);

        pool.shutdown();                // 别忘了关闭线程池
    }
}

class MatchCounter implements Callable<Integer> {

    private ExecutorService pool;
    private String keyWord;
    private File dir;

    public MatchCounter(ExecutorService pool, String keyWord, File dir) {
        this.pool = pool;
        this.keyWord = keyWord;
        this.dir = dir;
    }

    @Override
    public Integer call() throws Exception {
        int cnt = 0;
        try {
            File[] files = dir.listFiles();
            List<Future<Integer>> ress = new ArrayList<>();

            for (File f: files) {           // 分治
                if (f.isDirectory()) {      // 开启新线程,从线程池中
                    MatchCounter mc = new MatchCounter(pool, keyWord, f);
                    Future<Integer>res = pool.submit(mc);
                    ress.add(res);
                }
                else {                      // 如果是文件直接计算
                    if (search(f)) cnt++;
                }
            }

            for (Future<Integer>res : ress) {
                cnt += res.get();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }
    public boolean search(File file) {
        try {
            try (Scanner sc = new Scanner(file, "UTF-8")){
                boolean flag = false;
                while(!flag && sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.contains(keyWord)) flag = true;
                }
                return flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
