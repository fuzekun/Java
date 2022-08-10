package homework;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


public class HomeWork11 {

	
	public static void print(String tilte) {
		System.out.println("习题" + tilte);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		print("9.1");
		TestRectangel();
		
		
		print("9.3");
		TestDate();
		
		print("9.4");
		TestRandom();
		
		print("9.5");
		TestCaleda();
		
		
		print("9.6");
		TestStopWatch();
		
		
		print("9.9");
		TestRegularPolygon();
		
	}
	//9.1
	public static void TestRectangel () {
		Rectangel rectangel = new Rectangel(4,40);
		Rectangel rectangel2 = new Rectangel(3.5,35.9);
		System.out.println("宽" + rectangel.getHeight() + "高" + rectangel.getWidth() + "周长" + rectangel.getPrime());
		System.out.println("宽" + rectangel2.getHeight() + "高" + rectangel2.getWidth() + "周长" + rectangel2.getPrime());
	}
	
	//9.3
	public static void TestDate() {
		Date date = new Date();
		long time = 10000;
		for(int i = 0;i < 8;i++) {
			time *= 10;
			date.setTime(time);
			System.out.println(date.getDate());
		}
	}
	//9.4
	public static void  TestRandom() {
		Random random = new Random();
		random.setSeed(1000);
		for(int i = 0;i < 50;i++) {
			System.out.println(random.nextInt(100));
		}
	}
	//9.5
	public static void TestCaleda() {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		System.out.println("当前的年月日" + 
		gregorianCalendar.get(GregorianCalendar.YEAR)+
		gregorianCalendar.get(GregorianCalendar.MONTH)+
		gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH));
	
		gregorianCalendar.setTimeInMillis(134567898765L);
		
		System.out.println("设置后当前的年月日" + 
		gregorianCalendar.get(GregorianCalendar.YEAR)+
		gregorianCalendar.get(GregorianCalendar.MONTH)+
		gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH));
	}
	
	
	//9.6
	public static void TestStopWatch() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		//时间复杂度是O（n * n）所以，大约时间差不多
		for(int i = 0;i < 100000;i++) {
			for(int j = 0;j < 100000;j++) {
				int a = 0;
				int b = 1;
				int tmp  = a;
				a = b;
				b = tmp;
			}
		}
		stopWatch.stop();
		System.out.println("运行时间是" + stopWatch.getElapsedTime());
	}
	
	//9.9
	public static void TestRegularPolygon() {
		RegularPolygon regularPolygon = new RegularPolygon(6,4);
		RegularPolygon regularPolygon2 = new RegularPolygon(10,4,5.6,7.8);
		System.out.println("周长是 :"+regularPolygon.getPrime() + "面积是:"+regularPolygon.getArea());
		System.out.println("周长是 :"+regularPolygon2.getPrime() + "面积是:"+regularPolygon2.getArea());
	}
	
	
}
class Rectangel{
	private double height;
	private double width;
	public Rectangel() {
		// TODO Auto-generated constructor stub
	}
	public Rectangel(double height, double width) {
		super();
		this.height = height;
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getPrime() {
		return height * 2 + width * 2;
	}
	public double getArea() {
		return height * width;
	}
	
}


class StopWatch{
	
	private long startTime = 0;
	private long endTime = 0;
	public StopWatch() {
		// TODO Auto-generated constructor stub
	}
	public void start() {
		startTime = System.currentTimeMillis();
	}
	public void stop() {
		endTime = System.currentTimeMillis();
	}
	public long  getElapsedTime() {
		return endTime - startTime;
	}
}

class RegularPolygon{
	private int n = 3;
	double side = 1;
	double x = 0;
	double y = 0;
	public RegularPolygon() {
		// TODO Auto-generated constructor stub
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public double getSide() {
		return side;
	}
	public void setSide(double side) {
		this.side = side;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public RegularPolygon(int n, double side) {
		super();
		this.n = n;
		this.side = side;
		this.x = x;
		this.y = y;
	}
	public RegularPolygon(int n, double side, double x, double y) {
		super();
		this.n = n;
		this.side = side;
		this.x = x;
		this.y = y;
	}	
	public double getPrime() {
		return n * side;
	}
	public double getArea() {
		return n * side * side / 4 * Math.tan(Math.PI/n);
	}
	
}