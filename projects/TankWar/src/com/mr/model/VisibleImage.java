package com.mr.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ����ʾͼ�������
 * 
 * @author www.mingrisoft.com
 *
 */
public abstract class VisibleImage {
	/**
	 * ͼ�������
	 */
	public int x;// ͼ�������
	/**
	 * ͼ��������
	 */
	public int y;// ͼ��������
	/**
	 * ͼ��Ŀ�
	 */
	int width;// ͼ��Ŀ�
	/**
	 * ͼ��ĸ�
	 */
	int height;// ͼ��ĸ�
	/**
	 * ͼ�����
	 */
	BufferedImage image;// ͼ�����

	/**
	 * ���췽��
	 * @param x -������
	 * @param y -������
	 * @param width -��
	 * @param height -��
	 */
	public VisibleImage(int x, int y, int width, int height) {
		this.x = x;// ������
		this.y = y;// ������
		this.width = width;// ��
		this.height = height;// ��
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);// ʵ����ͼƬ
	}

	/**
	 * ���췽��
	 * @param x -������
	 * @param y -������
	 * @param url -ͼƬ·��
	 */
	public VisibleImage(int x, int y, String url) {
		this.x = x;// ������
		this.y = y;// ������
		try {
			image = ImageIO.read(new File(url));// ��ȡ��·����ͼƬ����
			this.width = image.getWidth();// ��ΪͼƬ��
			this.height = image.getHeight();// ��ΪͼƬ��
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡͼƬ
	 * @return ����ʾ��ͼƬ
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * ����ͼƬ
	 * @param image - ����ʾ��ͼƬ
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * ����ͼƬ
	 * @param image - ����ʾ��ͼƬ
	 */
	public void setImage(String url) {
		try {
			this.image = ImageIO.read(new File(url));// ��ȡָ��λ�õ�ͼƬ
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ж��Ƿ�����ײ
	 * @param v - Ŀ��ͼƬ����
	 * @return ��������ཻ���򷵻�true�����򷵻�false
	 */
	public boolean hit(VisibleImage v) {
		return hit(v.getBounds());// ִ�����ط���
	}

	/**
	 * �ж��Ƿ�����ײ
	 * @param r - Ŀ��߽�
	 * @return ��������ཻ���򷵻�true�����򷵻�false
	 */
	public boolean hit(Rectangle r) {
		if (r == null) {// ���Ŀ��Ϊ��
			return false;// ���ز�������ײ
		}
		return getBounds().intersects(r);// �������ߵı߽�����Ƿ��ཻ
	}

	/**
	 * ��ȡ�߽����
	 */
	public Rectangle getBounds() {
		// ����һ��������(x,y)λ�ã����Ϊ(width, height)�ľ��α߽���󲢷���
		return new Rectangle(x, y, width, height);
	}

	/**
	 * ��ȡͼ��Ŀ�
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * ���ÿ�
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * ��ȡ��
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * ���ø�
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * ��дtoString����������ֱ����ʾ�˳������������Ϣ
	 */
	@Override
	public String toString() {
		return "Visiblemage [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

}
