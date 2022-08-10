package Thread;

public class 线程 extends Thread{

	int i = 0;
	public void run()				//重写run方法，run方法的的方法体就是线程执行体
	{
		for(;i < 100;i++)
		{
			System.out.println(getName()  + i);
		}
	}
	private static String say = "我要学会你"; 
	public static void main(String[] args) {
		System.out .println("你好 java" + say);
		new 线程().start();		//实XX例化Tread 子类并启动线程
		
	}
}
