package com.mr.model.wall;

import com.mr.util.ImageUtil;

/**
 * �ݵ�
 * 
 * @author www.mingrisoft.com
 *
 */
public class GrassWall extends Wall {
	/**
	 * �ݵع��췽��
	 * 
	 * @param x
	 *            - ��ʼ��������
	 * @param y
	 *            - ��ʼ��������
	 */
	public GrassWall(int x, int y) {
		super(x, y, ImageUtil.GRASSWALL_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ�ϲݵ�ͼƬ
	}
}
