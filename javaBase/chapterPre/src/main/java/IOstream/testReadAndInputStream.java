package IOstream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

public class testReadAndInputStream {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//ʹ���ֽ�����ȡ�ļ�
        File file = new File("write.txt");
    	String line;
    	StringBuffer stringBuffer = new StringBuffer();
        try {
        	//���ֽ��������ļ�
        	InputStream inputStream = new FileInputStream(file);
        	//��������ֽ���ת�����ַ���
        	Reader reader = new InputStreamReader(inputStream,"UTF-8");
        	//���ַ������뻺����
        	BufferedReader bufferedReader = new BufferedReader(reader);
        	//�������ķ����ַ���������
        	while((line = bufferedReader.readLine())  != null ) {
        		stringBuffer.append(line);
        	}
        	if(bufferedReader != null) {//�ճ��ж�
        		bufferedReader.close();
        	}
        	//��ȡ������������
        	String content = stringBuffer.toString();
        	System.out.println(content);
        }catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        try {
            //���ֱ��ʹ���ַ�����ȡ�ļ�����
            Reader reader = new FileReader(new File("write.txt"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            while((line = bufferedReader.readLine()) != null) {
            	System.out.println(line);
            }
            if(bufferedReader!=null) {
            	bufferedReader.close();
            }
        }
        catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        }
        //��������������������ֽ�����ת�����ַ�����Ȼ�����huan������
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	//���������������ַ������뻺��
    	BufferedWriter bw = new BufferedWriter(new FileWriter(new File("file.txt")));
    	String s = null;
    	//���ڴ滺����д���ļ�������
		while((s = br.readLine()).length() > 0) {
    		bw.write(s);
    	}
    	bw.flush();
		if(bw != null) {
			bw.close();
		}
        FileOutputStream fos = new FileOutputStream("write.txt");//�ֽ������ַ���Ϣд���ļ�ô������
        int a;
        while ((a = System.in.read()) != -1) {
            fos.write(a);
        }
        fos.close();
        FileInputStream fis = new FileInputStream("write.txt");//���ڶ�ȡ�������ļ�
        byte[] b = new byte[16];
        fis.read(b);
    	while((a = System.in.read()) != -1) {
        	System.out.print(a + "");
        }
    	fis.close();
	}
}
