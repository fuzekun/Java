package com.mr.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import com.mr.model.Base;
import com.mr.model.Boom;
import com.mr.model.Bot;
import com.mr.model.Bullet;
import com.mr.model.Level;
import com.mr.model.Map;
import com.mr.model.Tank;
import com.mr.model.wall.Wall;
import com.mr.type.GameType;
import com.mr.type.TankType;
import com.mr.util.ImageUtil;

/**
 * 游戏面板 实际大小[794,572]
 * 
 * @author www.mingrisoft.com
 *
 */
public class GamePanel extends JPanel implements KeyListener {
	/**
	 * 游戏界面刷新时间：20毫秒
	 */
	public static final int FRESH = 20;
	private BufferedImage image;// 在面板中显示的主图片
	private Graphics2D g2;// 图片的绘图对象
	private MainFrame frame;// 主窗体
	private GameType gameType;// 游戏模式
	private Tank play1, play2;// 玩家1、玩家2
	private boolean y_key, s_key, w_key, a_key, d_key, up_key, down_key, left_key, right_key, num1_key;// 按键是否按下标志，左侧单词是按键名
	private int level;// 关卡值
	private List<Bullet> bullets;// 所有子弹集合
	private volatile List<Tank> allTanks;// 所有坦克集合
	private List<Tank> botTanks;// 电脑坦克集合
	private final int botCount = 1;// 电脑坦克总数
	private int botReadyCount = botCount;// 准备出场的电脑坦克总数
	private int botSurplusCount = botCount;// 电脑坦克剩余量
	private int botMaxInMap = 6;// 场上最大电脑坦克数
	private int botX[] = { 10, 367, 754 };// 电脑坦克出生的3个横坐标位置
	private List<Tank> playerTanks;// 玩家坦克集合
	private volatile boolean finish = false;// 游戏是否结束
	private Base base;// 基地
	private List<Wall> walls;// 所有墙块
	private List<Boom> boomImage;// 坦克阵亡后的爆炸效果集合
	private Random r = new Random();// 随机数对象
	private int createBotTimer = 0;// 生产电脑计时器
	private Tank survivor;// （玩家）幸存者,用于绘制最后一个爆炸效果

	/**
	 * 游戏面板构造方法
	 * 
	 * @param frame
	 *            - 主窗体
	 * @param level
	 *            - 关卡
	 * @param gameType
	 *            - 游戏模式
	 */
	public GamePanel(MainFrame frame, int level, GameType gameType) {
		this.frame = frame;
		this.level = level;
		this.gameType = gameType;
		setBackground(Color.WHITE);// 面板使用白色背景
		init();// 初始化组件
		Thread t = new FreshThead();// 创建游戏帧刷新线程
		t.start();// 启动线程
		addListener();// 开启监听
	}

	/**
	 * 组件初始化
	 */
	private void init() {
		bullets = new ArrayList<Bullet>();// 实例化子弹集合
		allTanks = new ArrayList<>();// 实例化所有坦克集合
		walls = new ArrayList<>();// 实例化所有墙块集合
		boomImage = new ArrayList<>();// 实例化爆炸效果集合

		image = new BufferedImage(794, 572, BufferedImage.TYPE_INT_BGR);// 实例化主图片，采用面板实际大小
		g2 = image.createGraphics();// 获取主图片绘图对象

		playerTanks = new ArrayList<>();// 实例化玩家坦克集合
		play1 = new Tank(278, 537, ImageUtil.PLAYER1_UP_IMAGE_URL, this, TankType.player1);// 实例化玩家1
		if (gameType == GameType.TWO_PLAYER) {// 如果是双人模式
			play2 = new Tank(448, 537, ImageUtil.PLAYER2_UP_IMAGE_URL, this, TankType.player2);// 实例化玩家2
			playerTanks.add(play2);// 玩家坦克集合添加玩家2
		}
		playerTanks.add(play1);// 玩家坦克集合添加玩家1

		botTanks = new Vector<>();// 实例化电脑坦克集合
		botTanks.add(new Bot(botX[0], 1, this, TankType.bot));// 在第一个位置添加电脑
		botTanks.add(new Bot(botX[1], 1, this, TankType.bot));// 在第二个位置添加电脑
		botTanks.add(new Bot(botX[2], 1, this, TankType.bot));// 在第三个位置添加电脑
		botReadyCount -= 3;// 准备出场的坦克总数减去初始化数量
		allTanks.addAll(playerTanks);// 所有坦克集合添加玩家坦克集合
		allTanks.addAll(botTanks);// 所有坦克集合添加电脑坦克集合
		base = new Base(367, 532);// 实例化基地
		initWalls();// 初始化地图中的墙块
	}

