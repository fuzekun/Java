//package com.mr.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Properties;
//
//import com.mr.model.wall.BrickWall;
//import com.mr.model.wall.GrassWall;
//import com.mr.model.wall.IronWall;
//import com.mr.model.wall.RiverWall;
//import com.mr.model.wall.Wall;
//import com.mr.type.WallType;
//
///**
// * 地图数据工具类
// *
// * @author www.mingrisoft.com
// *
// */
//
//public class MapIO {
//	// 地图数据文件路径
//	public final static String DATA_PATH = "map/data/";
//	// 地图预览图路径
//	public final static String IMAGE_PATH = "map/image/";
//	// 地图数据文件后缀
//	public final static String DATA_SUFFIX = ".map";
//	// 地图预览图后缀
//	public final static String IMAGE_SUFFIX = ".jpg";
//	/**
//	 * 获取指定名称地图的所有墙块集合
//	 *
//	 * @param mapName
//	 * @return
//	 */
//	public static List<Wall> readMap(String mapName) {
//		// 创建对应名称的地图文件
//		File file = new File(DATA_PATH + mapName + DATA_SUFFIX);
//		return readMap(file);// 调用重载方法
//	}
//
//	/**
//	 *
//	 * 获取地图文件的所有墙块集合
//	 *
//	 * @param file
//	 *            - 地图文件
//	 * @return 此地图中的所有墙块集合
//	 */
//	public static List<Wall> readMap(File file) {
//		Properties pro = new Properties();// 创建属性集对象
//		List<Wall> walls = new ArrayList<>();// 创建总墙块集合
//		try {
//			pro.load(new FileInputStream(file));// 属性集对象读取地图文件
//			String brickStr = (String) pro.get(WallType.brick.name());// 读取地图文件中砖墙名称属性的字符串数据
//			String grassStr = (String) pro.get(WallType.grass.name());// 读取地图文件中草地名称属性的字符串数据
//			String riverStr = (String) pro.get(WallType.river.name());// 读取地图文件中河流名称属性的字符串数据
//			String ironStr = (String) pro.get(WallType.iron.name());// 读取地图文件中铁墙名称属性的字符串数据
//			if (brickStr != null) {// 如果读取的砖墙数据不是null
//				walls.addAll(readWall(brickStr, WallType.brick));// 解析数据，并将数据中解析出的墙块集合添加到总墙块集合中
//			}
//			if (grassStr != null) {// 如果读取的草地数据不是null
//				walls.addAll(readWall(grassStr, WallType.grass));// 解析数据，并将数据中解析出的墙块集合添加到总墙块集合中
//			}
//			if (riverStr != null) {// 如果读取的河流数据不是null
//				walls.addAll(readWall(riverStr, WallType.river));// 解析数据，并将数据中解析出的墙块集合添加到总墙块集合中
//			}
//			if (ironStr != null) {// 如果读取的铁墙数据不是null
//				walls.addAll(readWall(ironStr, WallType.iron));// 解析数据，并将数据中解析出的墙块集合添加到总墙块集合中
//			}
//			return walls;// 返回总墙块集合
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 解析墙块数据
//	 *
//	 * @param data
//	 *            - 墙块坐标数据字符串
//	 * @param type
//	 *            - 墙块类型
//	 * @return 墙块集合
//	 */
//	private static List<Wall> readWall(String data, WallType type) {
//		String walls[] = data.split(";");// 使用“;”分割字符串
//		Wall wall;// 创建墙块对象
//		List<Wall> w = new LinkedList<>();// 创建墙块集合
//		switch (type) {// 判断坦克类型
//		case brick:// 如果是砖墙
//			for (String wStr : walls) {// 遍历分割结果
//				String axes[] = wStr.split(",");// 使用“,”分割字符串
//				// 创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
//				wall = new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// 在此坐标上创建砖墙对象
//				w.add(wall);// 集合中添加此墙块
//			}
//			break;
//		case river:// 如果是河流
//			for (String wStr : walls) {// 遍历分割结果
//				String axes[] = wStr.split(",");// 使用“,”分割字符串
//				// 创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
//				wall = new RiverWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// 在此坐标上创建河流对象
//				w.add(wall);// 集合中添加此墙块
//			}
//			break;
//		case grass:// 如果是草地
//			for (String wStr : walls) {// 遍历分割结果
//				String axes[] = wStr.split(",");// 使用“,”分割字符串
//				// 创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
//				wall = new GrassWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// 在此坐标上创建草地对象
//				w.add(wall);// 集合中添加此墙块
//			}
//			break;
//		case iron:// 如果是铁墙
//			for (String wStr : walls) {// 遍历分割结果
//				String axes[] = wStr.split(",");// 使用“,”分割字符串
//				// 创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
//				wall = new IronWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// 在此坐标上创建铁墙对象
//				w.add(wall);// 集合中添加此墙块
//			}
//			break;
//		}
//		return w;// 返回墙块集合
//	}
//}

package com.mr.util;

import com.mr.model.wall.Wall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
/**
 *
 * 不仅可以从文件中读出x, y的坐标
 * 还可以读出图片的路径名称
 * 读出墙的类型并创建之
 *
 * */
/**
 * 没有使用到枚举墙类
 * 也可以不适用ImageUtil类
 * */
public class MapIO {
	//地图数据文件路径
	public static final String DATA_PATH = "map/data/";
	//地图预览的图片路径
	public static final String IMAGE_PATH = "map/image/";
	//地图数据文件的后缀
	public static final String DATA_SUFFIX = ".map";
	//地图预览图片的后缀
	public static final String IMAGE_SUFFIX = ".jpg";

	public static List<Wall> readMap(String mapName){
		File file = new File(DATA_PATH + mapName + DATA_SUFFIX);
		return readMap(file);
	}
	public static List<Wall> readMap(File file) {
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<Wall> walls = new ArrayList<>();
		try {
			pro.load(new FileInputStream(new File("map/data/wallType.map"))); //可以从配置类中读取
			pro2.load(new FileInputStream(file));
			String str = (String) pro.get("wallType");
			String[] types = str.split(";");
//            List<String>nonEmptyWall = new ArrayList<>();
			for (String type : types) {
				String tmp = (String) pro2.get(type);
				if (tmp != null) {
//                    System.out.println(type);
					String typeClass = (String) pro.get(type + "WallClass");
//                    System.out.println(pro.get(type + "WallClass"));
					walls.addAll(readWall(tmp, typeClass));
				}
			}
			return walls;
		} catch (FileNotFoundException e) {
			System.out.println("找不到wallType文件!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("wallType文件读取失败!");
		}
		return null;
	}

	private static List<Wall> readWall(String data, String typeClass) {
		String walls[] = data.split(";");
		Wall wall;
		List<Wall> w = new LinkedList<>();
		for (String wStr : walls) {
			String axes[] = wStr.split(",");
			try {
				Class c = Class.forName(typeClass);
				Class[] parameterTypes = {int.class, int.class};
				Constructor con = c.getConstructor(parameterTypes);
				Object[] papameters = {Integer.parseInt(axes[0]),
						Integer.parseInt(axes[1])}; //是否可以直接转换成int类型
				wall = (Wall) con.newInstance(papameters);
				w.add(wall);
//                wall = (Wall) Class.forName(typeClass).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return w;
	}
}

