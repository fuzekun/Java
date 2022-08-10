package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
*
*   使用线程池 + future进行爬取
*
*
* */
public class Crawl4 {

    HashMap<String, List<String>> G = new HashMap<>();

    private class HtmlParser {
        List<String>getUrls(String start) {
            if (G.containsKey(start)) {
                List<String>ans = G.get(start);
                System.out.println("start = " + start + ", sz = "+ ans.size());
                return ans;
            }
            return new ArrayList<>();
        }
    }
    String hostName;

    private ConcurrentHashMap<String, Boolean> totalUrls = new ConcurrentHashMap<>();


    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        // bfs开始
        hostName = extractHostName(startUrl);

        ExecutorService pool = Executors.newCachedThreadPool();
        Future<List<String>>taskRes = pool.submit(new Chore(this, htmlParser, startUrl, pool));

        List<String>ans = new ArrayList<>();
        try {
            ans = taskRes.get();
        }catch (Exception e) {
            e.printStackTrace();
        }

        pool.shutdown();
        // System.out.println("最大的线程数量:" + ((ThreadPoolExecutor)pool).getLargestPoolSize());
        return ans;
    }



    private class Chore implements Callable<List<String>> {
        private Crawl4 solution;
        private HtmlParser htmlParser;
        private String urlToCrawl;
        private ExecutorService pool;

        public Chore(Crawl4 solution, HtmlParser htmlParser, String urlToCrawl, ExecutorService pool) {
            this.solution = solution;
            this.htmlParser = htmlParser;
            this.pool = pool;
            this.urlToCrawl = urlToCrawl;
        }

        @Override
        public List<String> call() throws Exception {

//            System.out.println("url = " + urlToCrawl);
            // 此处不需要使用并发的，因为统计只有主线程进行
            List<String>ans = new ArrayList<>();
            ans.add(urlToCrawl);
            this.solution.totalUrls.put(urlToCrawl, true);

            List<String> urls = htmlParser.getUrls(urlToCrawl);
            List<Future<List<String>>> ress = new ArrayList<>();

            for (String url : urls) {       // 每一个结点开启一个新的线程进行计算

                if (this.solution.totalUrls.containsKey(url)) continue;
                this.solution.totalUrls.put(url, true);

                String hostName = this.solution.extractHostName(url);
                if (!hostName.equals(this.solution.hostName)) continue;

                Chore c = new Chore(solution, htmlParser, url, pool);
                Future<List<String>> res = pool.submit(c);
                ress.add(res);
            }

            // 计算完成所有的任务，直接进行返回就行了
            for (Future<List<String>>f:ress) {
                ans.addAll(f.get());
            }
            return ans;
        }
    }

    private String extractHostName(String url) {
        String processedUrl = url.substring(7);

        int index = processedUrl.indexOf("/");
        if (index == -1) {
            return processedUrl;
        } else {
            return processedUrl.substring(0, index);
        }
    }



    public void build(int[][] edges) {
        String[] s = {"http://news.yahoo.com",
                "http://news.yahoo.com/news",
                "http://news.yahoo.com/news/topics/",
                "http://news.google.com"};


        for (int[] e : edges) {
            String u = s[e[0]];
            String v = s[e[1]];
            if (G.containsKey(u)) {
                G.get(u).add(v);
            } else {
                List<String> l = new ArrayList<>();
                l.add(v);
                G.put(u, l);
            }
        }
//        for (String t : G.get("http://news.yahoo.com/news/topics/")) {
//            System.out.println(t);
//        }
    }

    public static void main(String[] args) {
        Crawl4 c = new Crawl4();
        String input = " [[0,2],[2,1],[3,2],[3,1],[3,0],[2,0]]";
        input = input.replace("[", "{");
        input = input.replace("]", "}");
        System.out.println(input);
        int[][] edges =   {{0,2},{2,1},{3,2},{3,1},{3,0},{2,0}};
        c.build(edges);
        List<String> ans = c.crawl("http://news.yahoo.com/news/topics/", c.new HtmlParser());
        for (String s: ans) {
            System.out.println(s);
        }
    }


}
