package DataConstructure;

import java.io.CharArrayWriter;
import java.io.Writer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		Writer writer = new CharArrayWriter();	
		for(long i = 0;i < 259320823;i++) {
			
		}
		
		time = System.currentTimeMillis() - time;
		System.out.println("程序运行了" + time + "ms");
	
//		long time = System.currentTimeMillis();
//		int i = 0;
//		for(;;i++) {
//			if(System.currentTimeMillis() - time > 1000)break;
//		}
//		time = System.currentTimeMillis() - time;
//		System.out.println("程序" + time +"ms时间内执行了" + i + "条语句" );
	}
}
