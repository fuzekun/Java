package InterfrenceAndABS;


public class TestGeometericObject {

	static int are = 1;//静态数据只能由对
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeometericObject object1 = new Circle(5);
		GeometericObject object2 = new Rectangle(5,3);
		
		System.out.println("The two GeometericObject have the same Area ?\n" + equalArea(object1, object2));
		display(object1);
		display(object2);
		
		/*
		 * If the method only is in the sonClass, it can not be used by the example defined by the fatherClass; 
		 * eg:
		GeometericObject circl1 = new Circle(2.5);
		circl1.equal(new Circle(3.5));*/
		
		
	}
	public static boolean equalArea(Circle object1 ,Rectangle object2) {
		return object1.getArea() == object2.getArea();
	}
	
	public static boolean equalArea(GeometericObject object1, GeometericObject object2) {
		return object1.getArea() == object2.getArea();
	}
	
	public static void display(GeometericObject object) {
		System.out.println();
		System.out.println("The Area is " + object.getArea());
		System.out.println("The Perimeter is " + object.getPerimeter());
	}
	
}