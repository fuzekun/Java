package array;
/*
 * author ������
 * time 2019/3/14
 * software eclipse
 * */
//ѧϰ�쳣�����Լ��ı���I/O

/*����
 * 1.static ʹ�õ�ԭ��:
 * 2.java���ǲ��Ǻ�c++�еĶ�̬һ��
 * 3.java�е�ȡ����c++�е�ʲô����������
 * 4.java�еĳ�����ĸ���
 * 5.��ô��java����excel�ļ�
 * 6.��ô��java�����ı��ļ�
 * 7.��ô��javaдһ��ͼ��ݹ���ϵͳ
 * 8.java�еĺ�����ô����ַ
 * 9.java����û��ģ����ô��
 * 10.ʲô���ı���ǿ��ת�������߾���ת��Ϊ�;��ȣ�
 * */

/*
 * 1.�����ʵ�������󣬾Ͳ���ʹ�÷Ǿ�̬�ķ����ͱ���������ʵ������Ҳ����ʹ��ʵ�����ķ����ͱ�������Ϊ��̬�ĺ�ʵ����֮������ݷ���������һ��λ��
 * 2.
 * 
 * */
import java.util.*;
public class chaper12 {
	 
	//�������͵����뺯��
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
	
	static int gcd(int a,int b) {//ŷ�������Լ��
		return b == 0 ? a:gcd(b,a % b);
	}
	
	static int getRodome() {//����'a'��'z'��ask��
		int ans = (int)((int)'a' + Math.random() * (int)'z' - (int)'a' + 1);
		return ans;
	}
	
	static void testChange() {
		/*���Ը�������֮���ת������
		 * */
		String a  = "a";
		char[] A = a.toCharArray();
		char b = 'b';
		String s = b+"";
		char c = a.charAt(0);
		System.out.println(A[0] + b + c + s);//��ֹ˵��Щ����û�ù�
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		chaper12 tmp = new chaper12();
		System.out.println(tmp.tt(12));
		PR("ʱ��:\n");
		tmp.testData();
		tmp.getData();
		
		PR("10��8�����Լ����:");
		PR(Integer.toString(gcd(10,8)) + "\n");
		
		PR((char)getRodome() + "");
		PR("\n");
		
		PR("���Դ�������:\n");
		
	}
}
