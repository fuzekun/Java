package homework;

import java.util.Scanner;

public class homework7 {
	private final static Scanner sc = new Scanner(System.in);
	
	static void printTile(String s) {
		System.out.println("\n习题" + s);
	}
	
	public static void main(String[] args) {
		printTile("6.1");
		que1();
		printTile("6.2");
		que2();
		printTile("6.3");
		que3();
		printTile("6.4");
		que4();
		printTile("6.6");
		que5();
		printTile("6.10");
		que6();
		printTile("6.14");
		que7();
		printTile("6.16");
		que8();
	}
	static void que1() {
		for(int i = 0;i < 100;i++) {
			if((i + 1) % 10 == 0)System.out.println(getPentagonnalNumber(i));
			else System.out.print(getPentagonnalNumber(i) + " ");
		}
	}
	static void que2() {
		
		System.out.println("输入一个数字:");
		long n = sc.nextLong();
		System.out.println("数字之和为:" + sumDigits(n));
	}	
	static void que3() {
		
		System.out.println("输入一个数字:");
		int n = sc.nextInt();
		System.out.println("逆序为:" + reverse(n));
		System.out.println("是不是回文:" + isPalindrome(n));
		
	}	
	static void que4() {
		
		System.out.println("输入一个数字:");
		int n = sc.nextInt();
		System.out.println("逆序为:" + reverse(n));
		
	}	
	static void que5() {
		
		System.out.println("输入一个数字");
		int n = sc.nextInt();
		displayPatten(n);
	}	
	static void que6() {
		
		getPrime();
		System.out.println("存在了prime数组中");
	}	
	static void que7() {
		
		int n = 10;
		System.out.println("i" + "      " + "m[i]");
		System.out.println("_______________");
		for(int i = 0;i < n;i++) {
			System.out.printf("%3d     %1.4f\n",(i * 100 + 1)  ,m(i * 100 + 1));
		}
		
	}	
	static void que8() {
		System.out.println("2000到2020年的天数是:");
		for(int i = 2000;i < 2020;i++) {
			System.out.print( NumberIFDaysInAYear(i)+ " ");
			if((i + 1 ) % 10 == 0) System.out.println();
		}
	}
	public  static int  getPentagonnalNumber(int n) {
			int ans = n * (3 * n - 1) / 2;
			return ans;
	}
	public  static int sumDigits(long n) {
		int sum = 0;
		while(n != 0) {
			sum += n % 10;
			n /= 10;
		}
		return sum;
	}
	public  static int reverse(int num) {
		int[] list = new int[9];
		int cnt = 0;
		while(num != 0) {
			list[cnt++] = num % 10;
			num /= 10;
		}
		int ans = 0;
		for(int i = 0;i < cnt;i++) {
			ans = list[i] + ans * 10;
		}
		return ans ;
	}
	public  static boolean  isPalindrome(int num) {
		boolean flag = false;
		if(num == reverse(num))flag = true;
		return flag;
	}
	public  static void  displayPatten(int n) {
		for(int i = 1;i <= n;i++) {
			for(int j = 0;j < 2 * (n - i);j++) {
				System.out.print(" ");
			}
			for(int j = i;j > 0;j--) {
				if(j != 1)
					System.out.print(j + " ");
				else System.out.print(j);
			}
			System.out.println();
		}
	}
	public  static void getPrime() {
		boolean[] vis = new boolean[1000000];
		int[] prime = new int[10001];
		int cnt = 0;
		for(int i = 2;i < 1000000 && cnt < 10000;i++) {
			if(!vis[i]) {
				prime[cnt++] = i;
				for(int j = i;j < 100000;j += i) {
					vis[j] = true;
				}
			}
		}
	}
	public static double m(int i) {
		double ans = 0;
		int mu = 1;
		for(int j = 1;j <= i;j++) {
			ans = ans + 1.0 / (2.0 * j - 1.0) * mu;
			mu *= -1;
		}
		return ans * 4;
	}
	public  static int  NumberIFDaysInAYear(int year) {
		if((year / 4 == 0 && year % 100 != 0)||year % 400 == 0)return 366;
		else return 365;
	}
}
