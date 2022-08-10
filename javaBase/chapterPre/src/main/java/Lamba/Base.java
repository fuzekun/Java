package Lamba;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Thread.TestSyn2;
import javafx.application.Platform;
//表达式的基本语法
public class Base {
	Object o1 = new Object(), o2 = new Object(); //创建两个互斥访问的资源
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
			  
			// 以前的循环方式  
			for (String player : players) {  
			     System.out.print(player + "; ");  
			}  
			 
			// 使用 lambda 表达式以及函数操作(functional operation)  
			players.forEach((play)-> System.out.print(play + "; "));
			ss.forEach((a) -> System.out.println(a));
	}
	
	public void  userThread() {
		
		//使用Lamta表达式写死锁问题
		new Thread( () -> {
			System.out.println("线程一启动....");
			synchronized (o1) {
				System.out.println("得到o1,等待o2");
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					System.out.println("第一个sleep报错");
				}
				synchronized (o2) {
					System.out.println("任务完成");
				}
			}
		 }).start();
		new Thread( () -> {
			System.out.println("线程二启动...");
			synchronized(o2) {
			    System.out.println("得到o2,等待o1");
				try {
					Thread.sleep(500);
				}catch(Exception e) {
				System.out.println("第二个sleep报错");
				}
				synchronized (o1) {
					System.out.println("任务2完成");
				}
			}
     	}) .start();
	}
	
	//测试synchronize锁不住的情况
	public void testSyn() {
		new Thread(() -> {
			synchronized (sum) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("sum为 :" + sum);
			}
		}).start();
		new Thread(() -> {
			sum = 100; 						//在另一个程序中修改sum的值.
		} ).start(); 
	}
	

	public static void main(String[] args) {
		Base base = new Base();
		//base.userThread();
		base.testSyn();
	}
 }
