package Thread;

public class TestSyn2 implements Runnable{
	
	
	private int b = 100;
	public synchronized void m1() throws InterruptedException {
		b = 1000;
		Thread.sleep(3000);//如果不锁住m2，那么就会被改变b
		System.out.println("m1.b = " + b);
	}
	public int getb() {
		return b;
	}
	public synchronized void m2() throws InterruptedException{
		b = 2000;
		System.out.println("m2.b = "+b);
	}
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		TestSyn2 ee = new TestSyn2();
		Thread tt = new Thread(ee);
		tt.start();//执行tt线程.
		Thread.sleep(1000);//让线程tt先执行
		ee.m2();//执行主线程
		System.out.println(ee.getb());//等于1000或者等于2000取决于tt和这个方法谁先
	}
	public void run() {
		try {
			m1();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

//class m1 implements Runnable {
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//}
//
//class m2 implements Runnable{
//	public void run() {
//		
//	}
//	}
//}