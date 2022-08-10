package Lamba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class example {
	private static final List<Empoyee> List = null;
	public static void main(String[] args) {
		example e = new example();
		e.test12();
		
	}
	//匿名内部类
	public void neibuClass() {
		Comparator<Integer>com = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};
		TreeSet<Integer> treeSet = new TreeSet<>(com);

	}
	
	//使用Lambda表达式
	public void testLClass() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		TreeSet<Integer>ts = new TreeSet<>(com);
	}
	
	//需求:获取当前工四年龄大于32的员工信息
	List<Empoyee>empoyees = Arrays.asList(
			new Empoyee(14, "张三"),
			new Empoyee(37, "李四"),
			new Empoyee(36, "王五"),
			new Empoyee(35, "赵刘")
			);
	public void test3() {
		//获取年龄大于35
		List<Empoyee>list = filterEmpoyees(empoyees);
		for(Empoyee empoyee : list) {
			System.out.println(empoyee);
		}
		//获取工资大于5000
		List<Empoyee>list2 = salartfilter(empoyees);
		for(Empoyee empoyee : list2) {
			System.out.println(empoyee);
		}
	}
	
	
	public List<Empoyee> filterEmpoyees(List<Empoyee> list) {
		List<Empoyee>empoyees = new ArrayList<>();
		for(Empoyee empoyee : empoyees) {
			if(empoyee.getAge() >= 35) {
				empoyees.add(empoyee);
			}
		}
		return empoyees;
	}
	

	
	//需求:获取当前工人中，工资大于5000的员工信息
	public List<Empoyee> salartfilter(List<Empoyee> list) {
		List<Empoyee>empoyees = new ArrayList<>();
		for(Empoyee empoyee : empoyees) {
			if(empoyee.getSalary() >= 5000) {
				empoyees.add(empoyee);
			}
		}
		return empoyees;
	}
	
	
	//优化方式一：策略设局模式
	public  List<Empoyee> filterEmployees(List<Empoyee> list, MyPredicate<Empoyee> myPredicate) {
		List<Empoyee>empoyees = new ArrayList<>();
		for(Empoyee empoyee : empoyees) {
			if(myPredicate.test(empoyee)) {
				empoyees.add(empoyee);
			}
		}
		return empoyees;
	}
	
	public void test4() {
		List<Empoyee> list = filterEmployees(empoyees, new FilterEmployeeByAge());
		for(Empoyee empoyee : list) {
			System.out.println(empoyee);
		}
	}
	
	//优化方式二：匿名内部类
	public void test5() {
		List<Empoyee> list = filterEmployees(empoyees, new MyPredicate<Empoyee>() {
			@Override
			public boolean test(Empoyee t) {
				return t.getAge() > 16;
			}
		});
		for(Empoyee empoyee : list) {
			System.out.println(empoyee);
		}
	}
	//优化方式三:lambda表达式
	public void test6() {
		List<Empoyee> list = filterEmployees(empoyees, (e) -> e.getAge() >= 16);
		list.forEach(System.out::println);
		List<Empoyee> list2 = filterEmployees(empoyees, (e) -> e.getSalary() >= 3500);
		list.forEach(System.out::println);
	}
	//优化方式四:predict语法
	public void test7() {
		empoyees.stream()
				.filter((e) -> e.getAge() >= 16)
				.limit(2)
				.forEach(System.out::println);
		empoyees.stream()
				.filter((e) -> e.getSalary() >= 3500)
				.limit(3)
				.forEach((x) ->{
					x.setSalary(5000);
				});
	}
	
	
	//实现两个数字的加减乘除操作
	
	public void test8() {
		System.out.println(opxy(100, 1000, (x, y)-> x + y));
	}
	
	public int opxy(int x, int y, Opration op) {
		return op.oprt(x, y);
	}
	
	//调用Collection.sort()方法，通过定制排序比较两个Employee（先按住奥年龄比，年龄相同按照姓名比），使用Lambda接口作为参数传递
	public void test9() {
		Collections.sort(empoyees, (e1, e2) -> {
			if(e1.getAge() == e2.getAge()) {
				return Integer.compare(e1.getSalary(), e2.getSalary());
			}else {
				return Integer.compare(e1.getAge(), e2.getAge());
			}
		});
		empoyees.forEach(System.out::println);
	}
	
	
	//写一个方法将接口作为参数，使得字符串被转换为大写
	public void test10() {
		String s = "fdafda,FDAEFA,AD";
		//转换成大写
		System.out.println(oprateSt(s, (x) -> x.toUpperCase()));
		//转换成小写
		System.out.println(oprateSt(s, (x) -> x.toLowerCase()));
		//通过逗号分开
		System.out.println(oprateSt(s, (x) -> x.split(",").toString()));
	}
	public String oprateSt(String x, StringSolve ss) {
		return ss.solve(x);
	}
	
	//
	public void op(long l1, long l2, MyFuction<Long, Long> mf) {
		System.out.println(mf.getValue(l1, l1));
	}
	public void test11() {
		op(55, 66, (x, y) -> x * y);
		
		op(55, 66, (x, y) -> x + y);
	}
	
	//
	public void test12() {
		Predicate<String>filter = (x) -> {
			char[] chars = x.toCharArray();
			 for(int i = 0; i < chars.length; i++) {
				 if(chars[i] == '赌') {
					 if(chars[i + 1] == '博') {
						 return false;
					 }
				 }
			 }
			 return true;
		};
		String s = "今天你赌博了吗";
		if(filter.test(s)) {
			System.out.println("安全");
		}else {
			System.out.println("不安全");
		}
		
	}
	
	
		
}
