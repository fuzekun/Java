package Thread;

public class TestSync implements Runnable{

	private Timer timer = new Timer();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestSync test = new TestSync();
		Thread thread1 = new Thread(test);
		Thread thread2 = new Thread(test);
		thread1.setName("t1");
		thread2.setName("t2");
		thread1.start();
		thread2.start();
	}
	public void run() {
		timer.add(Thread.currentThread().getName());
	}

}
class Timer {
	
	private static int num = 0;
	private boolean flag = true;
	public void add(String s) {
		synchronized (this) {
			num++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(s +"ÄãÊÇ"+num+"¸ö");
		}
	}
	public void shutdown() {
		flag = false;
	}
	
}