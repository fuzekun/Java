package Thread;
import java.util.concurrent.*;
public class ExcutorDemo {
	
	
	public static void main(String[] args) {
		//创建
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		//执行
		executor.execute(new PrintChar('a',100));
		executor.execute(new PrintChar('b',100));
		executor.execute(new PrintNum(100));
		
		//关闭
		executor.shutdown();
	}
	
}
