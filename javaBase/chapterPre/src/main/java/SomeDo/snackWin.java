package SomeDo;

//类snackWin

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;//引入button
import javax.swing.JPanel;

public class snackWin extends JPanel implements ActionListener, KeyListener {
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//初始化蛇棋盘的信息
  static final int Up = 0 , Down = 1 , Left = 2 , Right = 3;
  //边距，长宽
  static final int GameLocX = 50 , GameLocY = 50 , GameWidth = 700 , GameHeight = 500 , Size = 10;
  static int rx , ry , score = 0 , speed = 0;
  
  //控制信息
  boolean startFlag = false;
  JButton startButton , stopButton , quitButton; 
 
  //声明一条蛇
  Snack snack;
  
  //构造函数
  public snackWin() {
	  
	  //蛇的创建
	  snack = new Snack();
	  
	  //产生食物
      rx = (int)(Math.random() * (GameWidth - 10) + GameLocX);//相当于[50，690]取随机数
      ry = (int)(Math.random() * (GameHeight - 10) + GameLocY);//同理
      
      //控制键的创建并命名
      startButton = new JButton("开始");//JButton中自带的startButton;
      stopButton = new JButton("暂停");
      quitButton = new JButton("退出");
      
      //控制键的位置
      setLayout(new FlowLayout(FlowLayout.LEFT));
      
      //控制键的定义
      this.add(startButton);
      this.add(stopButton);
      this.add(quitButton);
      startButton.addActionListener(this);
      stopButton.addActionListener(this);
      quitButton.addActionListener(this);
      this.addKeyListener(this);
      
      //创建一个蛇线程并调用
      new Thread(new snackThread()).start();  
  }
  
  //将图像先是给用户
  public void paint(Graphics g)
  {
	  //界面
      super.paint(g);   		//没有会将button刷掉
      g.setColor(Color.white);  //界面的颜色
      g.fillRect(GameLocX, GameLocY, GameWidth, GameHeight);  //刷新界面
      g.setColor(Color.black);
      g.drawRect(GameLocX, GameLocY, GameWidth, GameHeight);  //绘制边界
      g.drawString("Score: " + score + "        Speed: " + speed + "      速度最大为100" , 250, 25);
      
      //食物
      g.setColor(Color.green);
      g.fillRect(rx, ry, 9, 9);   //食物
      
      //蛇
      snack.draw(g);
  }
  
  //控制蛇的行为
  public void actionPerformed(ActionEvent e) {
	  
	  //开始
      if(e.getSource() == startButton) {
          startFlag = true;
          startButton.setEnabled(false);
          stopButton.setEnabled(true);
      }
      
      //暂停
      if(e.getSource() == stopButton) {
          startFlag = false;
          startButton.setEnabled(true);
          stopButton.setEnabled(false);
      }
      
      //结束
      if(e.getSource() == quitButton) {
          System.exit(0);
      }
      
      //不知道是什么
      this.requestFocus();
  }
  
  //按键就回移动
  public void keyPressed(KeyEvent e) {
      //System.out.println(e.getKeyCode()); 
      if(!startFlag) return ;
      
      //方向
      switch(e.getKeyCode()) {
      case KeyEvent.VK_UP:
          if( snack.getDir() == Down) break;//如果是向下的就不改变
          snack.changeDir(Up);
          break;
      case KeyEvent.VK_DOWN:
          if( snack.getDir() == Up) break;
          snack.changeDir(Down);
          break;
      case KeyEvent.VK_LEFT:
          if( snack.getDir() == Right) break;
          snack.changeDir(Left);
          break;
      case KeyEvent.VK_RIGHT:
          if(snack.length() != 1 && snack.getDir() == Left) break;//因为snake 的长度永远大于1所以不需要加上这一句
          snack.changeDir(Right);
          break;
      }
  }
  
  
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  
  //类中的类是什么操作?
  class snackThread implements Runnable
  {
      public void run() {
          while(true) {
              try {
                  Thread.sleep(100 - speed >= 0 ? 100 - speed : 0);
                  repaint();
                  if(startFlag) {
                      snack.move();
                  }
              }
              catch(InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  }
}