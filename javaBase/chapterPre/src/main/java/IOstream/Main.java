package IOstream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		for(int i = 0;i < args.length;i++) {
			System.out.println("args[" + i + "] is " + args[i]);

		}
		
		testFile();
	}
	static void testInput() {
		
		//Ouput What you inputed;
		int b;
		try {
			System.out.println("please Input:");
			while ((b = System.in.read()) != -1) {
				System.out.print((char) b);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	static void testErrorInput() {
		String s;
		// 创建缓冲区阅读器从键盘逐行读入数据
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);
		System.out.println("Unix系统: ctrl-d 或 ctrl-c 退出"
				+ "\nWindows系统: ctrl-z 退出");
		try {
			// 读一行数据，并标准输出至显示器
			s = in.readLine();
			// readLine()方法运行时若发生I/O错误，将抛出IOException异常
			while (s != null) {
				System.out.println("Read: " + s);
				s = in.readLine();
			}
			// 关闭缓冲阅读器
			in.close();
		} catch (IOException e) { // Catch any IO exceptions.
			e.printStackTrace();
		}
	}
	static void testFile() throws IOException{

		File dir = new File("");
		File f1 = new File(dir, "fileOne.txt");
		File f2 = new File(dir, "fileTwo.java");
		// 文件对象创建后，指定的文件或目录不一定物理上存在
		if (!dir.exists())
			dir.mkdir();
		if (!f1.exists())
			f1.createNewFile();
		if (!f2.exists())
			f2.createNewFile();
		System.out.println("f1's AbsolutePath=  " + f1.getAbsolutePath());
		System.out.println("f1 Canread=" + f1.canRead());
		System.out.println("f1's len= " + f1.length());
		String[] FL;
		FL = dir.list();
		for (int i = 0; i < FL.length; i++) {
			System.out.println(FL[i] + " is in" + dir);
		}

	}
	

}
