package IOstream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileWriteAndOutputStream {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("write.txt");
		try {
			//�����������ļ������Ҳ���
			OutputStream outputStream = new FileOutputStream(file);
			//���뻺����
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
			int a = 0;
			while((a = System.in.read()) != -1) {
				bufferedOutputStream.write(a);
			}
			bufferedOutputStream.flush();
			if(bufferedOutputStream!=null)bufferedOutputStream.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
