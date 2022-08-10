package SomeDo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;


public class mygame extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void paint(Graphics g) {
		Color c = g.getColor();
		Font f = g.getFont();
		
		g.setColor(Color.RED);
		g.setFont(new Font("宋体",Font.BOLD,20));
		g.drawLine(100, 100, 300, 300);
		g.drawRect(100, 100, 300, 300);
		g.drawOval(100, 100, 300, 300);
		g.drawString("我是比你牛逼1000倍的人", 100, 200);
		
		g.setColor(c);
		g.setFont(f);
	}
	
	void Lanchframe() {
		setSize(500,500);
		setLocation(100,100);
		setVisible(true);
	}
	public static void main(String[] args){
		// TODO Auto-generated method stub
		mygame a = new mygame();
		a.Lanchframe();
	}
}
