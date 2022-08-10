package com.mr.model;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import com.mr.frame.GamePanel;
import com.mr.type.Direction;
import com.mr.type.TankType;
import com.mr.util.ImageUtil;

/**
 * ������̹��
 * 
 * @author www.mingrisoft.com
 *
 */
public class Bot extends Tank {
	private Random random = new Random();// �����
	private Direction dir;// �ƶ�����
	private int fresh = GamePanel.FRESH;// ˢ��ʱ�䣬������Ϸ����ˢ��ʱ��
	private int MoveTimer = 0;// �ƶ���ʱ��

	/**
	 * 
	 * �����˹��췽��
	 * 
	 * @param x
	 *            -������
	 * @param y
	 *            -������
	 * @param gamePanel
	 *            - ��Ϸ���
	 * @param type
	 *            - ̹������
	 */

	public Bot(int x, int y, GamePanel gamePanel, TankType type) {
		super(x, y, ImageUtil.BOT_DOWN_IMAGE_URL, gamePanel, type);// ���ø��๹�췽����ʹ��Ĭ�ϻ�����̹��ͼƬ
		dir = Direction.DOWN;// �ƶ�����Ĭ������
		setAttackCoolDownTime(1000);// ���ù�����ȴʱ��
		// setSpeed(2);//���û������ƶ��ٶ�
	}

	/**
	 * ������չ���ж�
	 */
	public void go() {
		if (isAttackCoolDown()) {// ���������ȴʱ�����
			attack();// ����
		}

		if (MoveTimer >= 3000) {// ����ƶ���ʱ����¼����3��
			dir = randomDirection();// ��������ƶ�����
			MoveTimer = 0;// �����ƶ���ʱ��
		} else {
			MoveTimer += fresh;// ��ʱ������ˢ��ʱ�����
		}

		switch (dir) {// �ж��ƶ�����
		case UP:// �����������
			upward();// �����ƶ�
			break;
		case DOWN:// �����������
			downward();// �����ƶ�
			break;
		case RIGHT:// �����������
			rightward();// �����ƶ�
			break;
		case LEFT:// �����������
			leftward();// �����ƶ�
			break;
		}
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	private Direction randomDirection() {
		int rnum = random.nextInt(4);// ��ȡ���������Χ��0-3
		switch (rnum) {// �ж������
		case 0:// �����0
			return Direction.UP;// ��������
		case 1:// �����1
			return Direction.RIGHT;// ��������
		case 2:// �����2
			return Direction.LEFT;// ��������
		default:
			return Direction.DOWN;// ��������
		}
	}

	/**
	 * ��д�ƶ������ı߽��¼�
	 */
	protected void moveToBorder() {
		if (x < 0) {// ���̹�˺�����С��0
			x = 0;// ��̹�˺��������0
			dir = randomDirection();// ��������ƶ�����
		} else if (x > gamePanel.getWidth() - width) {// ���̹�˺����곬�������Χ
			x = gamePanel.getWidth() - width;// ��̹�˺����걣�����ֵ
			dir = randomDirection();// ��������ƶ�����
		}
		if (y < 0) {// ���̹��������С��0
			y = 0;// ��̹�����������0
			dir = randomDirection();// ��������ƶ�����
		} else if (y > gamePanel.getHeight() - height) {// ���̹�������곬�������Χ
			y = gamePanel.getHeight() - height;// ��̹�������걣�����ֵ
			dir = randomDirection();// ��������ƶ�����
		}
	}

	/**
	 * ��д����̹�˷���
	 */
	boolean hitTank(int x, int y) {
		Rectangle next = new Rectangle(x, y, width, height);// ������ײλ��
		List<Tank> tanks = gamePanel.getTanks();// ��ȡ����̹�˼���
		for (int i = 0, lengh = tanks.size(); i < lengh; i++) {// ����̹�˼���
			Tank t = tanks.get(i);// ��ȡ̹�˶���
			if (!this.equals(t)) {// �����̹�˶����뱾������ͬһ��
				if (t.isAlive() && t.hit(next)) {// ����Է�˵�Ǵ��ģ������뱾��������ײ
					if (t instanceof Bot) {// ����Է�Ҳ�ǵ���
						dir = randomDirection();// ��������ƶ�����
					}
					return true;// ������ײ
				}
			}
		}
		return false;// δ������ײ
	}

	/**
	 * ��д����������ÿ�ι���ֻ��4%���ʻᴥ�����๥������
	 */
	@Override
	public void attack() {
		int rnum = random.nextInt(100);// �������������Χ��0-99
		if (rnum < 4) {// ��������С��4
			super.attack();// ִ�и��๥������
		}
	}
}
