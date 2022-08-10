package SomeDo;


//类snack
//定义蛇类
//导入相当于是头文件
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;		//数组列表
import java.util.List;			//链表

import javax.swing.JOptionPane;

class Node {
  private int x , y;
  public Node() {}										 //构造函数
  public Node(int a , int b) {x = a; y = b;}			 //构造函数的多态
  public Node(Node tmp) {x = tmp.getX(); y = tmp.getY();}//构造函数的多态
  int getX() {return x;}
  int getY() {return y;}
  void setX(int a) {x = a;}//重置x
  void setY(int b) {y = b;}//重置y
}
public class Snack {
  static final int DIR[][] = {{0 , -1} , {0 , 1} , {-1 , 0} , {1 , 0}};//蛇的方向:用方向向量来实现,上下左右
  private List<Node> lt = new ArrayList<Node>();//
  private int curDir;
  
  //构造函数，构造一条蛇
  
  public Snack() {
      curDir = 3;				  //蛇的初始方向是向右边
      lt.add(new Node(350 , 250));//初始化蛇在350，250处生成
      //初始化三个蛇身子
      for(int i = 1;i <= 3;i++) {
    	  lt.add(new Node(350 - 10 * i,250));
      }
  }
  
  int length() {return lt.size();}  //获取长度
  int getDir() {return curDir;} 	//获取方向
  
  //将蛇表现出来
  void draw(Graphics g) 
  {
      g.setColor(Color.red); 
      g.fillRect(lt.get(0).getX(),lt.get(0).getY(),9,9);
      g.setColor(Color.yellow);
      for(int i = 1; i < lt.size(); i++) {
          g.fillRect(lt.get(i).getX(), lt.get(i).getY(), 9, 9);
      }
  }
  
  //死亡的唯一表现形式:头撞身子
  //可以改成有墙壁的
  boolean Dead() {
      for(int i = 1; i < lt.size(); i++) {
          if(lt.get(0).getX() == lt.get(i).getX() && lt.get(0).getY() == lt.get(i).getY()) 
              return true;
      }
      return false;
  }
  
  //蛇头移动
  Node headMove() 
  {
      int tx = lt.get(0).getX() + snackWin.Size * DIR[curDir][0];
      int ty = lt.get(0).getY() + snackWin.Size * DIR[curDir][1];
      if(tx > snackWin.GameLocX + snackWin.GameWidth - snackWin.Size) tx = snackWin.GameLocX;//相当于取余数
      if(tx < snackWin.GameLocX) tx = snackWin.GameWidth + snackWin.GameLocX - snackWin.Size;
      if(ty > snackWin.GameLocY + snackWin.GameHeight - snackWin.Size) ty = snackWin.GameLocY;
      if(ty < snackWin.GameLocY) ty = snackWin.GameHeight + snackWin.GameLocY - snackWin.Size;
      Node tmp = new Node(tx, ty);
      return tmp;
  }
  
  //蛇头移动后的结果:吃食物，死亡
  void move()
  {
      Node head = headMove() , newNode = new Node();//为什么需要加上node(),初始化
      boolean eat = false;
      
      //吃食，生成新的食物
      if(Math.abs(head.getX() - snackWin.rx) < 9 && Math.abs(head.getY() - snackWin.ry) < 9) {
          eat = true;
          newNode = new Node(lt.get(lt.size() - 1));
          snackWin.rx = (int)(Math.random() * (snackWin.GameWidth - 10) + snackWin.GameLocX);//应该是-10，否则出去了
          snackWin.ry = (int)(Math.random() * (snackWin.GameHeight - 10) + snackWin.GameLocY);
      }
      
      //蛇头随着蛇尾移动
      for(int i = lt.size() - 1; i > 0; i--)//最后一个位置舍去，就是尾巴向前移动一个 
          lt.set(i, lt.get(i - 1));
      lt.set(0, head);//新的蛇头保存下来
      
      //死亡之后弹出对话框
      if(Dead()) {
          JOptionPane.showMessageDialog(null, "Snake eating itself", "Message", JOptionPane.ERROR_MESSAGE);
          System.exit(1);
      }
      
      //吃食，分数增加
      if(eat) {
          lt.add(newNode);//如果吃了食物最后一个尾巴在长上
          snackWin.score++;
          snackWin.speed++;
      }
  }
  
  //改变方向的函数
  void changeDir(int dir) {curDir = dir;}
}