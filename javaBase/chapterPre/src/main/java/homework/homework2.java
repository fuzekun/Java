package homework;
import java.util.*;
/**
 * author:付泽坤;
 * time:2019/3/8
 * software:eclips 
 * */
public class homework2 {

	//习题2.6
	static void threeNum() {
		System.out.println("习题2.6");
		Scanner input= new Scanner(System.in);
		System.out.print("Enter a num between 0 and 1000:");
		int n = input.nextInt();
		int ans = 0;
		while(n != 0) {
			int tmp = n % 10;
			ans += tmp;
			n /= 10;
		}
		input.close();
		System.out.println("The sum of the digits:" + ans + "\n");
	}
	
	//习题2.7
	static void getDate() {
		System.out.println("习题2.7");
		System.out.print("Enter a number of mimutes:");
		Scanner input = new Scanner(System.in);
		long n = input.nextLong();
		input.close();
		System.out.println(n + "年有"+ n / 365 + "年" + n % 365 + "天\n");
	}
	
	//习题2.8
	static void getTime() {
		System.out.println("习题2.8");
		System.out.println("Enter a number zone iffset to GMT:");
		Scanner input = new Scanner(System.in);
		input.close();
		long a = input.nextInt();
		long totalSconds = System.currentTimeMillis() / 1000;
		long currentSconds = totalSconds % 60 + a;
		long totalMimutes =  totalSconds / 60;
		long currentMimutes = totalMimutes % 60;
		long totalHours = totalMimutes / 60;
		long currentHours = totalHours % 24;
		System.out.println("Current time is:  " + currentHours + ":" +  currentMimutes + ":" + currentSconds);
		System.out.println("");
	}
	
	//习题2.13
	static void getMoney() {
		System.out.println("习题2.13");
		System.out.println("Enter the monthly saving amount:");
		Scanner input = new Scanner(System.in);
		double monthMoney = input.nextDouble();
		input.close();
		double nowMoney = 0;
		for(int i = 0;i < 6;i++) {
			double afterMoney = (nowMoney + monthMoney)* (1 + 0.00417);
			nowMoney = afterMoney;
		}
		System.out.print("After six month ,the account value is: " + nowMoney);
		System.out.println("");
	}
	
	//习题2.23
	static void print(String s) {
		System.out.print(s);
	}
	
	static double scanf() {
		Scanner input = new Scanner(System.in);
		double tmp = input.nextDouble();
		input.close();
		return tmp;
	}
	static void getSpend() {
		print("习题2.23\n");
		print("Enter the driving distance:\n");
		double distance = scanf();
		print("Enter mile per gallon:\n");
		double gallon = scanf();
		print("Enter prince per gallon:\n");
		double money = scanf();
		double ans = distance / gallon * money;
		String s = "The cost of driving is : $" + ans;
		print(s);
		print("\n");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		threeNum();
		getDate();
		getTime();
		getMoney();
		getSpend();
	}

}
