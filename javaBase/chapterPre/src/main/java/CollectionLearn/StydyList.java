package CollectionLearn;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

public class StydyList {
	
	public static void main(String[] args) {
		testListIt();
	}

	public static void testListIt() {
		LinkedList<Integer>linkedList = new LinkedList<>();
		ListIterator<Integer>iterator = linkedList.listIterator();
		int a = 10;
		while(a != 0) {
			int c = (int)(Math.random() * 20 + 1);
			iterator.add(c);
			a--;
		}
		//����ָ��λ��
		iterator = linkedList.listIterator();
		while(iterator.hasNext()) {
			System.out.print(iterator.next()+" ");
		}
		System.out.println("");
		iterator.set(9);
		System.out.println(linkedList.getFirst());
		System.out.println(linkedList.getLast());
		while(iterator.hasPrevious()) {
			System.out.print(iterator.previous()+" ");
		}
		System.out.println();
		iterator.set(9);
		System.out.println(linkedList.getFirst());
		System.out.println(linkedList.getLast());
		String s = "Fda";
		String sub = s.substring(2,3);//���ұ�
		System.out.println(sub);
	}
	public static void testIterable() {
		Set<String>set = new TreeSet<>();
		set.add("e");
		set.add("f");
		Iterator<String>it = set.iterator();
		while(it.hasNext()) {
			System.out.println(it.next() + " ");
		}
	}
	@SuppressWarnings("unchecked")
	public  static void testArrayList() {
		ArrayList<String>arrayList = new ArrayList<>();
		ArrayList<String>arrayList2 = new ArrayList<>();
		
		for(int i = 0;i < 10;i++)arrayList.add("��");
		for(int i = 0;i < 10;i++)arrayList.add("ɾ");
		for(int i = 0;i < 8;i++)arrayList2.add("ɾ");
		ArrayList<String>clonearray = (ArrayList<String>)arrayList.clone();//ArrayList�ɿ�¡
		//���ϵĲ���
		clonearray.addAll(arrayList2);
		System.out.println("���ϲ���:");
		for(String i : clonearray) {
			System.out.print(i + " ");
		}
		System.out.println();
		clonearray = (ArrayList<String>)arrayList.clone();
		//���ϵĽ���
		clonearray.retainAll(arrayList2);
		System.out.println("���Ͻ���:");
		for(String i : clonearray) {
			System.out.print(i + " ");
		}
		System.out.println();
		//���ϵĲ
		System.out.println("���� �Ĳ:");
		clonearray = (ArrayList<String>)arrayList.clone();
		clonearray.removeAll(arrayList2);
		for(String i : clonearray) {
			System.out.print(i + " ");
		}
	}
}
