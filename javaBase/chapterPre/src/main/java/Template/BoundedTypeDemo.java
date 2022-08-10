package Template;

import InterfrenceAndABS.Circle;
import InterfrenceAndABS.GeometericObject;
import InterfrenceAndABS.Rectangle;
public class BoundedTypeDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeometericObject rectangle = new Rectangle(3,5);
		GeometericObject circle = new Circle(4);
		System.out.println("Same area?\n" + equealArea(rectangle, circle));
	}
	public static <E extends GeometericObject> boolean equealArea(E rectangle,E circle) {
		return rectangle.getArea() == circle.getArea();
	}

}
