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
		
		//使用字节流读取文件
        File file = new File("write.txt");
    	String line;
    	StringBuffer stringBuffer = new StringBuffer();
        try {
        	//以字节流读入文件
        	InputStream inputStream = new FileInputStream(file);
        	//将读入的字节流转化成字符流
        	Reader reader = new InputStreamReader(inputStream,"UTF-8");
        	//将字符流加入缓冲区
        	BufferedReader bufferedReader = new BufferedReader(reader);
        	//将读到的放入字符串缓冲区
        	while((line = bufferedReader.readLine())  != null ) {
        		stringBuffer.append(line);
        	}
        	if(bufferedReader != null) {//日常判断
        		bufferedReader.close();
        	}
        	//读取缓冲区的内容
        	String content = stringBuffer.toString();
        	System.out.println(content);
        }catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        try {
            //如果直接使用字符流读取文件！！
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
        //创建缓冲区，将输入的字节流，转化成字符流，然后放入huan缓冲区
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	//创建缓冲区，将字符流加入缓冲
    	BufferedWriter bw = new BufferedWriter(new FileWriter(new File("file.txt")));
    	String s = null;
    	//将内存缓冲区写入文件缓冲区
		while((s = br.readLine()).length() > 0) {
    		bw.write(s);
    	}
    	bw.flush();
		if(bw != null) {
			bw.close();
		}
        FileOutputStream fos = new FileOutputStream("write.txt");//字节流将字符信息写入文件么的问题
        int a;
        while ((a = System.in.read()) != -1) {
            fos.write(a);
        }
        fos.close();
        FileInputStream fis = new FileInputStream("write.txt");//用于读取二进制文件
        byte[] b = new byte[16];
        fis.read(b);
    	while((a = System.in.read()) != -1) {
        	System.out.print(a + "");
        }
    	fis.close();
	}
}
