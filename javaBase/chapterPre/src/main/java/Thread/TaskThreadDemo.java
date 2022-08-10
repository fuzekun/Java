package Thread;


public class TaskThreadDemo implements Runnable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		TaskThreadDemo taskThreadDemo = new TaskThreadDemo();
		//创建任务
		Runnable printA = new PrintChar('a', 100);
		Runnable printB = new PrintChar('b', 100);
		Runnable printN = new PrintNum(100);
		
		//创建线程
		Thread thread1 = new Thread(printA);
		Thread thread2 = new Thread(printB);
		Thread thread3 = new Thread(printN);
		//thread1.setPriority(Thread.NORM_PRIORITY + 3);
		//开启线程//如果改成了run就会出现不并发的现象。
		thread3.start();
		thread1.start();
		thread2.start();
		((PrintNum) printN).shutdown();
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Test");
	}
	/*public TaskThreadDemo() {
		TaskThreadDemo tast = new TaskThreadDemo();
	}*/
	public TaskThreadDemo() {
		Thread t = new Thread(this);
		t.start();
	}
}


//打印字符
class PrintChar implements Runnable{
	private char charToPrint;
	private int times;
	public PrintChar(char c,int t) {
		charToPrint = c;
		times = t;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0;i < times;i++) {
			System.out.print(charToPrint + 
					" ");
			if((i + 1) % 10 == 0)System.out.println();
		}
	}
}

//打印数字1-num
class PrintNum implements Runnable{
	private int num;
	private boolean flag = true;
	public PrintNum(int n) {
		num = n;
	}
	public void shutdown() {
		flag = false;
	}
	public void run(){
		// TODO Auto-generated method stub
//		for(int i = 0; i < num;i++) {
//			System.out.print(" " + i);
//			if((i + 1) % 10 == 0)System.out.println();
//			try {
//				Thread.sleep(100);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
		while(flag) {
			System.out.print("c ");
		}
	}
}
