package SomeDo;
import java.awt.*;
import javax.swing.*;
public class ballgame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image ball = Toolkit.getDefaultToolkit().getImage("Images/ball.jpg");
	Image desk = Toolkit.getDefaultToolkit().getImage("Images/desk.png");
	
	int x = 100;//��ʼС���λ��
	int y = 100;
	
	int rspeed = 6;
	int dspeed = 8;
	
	//�����ڵķ���
	public void paint(Graphics g) {
		g.drawImage(desk,40,40,null);
		g.drawImage(ball,x,y,null);
		//�����ƶ�
		x += rspeed;
		if(x > 670 || x < 80)rspeed = -rspeed;
		//�����ƶ�
		y += dspeed;
		if(y < 80 || y > 340)dspeed = -dspeed;
		
	}
	
	//���ڼ���
	void lanchFrame() {
		setSize(856,450);
		setLocation(50,50);
		setVisible(true);
		//�ػ�����
		while(true) {
			repaint();					//�൱�ڵ���paint;
			try {
				Thread.sleep(40);//1s��20��
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	//main�����ǳ���ִ�е����
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���ڼ���
		ballgame game = new ballgame();
		game.lanchFrame();
	}
}
