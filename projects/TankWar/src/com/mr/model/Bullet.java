package com.mr.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.mr.frame.GamePanel;
import com.mr.model.wall.BrickWall;
import com.mr.model.wall.IronWall;
import com.mr.model.wall.Wall;
import com.mr.type.Direction;
import com.mr.type.TankType;

/**
 * �ӵ�
 * 
 * @author www.mingrisoft.com
 *
 */
public class Bullet extends VisibleImage {
	Direction direction;
	static final int LENGTH = 8;// �ӵ��ģ������壩�߳�
	private GamePanel gamePanel;// ��Ϸ���
	private int speed = 7;// �ƶ��ٶ�
	private boolean alive = true;// �ӵ��Ƿ����Ч��
	Color color = Color.ORANGE;// �ӵ���ɫ.��ɫ
	TankType owner;// �����ӵ���̹������

	/**
	 * 
	 * �ӵ����췽��
	 * 
	 * @param x
	 *            - �ӵ��ĳ�ʼ������
	 * @param y
	 *            - �ӵ���ʼ������
	 * @param direction
	 *            - �ӵ����䷽��
	 * @param gamePanel
	 *            - ��Ϸ������
	 * @param owner
	 *            - �����ӵ���̹������
	 */
	public Bullet(int x, int y, Direction direction, GamePanel gamePanel, TankType owner) {
		super(x, y, LENGTH, LENGTH);// ���ø��๹�췽��
		this.direction = direction;
		this.gamePanel = gamePanel;
		this.owner = owner;
		init();// ��ʼ�����
	}

	/**
	 * ��ʼ�����
	 */
	private void init() {
		Graphics g = image.getGraphics();// ��ȡͼƬ�Ļ�ͼ����
		g.setColor(Color.WHITE);// ʹ�ð�ɫ��ͼ
		g.fillRect(0, 0, LENGTH, LENGTH);// ����һ����������ͼƬ�İ�ɫʵ�ľ���
		g.setColor(color);// ʹ���ӵ���ɫ
		g.fillOval(0, 0, LENGTH, LENGTH);// ����һ����������ͼƬ��ʵ��Բ��
		g.setColor(Color.BLACK);// ʹ�ú�ɫ
		g.drawOval(0, 0, LENGTH - 1, LENGTH - 1);// ��Բ�λ���һ����ɫ�ı߿򣬷�ֹ����磬��߼�С1����
	}

	/**
	 * �ӵ��ƶ�
	 */
	public void move() {
		switch (direction) {// �ж��ƶ�����
		case UP:// �������
			upward();// �����ƶ�
			break;
		case DOWN:// �������
			downward();// �����ƶ�
			break;
		case LEFT:// �������
			leftward();// �����ƶ�
			break;
		case RIGHT:// �������
			rightward();// �����ƶ�
			break;
		}
	}

	/**
	 * �����ƶ�
	 */
	private void leftward() {
		x -= speed;// ���������
		moveToBorder();// �ƶ������߽�ʱ�����ӵ�
	}

	/**
	 * �����ƶ�
	 */
	private void rightward() {
		x += speed;// ����������
		moveToBorder();// �ƶ������߽�ʱ�����ӵ�
	}

	/**
	 * �����ƶ�
	 */
	private void upward() {
		y -= speed;// ���������
		moveToBorder();// �ƶ������߽�ʱ�����ӵ�
	}

	/**
	 * �����ƶ�
	 */
	private void downward() {
		y += speed;// ����������
		moveToBorder();// �ƶ������߽�ʱ�����ӵ�
	}

	/**
	 * ����̹��
	 */
	public void hitTank() {
		List<Tank> tanks = gamePanel.getTanks();// ��ȡ����̹�˵ļ���
		for (int i = 0, lengh = tanks.size(); i < lengh; i++) {// ����̹�˼���
			Tank t = tanks.get(i);// ��ȡ̹�˶������
			if (t.isAlive() && this.hit(t)) {// ���̹���Ǵ���Ĳ����ӵ�������̹��
				switch (owner) {// �ж��ӵ�����ʲô̹��
				case player1:// ��������1���ӵ���Ч��ͬ��
				case player2:// ��������2���ӵ�
					if (t instanceof Bot) {// ������е�̹���ǵ���
						alive = false;// �ӵ�����
						t.setAlive(false);// ����̹������
					} else if (t instanceof Tank) {// ������е������
						alive = false;// �ӵ�����
					}
					break;
				case bot:// ����ǵ��Ե��ӵ�
					if (t instanceof Bot) {// ������е�̹���ǵ���
						alive = false;// �ӵ�����
					} else if (t instanceof Tank) {// ������е������
						alive = false;// �ӵ�����
						t.setAlive(false);// ���̹������
					}
					break;
				default:// Ĭ��
					alive = false;// �ӵ�����
					t.setAlive(false);// ̹������
				}
			}
		}
	}

	/**
	 * ���л���
	 */
	public void hitBase() {
		Base b = gamePanel.getBase();// ��ȡ����
		if (this.hit(b)) {// ����ӵ����л���
			alive = false;// �ӵ�����
			b.setAlive(false);// ��������
		}
	}

	/**
	 * ����ǽ��
	 */
	public void hitWall() {
		List<Wall> walls = gamePanel.getWalls();// ��ȡ����ǽ��
		for (int i = 0, lengh = walls.size(); i < lengh; i++) {// ��������ǽ��
			Wall w = walls.get(i);// ��ȡǽ�����
			if (this.hit(w)) {// ����ӵ�����ǽ��
				if (w instanceof BrickWall) {// �����שǽ
					alive = false;// �ӵ�����
					w.setAlive(false);// שǽ����
				}
				if (w instanceof IronWall) {// ����Ǹ���
					alive = false;// �ӵ�����
				}
			}
		}
	}

	/**
	 * �ƶ������߽�ʱ�����ӵ�
	 */
	private void moveToBorder() {
		if (x < 0 || x > gamePanel.getWidth() - getWidth() || y < 0 || y > gamePanel.getHeight() - getHeight()) {// ����ӵ������뿪��Ϸ���
			dispose();// �����ӵ�
		}
	}

	/**
	 * �����ӵ�
	 */
	private synchronized void dispose() {
		alive = false;// ����Ч��״̬��Ϊfalse
	}

	/**
	 * ��ȡ�ӵ����״̬
	 * 
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
}
