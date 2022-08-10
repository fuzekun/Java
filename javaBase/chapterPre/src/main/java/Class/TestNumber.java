package Class;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;


public class TestNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Number>list = new ArrayList<>();
		list.add(45);
		list.add(1e2);
		list.add(new BigInteger("355550596554"));
		list.add(new BigDecimal("2.6596565656565"));
		
		System.out.println("The largest number is "+ getlargestNumber(list));
		
		test();
	}
	
	static void test() {
		
		/*
		 * can not change Integer to Double 
		 * It is like that you can't change the class named pig to the class named person!!!
		 * 
		Number number = new Integer(4);
		double douler = (double)number;
		System.out.println(douler); 
		*/
		
		/*
		 * the method intValue ,doubleValue or floatValue ect in Class Number was defined as abstract ,
		 * so you can not use it by the example of number;
		 * The method compareTo(Integer) is undefined for the type Number
		 * The Class Integer is final class ,so it can not be extended;
		 *	强转也不行
		 
		Number number  = new Integer(3);
		Integer number = new Integer(3);
		System.out.println(number.intValue());
		number = (Integer)number;
		System.out.println(number.compareTo(new Integer(4)));*/
		Number number = new Integer(3);//父类可以指向子类
		System.out.println(number);
	}
	public static Number getlargestNumber(ArrayList<Number>list) {
		if(list == null || list.size() == 0)return null;
		
		Number number = list.get(0);
		for(int i = 0;i < list.size();i++) {
			number = Math.max(number.doubleValue(),list.get(i).doubleValue());//have to change to doubleValue() else will be fault;
		}
		return number;
	}
	
}