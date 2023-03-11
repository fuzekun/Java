package simpledb;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Zekun Fu
 * @date: 2023/2/23 9:43
 * @Description:
 */
public class SimpleTest {
    @Test
    public void test() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Map<Character, Integer> mp = new HashMap<>();
        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            char ch = chs[i];
            mp.computeIfPresent(ch, (key, val)->{
                return val + 1;
            });                                             // 如果存在加上1
            mp.putIfAbsent(ch, 1);                          // 如果不存在改成1
        }
        char ch = 'a';
        int maxv = 0;
        for (Map.Entry<Character, Integer>entry : mp.entrySet()) {
            if (maxv < entry.getValue()) {
                ch = entry.getKey();
                maxv = entry.getValue();
            }
            else if (maxv == entry.getValue()) ch = (char)Math.min(ch, entry.getKey());
        }
        System.out.println(ch);
        System.out.println(maxv);
    }


    @Test
    public void riqi() throws Exception {
        // 计算回文日期
//        1. Canlenda的日期;
//        2. 转换成字符串；
//        3. 判断字符串是否为回文串；
        Scanner scan = new Scanner(System.in);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String s = "20040229";
//        Date maxdate = sdf.parse("99991231");           // 再往后就是10000年了
//        Date dt = sdf.parse(s);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);

        int flag1 = 1, flag2 = 1;
        while (flag1 == 1 || flag2 == 1) {
//            rightNow.add(Calendar.DAY_OF_YEAR, 1);
//            Date dt1 = rightNow.getTime();
//            String reStr = sdf.format(dt1);
//            System.out.println(reStr);
//            if (dt1.compareTo(maxdate) > 0) break;
            Integer num = Integer.parseInt(s);
            s = String.valueOf(runTime(num));
            if (flag1 == 1 && checkP(s)) {
                System.out.println(s);
                flag1 = 0;
            }
            if (flag2 == 1 && checkA(s)) {
                System.out.println(s);
                flag2 = 0;
            }
        }
    }
//    private static int nextTime(int num) {
//        int year = num / 1000, month = (num / 100) % 100, day = num % 100;
//        day++;
//        if ()
//    }
//    private static int runTime(int num) {
//        int year = num/10000, month = (num/100)%100, day = num%100;
//        day++;
//        if(day > 31) {                  // 因为2月不可能出现92200229,并且30，03开头的年份不存在。所以可以假设没一月都是31天。
//            day = 1;                    // 但是13111130会出现13111131号这个错误的日期
//            month ++;
//            if (month > 12) {
//                month = 1;
//                year++;
//            }
//        }
//        return year*10000+month*100+day;
//    }
    private static int runTime(int num) throws ArrayIndexOutOfBoundsException {
        int year = num / 10000, month = (num / 100) % 100, day = num % 100;
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) days[2] = 29;                 // 闰年有29天
        if (year > 9999 || month > 12 || day > days[month]) {
//            System.out.println(num);
//            System.out.println("日期" + day);
//            System.out.println("实际应该有" + days[month]);
            throw new ArrayIndexOutOfBoundsException("日期格式错误！");
        }
        day++;
        if (day > days[month]) {
            month++;
            day = 1;
        }
        if (month > 12) {
            month = 1;
            year++;
        }
        return year * 10000 + month * 100 + day;
    }
    private boolean checkA(String s) {
        if (!checkP(s)) return false;
        return s.substring(0, 2).equals(s.substring(2, 4));
    }
    private boolean checkP(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }
}
