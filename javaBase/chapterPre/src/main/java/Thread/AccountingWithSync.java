package Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AccountingWithSync {
	private static SafeAccount account = new SafeAccount();
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0;i < 100;i++) {
			executor.execute(new add());
		}
		//关闭很有必要啊
		executor.shutdown();
		//在没完成之前不会输出，很强!
		while(!executor.isTerminated()) {
		}
		System.out.println(account.getBalance());
		
	}
	private static class add implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			account.deposite(1);
		}
	}
}

class SafeAccount implements Accout{

	private static Lock lock = new ReentrantLock();//加锁
	int balance = 0;
	@Override
	public void deposite(int amount) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			int newBalance = balance + 1;
			Thread.sleep(10);
			balance = newBalance;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	@Override
	public int getBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
}