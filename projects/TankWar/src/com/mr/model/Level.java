package com.mr.model;

import java.io.File;
import java.io.FileNotFoundException;

import com.mr.util.MapIO;

/**
 * 关卡
 * 
 * @author www.mingrisoft.com
 *
 */
public class Level {
	private static int nextLevel = 12;// 下一关记录
	private static int previsousLevel = -1;// 上一关记录
	private static int count = 13;// 关卡总数

	static {
		try {
			File f = new File(MapIO.DATA_PATH);// 创建地图文件目录文件夹
			if (!f.exists()) {// 如果此文件夹不存在
				throw new FileNotFoundException("地图文件缺失！");// 抛出异常
			}
			File fs[] = f.listFiles();// 获取地图文件目录文件夹下的所有文件对象
			count = fs.length;// 将文件数量作为关卡总数
			if (count == 0) {// 如果目录下没有任何文件
				throw new FileNotFoundException("地图文件缺失！");// 抛出异常
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下一关
	 * 
	 * @return 关卡号
	 */
	public static int nextLevel() {
		previsousLevel = nextLevel;// 记录下一关关卡号改变之前的值
		nextLevel++;// 下一关记录+1
		if (nextLevel > count) {// 如果关卡数大于关卡总数
			nextLevel = 1;// 从第一关开始
		}
		return previsousLevel;// 返回下一关的值
	}

	/**
	 * 上一关
	 * 
	 * @return 关卡号
	 */
	public static int previsousLevel() {
		return previsousLevel;// 返回上一关的值
	}
}
