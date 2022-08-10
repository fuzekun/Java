package SomeDo;


//��snack
//��������
//�����൱����ͷ�ļ�
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;		//�����б�
import java.util.List;			//����

import javax.swing.JOptionPane;

class Node {
  private int x , y;
  public Node() {}										 //���캯��
  public Node(int a , int b) {x = a; y = b;}			 //���캯���Ķ�̬
  public Node(Node tmp) {x = tmp.getX(); y = tmp.getY();}//���캯���Ķ�̬
  int getX() {return x;}
  int getY() {return y;}
  void setX(int a) {x = a;}//����x
  void setY(int b) {y = b;}//����y
}
public class Snack {
  static final int DIR[][] = {{0 , -1} , {0 , 1} , {-1 , 0} , {1 , 0}};//�ߵķ���:�÷���������ʵ��,��������
  private List<Node> lt = new ArrayList<Node>();//
  private int curDir;
  
  //���캯��������һ����
  
  public Snack() {
      curDir = 3;				  //�ߵĳ�ʼ���������ұ�
      lt.add(new Node(350 , 250));//��ʼ������350��250������
      //��ʼ������������
      for(int i = 1;i <= 3;i++) {
    	  lt.add(new Node(350 - 10 * i,250));
      }
  }
  
  int length() {return lt.size();}  //��ȡ����
  int getDir() {return curDir;} 	//��ȡ����
  
  //���߱��ֳ���
  void draw(Graphics g) 
  {
      g.setColor(Color.red); 
      g.fillRect(lt.get(0).getX(),lt.get(0).getY(),9,9);
      g.setColor(Color.yellow);
      for(int i = 1; i < lt.size(); i++) {
          g.fillRect(lt.get(i).getX(), lt.get(i).getY(), 9, 9);
      }
  }
  
  //������Ψһ������ʽ:ͷײ����
  //���Ըĳ���ǽ�ڵ�
  boolean Dead() {
      for(int i = 1; i < lt.size(); i++) {
          if(lt.get(0).getX() == lt.get(i).getX() && lt.get(0).getY() == lt.get(i).getY()) 
              return true;
      }
      return false;
  }
  
  //��ͷ�ƶ�
  Node headMove() 
  {
      int tx = lt.get(0).getX() + snackWin.Size * DIR[curDir][0];
      int ty = lt.get(0).getY() + snackWin.Size * DIR[curDir][1];
      if(tx > snackWin.GameLocX + snackWin.GameWidth - snackWin.Size) tx = snackWin.GameLocX;//�൱��ȡ����
      if(tx < snackWin.GameLocX) tx = snackWin.GameWidth + snackWin.GameLocX - snackWin.Size;
      if(ty > snackWin.GameLocY + snackWin.GameHeight - snackWin.Size) ty = snackWin.GameLocY;
      if(ty < snackWin.GameLocY) ty = snackWin.GameHeight + snackWin.GameLocY - snackWin.Size;
      Node tmp = new Node(tx, ty);
      return tmp;
  }
  
  //��ͷ�ƶ���Ľ��:��ʳ�����
  void move()
  {
      Node head = headMove() , newNode = new Node();//Ϊʲô��Ҫ����node(),��ʼ��
      boolean eat = false;
      
      //��ʳ�������µ�ʳ��
      if(Math.abs(head.getX() - snackWin.rx) < 9 && Math.abs(head.getY() - snackWin.ry) < 9) {
          eat = true;
          newNode = new Node(lt.get(lt.size() - 1));
          snackWin.rx = (int)(Math.random() * (snackWin.GameWidth - 10) + snackWin.GameLocX);//Ӧ����-10�������ȥ��
          snackWin.ry = (int)(Math.random() * (snackWin.GameHeight - 10) + snackWin.GameLocY);
      }
      
      //��ͷ������β�ƶ�
      for(int i = lt.size() - 1; i > 0; i--)//���һ��λ����ȥ������β����ǰ�ƶ�һ�� 
          lt.set(i, lt.get(i - 1));
      lt.set(0, head);//�µ���ͷ��������
      
      //����֮�󵯳��Ի���
      if(Dead()) {
          JOptionPane.showMessageDialog(null, "Snake eating itself", "Message", JOptionPane.ERROR_MESSAGE);
          System.exit(1);
      }
      
      //��ʳ����������
      if(eat) {
          lt.add(newNode);//�������ʳ�����һ��β���ڳ���
          snackWin.score++;
          snackWin.speed++;
      }
  }
  
  //�ı䷽��ĺ���
  void changeDir(int dir) {curDir = dir;}
}