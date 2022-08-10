package Template;


import java.util.ArrayList;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testStack();
		testStaticE();
	}
	
	static void test3() {
		//E[] e = new E[]错误
	}

	static void test2() {
		Stack<Integer>stack1 = new Stack<>();
		Stack<Object>stack2 = new Stack<>();
		try {
			add(stack1, stack2);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	static <T>void add(Stack<? extends T> stack1,Stack<T> stack2) throws StackEmptyException {
		while(!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
	}
	
	static void test1(){
		/*ArrayList dates = new ArrayList<>();
		dates.add(new Date());
		dates.add(new String());
		Date date = (Date) dates.get(0);//没强制转换就会报错，因为返回的是object类。
		System.out.println(date);*/
		/*
		ArrayList<Date> date2 = new ArrayList<>();
		date2.add(new Date());
		date2.add(new Date());
		*/
		ArrayList<Date> date2 = new ArrayList<>();
		date2.add(new Date());
		Date date3 = date2.get(0);
		System.out.println(date3);
	}
	static void testStack() {
		Stack<Integer>stack = new Stack<>();
		stack.push(1);
		int x = stack.peek();
		System.out.println(x);
		try {
			stack.pop();
			stack.pop();
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	static void testStaticE(){
		Integer[] integers = {1,3,45,6,6};
		String[] strings = {"beijing","london","New York"};
		Main.<Integer>print(integers);
		Main.<String>print(strings);
	}
	//静态的方法可以使用泛型
	public static<E>void print(E[] list){
		for(int i = 0;i < list.length;i++) {
			System.out.println(list[i] + " ");
		}
		System.out.println();
	}
}
