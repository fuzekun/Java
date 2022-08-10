package Class;

/*
public class OuterClass {
    public void display(){
        System.out.println("OuterClass...");
    }
    
    public class InnerClass{
        public OuterClass getOuterClass(){
            return OuterClass.this;
        }
    }
    
    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
        innerClass.getOuterClass().display();
    }
}*/



public class OuterClass {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Address address = new Address("beijign","wudaokou");
		OuterClass person = new OuterClass("fuzekun", 24, address);
		System.out.println(person);
	}
	@Override
	public String toString() {
		return "OuterClass [name=" + name + ", age=" + age + ", address=" + address + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public OuterClass(String name, int age, Address address) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
	}
	private String name;
	private int age;
	private Address address;
	
}
class Address{
	public Address(String city, String street) {
		super();
		this.city = city;
		this.street = street;
	}
	private String city;
	private String street;
	public String getCity() {
		return city;
	}
	@Override
	public String toString() {
		return "Address [city=" + city + ", street=" + street + "]";
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
}
