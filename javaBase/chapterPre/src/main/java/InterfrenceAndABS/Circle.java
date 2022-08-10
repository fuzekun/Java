package InterfrenceAndABS;

public class Circle extends GeometericObject{
	private double radius;
	public Circle() {
		
	}
	public Circle(double r) {
		radius = r;
	}
	public double getRadius() {
		return radius;
	}
	@Override
	public String toString() {
		return "Circle [radius=" + radius + ", getArea()=" + getArea() + "]";
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return radius * radius * Math.PI;
	}
	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return Math.PI * radius * 2;
	}
	public boolean equal(Circle c2) {
		return this.getArea() == c2.getArea();
	}
}
