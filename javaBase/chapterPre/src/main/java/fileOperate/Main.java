package fileOperate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {
	
	

	static void testSeperator()throws FileNotFoundException{
		File file = new File("txt\\testSeperator.txt");
		
		try(
				//�����ļ�
				PrintWriter printWriter = new PrintWriter(file);
			){
				printWriter.println("34 5 6");//д������
			}
		try(
				Scanner scanner = new Scanner(file);
			){
			while(scanner.hasNext()) {
				int intValue = scanner.nextInt();
				String  lineString = scanner.nextLine();
				System.out.println(lineString);
				System.out.println(intValue);
			}
		}
	}

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		writeData w = new writeData();
		Read read = new Read();
		try {
			w.write();
			System.out.println("�ļ�д�����");
			read.read();
			//testSeperator();
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
