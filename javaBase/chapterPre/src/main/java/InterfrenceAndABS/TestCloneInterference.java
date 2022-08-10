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
            TestCloneInterference bean = (TestCloneInterference) o;//O��object��������Ҫת��������
            return bean.getA().equals(a);
        }
        return false;
    }
    public void print() {
		System.out.println(a);
	}
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Bean bean = new Bean("����һ����·��");
		Bean clonebean = (Bean)(bean.clone());
		clonebean.print();
		System.out.println("����ʵ���ĳ�Ա��������ȵ���?"+clonebean.equals(bean));
	}

}
class Bean extends TestCloneInterference{
	public Bean(String s) {
		// TODO Auto-generated constructor stub
		super(s);
	}
}