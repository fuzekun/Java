package SomeDo;
import java.awt.*;
import javax.swing.*;

public class ballgame2 extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//����ͼƬ
	Image ball = Toolkit.getDefaultToolkit().getImage("Images/ball.jpg");
	Image desk = Toolkit.getDefaultToolkit().getImage("Images/desk.png");
	
	//����С��ĳ�ʼ����
	double x = 100;
	double y = 100;
	
	//�����ƶ��ķ���
	double degree = 3.14/3;
	
	//��ͼƬ
	public void paint(Graphics g) {
		System.out.println("��ͼƬ");
		g.drawImage(desk,40,40,null);
		g.drawImage(ball,(int)x,(int)y,null);
		x += 10 * Math.cos(degree);
		y -= 10 * Math.sin(degree);
		if(x < 80 || x > 670) {
			degree = 3.14 - degree;
		}
		if(y > 340 || y < 80) {
			degree = -degree;
		}
	}
	
	//�򿪴���
	public void lanchframe() {
		setSize(856,400);
		setLocation(100,100);
		setVisible(true);
		while(true) {
			try{
				Thread.sleep(40);
			}catch(Exception e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ballgame2 game = new ballgame2();
		game.lanchframe();
	}

}
