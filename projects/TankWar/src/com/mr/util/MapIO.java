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
// * ��ͼ���ݹ�����
// *
// * @author www.mingrisoft.com
// *
// */
//
//public class MapIO {
//	// ��ͼ�����ļ�·��
//	public final static String DATA_PATH = "map/data/";
//	// ��ͼԤ��ͼ·��
//	public final static String IMAGE_PATH = "map/image/";
//	// ��ͼ�����ļ���׺
//	public final static String DATA_SUFFIX = ".map";
//	// ��ͼԤ��ͼ��׺
//	public final static String IMAGE_SUFFIX = ".jpg";
//	/**
//	 * ��ȡָ�����Ƶ�ͼ������ǽ�鼯��
//	 *
//	 * @param mapName
//	 * @return
//	 */
//	public static List<Wall> readMap(String mapName) {
//		// ������Ӧ���Ƶĵ�ͼ�ļ�
//		File file = new File(DATA_PATH + mapName + DATA_SUFFIX);
//		return readMap(file);// �������ط���
//	}
//
//	/**
//	 *
//	 * ��ȡ��ͼ�ļ�������ǽ�鼯��
//	 *
//	 * @param file
//	 *            - ��ͼ�ļ�
//	 * @return �˵�ͼ�е�����ǽ�鼯��
//	 */
//	public static List<Wall> readMap(File file) {
//		Properties pro = new Properties();// �������Լ�����
//		List<Wall> walls = new ArrayList<>();// ������ǽ�鼯��
//		try {
//			pro.load(new FileInputStream(file));// ���Լ������ȡ��ͼ�ļ�
//			String brickStr = (String) pro.get(WallType.brick.name());// ��ȡ��ͼ�ļ���שǽ�������Ե��ַ�������
//			String grassStr = (String) pro.get(WallType.grass.name());// ��ȡ��ͼ�ļ��вݵ��������Ե��ַ�������
//			String riverStr = (String) pro.get(WallType.river.name());// ��ȡ��ͼ�ļ��к����������Ե��ַ�������
//			String ironStr = (String) pro.get(WallType.iron.name());// ��ȡ��ͼ�ļ�����ǽ�������Ե��ַ�������
//			if (brickStr != null) {// �����ȡ��שǽ���ݲ���null
//				walls.addAll(readWall(brickStr, WallType.brick));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
//			}
//			if (grassStr != null) {// �����ȡ�Ĳݵ����ݲ���null
//				walls.addAll(readWall(grassStr, WallType.grass));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
//			}
//			if (riverStr != null) {// �����ȡ�ĺ������ݲ���null
//				walls.addAll(readWall(riverStr, WallType.river));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
//			}
//			if (ironStr != null) {// �����ȡ����ǽ���ݲ���null
//				walls.addAll(readWall(ironStr, WallType.iron));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
//			}
//			return walls;// ������ǽ�鼯��
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * ����ǽ������
//	 *
//	 * @param data
//	 *            - ǽ�����������ַ���
//	 * @param type
//	 *            - ǽ������
//	 * @return ǽ�鼯��
//	 */
//	private static List<Wall> readWall(String data, WallType type) {
//		String walls[] = data.split(";");// ʹ�á�;���ָ��ַ���
//		Wall wall;// ����ǽ�����
//		List<Wall> w = new LinkedList<>();// ����ǽ�鼯��
//		switch (type) {// �ж�̹������
//		case brick:// �����שǽ
//			for (String wStr : walls) {// �����ָ���
//				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
//				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
//				wall = new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ���שǽ����
//				w.add(wall);// ��������Ӵ�ǽ��
//			}
//			break;
//		case river:// ����Ǻ���
//			for (String wStr : walls) {// �����ָ���
//				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
//				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
//				wall = new RiverWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ�����������
//				w.add(wall);// ��������Ӵ�ǽ��
//			}
//			break;
//		case grass:// ����ǲݵ�
//			for (String wStr : walls) {// �����ָ���
//				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
//				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
//				wall = new GrassWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ����ݵض���
//				w.add(wall);// ��������Ӵ�ǽ��
//			}
//			break;
//		case iron:// �������ǽ
//			for (String wStr : walls) {// �����ָ���
//				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
//				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
//				wall = new IronWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ�����ǽ����
//				w.add(wall);// ��������Ӵ�ǽ��
//			}
//			break;
//		}
//		return w;// ����ǽ�鼯��
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
 * �������Դ��ļ��ж���x, y������
 * �����Զ���ͼƬ��·������
 * ����ǽ�����Ͳ�����֮
 *
 * */
/**
 * û��ʹ�õ�ö��ǽ��
 * Ҳ���Բ�����ImageUtil��
 * */
public class MapIO {
	//��ͼ�����ļ�·��
	public static final String DATA_PATH = "map/data/";
	//��ͼԤ����ͼƬ·��
	public static final String IMAGE_PATH = "map/image/";
	//��ͼ�����ļ��ĺ�׺
	public static final String DATA_SUFFIX = ".map";
	//��ͼԤ��ͼƬ�ĺ�׺
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
			pro.load(new FileInputStream(new File("map/data/wallType.map"))); //���Դ��������ж�ȡ
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
			System.out.println("�Ҳ���wallType�ļ�!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("wallType�ļ���ȡʧ��!");
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
						Integer.parseInt(axes[1])}; //�Ƿ����ֱ��ת����int����
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

