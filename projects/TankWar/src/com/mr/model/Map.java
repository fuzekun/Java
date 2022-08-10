package com.mr.model;

import java.util.ArrayList;
import java.util.List;

import com.mr.model.wall.BrickWall;
import com.mr.model.wall.Wall;
import com.mr.util.MapIO;

/**
 * ��ͼ
 * 
 * @author www.mingrisoft.com
 *
 */
public class Map {
	private static List<Wall> walls = new ArrayList<>();// ��ͼ������ǽ��ļ���

	/**
	 * ˽�й��췽��
	 */
	private Map() {

	}

	/**
	 * ��ȡ��ͼ����
	 * 
	 * @param level
	 *            - �ؿ���
	 * @return ָ���ؿ��ĵ�ͼ����
	 */
	public static Map getMap(String level) {
		walls.clear();// ǽ�鼯�����
		walls.addAll(MapIO.readMap(level));// ��ȡָ���ؿ���ǽ�鼯��
		// ����שǽ
		for (int a = 347; a <= 407; a += 20) {// ѭ������שǽ�ĺ�����
			for (int b = 512; b <= 572; b += 20) {// ѭ������שǽ��������
				if (a >= 367 && a <= 387 && b >= 532) {// ���ǽ������ط����غ�
					continue;// ִ����һ��ѭ��
				} else {
					walls.add(new BrickWall(a, b));// ǽ�鼯�������ǽ��
				}
			}
		}
		return new Map();// �����µĵ�ͼ����
	}

	/**
	 * ��ȡ��ͼ����
	 * 
	 * @param level
	 *            - �ؿ���
	 * @return ָ���ؿ��ĵ�ͼ����
	 */
	public static Map getMap(int level) {
		return getMap(String.valueOf(level));// �������ط���
	}

	/**
	 * ��ȡ��ͼ�����е�����ǽ��
	 * 
	 * @return ǽ�鼯��
	 */
	public List<Wall> getWalls() {
		return walls;
	}

}
