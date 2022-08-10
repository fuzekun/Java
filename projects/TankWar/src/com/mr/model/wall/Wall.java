package com.mr.model.wall;

import com.mr.model.VisibleImage;

/**
 * ǽ��������
 * 
 * @author www.mingrisoft.com
 *
 */
public abstract class Wall extends VisibleImage {
	private boolean alive = true;// ǽ������Ч��

	/**
	 * ǽ���췽��
	 * 
	 * @param x
	 *            - ��ʼ��������
	 * @param y
	 *            - ��ʼ��������
	 * @param url
	 *            - ��ʼ��ͼƬ·��
	 */
	public Wall(int x, int y, String url) {
		super(x, y, url);// ���ø��๹�췽��
	}

	/**
	 * �����Ƿ���Ч
	 * 
	 * @return �Ƿ���Ч
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * �����Ƿ���Ч
	 * 
	 * @param alive
	 *            -�Ƿ���Ч
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * ��д�жϷ������������ǽ���������ͬ������Ϊ����ǽ����ͬһ��ǽ��
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Wall) {// �������Ķ�����ǽ������������
			Wall w = (Wall) obj;// ǿ��תΪǽ�����
			if (w.x == x && w.y == y) {// �������ǽ��������ͬ
				return true;// ����ǽ������ͬ��
			}
		}
		return super.equals(obj);// ���ظ��෽��
	}
}
