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

		try(//��try�ؼ����������Զ��ر�
			//����file
				PrintWriter  printWriter = new PrintWriter(file);
				//����һ����д�Ķ���
				Scanner scanner = new Scanner(System.in);
		){//д��
			printWriter.println("\t\t\t�ļ�����");
			printWriter.println("  �ļ�����ɾ��ģ��ж��ļ��Ƿ���ڣ��ļ���������Ϣ���޸�ʱ�䣬��С��Ȩ����Ϣ�ȣ� ��Ŀ¼����");
		}
		
	}
}
