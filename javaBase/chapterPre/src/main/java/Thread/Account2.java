package Thread;

import java.util.concurrent.Semaphore;

public class Account2 {
	private static Semaphore semaphore = new Semaphore(1);
	private int balance = 0;
	
	public int getBalance() {
		return balance;
	}
	
	public  void  deposite(int amount) {
		try {
			semaphore.acquire();
			int newBalance = balance + amount;
			Thread.sleep(10);
			balance = newBalance;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			semaphore.release();
		}
		
	}
}
