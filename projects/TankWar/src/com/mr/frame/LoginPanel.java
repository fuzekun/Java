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
 * ��½��壨ѡ����Ϸģʽ��
 * 
 * @author www.mingrisoft.com
 *
 */
public class LoginPanel extends JPanel implements KeyListener {
	private MainFrame frame;// ������
	private GameType type;// ��Ϸģʽ
	private Image backgroud;// ����ͼƬ
	private Image tank;// ̹��ͼ��
	private int y1 = 370, y2 = 430;// ̹��ͼ���ѡ�������Y����
	private int tankY = y1;// ̹��ͼ��Y����

	/**
	 * ��½��幹�췽��
	 * 
	 * @param frame
	 *            - ������
	 */
	public LoginPanel(MainFrame frame) {
		this.frame = frame;
		addListener();// ����������
		try {
			backgroud = ImageIO.read(new File(ImageUtil.LOGIN_BACKGROUD_IMAGE_URL));// ��ȡ����ͼƬ
			tank = ImageIO.read(new File(ImageUtil.PLAYER1_RIGHT_IMAGE_URL));// ��ȡ̹��ͼ��
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��д��ͼ����
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(backgroud, 0, 0, getWidth(), getHeight(), this);// ���Ʊ���ͼƬ�������������
		Font font = new Font("����", Font.BOLD, 35);// ��������
		g.setFont(font);// ʹ������
		g.setColor(Color.WHITE);// ʹ�ð�ɫ
		g.drawString("1 PLAYER", 350, 400);// ���Ƶ�һ������
		g.drawString("2 PLAYER", 350, 460);// ���Ƶڶ�������
		g.drawImage(tank, 280, tankY, this);// ����̹��ͼ��
	}

	/**
	 * ��ת�ؿ����
	 */
	private void gotoLevelPanel() {
		frame.removeKeyListener(this);// ������ɾ�����̼���
		frame.setPanel(new LevelPanel(Level.nextLevel(), frame, type));// ��������ת���ؿ����
	}

	/**
	 * ����������
	 */
	private void addListener() {
		frame.addKeyListener(this);// ������������̼�����������ʵ��KeyListener�ӿ�
	}

	/**
	 * ����������ʱ
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();// ��ȡ���µİ���ֵ
		switch (code) {// �жϰ���ֵ
		case KeyEvent.VK_W:// ������µ��ǡ�W����Ч��ͬ��
		case KeyEvent.VK_UP:// ������µ��ǡ�������Ч��ͬ��
		case KeyEvent.VK_S:// ������µ��ǡ�S����Ч��ͬ��
		case KeyEvent.VK_DOWN:// ������µ��ǡ�����
			if (tankY == y1) {// ���̹��ͼ���ڵ�һ��λ��
				tankY = y2;// ��ͼ���Ϊ�ڶ���λ��
			} else {
				tankY = y1;// ��ͼ���Ϊ��һ��λ��
			}
			repaint();
			break;
		case KeyEvent.VK_Y:// ������µ��ǡ�Y����Ч��ͬ��
		case KeyEvent.VK_NUMPAD1:// ������µ���С����1��Ч��ͬ��
		case KeyEvent.VK_ENTER:// ������µ��ǡ�Enter����Ч��ͬ��
			if (tankY == y1) {// ���̹��ͼ���ڵ�һ��λ��
				type = GameType.ONE_PLAYER;// ��ϷģʽΪ����ģʽ
			} else {
				type = GameType.TWO_PLAYER;// ��ϷģʽΪ˫��ģʽ
			}
			gotoLevelPanel();// ��ת�ؿ����
			break;
		}
	}

	/**
	 * ����̧��ʱ
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// ��ʵ�ִ˷�����������ɾ��
	}

	/**
	 * ����ĳ�����¼�
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// ��ʵ�ִ˷�����������ɾ��
	}
}
