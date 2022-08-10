package Thread;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustemerAndP {
	private static Buffer buffer = new Buffer();
	
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new ProducerTeask());
		executor.execute(new CustomerTask());
		executor.shutdown();
	}
	
	private static class ProducerTeask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int i = 1;
				while(true) {
					System.out.println("\t\t\tProducer writes" + i);
						buffer.write(i++);
						Thread.sleep((int)(Math.random() * 1000));
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	private static class CustomerTask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub	
			int i = 0;
			try {
				while(true) {
					System.out.println("\t\t\tCostumer reads" + buffer.read());
					Thread.sleep((int)Math.random()*1000);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	private static class Buffer{
		private static final int CAPACITY = 1;//容量
		private LinkedList<Integer>queue = new LinkedList<>();
		
		//创建一个新锁
		private static Lock lock = new ReentrantLock();
		
		//创建两种情况
		private static Condition notEmpty = lock.newCondition();
		private static Condition notFull = lock.newCondition();
		
		public void write(int value) {
			lock.lock();
			try {
				while(queue.size() == CAPACITY) {
					System.out.println("wait for not full condition");
					notFull.await();
				}
				queue.offer(value);
				notEmpty.signal();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
		public int read() {
			int value = 0;
			lock.lock();
			try {
				while(queue.size() == 0) {
					System.out.println("Wait for notEmpty condition");
					notEmpty.await();
				}
				value = queue.remove();
				notFull.signal();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				lock.unlock();
				return value;
			}
		}
		
	}
}