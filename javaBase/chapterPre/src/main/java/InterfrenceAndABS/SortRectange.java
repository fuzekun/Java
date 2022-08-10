package InterfrenceAndABS;

public class SortRectange {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComparableRectange[] rectanges = {
			new ComparableRectange(3.05,4.5),
			new ComparableRectange(3.6,4.5),
			new ComparableRectange(3.3,4.5)
		};
		java.util.Arrays.sort(rectanges);
		for(int i = 0;i < rectanges.length;i++) {
			System.out.println(rectanges[i]);
		}
		
		Integer n1 = new Integer(3);
		Object n2 = new Integer(4);
		System.out.println(n1.compareTo((Integer)n2));
		
		Person[] persons = {
				new Person(1),
				new Person(4),
				new Person(3),
				new Person(23)
				
		};
		java.util.Arrays.sort(persons);
		for(int i = 0;i < persons.length;i++) {
			System.out.println(persons[i]);
		}
	}
}
class Person implements Comparable<Person>{
	private int id;

	public Person(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		if(getId() > o.getId())return 1;
		else if(getId() == o.getId()) return 0;
		else return -1;
	}
	
}
