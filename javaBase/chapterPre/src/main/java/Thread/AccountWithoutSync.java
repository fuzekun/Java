package Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * 测试synchronized的强大威力
 * 
 * */
public class AccountWithoutSync {
	
	//这个可以换成很多，这个是这个文件外面的类
	private static Account2 account = new Account2();
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool(); 
		
		
		for(int i = 0;i < 100;i++) {
			executor.execute(new Add());
		}
		executor.shutdown();
		
		while(!executor.isTerminated()) {
		}
		System.out.println(account.getBalance());
	}
	private static class Add implements Runnable{
		public void run() {
			account.deposite(1);
		}
	}
}

class SafeAccount1 implements Accout{
	private int balance = 0;


	@Override
	public void deposite(int amount) {
		// TODO Auto-generated method stub
		synchronized (this) {
			int newBalance = balance + amount;
			try {
				Thread.sleep(3);
			}catch (InterruptedException e) {
				// TODO: handle exception
			}
			balance = newBalance;
		}
	}

	@Override
	public int getBalance() {
		// TODO Auto-generated method stub
		return balance;
	}
}