package leetcode;

import java.util.*;
import java.util.concurrent.Semaphore;
public class Crawl {

    String domain;
    HashMap<String, List<String>> G = new HashMap<>();
    HashMap<String, Boolean> vis = new HashMap<>();

    private class HtmlParser {
        List<String>getUrls(String start) {
            return G.containsKey(start) ? G.get(start) : new ArrayList<>();
        }
    }

    private String getDomain(String hurl) {
        String url = hurl.split("//")[1];
//        System.out.println("url = " + url);
        String domain = url.split("/")[0];
//        System.out.println("domain = " + domain);
        return domain;
    }

    public List<String> getUrls(String startUrl, HtmlParser htmlParser) {
        Queue<String> que = new LinkedList<>();
        List<String> ans = new LinkedList<>();
        ans.add(startUrl);
        que.add(startUrl);
        vis.put(startUrl, true);

        while(!que.isEmpty()) {
            String u = que.poll();
            for (String v : htmlParser.getUrls(u)) {
                if (vis.containsKey(v)) continue;
                vis.put(v, true);
                if (!getDomain(v).equals(domain)) continue;
                que.add(v);
                ans.add(v);
            }
        }

        return ans;
    }

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        domain = getDomain(startUrl);

        List<String>urls = getUrls(startUrl, htmlParser);

        return urls;
    }

    public void build(int[][] edges) {
        String[] s = {"http://news.yahoo.com",
                "http://news.yahoo.com/news",
                "http://news.yahoo.com/news/topics/",
                "http://news.google.com",
                "http://news.yahoo.com/us",
                "http://news.yaho1.com/us2",
                "http://news.yahoo.com/us3"};


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
        Crawl c = new Crawl();
        String input = " [[2,0],[2,1],[3,2],[3,1],[0,4],[0,5],[5,6]]";
        input = input.replace("[", "{");
        input = input.replace("]", "}");
        System.out.println(input);
        int[][] edges =   {{2,0},{2,1},{3,2},{3,1},{0,4},{0,5},{5,6}};
        c.build(edges);
        List<String> ans = c.crawl("http://news.yahoo.com/news/topics/", c.new HtmlParser());
        for (String s: ans) {
            System.out.println(s);
        }
    }


}
