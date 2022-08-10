package Thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BlockingQueue {
	private static ArrayBlockingQueue<Integer>buffer = new ArrayBlockingQueue<>(2);
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new Porducer());
		executor.execute(new Customer());
		executor.shutdown();
	}
	
	private static class Porducer implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				int i = 1;
				while(true) {
					System.out.println("write " + i);
					buffer.put(i++);
					Thread.sleep((int)Math.random() * 1000);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	private static class Customer implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true) {
					System.out.println("read "+ buffer.take());
					Thread.sleep((int)Math.random()*1000);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
