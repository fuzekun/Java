package Thread;

public class �߳� extends Thread{

	int i = 0;
	public void run()				//��дrun������run�����ĵķ���������߳�ִ����
	{
		for(;i < 100;i++)
		{
			System.out.println(getName()  + i);
		}
	}
	private static String say = "��Ҫѧ����"; 
	public static void main(String[] args) {
		System.out .println("��� java" + say);
		new �߳�().start();		//ʵXX����Tread ���ಢ�����߳�
		
	}
}
