package SomeDo;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Runnable;
public class SleepMethodTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private Thread t;
	//������ɫ����
	private static Color[] color = {Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,
	Color.ORANGE,Color.YELLOW,Color.RED,Color.PINK,Color.LIGHT_GRAY};
	
	private static final Random rand = new Random(); 							//�����������
	private static Color getC(){												//��ȡ�����ɫֵ�ķ���
		//�������һ��color���鳤�ȷ�Χ�ڵ����֣��Դ���Ϊ������ȡ��ɫ
		return color[rand.nextInt(color.length)];
	}
	public SleepMethodTest(){
		t = new Thread(new Draw());												//���������̶߳���
		t.start();																//�����߳�
	}
	
	
	class Draw implements Runnable{//�����ڲ��� �࣬�����ڴ����л�������
		int x = 3000;
		int y = 5000;
		public void run() {
			// TODO Auto-generated method stub
			while(true) {														//����ѭ��
				try {
					Thread.sleep(100);											//����0.1�� 
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				Graphics graphics = getGraphics();
				graphics.setColor(getC());												//���û�ͼ��ɫ
				graphics.drawLine(x,y,100,y++);
					if(y >= 800) {
						y = 500;
					}
				}
			}
		}
		//��ȡ�齨��ͼ�����Ķ���
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init(new SleepMethodTest(),100,100);
	}
	//��ʼ���������ķ���
	public static void init(JFrame frame,int width,int height) {		//��һ���ǲ�����ƴд���󣬵ڶ���Ƕ�׹�ϵӦ�������в���
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		
	}
}
