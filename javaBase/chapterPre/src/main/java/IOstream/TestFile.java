package IOstream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{	
		       FileInputStream rf=new   FileInputStream("e:\\eclipse��װ��java�ļ�\\���ļ��нṹ.txt");
		       int n=512;   byte  buffer[]=new  byte[n];   
		       while((rf.read(buffer,0,n)!=-1)&&(n>0)){
		           System.out.println(new String(buffer) );
		        }
		        System.out.println();
		        rf.close();
		} catch(IOException  IOe){	    
		      System.out.println(IOe.toString());
		}
		try {
			System.out.println("please Input from Keyboard");
			int count, n = 512;
			byte buffer[] = new byte[n];
			count = System.in.read(buffer);
			FileOutputStream wf = new FileOutputStream("e:\\testFile.txt",true);
			wf.write(buffer, 0, count);
			wf.close(); // ����д��������ʱ������close�����ر�����
			System.out.println("Save to the write.txt");
		} catch (IOException IOe) {
			System.out.println("File Write Error!");
		}
	}
}
