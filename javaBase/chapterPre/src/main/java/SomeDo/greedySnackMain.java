package SomeDo;

//��greedySnackMain

import javax.swing.JFrame;


public class greedySnackMain extends JFrame {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//������һ����
	snackWin snackwin;
  
  //�Ի���ĳ�ʼ��
  static final int Width = 900 , Height = 700 , LocX = 400 , LocY = 80;
  
  public greedySnackMain() {
      super("GreedySncak_SL");
      
      //������һ����
      snackwin = new snackWin();
      
      //�ڶԻ����м�����һ����
      add(snackwin);
      
      //�Ի������Ϣ����
      this.setSize(Width, Height);	//Ϊ�Ի������ӳ���
      this.setVisible(true);    	//�Ի������
      this.setLocation(LocX, LocY); //�Ի����λ��
      //snackwin.requestFocus();
  }
  
  //������
  public static void main(String[] args) {
	  
	  //ֱ�Ӵ����Ϳ�����,�ò������е���Ϣ
      new greedySnackMain();
  }
}
