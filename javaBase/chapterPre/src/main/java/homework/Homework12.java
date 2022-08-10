package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Homework12 {
	
	static private double a = 0,b = 0,c = 0,d = 0,e = 0,f = 0;
	static void printTitle(String num) {
		System.out.println("习题"+num);
	}
	public static void main(String[] args) {
		
		printTitle("9.10");
		QuadraticEquation quadraticEquation = new QuadraticEquation(1, 2, 1);
		quadraticEquation.getRoot();
		
		printTitle("9.12");
		LineEquation lineEquation = new LineEquation(1, 1, 1, 3, 1, 1);
		lineEquation.Show();
		
		printTitle("9.13");
		cin();
		LineEquation lineEquation2 = new LineEquation(a, b, c, d, e, f);
		lineEquation2.Show();
		
		printTitle("10.4");
		MyPoint point = new MyPoint(0,0);
		MyPoint point2 = new MyPoint(10,30.5);
		System.out.println("两点之间的距离是:" + point.distance(point2));
		
		printTitle("10.5");
		Stack<Integer>stack = getYinzi(120);
		if(!stack.isEmty()) {
			System.out.println(stack.pop());
		}
	}
	
	//习题9.10
	private static class QuadraticEquation{
		private double a,b,c;
		public QuadraticEquation(double a,double b,double c) {
			// TODO Auto-generated constructor stub
			this.a = a;
			this.b = b;
			this.c = c;
		}
		public double getA() {
			return a;
		}
		public double getB() {
			return b;
		}
		public double getC() {
			return c;
		}
		public double getDicriminant() {
			return b * b - 4 * a * c;
		}
		public double getRoot1() {
			if(getDicriminant() >=0)
				return (-b+Math.sqrt(getDicriminant()))/2*a;
			return 0;
		}
		public double getRoot2() {
			if(getDicriminant() >=0)
				return (-b-Math.sqrt(getDicriminant()))/2*a;
			return 0;
		}
		public void getRoot() {
			if(getDicriminant() >0) {
				System.out.println("两个根分别是"+getRoot1() +" "+getRoot2());
			}
			else if(getDicriminant() == 0) {
				System.out.println("一个根是"+getRoot1());
			}
			else  System.out.println("没有实数根");
		}
	}
	//习题9.12
	static private class LineEquation {
		private double a,b,c,d,f,e;

		public LineEquation(double a, double b, double c, double d, double e, double f) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.f = f;
			this.e = e;
		}
		public double getA() {
			return a;
		}
		public double getC() {
			return c;
		}
		public double getD() {
			return d;
		}
		public double getB() {
			return b;
		}
		public double getE() {
			return e;
		}
		public double getF() {
			return f;
		}
		public boolean isSovable() {
			return a * b - b * c == 0?false:true;
		}
		public double getX() {
			return (e * d - b * f) / (a * d - b * c);
		}
		public double getY() {
			return (a * f - e * c) / (a * d - b * c);
		}
		public void Show() {
			if(isSovable()) {
				System.out.println("没有交点");
				return ;
			}
			System.out.println("交点是("+getX() + "," + getY()+")");
		}
	}
	//习题9.13
	static void cin() {
		System.out.println("输入两条直线");
		Scanner scanner = new Scanner(System.in);
		a = scanner.nextDouble();
		b = scanner.nextDouble();
		c = scanner.nextDouble();
		d = scanner.nextDouble();
		e = scanner.nextDouble();
		f = scanner.nextDouble();
		scanner.close();
	}
	
	//习题10.4
	static private class MyPoint{
		private double x,y;
		public double getX() {
			return x;
		}
		public double getY() {
			return y;
		}
		public MyPoint() {
			// TODO Auto-generated constructor stub
			this.x = 0;
			this.y = 0;
		}
		public MyPoint(double x,double y) {
			this.x = x;
			this.y =y;
		}
		public double distance(double otherx,double othery) {
			return Math.sqrt((x - otherx) *(x - otherx) + (y - othery) * (y - othery)); 
		}
		public double distance(MyPoint point) {
			return Math.sqrt((x - point.x) *(x - point.x) + (y - point.y) * (y - point.y));
		}
	}
	//习题10.5
	static private class Stack<T> {
		private List<T>list;
		public Stack() {
			// TODO Auto-generated constructor stub
			list = new ArrayList<>();
		}
		public T pop() {
			if(list.isEmpty()) {
				return list.remove(list.size());
			}
			return null;
		}
		public void put(T a) {
			list.add(a);
		}
		public boolean isEmty() {
			return list.isEmpty();
		}
	}
	static boolean isPrime(int a) {
		for(int i = 0;i < Math.sqrt(a);i++) {
			if(i * i == a) {
				return false;
			}
		}
		return true;
	}
	static Stack<Integer> getYinzi(int a) {
		Stack<Integer>stack = new Stack<>();
		int n = a;
		for(int i = 2;i < n;i++) {
			if(isPrime(i) && a % i == 0) {
				while(a % i != 0) {
					System.out.println(i);
					stack.put(i);
					a /= i;
				}
			}
		}
		return stack;
	}
}
