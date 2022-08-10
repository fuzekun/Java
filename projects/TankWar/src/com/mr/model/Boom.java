package com.mr.model;

import java.awt.Graphics2D;

import com.mr.frame.GamePanel;
import com.mr.util.ImageUtil;

/**
 * 爆炸效果（图片)
 * 
 * @author www.mingrisoft.com
 *
 */
public class Boom extends VisibleImage {

	private int timer = 0;// 计时器
	private int fresh = GamePanel.FRESH;// 刷新时间
	private boolean alive = true;// 是否存活

	/**
	 * 爆炸效果工作方法
	 * 
	 * @param x
	 *            - 爆炸图片横坐标
	 * @param y-
	 *            - 爆炸图片纵坐标
	 */
	public Boom(int x, int y) {
		super(x, y, ImageUtil.BOOM_IMAGE_URL);// 调用父类构造方法，使用默认爆炸效果图片
	}

	/**
	 * 展示爆炸图片,此照片只显示0.5秒
	 * 
	 * @param g2
	 *            绘图对象
	 */
	public void show(Graphics2D g2) {
		if (timer >= 500) {// 当计时器记录已到0.5秒
			alive = false;// 爆炸效果失效
		} else {
			g2.drawImage(getImage(), x, y, null);// 绘制爆炸效果
			timer += fresh;// 计时器按照刷新时间递增
		}
	}

	/**
	 * 爆炸图片是否有效
	 * 
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
}
