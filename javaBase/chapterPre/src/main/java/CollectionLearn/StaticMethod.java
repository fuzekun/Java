package CollectionLearn;


import java.util.Collections;

import java.util.List;


import java.util.ArrayList;
import java.util.Arrays;

import InterfrenceAndABS.Circle;
import InterfrenceAndABS.GeometericObject;
import InterfrenceAndABS.Rectangle;

public class StaticMethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestArrayStaticMethond();

	}
	public static void TestArrayStaticMethond(){
		List<String>list = Arrays.asList("red","green","blue");
		Collections.reverse(list);
		for(String i:list) {
			System.out.print(i +" ");
		}
		System.out.println();
		ArrayList<GeometericObject>list2 = new ArrayList<>();
		list2.add(new Rectangle(4,5));
		list2.add(new Rectangle(4,3));
		list2.add(new Circle(4));//如果子类没有方法会调用父类的方法，知道找到第一个停止。
		list2.add(new Rectangle(4,6));
		Collections.sort(list2,new GeometricObjectComparator());
		for(GeometericObject i : list2) {
			testObject(i);
		}

		List<String>cplist = Arrays.asList("re","b","g");
		Collections.copy(cplist, list);
		for(String i : cplist) {
			System.out.print(i + " ");
		}
	}
	public static void testObject(Object o) {
		GeometericObject g1 = (GeometericObject)o;
		System.out.println(g1);
	}
}
