package IOstream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class EncodeTest {

	private static void readBuff(byte [] buff) throws IOException {
	       ByteArrayInputStream in =new ByteArrayInputStream(buff);
	        int data;
	        while((data=in.read())!=-1)   System.out.print(data+"  ");
	        System.out.println();     in.close();     }
	 
	   public static void main(String args[]) throws IOException {
		   //һ������16λ���룬���õ������ֽڱ��档
	       System.out.println("�ڴ��в���unicode�ַ����룺" );
	       char   c='��';
	       int lowBit=c&0xFF;     int highBit=(c&0xFF00)>>8;
	       System.out.println(""+lowBit+"   "+highBit);
	       String s="��";
	       System.out.println("���ز���ϵͳĬ���ַ����룺");
	       readBuff(s.getBytes());
	       System.out.println("����GBK�ַ����룺");
	       readBuff(s.getBytes("GBK"));
	       System.out.println("����UTF-8�ַ����룺");    
	       readBuff(s.getBytes("utf-8"));
	   }
}