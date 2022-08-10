package homework;

import java.util.Date;
import java.util.Scanner;



public class homework8 {

	private final static Scanner sc = new Scanner(System.in);
	static void printTile(String s) {
		System.out.println("\n习题" + s);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		printTile("6.23");
		que1();
		printTile("6.24");
		que2();
		printTile("6.25");
		que3();
		printTile("6.26");
		que4();
		printTile("6.27");
		que5();
		printTile("6.30");
		que6();
		printTile("6.35");
		que7();
		printTile("6.38");
		que8();
		printTile("6.39");
		que9();
	}
	static void que1() {
		System.out.println("出入一个字符串");
		String s = sc.nextLine();
		System.out.println("出入一个字符");
		s = sc.nextLine();
		char  e = s.charAt(0);
		System.out.println("总数为:" + count(s, e));
	}
	static void que2() {
		Date data = new Date();
		System.out.println("现在的时间为:");
		System.out.println(data.toString());
	}
	static void que3() {
		long millis = sc.nextLong();
		convertMillis(millis);
	}
	
	static void que4() {
		final int maxn = 100000;
		
		//判断素数
		boolean[] isnotPrim = new boolean[maxn];
		for(int i = 2;i < maxn;i++) {
			if(isnotPrim[i] == false) {//如果是素数
				for(int j = i + i;j < maxn;j = j + i) {//它的多少倍都不是素数了。
					isnotPrim[j] = true;//容易把j写成i
				}
			}
		}
		//判断回文数
		int sum = 0;
		for(int i = 0;i < maxn && sum < 100;i++) {
			String tmp = Integer.toString(i);
			char[] ch = tmp.toCharArray();
			int front = 0;
			int back = tmp.length() - 1;
			boolean flag = true;
			while(front < back) {
				if(ch[front] != ch[back]) {
					flag = false;
					break;
				}
				front++;//容易错
				back--;//同样
			}
			if(flag && !isnotPrim[i]) {
				sum++;
				if(sum % 10 == 0) {
					System.out.println(i);
				}
				else System.out.print(i + " ");
			}
		}
	}
	static void que5() {
		final int maxn = 100000;
		
		//判断素数
		boolean[] isnotPrim = new boolean[maxn];
		for(int i = 2;i < maxn;i++) {
			if(isnotPrim[i] == false) {//如果是素数
				for(int j = i + i;j < maxn;j = j + i) {//它的多少倍都不是素数了。
					isnotPrim[j] = true;//容易把j写成i
				}
			}
		}
		int sum = 0;
		for(int i = 0 ;i < maxn && sum < 100;i++) {
			
			if(!isnotPrim[i]) {
				String s = Integer.toString(i);
				char[] ch = s.toCharArray();
				int begin = 0;
				int back = ch.length - 1;
				while(begin < back) {
					char tmp = ch[begin];
					ch[begin] = ch[back];
					ch[back] = tmp;
					begin++;
					back--;
				}
                s = "" ;
				for(int j = 0;j < ch.length;j++) {
					s += ch[j];
				}
				int x = Integer.valueOf(s);
				if(!isnotPrim[x]) {
					sum++;
					if(sum % 10 == 0)System.out.println(i);
					else System.out.print(i + " ");
				}
			}
		}
	}
	static void que6() {
		int[] num = {1,2,3,4,5,6};
		int num1 = num[(int)(Math.random() * 6)];
		int num2 = num[(int)(Math.random() * 6)];
		int sum = num1 + num2;
		System.out.println("You roolled:" + num1 + "+" + num2 );
		if(sum == 2 || sum == 3 || sum == 12) {
			System.out.println("You lose");
		}
		else 
			if(sum == 7 || sum == 11)System.out.println("You win");
		else {
			for(int i = 0;;i++) {
				num1 = num[(int)(Math.random() * 6)];
				num2 = num[(int)(Math.random() * 6)];
				int sum2 = num1 + num2;
				System.out.println("You roolled:" + num1 + "+" + num2 );
				if(sum2 == sum) {
					System.out.println("You Win");
					break;
				}
				else if(sum2 == 7) {
					System.out.println("You loose");
					break;
				}
				sum = sum2;
			}
		}
	}
	static void que7() {
		System.out.println("输入边");
		double side = sc.nextDouble();
		System.out.println("面积是:" + area(side));
	}
	static void que8() {
		for(int i = 0;i < 100;i++) {
			char ch = (char)('A' + (int)(Math.random() * 26));//A + 25等于z；
			if((i+1) % 10 == 0 ) {
				System.out.println(ch);
			}
			else System.out.print(ch + " ");
		}
		for(int i = 0;i < 100;i++) {
			int num = (int)(Math.random() * 10);//[0,(int)9.多]就是0 到9；
			if((i + 1) % 10 == 0){
				System.out.println(num);
			}
			else System.out.print(num + " ");
		}
	}
	static void que9() {
		System.out.println("输入直线和点");
		double x0 = sc.nextDouble();
		double y0 = sc.nextDouble();
		double x1 = sc.nextDouble();
		double y1 = sc.nextDouble();
		double x2 = sc.nextDouble();
		double y2 = sc.nextDouble();
		if(leftofTheLine(x0, y0, x1, y1, x2, y2))System.out.println("左侧");
		else if(rightofTheLine(x0, y0, x1, y1, x2, y2))System.out.println("右侧");
		else if(OnTheLine(x0, y0, x1, y1, x2, y2))System.out.println("直线上");
		else if(OntheSegment(x0, y0, x1, y1, x2, y2))System.out.println("线段上");
	}
	
	static int count(String s,char e) {
		char[] ch = s.toCharArray();
		int ans = 0;
		for(int i =0;i < ch.length;i++) {
			if(ch[i] == e) {
				ans++;
			}
		}
		return ans;
	}
	static String convertMillis(long millis) {
		long totalSecond = (long)millis/1000;
		long nowSecond = (long)totalSecond %60;
		long totalMimutes = totalSecond / 60;
		long nowMinutes = (long)totalMimutes % 60;
		long totalHours = totalMimutes / 60;
		long nowHours = totalHours % 24;
		return "" + nowHours + ":" + nowMinutes + ":" + nowSecond;
	}
	static double area(double side) {
		double area;
		area = (5 * side * side) / (4 * Math.tan(Math.PI / 5));
		return area;
	}
	//左边
	static boolean leftofTheLine(double x0,double y0,double x1,double y1,double x2,double y2) {
		boolean flag = false;
		if(((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) > 0)flag = true;
		return flag;
	}
	//右边
	static boolean rightofTheLine(double x0,double y0,double x1,double y1,double x2,double y2) {
		boolean flag = false;
		if(((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) < 0)flag = true;
		return flag;
	}
	//直线上
	static boolean OnTheLine(double x0,double y0,double x1,double y1,double x2,double y2) {
		boolean flag = false;
		if(((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) == 0)flag = true;
		return flag;
	}
	//线段上
	static boolean OntheSegment(double x0,double y0,double x1,double y1,double x2,double y2) {
		boolean flag = false;
		if(((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) == 0 && (x2 >= Math.min(x1, x0) && x2 <= Math.max(x1, x0))
				&& (y2 >= Math.min(y0, y1) && y2 <= Math.max(y0, y1)))flag = true;
		return flag;
	}
}
