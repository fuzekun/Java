package Thread;

//���߳�ϵͳ�ĵ���
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
		RunnableThreadTest2 rtt = new RunnableThreadTest2();//ʵ����
		new Thread(rtt,"���߳�1").start();				  //�����߳�1
		new Thread(rtt,"���߳�2").start();				  //�������߳�2��
	}
}
