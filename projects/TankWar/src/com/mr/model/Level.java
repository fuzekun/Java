package com.mr.model;

import java.io.File;
import java.io.FileNotFoundException;

import com.mr.util.MapIO;

/**
 * �ؿ�
 * 
 * @author www.mingrisoft.com
 *
 */
public class Level {
	private static int nextLevel = 12;// ��һ�ؼ�¼
	private static int previsousLevel = -1;// ��һ�ؼ�¼
	private static int count = 13;// �ؿ�����

	static {
		try {
			File f = new File(MapIO.DATA_PATH);// ������ͼ�ļ�Ŀ¼�ļ���
			if (!f.exists()) {// ������ļ��в�����
				throw new FileNotFoundException("��ͼ�ļ�ȱʧ��");// �׳��쳣
			}
			File fs[] = f.listFiles();// ��ȡ��ͼ�ļ�Ŀ¼�ļ����µ������ļ�����
			count = fs.length;// ���ļ�������Ϊ�ؿ�����
			if (count == 0) {// ���Ŀ¼��û���κ��ļ�
				throw new FileNotFoundException("��ͼ�ļ�ȱʧ��");// �׳��쳣
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��һ��
	 * 
	 * @return �ؿ���
	 */
	public static int nextLevel() {
		previsousLevel = nextLevel;// ��¼��һ�عؿ��Ÿı�֮ǰ��ֵ
		nextLevel++;// ��һ�ؼ�¼+1
		if (nextLevel > count) {// ����ؿ������ڹؿ�����
			nextLevel = 1;// �ӵ�һ�ؿ�ʼ
		}
		return previsousLevel;// ������һ�ص�ֵ
	}

	/**
	 * ��һ��
	 * 
	 * @return �ؿ���
	 */
	public static int previsousLevel() {
		return previsousLevel;// ������һ�ص�ֵ
	}
}
