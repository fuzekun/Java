package InterfrenceAndABS;

public class Rectangle extends GeometericObject{
	private double width;
	private double height;
	public Rectangle() {
		// TODO Auto-generated constructor stub
		this(2,5);
	}
	public Rectangle(double wd,double ht) {
		width = wd;
		height = ht;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return width * height;
	}
	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return 2 * (width + height);
	}
	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", height=" + height + ", getArea()=" + getArea() + "]";
	}

}
