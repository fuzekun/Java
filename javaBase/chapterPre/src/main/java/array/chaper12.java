package array;
/*
 * author 付泽坤
 * time 2019/3/14
 * software eclipse
 * */
//学习异常处理以及文本的I/O

/*问题
 * 1.static 使用的原因:
 * 2.java中是不是和c++中的多态一样
 * 3.java中的取出了c++中的什么东西？（）
 * 4.java中的抽象类的概念
 * 5.怎么用java操作excel文件
 * 6.怎么用java操作文本文件
 * 7.怎么用java写一个图书馆管理系统
 * 8.java中的函数怎么传地址
 * 9.java中又没有模板怎么用
 * 10.什么样的必须强制转换：（高精度转化为低精度）
 * */

/*
 * 1.如果不实例化对象，就不能使用非静态的方法和变量。但是实例化，也可以使用实例化的方法和变量，因为静态的和实例化之后的数据方法不存在一个位置
 * 2.
 * 
 * */
import java.util.*;
public class chaper12 {
	 
	//四种类型的输入函数
	static void SC(int a) {
		Scanner sc = new Scanner(System.in);
		a = sc.nextInt();
		sc.close();
	}
	static void SC(double a) {
		Scanner sc = new Scanner(System.in);
		a = sc.nextDouble();
		sc.close();
	}
	static void SC(long a) {
		Scanner sc = new Scanner(System.in);
		a = sc.nextLong();
		sc.close();
	}
	static void SC(String s) {
		Scanner sc = new Scanner(System.in);
		s = sc.nextLine();
		sc.close();
	}
	
	static void PR(String s) {
		System.out.print(s);
	}
	
	int tt(int a) {
		return a;
	}
	
	void testData() {
		java.util.Date data = new java.util.Date();
		System.out.println(data.toString());
	}
	
	void getData() {
		long a = System.currentTimeMillis();
		long totalSeconds = a / 1000;
		long Seconds = totalSeconds % 60;
		long totalMimutes = totalSeconds / 60;
		long Mimutes = totalMimutes % 60;
		long totalHours = totalMimutes / 60;
		long Hours = totalHours % 24 + 8;
		String s = Hours + ":" + Mimutes + ":" + Seconds;
		PR(s+"\n");
	}
	
	static int gcd(int a,int b) {//欧几里得求公约数
		return b == 0 ? a:gcd(b,a % b);
	}
	
	static int getRodome() {//产生'a'到'z'的ask码
		int ans = (int)((int)'a' + Math.random() * (int)'z' - (int)'a' + 1);
		return ans;
	}
	
	static void testChange() {
		/*测试各种类型之间的转换问题
		 * */
		String a  = "a";
		char[] A = a.toCharArray();
		char b = 'b';
		String s = b+"";
		char c = a.charAt(0);
		System.out.println(A[0] + b + c + s);//防止说这些东西没用过
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		chaper12 tmp = new chaper12();
		System.out.println(tmp.tt(12));
		PR("时间:\n");
		tmp.testData();
		tmp.getData();
		
		PR("10和8的最大公约数是:");
		PR(Integer.toString(gcd(10,8)) + "\n");
		
		PR((char)getRodome() + "");
		PR("\n");
		
		PR("测试大整数类:\n");
		
	}
}