	/**
	 * 组件监听
	 */
	private void addListener() {
		frame.addKeyListener(this);// 主窗体载入键盘监听，本类已实现KeyListener接口
	}

	/**
	 * 初始化地图中的墙块
	 */
	private void initWalls() {
		Map map = Map.getMap(level);// 获取当前关卡的地图对象
		walls.addAll(map.getWalls());// 墙块集合添加当前地图中所有墙块
		walls.add(base);// 墙块集合添加基地
	}

	/**
	 * 重写绘制组件方法
	 */
	public void paint(Graphics g) {
		paintTankActoin();// 执行坦克动作
		CreateBot();// 循环创建电脑坦克
		paintImage();// 绘制主图片
		g.drawImage(image, 0, 0, this); // 将主图片绘制到面板上
	}

	/**
	 * 绘制主图片
	 */
	private void paintImage() {
		g2.setColor(Color.WHITE);// 使用白色
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());// 填充一个覆盖整个图片的白色矩形
		panitBoom();// 绘制爆炸效果
		paintBotCount();// 在屏幕顶部绘制剩余坦克数量
		panitBotTanks();// 绘制电脑坦克
		panitPlayerTanks();// 绘制玩家坦克
		allTanks.addAll(playerTanks);// 坦克集合添加玩家坦克集合
		allTanks.addAll(botTanks);// 坦克集合添加电脑坦克集合
		panitWalls();// 绘制墙块
		panitBullets();// 绘制子弹

		if (botSurplusCount == 0) {// 如果所有电脑都被消灭
			stopThread();// 结束游戏帧刷新线程
			paintBotCount();// 在屏幕顶部绘制剩余坦克数量
			g2.setFont(new Font("楷体", Font.BOLD, 50));// 设置绘图字体
			g2.setColor(Color.green);// 使用绿色
			g2.drawString("胜   利 !", 250, 400);// 在指定坐标绘制文字
			gotoNextLevel();// 进入下一关卡
		}

		if (gameType == GameType.ONE_PLAYER) {// 如果是单人模式
			if (!play1.isAlive()) {// 如果玩家阵亡
				stopThread();// 结束游戏帧刷新线程
				boomImage.add(new Boom(play1.x, play1.y));// 添加玩家1爆炸效果
				panitBoom();// 绘制爆炸效果
				paintGameOver();// 在屏幕中央绘制game over
				gotoPrevisousLevel();// 重新进入本关卡
			}
		} else {// 如果是双人模式
			if (play1.isAlive() && !play2.isAlive()) {// 如果玩家1是 幸存者
				survivor = play1;// 幸存者是玩家1
			} else if (!play1.isAlive() && play2.isAlive()) {
				survivor = play2;// 幸存者是玩家2
			} else if (!(play1.isAlive() || play2.isAlive())) {// 如果两个玩家全部阵亡
				stopThread();// 结束游戏帧刷新线程
				boomImage.add(new Boom(survivor.x, survivor.y));// 添加幸存者爆炸效果
				panitBoom();// 绘制爆炸效果
				paintGameOver();// 在屏幕中央绘制game over
				gotoPrevisousLevel();// 重新进入本关卡
			}
		}

