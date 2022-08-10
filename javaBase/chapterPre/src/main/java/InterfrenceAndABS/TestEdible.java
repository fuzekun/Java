package InterfrenceAndABS;

import java.util.Date;



public class TestEdible{
	// TODO Auto-generated constructor stub
	public static void main(String args[]){
		//System.out.println(new Date());
		Object[] objects = {new Tiger(),new Chichen(),new Apple()};
		/*Object tiger = new Tiger();
		System.out.println(((Animal) tiger).sound());*/
		for(int i = 0;i < objects.length;i++) {
			if(objects[i] instanceof Edible) {
				System.out.println(((Edible) objects[i]).howtoEat());
			}
			if(objects[i] instanceof Animal) {
				System.out.println(((Animal) objects[i]).sound());
			}
		}
		test();
	}
	static void test() {
		@SuppressWarnings("deprecation")
		Date date1 = new Date(2011,10,14);
		@SuppressWarnings("deprecation")
		Date date2 = new Date(2012,1,1);
		System.out.println(new Date());
		System.out.println(date1.compareTo(date2));
	}
}

interface Edible{
	public String howtoEat();
}

abstract class Animal{
	abstract String sound();
}

class Chichen extends Animal implements Edible{

	@Override
	public String howtoEat() {
		// TODO Auto-generated method stub
		return "Chicken :Fry it";
	}

	@Override
	String sound() {
		// TODO Auto-generated method stub
		return "Chichen :cock-a-double-doo";
	}
	
}

class Tiger extends Animal{


	@Override
	String sound() {
		// TODO Auto-generated method stub
		return "Tiger: RROAA";
	}
	
}

abstract class Fruit implements Edible{
}

class Apple extends Fruit{

	@Override
	public String howtoEat() {
		// TODO Auto-generated method stub
		return "Apple : make apple cider";
	}	
}

class Orange extends Fruit{

	@Override
	public String howtoEat() {
		// TODO Auto-generated method stub
		return "Orignge : make oringe juice";
	}
	
}