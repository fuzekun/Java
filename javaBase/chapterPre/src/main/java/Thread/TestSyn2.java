package Thread;

public class TestSyn2 implements Runnable{
	
	
	private int b = 100;
	public synchronized void m1() throws InterruptedException {
		b = 1000;
		Thread.sleep(3000);//�������סm2����ô�ͻᱻ�ı�b
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
		tt.start();//ִ��tt�߳�.
		Thread.sleep(1000);//���߳�tt��ִ��
		ee.m2();//ִ�����߳�
		System.out.println(ee.getb());//����1000���ߵ���2000ȡ����tt���������˭��
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