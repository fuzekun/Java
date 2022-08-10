package homework;

import java.util.Scanner;


public class homework6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(
				Scanner scanner = new Scanner(System.in);
		){
			System.out.println("习题5.14");
			System.out.println("输入两个整数");
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			System.out.println("最大公约数是:"+gcd(a,b));
			System.out.println("\n习题5.15");
			printAscc();
			System.out.println("\n习题5.16\n 请输入一个整数");
			int n = scanner.nextInt();
			getXinzi(n);
			System.out.println("\n习题5.17\n请输入一个代表行数的整数");
			n = scanner.nextInt();
			printTriangle(n);
			System.out.println("\n习题5.18");
			printTriangle2(6);
			System.out.println("\n习题5.24");
			printSum();
			System.out.println("\n习题5.26");
			getE();
			System.out.println("习题5.29");
			getDay();
		}
	}
	//习题5.29
	static void getDay() {
		System.out.println("输入年");
		Scanner sc = new Scanner(System.in);
		int year = sc.nextInt();
		year--;
		int week = (year * 365 + year / 4 - year / 100 + year / 400 + 1) % 7;
		int tmp = week;
		if(week == 0)week = 7;
		System.out.println("今年的第一天星期:" + week);
		
		printCalenda(year, tmp);
	}
	//打印日历
	static void printCalenda(int year,int first) {
		for(int i = 0;i < 14;i++) {
			System.out.print(" ");
		}
		System.out.println(year + "日历");
		for(int i = 0;i < 12;i++) {
			for(int j = 0;j < 16;j++) {
				System.out.print(" ");
			}
			System.out.println((i + 1) +"月");
			System.out.println("___________________________________");
			String[] week = {"sun","Mon","Tue",
					"Wed","Thu","Fri","Sat"};
			for(int j = 0;j < 7;j++) {
				System.out.print(week[j] + "  ");
			}
			int[] monthDay = {
					31,28,31,30,31,30,31,31,30,31,30,31
			};
			if((year % 4 == 0 && year % 100 != 10) || year % 400 == 0)monthDay[1] = 29;
			System.out.println();
			for(int j = 1;j <= monthDay[i];j++) {
				if(j == 1) {//每个月的第一天
					for(int k = 0;k < first*5;k++) {
						System.out.print(" ");
					}
				}
				first++;
				first %= 7;
				if(first == 0) {
					System.out.println(j);
				}
				else System.out.printf("%2d   ",j);
			}
			System.out.println();
		}
	}
	static void getE() {
		for(int i = 1;i <= 10;i++) {
			double e = 0;
			double tmp = 1;
			for(int j = 1;j < 10000 * i;j++) {
				tmp /= (double)j;
				e += tmp;
			}
			System.out.println(e);
		}
	}
	static void getXinzi(int n) {
		//获取素数
		int j = 2;
		int cnt = 0;
		int[] prime = new int[100000 + 5];
		boolean[] vis = new boolean[100000 + 5];
		for(int i = 2;i < n;i++) {
			if(!vis[i]) {
				vis[i] = true;
				prime[cnt++] = i;
				for(j = i;j < n;j += i) {
					vis[j] = true;
				}
			}
		}
		//分解质因数
		for(int i = 0;i < cnt && n != 0;i++) {
			int tmp = prime[i];
			while(n % tmp == 0) {
				n /= tmp;
				System.out.print(tmp + " ");
			}
		}
	}
	static void printSum() {
		double sum = 0;
		for(int i = 1;i <= 97;i += 2) {
			int j = i + 2;
			sum += (double)i / (double)j; 
		}
		System.out.println(sum);
	}
	static void printTriangle2(int n) {
		for(int i = 1;i <= n;i++) {
			for(int j = 1;j <= i;j++) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 0;i < n;i++) {
			for(int j = 1;j <= n - i;j++) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 1;i <= n;i++) {
			for(int j = 0;j < 2 * (n - i);j++) {
				System.out.print(" ");
			}
			for(int j = 1;j <= i;j++) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 1;i <= n;i++) {
			for(int j = 0;j < 2 * (i - 1);j++) {
				System.out.print(" ");
			}
			for(int j = 1;j <= n - i + 1;j++) {
				System.out.print(j +" ");
			}
			System.out.println();
		}
	}
	static void printTriangle(int n) {
		for(int i = 1;i <= n;i++) {
			//输出空格
			for(int j = 0;j < 2 * n - i * 2;j++) {
				System.out.print(" ");
			}
			//输出左边
			for(int j = i;j >= 1;j--) {
				System.out.print(j + " ");
			}
			//输出右边
			for(int j = 2;j <= i;j++) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
	static void printAscc() {
		char a = '!';
		int cnt = 0;
		while(a!='~') {
			System.out.print((char)a);
			cnt++;
			cnt %= 10;a++;
			if(cnt == 0)System.out.println();
		}
	}
	static int gcd(int a,int b) {
		return b == 0?b:gcd(b,a%b);
	}

}
