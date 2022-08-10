package leetcode;

import javax.sound.midi.Soundbank;
import java.io.InputStream;

/**
 * @author: Zekun Fu
 * @date: 2022/5/29 9:59
 * @Description:
 * 验证IP地址的正确性
 */
public class IPConfirm {
    /*
    *
    *   "1 2 $3 4 $5 $6 7 8$ $9 $10$"
    * */
    public String discountPrices(String sentence, int discount) {
        String[] words = sentence.split(" ");
        StringBuilder ans = new StringBuilder();
        double dis = discount;
        dis = (1.0 - dis / 100.0);
        System.out.println(dis);
        int len = words.length;
        for (int i = 0; i < len; i++) {
            String word = words[i];
            int flag = 1;
            if (word.charAt(0) != '$' || word.length() == 1) flag = 0;
            for (int j = 1; j < word.length() && flag == 1; j++) {
                if (word.charAt(j) == '$' || !Character.isDigit(word.charAt(j))) {
                    flag = 0;
                }
            }
            if (flag == 1) {
                String t = word.substring(1);
                double num = Double.valueOf(t);
                num *= dis;
//                System.out.println(String.format("%.2f", num));
                t = "$" + String.format("%.2f", num);
                ans.append(t);
            }
            else
                ans.append(words[i]);
            if (i != len - 1) ans.append(" ");
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        String s = "f32eir5f6hlmmtnlq$zno3zbl5pr26b1xmet6q3rjzs422zqzsezpgi4jqx3h0olb428pk95qndkfz8hereio$2ewx0cnqlvnb6nl$$8iny7t4aemhnqzz6971rnq7pha97e9lf16227j5l2033pnddk $3513024 $516863 $604 $9128265 $945728 $nbf 5az21pm0tj $";
        int dis = 50;
        System.out.println(new IPConfirm().discountPrices(s, dis));
    }
}
