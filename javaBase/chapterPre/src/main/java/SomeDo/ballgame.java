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
	
	int x = 100;//初始小球的位置
	int y = 100;
	
	int rspeed = 6;
	int dspeed = 8;
	
	//画窗口的方法
	public void paint(Graphics g) {
		g.drawImage(desk,40,40,null);
		g.drawImage(ball,x,y,null);
		//向右移动
		x += rspeed;
		if(x > 670 || x < 80)rspeed = -rspeed;
		//向下移动
		y += dspeed;
		if(y < 80 || y > 340)dspeed = -dspeed;
		
	}
	
	//窗口加载
	void lanchFrame() {
		setSize(856,450);
		setLocation(50,50);
		setVisible(true);
		//重画窗口
		while(true) {
			repaint();					//相当于调用paint;
			try {
				Thread.sleep(40);//1s画20次
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	//main方法是程序执行的入口
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//窗口加载
		ballgame game = new ballgame();
		game.lanchFrame();
	}
}
