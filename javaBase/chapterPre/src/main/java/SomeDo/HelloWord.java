package SomeDo;
import java.awt.Container;
import java.net.URL;
import javax.swing.*;

public class HelloWord extends JFrame{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		// TODO Auto-generated method stub
		private JLabel jl = new JLabel();			//����JLabel����
		private static Thread t;					//�����̶߳���
		private int x = 0;							//�����ɱ仯�ĺ�����
		private Container container = getContentPane();//��������
		
		public HelloWord() {
			setBounds(0,0,2500,1000);				//���Զ�λ����Ĵ�С��λ��
			container.setLayout(null);				//ʹ���岻ʹ���κβ��ֹ�����
			try {
				//URL url=   Thread.currentThread().getContextClassLoader().getResource("SunRise.jpg");

				//��ȡ����ͬĿ¼�� ͼƬ��URL
				URL url = HelloWord.class.getResource("SunRise.jpg");
				Icon icon = new ImageIcon(url);		//ʵ����һ��Icon
				jl.setIcon(icon);  					//��ͼ������ڱ�ǩ��
			}catch(NullPointerException ex) {		//��׽��ָ���쳣
				System.out.println("ͼƬ�����ڣ��뽫SunRise.jpg��������ǰĿ¼��");
				return;								//��������
			}
			jl.setBounds(2000,50,2000,900);					//���ñ�ǩ�Ĵ�С��λ��
			t = new Thread(new Roll());
			t.start();									//�����߳�
			container.add(jl);							//����ǩ��ӵ�������
			setVisible(true);							//ʹ����ɼ�
			setDefaultCloseOperation(EXIT_ON_CLOSE);	//���ô���Ĺرշ�ʽ
		}
		
		class Roll implements Runnable{					//�����ڲ��࣬ʵ��Runnale�ӿ�
			public void run() {
				while(x >= -1100) {								//����ѭ������
					//����ǩ�ĺ������ñ�����ʾ
					jl.setBounds(x,0,2000,1000);
					try {
						Thread.sleep(10);				//ʹ�߳�����0.5��
					}catch(Exception e) {
						e.printStackTrace();
					}
					x -= 4;								//ʹ������ÿ������4
					if(x <= -1000) {
						x = 2000; 						//��ͼ�굽���ǩ���Ҷ�ʱ��ʹ��ص���ǩ�����
					}
				}
			}
		}
		
		public static void main(String[] args) {
			new HelloWord();						//ʵ����һ��SwingAndThread����
		}
}