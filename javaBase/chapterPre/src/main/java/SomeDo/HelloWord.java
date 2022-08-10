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
		private JLabel jl = new JLabel();			//声明JLabel对象
		private static Thread t;					//声明线程对象
		private int x = 0;							//声明可变化的横坐标
		private Container container = getContentPane();//生命容器
		
		public HelloWord() {
			setBounds(0,0,2500,1000);				//绝对定位窗体的大小与位置
			container.setLayout(null);				//使窗体不使用任何布局管理器
			try {
				//URL url=   Thread.currentThread().getContextClassLoader().getResource("SunRise.jpg");

				//获取本类同目录下 图片的URL
				URL url = HelloWord.class.getResource("SunRise.jpg");
				Icon icon = new ImageIcon(url);		//实例化一个Icon
				jl.setIcon(icon);  					//将图标放置在标签中
			}catch(NullPointerException ex) {		//捕捉空指针异常
				System.out.println("图片不存在，请将SunRise.jpg拷贝到当前目录下");
				return;								//结束方法
			}
			jl.setBounds(2000,50,2000,900);					//设置标签的大小与位置
			t = new Thread(new Roll());
			t.start();									//启动线程
			container.add(jl);							//将标签添加到容器中
			setVisible(true);							//使窗体可见
			setDefaultCloseOperation(EXIT_ON_CLOSE);	//设置窗体的关闭方式
		}
		
		class Roll implements Runnable{					//定义内部类，实现Runnale接口
			public void run() {
				while(x >= -1100) {								//设置循环条件
					//将标签的横坐标用变量表示
					jl.setBounds(x,0,2000,1000);
					try {
						Thread.sleep(10);				//使线程休眠0.5秒
					}catch(Exception e) {
						e.printStackTrace();
					}
					x -= 4;								//使横坐标每次增加4
					if(x <= -1000) {
						x = 2000; 						//当图标到达标签最右端时，使其回到标签最左端
					}
				}
			}
		}
		
		public static void main(String[] args) {
			new HelloWord();						//实例化一个SwingAndThread对象
		}
}