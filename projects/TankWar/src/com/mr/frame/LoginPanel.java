package com.mr.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.mr.model.Level;
import com.mr.type.GameType;
import com.mr.util.ImageUtil;

/**
 * 登陆面板（选择游戏模式）
 * 
 * @author www.mingrisoft.com
 *
 */
public class LoginPanel extends JPanel implements KeyListener {
	private MainFrame frame;// 主窗体
	private GameType type;// 游戏模式
	private Image backgroud;// 背景图片
	private Image tank;// 坦克图标
	private int y1 = 370, y2 = 430;// 坦克图标可选择的两个Y坐标
	private int tankY = y1;// 坦克图标Y坐标

	/**
	 * 登陆面板构造方法
	 * 
	 * @param frame
	 *            - 主窗体
	 */
	public LoginPanel(MainFrame frame) {
		this.frame = frame;
		addListener();// 添加组件监听
		try {
			backgroud = ImageIO.read(new File(ImageUtil.LOGIN_BACKGROUD_IMAGE_URL));// 读取背景图片
			tank = ImageIO.read(new File(ImageUtil.PLAYER1_RIGHT_IMAGE_URL));// 读取坦克图标
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写绘图方法
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(backgroud, 0, 0, getWidth(), getHeight(), this);// 绘制背景图片，填满整个面板
		Font font = new Font("黑体", Font.BOLD, 35);// 创建体字
		g.setFont(font);// 使用字体
		g.setColor(Color.WHITE);// 使用白色
		g.drawString("1 PLAYER", 350, 400);// 绘制第一行文字
		g.drawString("2 PLAYER", 350, 460);// 绘制第二行文字
		g.drawImage(tank, 280, tankY, this);// 绘制坦克图标
	}

	/**
	 * 跳转关卡面板
	 */
	private void gotoLevelPanel() {
		frame.removeKeyListener(this);// 主窗体删除键盘监听
		frame.setPanel(new LevelPanel(Level.nextLevel(), frame, type));// 主窗体跳转至关卡面板
	}

	/**
	 * 添加组件监听
	 */
	private void addListener() {
		frame.addKeyListener(this);// 主窗体载入键盘监听，本类已实现KeyListener接口
	}

	/**
	 * 当按键按下时
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();// 获取按下的按键值
		switch (code) {// 判断按键值
		case KeyEvent.VK_W:// 如果按下的是“W”，效果同下
		case KeyEvent.VK_UP:// 如果按下的是“↑”，效果同下
		case KeyEvent.VK_S:// 如果按下的是“S”，效果同下
		case KeyEvent.VK_DOWN:// 如果按下的是“↓”
			if (tankY == y1) {// 如果坦克图标在第一个位置
				tankY = y2;// 将图标改为第二个位置
			} else {
				tankY = y1;// 将图标改为第一个位置
			}
			repaint();
			break;
		case KeyEvent.VK_Y:// 如果按下的是“Y”，效果同下
		case KeyEvent.VK_NUMPAD1:// 如果按下的是小键盘1，效果同下
		case KeyEvent.VK_ENTER:// 如果按下的是“Enter”，效果同下
			if (tankY == y1) {// 如果坦克图标在第一个位置
				type = GameType.ONE_PLAYER;// 游戏模式为单人模式
			} else {
				type = GameType.TWO_PLAYER;// 游戏模式为双人模式
			}
			gotoLevelPanel();// 跳转关卡面板
			break;
		}
	}

	/**
	 * 按键抬起时
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// 不实现此方法，但不可删除
	}

	/**
	 * 键入某按键事件
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// 不实现此方法，但不可删除
	}
}
