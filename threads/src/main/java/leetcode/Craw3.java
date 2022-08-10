package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/*
*
*
*   使用线程安全的集合进行实现。
*   如果不睡眠,线程
* */
public class Craw3 {
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

    // 结果URL链表及对应锁。
    private ConcurrentLinkedQueue<String> resultUrls = new ConcurrentLinkedQueue<>();

    // 待抓取URL链表及对应锁。
    private ConcurrentLinkedQueue<String> urlsToCrawl = new ConcurrentLinkedQueue<>();

    // 当前正在执行的工作线程个数。
    private AtomicInteger choreCount = new AtomicInteger(0);

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        // bfs开始
        hostName = extractHostName(startUrl);
        this.totalUrls.put(startUrl, true);
        urlsToCrawl.add(startUrl);
        resultUrls.add(startUrl);
        while(true) {
            String urlToCrawl = urlsToCrawl.poll();
            if (urlToCrawl != null) {
                choreCount.getAndIncrement();
                new Thread(new Chore(this, htmlParser, urlToCrawl)).start();
            } else {
                if (this.choreCount.get() == 0)
                    break;
            }
        }
        List<String>ans = new ArrayList<>();
        while(!resultUrls.isEmpty()) {
            ans.add(resultUrls.poll());
        }
        return ans;
    }



    private class Chore implements Runnable {
        private Craw3 solution;
        private HtmlParser htmlParser;
        private String urlToCrawl;

        public Chore(Craw3 solution, HtmlParser htmlParser, String urlToCrawl) {
            this.solution = solution;
            this.htmlParser = htmlParser;
            this.urlToCrawl = urlToCrawl;
        }

        @Override
        public void run() {
            try {
                filterUrls(this.htmlParser.getUrls(urlToCrawl));
            } finally {
                this.solution.choreCount.getAndDecrement();      // 跑完了，线程减少。
            }
        }

        private void filterUrls(List<String> crawledUrls) {
            if (crawledUrls == null || crawledUrls.isEmpty()) {
                return;
            }

            for (String url : crawledUrls) {
                if (this.solution.totalUrls.containsKey(url)) continue;
                this.solution.totalUrls.put(url, true);

                // 如果当前域名不等于，那么就算后面有也不能算了。
                String hostName = this.solution.extractHostName(url);
                if (!hostName.equals(this.solution.hostName)) continue;

                this.solution.resultUrls.add(url);
                this.solution.urlsToCrawl.add(url);
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
        Craw3 c = new Craw3();
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
