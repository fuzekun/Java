package leetcode;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/*
*
*
*
*   总结：1. 使用宽搜的模型，每一个结点的读取使用一个线程。
*           最后队列为空并且线程为0的时候终止。
*         2. 每一个线程消费一个url，生产他出结点的全部url
* */
public class Crawl2 {   // 已知URL集合，存储当前可见的所有URL。

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

    // 使用锁
    private ReentrantLock mapLock = new ReentrantLock();
    private HashMap<String, Boolean> totalUrls = new HashMap<>();

    // 结果URL链表及对应锁。
    private ReentrantLock resultLock = new ReentrantLock();
    private LinkedList<String> resultUrls = new LinkedList<>();

    // 待抓取URL链表及对应锁。
    private ReentrantLock crawlLock = new ReentrantLock();
    private LinkedList<String> urlsToCrawl = new LinkedList<>();

    // 当前正在执行的工作线程个数。
    private AtomicInteger choreCount = new AtomicInteger(0);

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        // bfs开始
        hostName = extractHostName(startUrl);
        this.totalUrls.put(startUrl, true);
        addUrlToResult(startUrl);
        addUrlToCrawl(startUrl);

        while(true) {
            String urlToCrawl = fetchUrlToCrawl();
            if (urlToCrawl != null) {
                incrChore();
                new Thread(new Chore(this, htmlParser, urlToCrawl)).start();
            } else {
                if (this.choreCount.get() == 0)
                    break;
            }
            LockSupport.parkNanos(1L); // 开启新的线程之前，先睡眠1ms，防止竞争太激烈导致的线程完蛋
        }
        return this.fetchResult();
    }



    private class Chore implements Runnable {
        private Crawl2 solution;
        private HtmlParser htmlParser;
        private String urlToCrawl;

        public Chore(Crawl2 solution, HtmlParser htmlParser, String urlToCrawl) {
            this.solution = solution;
            this.htmlParser = htmlParser;
            this.urlToCrawl = urlToCrawl;
        }

        @Override
        public void run() {
            try {
                filterUrls(this.htmlParser.getUrls(urlToCrawl));
            } finally {
                this.solution.decrChore();      // 跑完了，线程减少。
            }
        }

        private void filterUrls(List<String> crawledUrls) {
            if (crawledUrls == null || crawledUrls.isEmpty()) {
                return;
            }

            for (String url : crawledUrls) {
//                if (this.solution.fechMapUrls(url)) {
//                    continue;
//                }
//                this.solution.putToMap(url);

                if (this.solution.fechMapUrls(url)) continue;
                this.solution.putToMap(url);

                // 如果当前域名不等于，那么就算后面有也不能算了。
                String hostName = this.solution.extractHostName(url);
                if (!hostName.equals(this.solution.hostName)) continue;

                this.solution.addUrlToResult(url);
                this.solution.addUrlToCrawl(url);
            }
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

    private void addUrlToResult(String url) {
        this.resultLock.lock();
        try {
            this.resultUrls.add(url);
        } finally {
            this.resultLock.unlock();
        }
    }

    private void addUrlToCrawl(String url) {
        this.crawlLock.lock();
        try {
            this.urlsToCrawl.add(url);
        } finally {
            this.crawlLock.unlock();
        }
    }

    private List<String> fetchResult() {
        this.resultLock.lock();
        try {
            return this.resultUrls;
        } finally {
            this.resultLock.unlock();
        }
    }

    private String fetchUrlToCrawl() {
        this.crawlLock.lock();
        try {
            return this.urlsToCrawl.poll();
        }finally {
            this.crawlLock.unlock();
        }
    }

    private boolean fechMapUrls(String url) {
        mapLock.lock();
        try {
            return totalUrls.containsKey(url);
        } finally {
            mapLock.unlock();
        }
    }

    private void putToMap(String url) {
        mapLock.lock();
        try {
            totalUrls.put(url, true);
        } finally {
            mapLock.unlock();
        }
    }

    private void incrChore() {
        this.choreCount.incrementAndGet();
    }

    private void decrChore() {
        this.choreCount.decrementAndGet();
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
        Crawl2 c = new Crawl2();
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
