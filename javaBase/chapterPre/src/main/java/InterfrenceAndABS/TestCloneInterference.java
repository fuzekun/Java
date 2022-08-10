package InterfrenceAndABS;

public class TestCloneInterference implements Cloneable{
	private String a;
	 
	public TestCloneInterference(String a) {
		// TODO Auto-generated constructor stub
		this.a = a;
	}
    public String getA() {
        return a;
    }
 
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object o) {
        if(o instanceof TestCloneInterference){
            TestCloneInterference bean = (TestCloneInterference) o;//O是object类所以需要转化成子类
            return bean.getA().equals(a);
        }
        return false;
    }
    public void print() {
		System.out.println(a);
	}
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Bean bean = new Bean("这是一条天路啊");
		Bean clonebean = (Bean)(bean.clone());
		clonebean.print();
		System.out.println("两个实例的成员变量是相等的吗?"+clonebean.equals(bean));
	}

}
class Bean extends TestCloneInterference{
	public Bean(String s) {
		// TODO Auto-generated constructor stub
		super(s);
	}
}