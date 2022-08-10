package Lamba;

public class Empoyee {
	int age;
	String name;
	int salary;
	public Empoyee(int age, String name) {
		this.age = age;
		this.name = name;
		this.salary = (int)(Math.random() * 10000);
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getSalary() {
		return salary;
	}
	@Override
	public String toString() {
		return "Empoyee [age=" + age + ", name=" + name + ", salary=" + salary + "]";
	}
	

	
}
