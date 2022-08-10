package com.mr.model;

import java.awt.Graphics2D;

import com.mr.frame.GamePanel;
import com.mr.util.ImageUtil;

/**
 * ��ըЧ����ͼƬ)
 * 
 * @author www.mingrisoft.com
 *
 */
public class Boom extends VisibleImage {

	private int timer = 0;// ��ʱ��
	private int fresh = GamePanel.FRESH;// ˢ��ʱ��
	private boolean alive = true;// �Ƿ���

	/**
	 * ��ըЧ����������
	 * 
	 * @param x
	 *            - ��ըͼƬ������
	 * @param y-
	 *            - ��ըͼƬ������
	 */
	public Boom(int x, int y) {
		super(x, y, ImageUtil.BOOM_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ�ϱ�ըЧ��ͼƬ
	}

	/**
	 * չʾ��ըͼƬ,����Ƭֻ��ʾ0.5��
	 * 
	 * @param g2
	 *            ��ͼ����
	 */
	public void show(Graphics2D g2) {
		if (timer >= 500) {// ����ʱ����¼�ѵ�0.5��
			alive = false;// ��ըЧ��ʧЧ
		} else {
			g2.drawImage(getImage(), x, y, null);// ���Ʊ�ըЧ��
			timer += fresh;// ��ʱ������ˢ��ʱ�����
		}
	}

	/**
	 * ��ըͼƬ�Ƿ���Ч
	 * 
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
}
