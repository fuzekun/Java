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
	//�����ڲ���
	public void neibuClass() {
		Comparator<Integer>com = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};
		TreeSet<Integer> treeSet = new TreeSet<>(com);

	}
	
	//ʹ��Lambda���ʽ
	public void testLClass() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		TreeSet<Integer>ts = new TreeSet<>(com);
	}
	
	//����:��ȡ��ǰ�����������32��Ա����Ϣ
	List<Empoyee>empoyees = Arrays.asList(
			new Empoyee(14, "����"),
			new Empoyee(37, "����"),
			new Empoyee(36, "����"),
			new Empoyee(35, "����")
			);
	public void test3() {
		//��ȡ�������35
		List<Empoyee>list = filterEmpoyees(empoyees);
		for(Empoyee empoyee : list) {
			System.out.println(empoyee);
		}
		//��ȡ���ʴ���5000
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
	

	
	//����:��ȡ��ǰ�����У����ʴ���5000��Ա����Ϣ
	public List<Empoyee> salartfilter(List<Empoyee> list) {
		List<Empoyee>empoyees = new ArrayList<>();
		for(Empoyee empoyee : empoyees) {
			if(empoyee.getSalary() >= 5000) {
				empoyees.add(empoyee);
			}
		}
		return empoyees;
	}
	
	
	//�Ż���ʽһ���������ģʽ
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
	
	//�Ż���ʽ���������ڲ���
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
	//�Ż���ʽ��:lambda���ʽ
	public void test6() {
		List<Empoyee> list = filterEmployees(empoyees, (e) -> e.getAge() >= 16);
		list.forEach(System.out::println);
		List<Empoyee> list2 = filterEmployees(empoyees, (e) -> e.getSalary() >= 3500);
		list.forEach(System.out::println);
	}
	//�Ż���ʽ��:predict�﷨
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
	
	
	//ʵ���������ֵļӼ��˳�����
	
	public void test8() {
		System.out.println(opxy(100, 1000, (x, y)-> x + y));
	}
	
	public int opxy(int x, int y, Opration op) {
		return op.oprt(x, y);
	}
	
	//����Collection.sort()������ͨ����������Ƚ�����Employee���Ȱ�ס������ȣ�������ͬ���������ȣ���ʹ��Lambda�ӿ���Ϊ��������
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
	
	
	//дһ���������ӿ���Ϊ������ʹ���ַ�����ת��Ϊ��д
	public void test10() {
		String s = "fdafda,FDAEFA,AD";
		//ת���ɴ�д
		System.out.println(oprateSt(s, (x) -> x.toUpperCase()));
		//ת����Сд
		System.out.println(oprateSt(s, (x) -> x.toLowerCase()));
		//ͨ�����ŷֿ�
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
				 if(chars[i] == '��') {
					 if(chars[i + 1] == '��') {
						 return false;
					 }
				 }
			 }
			 return true;
		};
		String s = "������Ĳ�����";
		if(filter.test(s)) {
			System.out.println("��ȫ");
		}else {
			System.out.println("����ȫ");
		}
		
	}
	
	
		
}
