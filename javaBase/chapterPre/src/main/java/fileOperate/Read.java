package fileOperate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Read {
	private File file;
	public Read() {
		// TODO Auto-generated constructor stub
		this.file = new File("txt\\testSeperator.txt");
	}

	public Read(String s) {
		// TODO Auto-generated method stub
		this.file = new File(s);
	}
	public void read() throws FileNotFoundException {
		if(!file.exists()) {
			System.out.println("�ļ�������");
			return ;
		}
		try(
			Scanner scanner = new Scanner(file);	
		){
			System.out.println("����������:");
			while(scanner.hasNext()) {
				String ans = scanner.nextLine();
				System.out.println( ans);
			}
		}
	}
}