		if (!base.isAlive()) {// 如果基地被击中
			stopThread();// 结束游戏帧刷新线程
			paintGameOver();// 在屏幕中央绘制game over
			base.setImage(ImageUtil.BREAK_BASE_IMAGE_URL);// 基地使用阵亡图片
			gotoPrevisousLevel();// 重新进入本关卡
		}
		g2.drawImage(base.getImage(), base.x, base.y, this);// 绘制基地
	}

	/**
	 * 在屏幕顶部绘制剩余坦克数量
	 */
	private void paintBotCount() {
		g2.setColor(Color.BLUE);// 使用蓝色
		g2.drawString("敌方坦克剩余：" + botSurplusCount, 337, 15);// 在指定坐标绘制字符串
	}

	/**
	 * 在屏幕中央绘制game over
	 */
	private void paintGameOver() {
		g2.setFont(new Font("楷体", Font.BOLD, 50));// 设置绘图字体
		g2.setColor(Color.RED);// 设置绘图颜色
		g2.drawString("Game Over !", 250, 400);// 在指定坐标绘制文字

	}

	/**
	 * 绘制爆炸效果
	 */
	private void panitBoom() {
		for (int i = 0; i < boomImage.size(); i++) {// 循环遍历爆炸效果集合
			Boom boom = boomImage.get(i);// 获取爆炸对象
			if (boom.isAlive()) {// 如果爆炸效果有效
				boom.show(g2);// 展示爆炸效果
			} else {// 如果爆炸效果无效
				boomImage.remove(i);// 在集合中刪除此爆炸对象
				i--;// 循环变量-1，保证下次循环i的值不会变成i+1，以便有效遍历集合，且防止下标越界
			}
		}
	}

	/**
	 * 绘制墙块
	 */
	private void panitWalls() {
		for (int i = 0; i < walls.size(); i++) {// 循环遍历墙块集合
			Wall w = walls.get(i);// 获取墙块对象
			if (w.isAlive()) {// 如果墙块有效
				g2.drawImage(w.getImage(), w.x, w.y, this);// 绘制墙块
			} else {// 如果墙块无效
				walls.remove(i);// 在集合中刪除此墙块
				i--;// 循环变量-1，保证下次循环i的值不会变成i+1，以便有效遍历集合，且防止下标越界
			}
		}
	}

	/**
	 * 绘制子弹
	 */
	private void panitBullets() {
		for (int i = 0; i < bullets.size(); i++) {// 循环遍历子弹集合
			Bullet b = bullets.get(i);// 获取子弹对象
			if (b.isAlive()) {// 如果子弹有效
				b.move();// 子弹执行移动操作
				b.hitBase();// 子弹执行击中基地判断
				b.hitWall();// 子弹执行击中墙壁判断
				b.hitTank();// 子弹执行击中坦克判断
				g2.drawImage(b.getImage(), b.x, b.y, this);// 绘制子弹
			} else {// 如果子弹无效
				bullets.remove(i);// 在集合中刪除此子弹
				i--;// 循环变量-1，保证下次循环i的值不会变成i+1，以便有效遍历集合，且防止下标越界
			}
		}
	}

	/**
	 * 绘制电脑坦克
	 */
	private void panitBotTanks() {
		for (int i = 0; i < botTanks.size(); i++) {// 循环遍历电脑坦克集合
			Bot t = (Bot) botTanks.get(i);// 获取电脑坦克对象
			if (t.isAlive()) {// 如果坦克存活
				t.go();// 电脑坦克展开行动
				g2.drawImage(t.getImage(), t.x, t.y, this);// 绘制坦克
			} else {// 如果坦克阵亡
				botTanks.remove(i);// 集合中删除此坦克
				i--;// 循环变量-1，保证下次循环i的值不会变成i+1，以便有效遍历集合，且防止下标越界
				boomImage.add(new Boom(t.x, t.y));// 在坦克位置创建爆炸效果
				decreaseBot();// 剩余坦克数量-1
			}
		}
	}

	/**
	 * 绘制玩家坦克
	 */
	private void panitPlayerTanks() {
		for (int i = 0; i < playerTanks.size(); i++) {// 循环遍历玩家坦克
			Tank t = playerTanks.get(i);// 获取玩家坦克对象
			if (t.isAlive()) {// 如果坦克存活
				g2.drawImage(t.getImage(), t.x, t.y, this);// 绘制坦克
			} else {// 如果坦克阵亡
				playerTanks.remove(i);// 集合中删除此坦克
				i--;// 循环变量-1，保证下次循环i的值不会变成i+1，以便有效遍历集合，且防止下标越界
				boomImage.add(new Boom(t.x, t.y));// 在坦克位置创建爆炸效果
			}
		}
	}

	/**
	 * 结束游戏帧刷新
	 */
	private synchronized void stopThread() {
		frame.removeKeyListener(this);// 主窗体删除本类键盘事件监听对象
		finish = true;// 游戏停止标志为true
	}

	/**
	 * 游戏帧刷新线程
	 */
	private class FreshThead extends Thread {
		public void run() {// 线程主方法
			while (!finish) {// 如果游戏未停止
				repaint();// 执行本类重绘方法
				try {
					Thread.sleep(FRESH);// 指定时间后重新绘制界面
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 添加电脑坦克，如果场上坦克未到达最大值，每4秒钟之后在三个出生位置随机选择其一，创建电脑坦克。
	 */
	private void CreateBot() {
		createBotTimer += FRESH;// 计时器按照刷新时间递增
		// “当场上电脑小于场上最大数时” 并且 “准备上场的坦克数量大于0” 并且 “计时器记录已过去4秒钟”
		if (botTanks.size() < botMaxInMap && botReadyCount > 0 && createBotTimer >= 4000) {
			int index = r.nextInt(3);// 随机获取0或1或2其中一个值
			Rectangle bornRect = new Rectangle(botX[index], 1, 35, 35);// 创建坦克随机出生区域
			for (int i = 0, lengh = allTanks.size(); i < lengh; i++) {// 循环遍历所有坦克集合
				Tank t = allTanks.get(i);// 获取坦克对象
				if (t.isAlive() && t.hit(bornRect)) {// 如果场上存在与随机位置重合并存活的坦克
					return;// 结束方法
				}
			}
			botTanks.add(new Bot(botX[index], 1, GamePanel.this, TankType.bot));// 在随机位置创造电脑坦克
			botReadyCount--;// 准备上场电脑数量-1
			createBotTimer = 0;// 产生电脑计时器重新计时
		}
	}

	/**
	 * 进入下一关卡
	 */
	private void gotoNextLevel() {
		Thread jump = new JumpPageThead(Level.nextLevel());// 创建跳转到下一关卡的线程
		jump.start();// 启动线程
	}

	/**
	 * 重新进入本关卡
	 */
	private void gotoPrevisousLevel() {
		Thread jump = new JumpPageThead(Level.previsousLevel());// 创建重新进入本关卡的线程
		jump.start();// 启动线程
	}

	/**
	 * 剩余坦克数量减少1
	 */
	public void decreaseBot() {
		botSurplusCount--;// 电脑剩余数量-1
	}

	/**
	 * 按键按下时
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {// 判断按下的按键值
		case KeyEvent.VK_J:// 如果按下的是“Y”
			y_key = true;// “Y”按下标志为true
			break;
		case KeyEvent.VK_W:// 如果按下的是“W”
			w_key = true;// “W”按下标志为true
			a_key = false;// “A”按下标志为false
			s_key = false;// “S”按下标志为false
			d_key = false;// “D”按下标志为false
			break;
		case KeyEvent.VK_A:// 如果按下的是“A”
			w_key = false;// “W”按下标志为false
			a_key = true;// “A”按下标志为true
			s_key = false;// “S”按下标志为false
			d_key = false;// “D”按下标志为false
			break;
		case KeyEvent.VK_S:// 如果按下的是“S”
			w_key = false;// “W”按下标志为false
			a_key = false;// “A”按下标志为false
			s_key = true;// “S”按下标志为true
			d_key = false;// “D”按下标志为false
			break;
		case KeyEvent.VK_D:// 如果按下的是“D”
			w_key = false;// “W”按下标志为false
			a_key = false;// “A”按下标志为false
			s_key = false;// “S”按下标志为false
			d_key = true;// “D”按下标志为true
			break;
		case KeyEvent.VK_HOME:// 如果按下的是“HOME”，效果同下
		case KeyEvent.VK_NUMPAD1:// 如果按下的是小键盘数字1
			num1_key = true;// 小键盘数字1按下标志为true
			break;
		case KeyEvent.VK_UP:// 如果按下的是“↑”
			up_key = true;// “↑”按下标志为true
			down_key = false;// “↓”按下标志为false
			right_key = false;// “→”按下标志为false
			left_key = false;// “←”按下标志为false
			break;
		case KeyEvent.VK_DOWN:// 如果按下的是“↓”
			up_key = false;// “↑”按下标志为false
			down_key = true;// “↓”按下标志为true
			right_key = false;// “→”按下标志为false
			left_key = false;// “←”按下标志为false
			break;
		case KeyEvent.VK_LEFT:// 如果按下的是“←”
			up_key = false;// “↑”按下标志为false
			down_key = false;// “↓”按下标志为false
			right_key = false;// “→”按下标志为false
			left_key = true;// “←”按下标志为true
			break;
		case KeyEvent.VK_RIGHT:// 如果按下的是“→”
			up_key = false;// “↑”按下标志为false
			down_key = false;// “↓”按下标志为false
			right_key = true;// “→”按下标志为true
			left_key = false;// “←”按下标志为false
			break;
		}
	}

	/**
	 * 根据按键按下状态，让坦克执行相应动作
	 */
	private void paintTankActoin() {
		if (y_key) {// 如果“Y”键是按下状态
			play1.attack();// 玩家1坦克攻击
		}
		if (w_key) {// 如果“W”键是按下状态
			play1.upward();// 玩家1坦克向上移动
		}
		if (d_key) {// 如果“D”键是按下状态
			play1.rightward();// 玩家1坦克向右移动
		}
		if (a_key) {// 如果“A”键是按下状态
			play1.leftward();// 玩家1坦克左移动
		}
		if (s_key) {// 如果“S”键是按下状态
			play1.downward();// 玩家1坦克向下移动
		}
		if (gameType == GameType.TWO_PLAYER) {
			if (num1_key) {// 如果“M”键是按下状态
				play2.attack();// 玩家2坦克攻击
			}
			if (up_key) {// 如果“←”键是按下状态
				play2.upward();// 玩家2坦克向上移动
			}
			if (right_key) {// 如果“→”键是按下状态
				play2.rightward();// 玩家2坦克向右移动
			}
			if (left_key) {// 如果“↑”键是按下状态
				play2.leftward();// 玩家2坦克左移动
			}
			if (down_key) {// 如果“↓”键是按下状态
				play2.downward();// 玩家2坦克后移动
			}
		}
	}

	/**
	 * 按键抬起时
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_J:// 如果抬起的是“Y”
			y_key = false;// “Y”按下标志为false
			break;
		case KeyEvent.VK_W:// 如果抬起的是“W”
			w_key = false;// “W”按下标志为false
			break;
		case KeyEvent.VK_A:// 如果抬起的是“A”
			a_key = false;// “A”按下标志为false
			break;
		case KeyEvent.VK_S:// 如果抬起的是“S”
			s_key = false;// “S”按下标志为false
			break;
		case KeyEvent.VK_D:// 如果抬起的是“D”
			d_key = false;// “D”按下标志为false
			break;
		case KeyEvent.VK_HOME:// 如果抬起的是“HOME”，效果同下
		case KeyEvent.VK_NUMPAD1:// 如果抬起的是小键盘1
			num1_key = false;// 小键盘1按下标志为false
			break;
		case KeyEvent.VK_UP:// 如果抬起的是“↑”
			up_key = false;// “↑”按下标志为false
			break;
		case KeyEvent.VK_DOWN:// 如果抬起的是“↓”
			down_key = false;// “↓”按下标志为false
			break;
		case KeyEvent.VK_LEFT:// 如果抬起的是“←”
			left_key = false;// “←”按下标志为false
			break;
		case KeyEvent.VK_RIGHT:// 如果抬起的是“→”
			right_key = false;// “→”按下标志为false
			break;
		}
	}

	/**
	 * 向子弹集合中添加子弹
	 * 
	 * @param b
	 *            - 添加的子弹
	 */
	public void addBullet(Bullet b) {
		bullets.add(b);// 子弹集合中添加子弹
	}

	/**
	 * 获取所有墙块集合
	 * 
	 * @return 所有墙块
	 */
	public List<Wall> getWalls() {
		return walls;
	}

	/**
	 * 获取基地对象
	 * 
	 * @return 基地
	 */
	public Base getBase() {
		return base;
	}

	/**
	 * 获取所有坦克集合
	 * 
	 * @return 所有坦克
	 */
	public List<Tank> getTanks() {
		return allTanks;
	}

	/**
	 * 游戏结束跳转线程
	 */
	private class JumpPageThead extends Thread {
		int level;// 跳转的关卡

		/**
		 * 跳转线程构造方法
		 * 
		 * @param level
		 *            - 跳转的关卡
		 */
		public JumpPageThead(int level) {
			this.level = level;
		}

		/**
		 * 线程主方法
		 */
		public void run() {
			try {
				Thread.sleep(1000);// 1秒钟后
				frame.setPanel(new LevelPanel(level, frame, gameType));// 主窗体跳转到指定关卡
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 键入某按键事件
	 */
	public void keyTyped(KeyEvent e) {
		// 不实现此方法，但不可删除
	}
}
