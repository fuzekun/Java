package fileOperate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * 
 * 1.����ͳ���һ�Ǯ
 * 2.����ͳ�Ƹ������Ǯ��,�����ı����м���
 * 3.����ͳ�ƻ��ھ���ĳһ�����ϵ�Ǯ��
 * 4.�����������û��������ķ��࣬��ô�����Ѵ������ж������й���
 * */

public class Total {

	public static void main(String[] args) {
		
		String [] kind = {"�Է�", "����", "ѧϰ", "��ʳˮ��"};    //��Ǯ������
		double sum = 0, day = 0, avrage = 0;				//��Ǯ���������������Լ�ƽ��ֵ
		String [] seq; 										//��Ǯ����
		
		
		try {
			InputStreamReader input = new InputStreamReader(new FileInputStream(new File("�˱�.txt")),"utf-8");
			BufferedReader bufferedReade = new BufferedReader(input);
			String s;
			System.out.print("������Ǯ��Ϊ:");
			while((s = bufferedReade.readLine()) != null) {
				String []tmp = s.split(":");
				if(tmp[0].equals("����")) {
					day++;
					sum += Double.parseDouble(tmp[1]);
				}
			}
			System.out.println(sum);
			System.out.println("����Ϊ:" + day);
			System.out.println("��Ǯ��ƽ����Ϊ:" + (sum / day));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ļ����� :" + e.getMessage());
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("�ļ���ȡ����" + e.getMessage());
		}
		

	}

}
