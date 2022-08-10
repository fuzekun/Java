package TryAndLog;

import java.io.*;

public class TrueTest {
    /*
    *
    *   测试如果try...catch中外面加上true会怎么样
    * */

    public static void main(String[] args) {
        while(true) {
            try(  Reader in = new FileReader(new File("test.txt"));
                  BufferedReader bf = new BufferedReader(in);
            ){
                String line = bf.readLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                try {
                    Thread.sleep(10000);        // 如果没有连接睡眠10s等待。
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
