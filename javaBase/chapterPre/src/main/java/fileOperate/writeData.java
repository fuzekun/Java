package fileOperate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class writeData {
	private File file;;
	public writeData(String s) {
		// TODO Auto-generated constructor stub
		this.file = new File(s);
				
	}
	
	public writeData() {
		this.file = new File("txt\\total.txt");
	}
	
	void write() throws FileNotFoundException{
		if(file.exists()) {
			System.out.println("file already exists");
			System.out.println("Do you want to remove it?\n I have rm it");
			file.delete();
		}

		try(//用try关键字声明会自动关闭
			//创建file
				PrintWriter  printWriter = new PrintWriter(file);
				//创建一个可写的对象
				Scanner scanner = new Scanner(System.in);
		){//写入
			printWriter.println("\t\t\t文件操作");
			printWriter.println("  文件的增删查改，判断文件是否存在，文件的属性信息（修改时间，大小，权限信息等） ，目录操作");
		}
		
	}
}
