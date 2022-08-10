package Lamba;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Thread.TestSyn2;
import javafx.application.Platform;
//���ʽ�Ļ����﷨
public class Base {
	Object o1 = new Object(), o2 = new Object(); //��������������ʵ���Դ
	String [] s = {"fda", "Fda", "Fda"};
	Integer sum = 0;
	List<String> ss = Arrays.asList(s);
	public void test() {
		String[] atp = {"Rafael Nadal", "Novak Djokovic",  
			       "Stanislas Wawrinka",  
			       "David Ferrer","Roger Federer",  
			       "Andy Murray","Tomas Berdych",  
			       "Juan Martin Del Potro"};  
			List<String> players =  Arrays.asList(atp);  
			  
			// ��ǰ��ѭ����ʽ  
			for (String player : players) {  
			     System.out.print(player + "; ");  
			}  
			 
			// ʹ�� lambda ���ʽ�Լ���������(functional operation)  
			players.forEach((play)-> System.out.print(play + "; "));
			ss.forEach((a) -> System.out.println(a));
	}
	
	public void  userThread() {
		
		//ʹ��Lamta���ʽд��������
		new Thread( () -> {
			System.out.println("�߳�һ����....");
			synchronized (o1) {
				System.out.println("�õ�o1,�ȴ�o2");
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					System.out.println("��һ��sleep����");
				}
				synchronized (o2) {
					System.out.println("�������");
				}
			}
		 }).start();
		new Thread( () -> {
			System.out.println("�̶߳�����...");
			synchronized(o2) {
			    System.out.println("�õ�o2,�ȴ�o1");
				try {
					Thread.sleep(500);
				}catch(Exception e) {
				System.out.println("�ڶ���sleep����");
				}
				synchronized (o1) {
					System.out.println("����2���");
				}
			}
     	}) .start();
	}
	
	//����synchronize����ס�����
	public void testSyn() {
		new Thread(() -> {
			synchronized (sum) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("sumΪ :" + sum);
			}
		}).start();
		new Thread(() -> {
			sum = 100; 						//����һ���������޸�sum��ֵ.
		} ).start(); 
	}
	

	public static void main(String[] args) {
		Base base = new Base();
		//base.userThread();
		base.testSyn();
	}
 }
