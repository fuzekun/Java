package com.mr.model.wall;

import com.mr.util.ImageUtil;

/**
 * ��ǽ
 * 
 * @author www.mingrisoft.com
 *
 */
public class IronWall extends Wall {
	/**
	 * 
	 * ��ǽ���췽��
	 * 
	 * @param x
	 *            - ��ʼ��������
	 * @param y
	 *            - ��ʼ��������
	 */
	public IronWall(int x, int y) {
		super(x, y, ImageUtil.IRONWALL_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ����ǽͼƬ
	}

}
