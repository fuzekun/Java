package com.mr.model;

import java.util.ArrayList;
import java.util.List;

import com.mr.model.wall.BrickWall;
import com.mr.model.wall.Wall;
import com.mr.util.MapIO;

/**
 * 地图
 * 
 * @author www.mingrisoft.com
 *
 */
public class Map {
	private static List<Wall> walls = new ArrayList<>();// 地图中所有墙块的集合

	/**
	 * 私有构造方法
	 */
	private Map() {

	}

	/**
	 * 获取地图对象
	 * 
	 * @param level
	 *            - 关卡数
	 * @return 指定关卡的地图对象
	 */
	public static Map getMap(String level) {
		walls.clear();// 墙块集合清空
		walls.addAll(MapIO.readMap(level));// 读取指定关卡的墙块集合
		// 基地砖墙
		for (int a = 347; a <= 407; a += 20) {// 循环基地砖墙的横坐标
			for (int b = 512; b <= 572; b += 20) {// 循环基地砖墙的纵坐标
				if (a >= 367 && a <= 387 && b >= 532) {// 如果墙块与基地发生重合
					continue;// 执行下一次循环
				} else {
					walls.add(new BrickWall(a, b));// 墙块集合中添加墙块
				}
			}
		}
		return new Map();// 返回新的地图对象
	}

	/**
	 * 获取地图对象
	 * 
	 * @param level
	 *            - 关卡数
	 * @return 指定关卡的地图对象
	 */
	public static Map getMap(int level) {
		return getMap(String.valueOf(level));// 调用重载方法
	}

	/**
	 * 获取地图对象中的所有墙块
	 * 
	 * @return 墙块集合
	 */
	public List<Wall> getWalls() {
		return walls;
	}

}
