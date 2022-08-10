package homework;
import java.util.Scanner;

public class homework3 {

	static void printTitle(double a) {
		System.out.println("ϰ��" + a);
	}
	
	//ϰ��3.4
	static void printMonth() {
		printTitle(3.4);
		int a = (int)(1 + (Math.random()*12 - 1));
		String[] month = {"January","February","March","April","May",
				"June","July","August","September","October","November",
				"December"};
		try {
			System.out.println(month[a - 1]);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("����Խ��");
		}
	}
	//ϰ��3.5
	static void printWeeek() {
		printTitle(3.5);
		String[] week = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter today's day");
		int today = sc.nextInt();
		System.out.println("Enter the number of days elapsed since today:");
		int afterday = sc.nextInt();
		afterday = (today + afterday) % 7;
		sc.close();
		try {
			System.out.println("Today is " +week[today] + "day and the future day is "+ week[afterday] + "day");
	
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("���������������0��6�е�����һ������");
		}
	}
	//ϰ��3.9
	static void printISBN() {
		printTitle(3.9);
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the firsr 9 digits of an integer :");
		String isbn = sc.nextLine();
		char[] ch = isbn.toCharArray();
		int ans = 0;
		for(int i = 0;i < 9;i++) {
			ans = (ans + (i + 1) * (ch[i] - '0')) % 11 ;
		}
		System.out.print("The ISBN-10 number is " + isbn);
		if(ans <= 9) {
			System.out.println(ans);
		}
		else System.out.println("X");
		sc.close();
	}
	//ϰ��3.15
	static void printLottery() {
		printTitle(3.5);
		//��ȡ�����
		int num = (int)(100 + (Math.random() * 900));//[100,1000);
		
		//�õ��������֣���vis���鱣��
		String s = Integer.toString(num);
		char[] patten = s.toCharArray();
		boolean[] vis = new boolean[10];
		for(int i = 0;i < patten.length;i++) {
			int Index = patten[i] - '0';
			vis[Index] = true;
		}
		
		//System.out.println("��ȷ�Ĳ�Ʊ����" + num);
		System.out.print("�������Ʊ��");
		Scanner sc = new Scanner(System.in);
		int num2 = sc.nextInt();
		System.out.println("��ȷ�Ĳ�Ʊ����" + num);
		sc.close();
		if(num2 == num) {							//��ȫƥ��
			System.out.println("������$10000");
			return;
		}
		
		//ͳ��ƥ�����ֵĸ���
		int cnt = 0;
		s = Integer.toString(num2);
		char[] ans = s.toCharArray();	
		for(int i = 0;i < ans.length;i++) {
			int Index = ans[i] - '0';
			if(vis[Index])vis[Index] = false;
		}
		for(int i = 0;i < vis.length;i++) {
			if(vis[i])cnt++;
		}
													//����ȫƥ��
		int ans1 = cnt * 1000;
		System.out.println("������$" + ans1);
		sc.close();
	}
	//ϰ��3.21
	static void getWeek() {
		printTitle(3.2);
		System.out.println("�Լ��ķ�����������");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter year");
		int year = sc.nextInt();
		System.out.println("Enter month");
		int month = sc.nextInt();
		System.out.println("Enter day");
		int day = sc.nextInt();
		int[] monthday = {31,28,31,30,31,30,31,31,30,31,30,31};
		
		year--;//��һ���������
		int totalDay = 365 * year + year / 4 - year / 100 + year /400 ; //��������Ķ���õ�������,�ӹ�ԪԪ������
		for(int i = 0;i < month - 1;i++) {
			totalDay += monthday[i];
		}
		totalDay += day;
		if((year % 4 == 0 && year % 100 != 0) | year % 400 == 0) {//����
			if(month > 2)totalDay++;							  //���ڶ���
		}
		
		String[] week = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
		System.out.println("Day of the week is "+week[totalDay%7] + "day");
		sc.close();
	}
	//ϰ��3.28
	static void Inrectangel() {
		printTitle(3.28);
		Scanner sc = new Scanner(System.in);
		System.out.println("�����һ�����ε�x��y�����꣬�Լ���͸�:");
		double x1 = sc.nextDouble(),y1 = sc.nextDouble(),w1 = sc.nextDouble(),h1 = sc.nextDouble();
		System.out.println("����ڶ������ε�x��y�����꣬�Լ���͸�:");
		double x2 = sc.nextDouble(),y2 = sc.nextDouble(),w2 = sc.nextDouble(),h2 = sc.nextDouble();
		Rect rect1 = new Rect();
		rect1.x1 = x1 - w1/ 2;
		rect1.x2 = x1 + w1 / 2;
		rect1.y1 = y1 - h1 / 2;
		rect1.y2 = y1 + h1 / 2;
		Rect rect2 = new Rect();
		rect2.x1 = x2 - w2 / 2;
		rect2.x2 = x2 + w2 / 2;
		rect2.y1 = y2 - h2 / 2;
		rect2.y2 = y2 + h2 / 2;
		
		if(rect1.x1 < rect2.x1 && rect1.y1 > rect2.y1 && rect1.x2 > rect2.x2 && rect1.y2 < rect2.y2) {
			System.out.println("r2 is inside r1");
		}
		else if((rect2.x1 < rect1.x1 && rect2.x1 > rect1.x2) || (rect2.x1 < rect1.x1 && rect2.x2 > rect1.x2))
		{
			System.out.println("r2 overlaps r1");
		}
		else System.out.println("r2 doesn't overlaps r1");
		sc.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printMonth();
		printWeeek();
		printISBN();
		printLottery();
		getWeek();
		Inrectangel();
		
	}
}

class Rect{
	public double x1,y1;
	public double x2,y2;
}