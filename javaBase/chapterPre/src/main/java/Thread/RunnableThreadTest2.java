package Thread;

//多线程系统的调用
public class RunnableThreadTest2 extends Thread implements Runnable{
	int i = 0;
	public void run()
	{
		for(int i = 0;i < 100; i++)
		{
			System.out.println(getName() + " " + i);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunnableThreadTest2 rtt = new RunnableThreadTest2();//实例化
		new Thread(rtt,"新线程1").start();				  //启动线程1
		new Thread(rtt,"新线程2").start();				  //启动新线程2；
	}
}
