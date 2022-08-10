package CollectionLearn;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import InterfrenceAndABS.GeometericObject;
import InterfrenceAndABS.Rectangle;
import InterfrenceAndABS.Triangle;
import InterfrenceAndABS.Circle;

public class StudySet {

	private static final int String = 0;
	private static final int TreeSet = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testSet();
		//testLinkSet();
		//TreeSetTest();
		testMethod();
	}	
	public static void testSet() {
		CloneSet<String>set = new CloneSet<>();//用于存储不同的元素
		for(int i = 0;i < 10;i++)set.add("增");
		for(int i = 0;i < 10;i++)set.add("删");
		System.out.println(set);
		CloneSet<String>set3 =  set;
		System.out.println("可以使用这种方法克隆"+set3);
		for(String s : set)System.out.print(s + " ");
		System.out.println("\n" + set.size());
		Set<String>set2 =  new TreeSet<>();
		Set<String>tmp = (Set<String>) set.clone();
		for(int i = 0;i < 8;i++)set2.add("删");
		set.removeAll(set2);  					//集合的差集
		System.out.println(set2);
	}
	/**
	 * 
	 */
	public static void testLinkSet() {
		Set<GeometericObject>set2 = new LinkedHashSet<>();
		set2.add(new Rectangle(3,4));
		set2.add(new Circle(4));
		set2.add(new Rectangle(1,2));
		for(GeometericObject g : set2)System.out.println(g);
	}
	
	public static void TreeSetTest() {
		
		Comparator<GeometericObject>c = new GeometricObjectComparator();
		Set<GeometericObject> set = new TreeSet<>(c);
		try {
			set.add(new Triangle(3,4,5));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		set.add(new Rectangle(3,4));
		set.add(new Circle(4));
		set.remove(new Rectangle(2,6));//比较的是面积，所以面积相等就会被删除;
		for(GeometericObject s:set) {
			System.out.println(s);
		}
		set.remove(new Rectangle(3,4));
		Rectangle o1 = new Rectangle(3,5);
		Rectangle o2 = new Rectangle(5,3);
		System.out.println(o1.equals(o1));
		System.out.println(o1.hashCode() == o2.hashCode());
	}
	
	static void testMethod() {
		TreeSet<String>set = new TreeSet<>();
		set.add("ye");
		set.add("blu");
		set.add("gree");
		Set<String>set2 = new HashSet<>();

		set2.add("ye");
		set2.add("blu");
		set2.add("gree");
		Set<String>stmp = set;
		stmp.addAll(set2);
		System.out.println(stmp);		
		
		String s = ((TreeSet<String>) set).higher("blu");
		s = ((TreeSet<String>)set).floor("blu");
		s =((TreeSet<String>)set).first();
		s = set.pollFirst();
		System.out.println(s);
		System.out.println(set);
		
		LinkedHashSet<String>linkedHashSet = new LinkedHashSet<>();
		LinkedHashSet<String>linkedHashSet2 = (LinkedHashSet<String>)linkedHashSet.clone();
	}
}
