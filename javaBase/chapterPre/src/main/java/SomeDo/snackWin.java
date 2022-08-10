package SomeDo;

//��snackWin

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;//����button
import javax.swing.JPanel;

public class snackWin extends JPanel implements ActionListener, KeyListener {
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//��ʼ�������̵���Ϣ
  static final int Up = 0 , Down = 1 , Left = 2 , Right = 3;
  //�߾࣬����
  static final int GameLocX = 50 , GameLocY = 50 , GameWidth = 700 , GameHeight = 500 , Size = 10;
  static int rx , ry , score = 0 , speed = 0;
  
  //������Ϣ
  boolean startFlag = false;
  JButton startButton , stopButton , quitButton; 
 
  //����һ����
  Snack snack;
  
  //���캯��
  public snackWin() {
	  
	  //�ߵĴ���
	  snack = new Snack();
	  
	  //����ʳ��
      rx = (int)(Math.random() * (GameWidth - 10) + GameLocX);//�൱��[50��690]ȡ�����
      ry = (int)(Math.random() * (GameHeight - 10) + GameLocY);//ͬ��
      
      //���Ƽ��Ĵ���������
      startButton = new JButton("��ʼ");//JButton���Դ���startButton;
      stopButton = new JButton("��ͣ");
      quitButton = new JButton("�˳�");
      
      //���Ƽ���λ��
      setLayout(new FlowLayout(FlowLayout.LEFT));
      
      //���Ƽ��Ķ���
      this.add(startButton);
      this.add(stopButton);
      this.add(quitButton);
      startButton.addActionListener(this);
      stopButton.addActionListener(this);
      quitButton.addActionListener(this);
      this.addKeyListener(this);
      
      //����һ�����̲߳�����
      new Thread(new snackThread()).start();  
  }
  
  //��ͼ�����Ǹ��û�
  public void paint(Graphics g)
  {
	  //����
      super.paint(g);   		//û�лὫbuttonˢ��
      g.setColor(Color.white);  //�������ɫ
      g.fillRect(GameLocX, GameLocY, GameWidth, GameHeight);  //ˢ�½���
      g.setColor(Color.black);
      g.drawRect(GameLocX, GameLocY, GameWidth, GameHeight);  //���Ʊ߽�
      g.drawString("Score: " + score + "        Speed: " + speed + "      �ٶ����Ϊ100" , 250, 25);
      
      //ʳ��
      g.setColor(Color.green);
      g.fillRect(rx, ry, 9, 9);   //ʳ��
      
      //��
      snack.draw(g);
  }
  
  //�����ߵ���Ϊ
  public void actionPerformed(ActionEvent e) {
	  
	  //��ʼ
      if(e.getSource() == startButton) {
          startFlag = true;
          startButton.setEnabled(false);
          stopButton.setEnabled(true);
      }
      
      //��ͣ
      if(e.getSource() == stopButton) {
          startFlag = false;
          startButton.setEnabled(true);
          stopButton.setEnabled(false);
      }
      
      //����
      if(e.getSource() == quitButton) {
          System.exit(0);
      }
      
      //��֪����ʲô
      this.requestFocus();
  }
  
  //�����ͻ��ƶ�
  public void keyPressed(KeyEvent e) {
      //System.out.println(e.getKeyCode()); 
      if(!startFlag) return ;
      
      //����
      switch(e.getKeyCode()) {
      case KeyEvent.VK_UP:
          if( snack.getDir() == Down) break;//��������µľͲ��ı�
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
          if(snack.length() != 1 && snack.getDir() == Left) break;//��Ϊsnake �ĳ�����Զ����1���Բ���Ҫ������һ��
          snack.changeDir(Right);
          break;
      }
  }
  
  
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  
  //���е�����ʲô����?
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