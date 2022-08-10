package Thread;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testS {
	private static Article article = new Article();
	
	public static void main(String[] args) {
		
	}
	
	private static class Buffer{
		private static Article article = new Article();
		public Buffer(Article article) {
			// TODO Auto-generated constructor stub
			this.article = article;
		}
		
		public static void read() {
			System.out.println(article.getId());
		}
		public static void write() {
			try {
				synchronized (article) {
					System.out.println("�߳�˯��");
					Thread.sleep(1000);
					System.out.println("˯�����");	
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}
	private static class Article{
		int id;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	}
}