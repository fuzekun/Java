package CollectionLearn;

import java.util.HashSet;
import java.util.Iterator;

/*用来实现可以克隆的set
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
			System.out.println("没有这号");
		}
		CloneSet<String>coloneSet = (CloneSet<String>) set.clone();
		System.out.println(coloneSet);
		Iterator<String>iterable = set.iterator();
		while(iterable.hasNext()) {
			System.out.println(iterable.next());
		}
	}
}
