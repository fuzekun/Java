package simpledb;

import java.io.*;
import java.util.*;

/**
 * @author: Zekun Fu
 * @date: 2023/3/2 18:04
 * @Description:
 */
public class CheckDifference {
    public static void main(String[] args) throws Exception{
        BufferedReader in1 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("pre.txt"))));
        BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("after.txt"))));
//        HashMap<String, String> pre = new HashMap<>();
        HashSet<String>pre = new HashSet<>();
        HashSet<String>after = new HashSet<>();
        String s;
        while ((s = in2.readLine()) != null) {
            pre.add(s);
        }
//        System.out.println(Arrays.toString(pre.toArray()));
        System.out.println("没有传输成功的文件为:");
        int cnt = 0;
        while ((s = in1.readLine()) != null) {
            if (!pre.contains(s)) {
                System.out.println(s);
                cnt++;
            }
            after.add(s);
        }
        if (cnt == 0) {
            System.out.println("空！");
        }
        else {
            System.out.println(String.format("共计 %d个", cnt));
        }
        Iterator<String>it = pre.iterator();
        List<String>wrongFile = new ArrayList<>();
        while (it.hasNext()) {
            String cur = it.next();
            if (!after.contains(cur)) {
                wrongFile.add(cur);
            }
        }
        if (wrongFile.size() != 0) {
            throw new IOException("文件传输出错!下列文件凭空产生!\n" + Arrays.toString(wrongFile.toArray()));
        }
        else {
            System.out.println("文件传输正确");
        }
    }
}
