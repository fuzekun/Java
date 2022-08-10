package homework;
import java.util.Scanner;
import java.util.regex.Pattern;
public class Homework4 {

	static void printTile(double tmp) {
		System.out.println("习题" + tmp);
	}
	//习题4.8
	static void printChar() {
		System.out.println("Enter an ASCII code ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		char ch = (char)num;
		System.out.println(ch);
		sc.close();
	}
	//习题4.9
	static void printInt() {
		System.out.println("Enter a Character:");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		char ch = s.charAt(0);
		System.out.println((int)ch);
		sc.close();
	}
	//习题4.11
	static void ChangeToHex() {
		System.out.println("Enter a Integer");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		if(num >= 0 && num <= 9)System.out.println(num);
		else System.out.println((char)('A' + num - 10));
		sc.close();
	}
	//4.21,使用正则表达式
	static void Match() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a SSN");
		String s = scanner.nextLine();
		
		if(Pattern.matches("[0-9]{3}-[0-9]{2}-[0-9]{4}", s))   
			System.out.println(s+" is an valid social security number");
		else System.out.println(s+" is an invalid social security number");
		scanner.close();
	}
	//4.23
	static void printDefine() {
		System.out.print("Enter employee's name:");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		System.out.print("Enter number of hours worked in a week:");
		int hour = sc.nextInt();
		System.out.print("Enter hourly pay rate :");
		double payRate = sc.nextDouble();
		System.out.print("Enter federal tax withholding rate:");
		double FederalTax = sc.nextDouble();
		System.out.print("Enter state tax witholding rate:");
		double StateTax = sc.nextDouble();
		System.out.println(name + hour + payRate + FederalTax + StateTax);
		sc.close();
	}
	//习题4.25
	static void getLicencePlatNumber() {
		String s = "";
		for(int i = 0;i < 3;i++) {
			char ch = (char)('A'+(Math.random()*('Z'-'A') + 1));
			s += ch;
		}
		for(int i = 0;i < 4;i++) {
			int num = (int)(Math.random() * 10);
			s += num;
		}
		System.out.println("The Licence Plat Number that was get by random is " + s);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printChar();
		printInt();
		ChangeToHex();
		Match();
		printDefine();
		getLicencePlatNumber();
		
	}

}
