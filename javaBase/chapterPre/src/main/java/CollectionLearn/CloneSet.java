package CollectionLearn;

import java.util.HashSet;
import java.util.Iterator;

/*����ʵ�ֿ��Կ�¡��set
 * 
 * 
 * */
public class CloneSet<E> extends HashSet<E> implements Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws CloneNotSupportedException {
		CloneSet<String>set = new CloneSet<>();
		set.add("fda");
		if(set.remove("sda")) {
			System.out.println("û�����");
		}
		CloneSet<String>coloneSet = (CloneSet<String>) set.clone();
		System.out.println(coloneSet);
		Iterator<String>iterable = set.iterator();
		while(iterable.hasNext()) {
			System.out.println(iterable.next());
		}
	}
}
