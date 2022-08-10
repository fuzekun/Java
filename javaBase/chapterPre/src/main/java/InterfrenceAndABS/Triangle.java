package InterfrenceAndABS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Triangle extends GeometericObject implements Comparable<Triangle>{

	private double a,b,c;
	public Triangle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Triangle [a=" + a + ", b=" + b + ", c=" + c + ", getArea()=" + getArea() + "]";
	}

	@Override
	public int compareTo(Triangle o) {
		// TODO Auto-generated method stub
		if(this.getArea() < o.getArea())return -1;
		else if(this.getArea() == o.getArea())return 0;
		else return 1;
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		double s = (a + b + c) / 2.0;
		double area = Math.sqrt(s*(s - a)*(s - b) * (s - c));
		return area;
	}

	public Triangle(double a, double b, double c) throws NotTriangelException{
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		if(a >= b + c || b >= a + c || c >= b + a)
		throw new NotTriangelException();
	}

	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return a + b + c;
	}
	static double getAreat(double a,double b,double c) {
		double s = (a + b + c) / 2.0;
		double area = Math.sqrt(s*(s - a)*(s - b) * (s - c));
		return area;
	}
	public static void main(String[] args) {
		ArrayList<Triangle>array = new ArrayList<>();
		try {
			array.add(new Triangle(2,3,4));
			array.add(new Triangle(2,4,4));
			array.add(new Triangle(3,5,4));
			array.add(new Triangle(2,4,5));
			Collections.sort(array);
			for(Triangle t : array) {
				System.out.println(t);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
