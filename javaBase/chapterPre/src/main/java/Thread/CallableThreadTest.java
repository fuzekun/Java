package Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
public class CallableThreadTest extends Thread implements Callable<Integer> {//创建Callable 接口的实现
	
	public static void main(String[] args) {
		CallableThreadTest ctt = new CallableThreadTest();
		FutureTask<Integer>ft = new FutureTask<>(ctt);
		for(int i = 0;i < 100;i++)
		{
			System.out.println(Thread.currentThread().getName() + "循环变量i的值" + i);
			if(i == 20)
			{
				new Thread(ft,"有返回值的线程").start();
			}
		}
		// TODO Auto-generated method stub
		try
		{
			System.out.println("子线程的返回值：" + ft.get());
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("程序完成");
		}
	}

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return 1;
	}
}
