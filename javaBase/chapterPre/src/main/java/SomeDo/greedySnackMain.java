package SomeDo;

//类greedySnackMain

import javax.swing.JFrame;


public class greedySnackMain extends JFrame {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//声明这一个类
	snackWin snackwin;
  
  //对话框的初始化
  static final int Width = 900 , Height = 700 , LocX = 400 , LocY = 80;
  
  public greedySnackMain() {
      super("GreedySncak_SL");
      
      //创建这一个类
      snackwin = new snackWin();
      
      //在对话框中加入这一个类
      add(snackwin);
      
      //对话框的信息设置
      this.setSize(Width, Height);	//为对话框增加长宽
      this.setVisible(true);    	//对话框可视
      this.setLocation(LocX, LocY); //对话框的位置
      //snackwin.requestFocus();
  }
  
  //主函数
  public static void main(String[] args) {
	  
	  //直接创建就可以了,用不到其中的信息
      new greedySnackMain();
  }
}
