package Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*读者写者问题
 * 算法思路，读是一个同步的问题
 * 读和写是互斥的
 * 1.如果读者的数量不为零就不需要通知写者
 * 2.如果读完了就通知读者
 * 错误了，只有一个能读
 * 
 * */
public class ReadAndWrite {
	private static Article article = new Article("");
	private static Buffer buffer = new Buffer();
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ExecutorService  executor = Executors.newCachedThreadPool();
		executor.execute(new Write("f"));
		executor.execute(new Write("d"));
		executor.execute(new Reader());
		executor.execute(new Reader());
		executor.shutdown();
	}
	
	private static class Reader implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				buffer.read();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	private static class Write implements Runnable{
		String s = null;
		public Write(String s) {
			// TODO Auto-generated constructor stub
			this.s = s;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				buffer.write(s);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	private static class Article{
		String message;
		public Article(String meesage) {
			// TODO Auto-generated constructor stub
			this.message = meesage;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message += message;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}
	}
	private static class Buffer{
		int readNum = 0;
		public synchronized void read() throws InterruptedException {
			readNum++;
			System.out.println("读者正在读" + article.getMessage());
			Thread.sleep(1000);
			readNum--;
			if(readNum == 0)this.notify();
		}
		public synchronized void write(String s){
			try {
				while(readNum != 0) {
					this.wait();
				}
				synchronized (article) {
					System.out.println("睡眠一秒");
					Thread.sleep(1000);
					System.out.println("睡眠完成,如果在这之前没被写就成功!!!");
					article.setMessage(s);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		     }
		}
	}
}
