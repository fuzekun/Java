package com.mr.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.mr.type.GameType;

/**
 * ��ʾ�ؿ����
 * 
 * @author www.mingrisoft.com
 *
 */
public class LevelPanel extends JPanel {
	private int level;// �ؿ�ֵ
	private GameType type;// ��Ϸģʽ
	private MainFrame frame;// ������
	private String levelStr;// ���������˸�Ĺؿ��ַ���
	private String ready = "";// ׼����ʾ

	/**
	 * �ؿ���幹�췽��
	 * 
	 * @param level
	 *            - �ؿ�ֵ
	 * @param frame
	 *            - ������
	 * @param type
	 *            - ��Ϸģʽ
	 */
	public LevelPanel(int level, MainFrame frame, GameType type) {
		this.frame = frame;
		this.level = level;
		this.type = type;
		levelStr = "Level " + level;// ��ʼ���ؿ��ַ���
		Thread t = new LevelPanelThread();// �����ؿ���嶯���߳�
		t.start();// �����߳�
	}

	/**
	 * ��д��ͼ����
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);// ʹ�ð�ɫ
		g.fillRect(0, 0, getWidth(), getHeight());// ���һ�������������İ�ɫ����
		g.setFont(new Font("Consolas", Font.BOLD, 50));// ���û�ͼ����
		g.setColor(Color.BLACK);// ʹ�ú�ɫ
		g.drawString(levelStr, 260, 300);// ���ƹؿ��ַ���
		g.setColor(Color.RED);// ʹ�ú�ɫ
		g.drawString(ready, 270, 400);// ����׼����ʾ
	}

	/**
	 * ��ת��Ϸ���
	 */
	private void gotoGamePanel() {
		frame.setPanel(new GamePanel(frame, level, type));// ��������ת���˹ؿ���Ϸ���
	}

	/**
	 * �ؿ���嶯���߳�
	 * 
	 * @author mr
	 *
	 */
	private class LevelPanelThread extends Thread {
		public void run() {
			for (int i = 0; i < 6; i++) {// ѭ��6��
				if (i % 2 == 0) {// ���ѭ��������ż��
					levelStr = "Level " + level;// �ؿ��ַ���������ʾ
				} else {
					levelStr = "";// �ؿ��ַ�������ʾ�κ�����
				}
				if (i == 4) {// ���ѭ����������
					ready = "Ready !";// ׼����ʾ��ʾ����
				}
				repaint();// �ػ����
				try {
					Thread.sleep(500);// ����0.5��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gotoGamePanel();// ��ת����Ϸ���
		}
	}
}
