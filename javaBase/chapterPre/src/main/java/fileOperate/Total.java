package fileOperate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * 
 * 1.用来统计我花钱
 * 2.用来统计各个项花的钱数,项在文本中有记载
 * 3.用来统计花在具体某一事务上的钱数
 * 4.如果记账里面没有那种项的分类，那么将花费大量的判断语句进行归类
 * */

public class Total {

	public static void main(String[] args) {
		
		String [] kind = {"吃饭", "生活", "学习", "零食水果"};    //花钱的总类
		double sum = 0, day = 0, avrage = 0;				//花钱的总数和总天数以及平均值
		String [] seq; 										//花钱的项
		
		
		try {
			InputStreamReader input = new InputStreamReader(new FileInputStream(new File("账本.txt")),"utf-8");
			BufferedReader bufferedReade = new BufferedReader(input);
			String s;
			System.out.print("花的总钱数为:");
			while((s = bufferedReade.readLine()) != null) {
				String []tmp = s.split(":");
				if(tmp[0].equals("共计")) {
					day++;
					sum += Double.parseDouble(tmp[1]);
				}
			}
			System.out.println(sum);
			System.out.println("天数为:" + day);
			System.out.println("花钱的平均数为:" + (sum / day));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("读入文件出错 :" + e.getMessage());
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("文件读取出错" + e.getMessage());
		}
		

	}

}
