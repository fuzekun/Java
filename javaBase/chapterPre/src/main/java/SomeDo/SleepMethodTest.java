package SomeDo;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Runnable;
public class SleepMethodTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private Thread t;
	//定义颜色数组
	private static Color[] color = {Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,
	Color.ORANGE,Color.YELLOW,Color.RED,Color.PINK,Color.LIGHT_GRAY};
	
	private static final Random rand = new Random(); 							//创建随机对象
	private static Color getC(){												//获取随机颜色值的方法
		//随机产生一个color数组长度范围内的数字，以此作为索引获取颜色
		return color[rand.nextInt(color.length)];
	}
	public SleepMethodTest(){
		t = new Thread(new Draw());												//创建匿名线程对象
		t.start();																//启动线程
	}
	
	
	class Draw implements Runnable{//定义内部的 类，用来在窗体中绘制线条
		int x = 3000;
		int y = 5000;
		public void run() {
			// TODO Auto-generated method stub
			while(true) {														//无限循环
				try {
					Thread.sleep(100);											//休眠0.1秒 
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				Graphics graphics = getGraphics();
				graphics.setColor(getC());												//设置绘图颜色
				graphics.drawLine(x,y,100,y++);
					if(y >= 800) {
						y = 500;
					}
				}
			}
		}
		//获取组建绘图上下文对象
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init(new SleepMethodTest(),100,100);
	}
	//初始化程序界面的方法
	public static void init(JFrame frame,int width,int height) {		//第一个是不是有拼写错误，第二个嵌套关系应该在类中不是
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		
	}
}
