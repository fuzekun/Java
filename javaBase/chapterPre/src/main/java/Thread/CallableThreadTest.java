package Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
public class CallableThreadTest extends Thread implements Callable<Integer> {//����Callable �ӿڵ�ʵ��
	
	public static void main(String[] args) {
		CallableThreadTest ctt = new CallableThreadTest();
		FutureTask<Integer>ft = new FutureTask<>(ctt);
		for(int i = 0;i < 100;i++)
		{
			System.out.println(Thread.currentThread().getName() + "ѭ������i��ֵ" + i);
			if(i == 20)
			{
				new Thread(ft,"�з���ֵ���߳�").start();
			}
		}
		// TODO Auto-generated method stub
		try
		{
			System.out.println("���̵߳ķ���ֵ��" + ft.get());
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("�������");
		}
	}

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return 1;
	}
}
