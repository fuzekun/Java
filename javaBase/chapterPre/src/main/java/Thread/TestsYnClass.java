package Thread;

public class TestsYnClass{
	private static Article article = new Article("");
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Write write = new Write(article, "fdd");
		new Thread(write).start(); 
		new Thread(new Read(article)).start();
		
	}
	private static class Article{
		String message = null;
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
	private static class Read implements Runnable{
		private Article article = null;
		public Read(Article article) {
			// TODO Auto-generated constructor stub
			this.article = article;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("正在读取信息.....");
			System.out.println("信息是:" + article.getMessage());
		}
	}
	
	private static class Write implements Runnable{
		private Article article;
		private String s;
		public Write(Article article,String s) {
			// TODO Auto-generated constructor stub
			this.article = article;
			this.s = s;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				synchronized (article) {
					System.out.println("睡眠一秒");
					Thread.sleep(1000);
					System.out.println("睡眠完成,如果在这之前没被读就成功!!!");
					article.setMessage(s);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
