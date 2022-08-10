package StringTest;

import org.junit.Test;

public class StringLen {
    public static void main(String[] args)throws Exception {
        String china = "钓";    // 使用utf-8占用三个字节, 那些困难的符号占用三个字节
        String eng = "this";
        char ch = '中';
        char ch2 = 'z';
        System.out.println(ch);
        String  a="中国";
        byte[] byte1 = charToByte(ch2);
        byte[] byte2 = china.getBytes("utf-16");
        System.out.println(byte2.length); // 使用utf-8，字符占用一个字节
        System.out.println(byte1.length);

    }

    @Test
    public void test1() throws Exception{
        String china = "钓鱼岛是中国的";

        for (int i = 0; i < china.length(); i++) {
            char ch = china.charAt(i);
            StringBuilder t = new StringBuilder();
            t.append(ch);
            String ss = t.toString();
            System.out.println(ss.getBytes("utf-16").length);
        }
        System.out.println(china.getBytes("utf-16").length);
        System.out.println(china.getBytes().length);
        System.out.println(china.getBytes("utf-8").length);
    }

    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        System.out.println(b[0]);
        System.out.println(b[1]);
        return b;
    }

}
