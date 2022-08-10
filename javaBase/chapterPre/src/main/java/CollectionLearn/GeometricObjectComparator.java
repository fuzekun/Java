package CollectionLearn;

import java.io.Serializable;
import java.util.Comparator;

import InterfrenceAndABS.Circle;
import InterfrenceAndABS.GeometericObject;
import InterfrenceAndABS.Rectangle;


//实现几何图形的比较
//可串行化的
public class GeometricObjectComparator implements Comparator<GeometericObject>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeometericObject g1 = new Rectangle(5,5);
		GeometericObject g3= new Circle(1);
		GeometericObject g = max(g1,g3,new GeometricObjectComparator());
		System.out.println("面积大的是"+g.getArea());
	}
	public static  GeometericObject max(GeometericObject g1,GeometericObject g2,Comparator<GeometericObject>c) {
		if(c.compare(g1, g2) > 0)return g1;
		else return g2;
	}

	@Override
	public int compare(GeometericObject o1, GeometericObject o2) {
		// TODO Auto-generated method stub
		if(o1.getArea() < o2.getArea())return -1;
		else if(o1.getArea() == o2.getArea())return 0;
		else return 1;
	}
}
