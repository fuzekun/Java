package Lamba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//����java8���Ĵ���ĺ���
public class FuorFunction {
	
	public static void main(String[] args) {
		FuorFunction function = new FuorFunction();
		function.test4();
	}
	public void test() {
		getNumList(10, () -> (int)(Math.random() * 100)).forEach(System.out::println);
	}
	
	//����Supplier�����ӿ�
	public List<Integer> getNumList(int num, Supplier<Integer> sup) {
		List<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < num; i++) {
			list.add(sup.get());
		}
		return list;
	}
	
	//����Function�ӿ�
	public void test2() {
		String s = "fdad, FDADS";
		strHander(s, (x) ->x.substring(0,5));
	}
	
	public void strHander(String s, Function<String, String>fun) {
		System.out.println(fun.apply(s));
	}
	
	//����Costumer�ӿ�
	public void test3() {
		testCuns(10000.0, (x) -> System.out.println("�������Ϊ:" + x + "Ԫ"));
	}
	public void testCuns(double money,Consumer<Double>con) {
		con.accept(money);
	}
	
	//�������������ַ��������뼯����
	public List<String> filterStr(List<String> ss, Predicate<String>pre) {
		List<String> strlist = new ArrayList<>();
		
		for(String str : ss) {
			System.out.println(str.length());
			if(pre.test(str)) {
				strlist.add(str);
			}
		}
		return strlist;
	}

	public void test4() {
		String []ss = {"�й�", "�ط�" , "�д�", "fdae"};
		List<String>t = Arrays.asList(ss);
		List<String>list = filterStr(t, (s) -> s.length() > 3);
		for(String tmp : list) {
			System.out.println(tmp);
		}
	}
	
	
}
