package array;

public class StudyString {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test();
	}
	
	
	public static void test() {
		
		//��һ������'c' - 'a'
		char x = 'a';
		char y = 'c';
		System.out.println(x++);
		System.out.println(++y);
		System.out.println(x - y);
		
		//�ڶ�������import java.*����û��String
		String s;
		s = "�Ұ��л�";
		System.out.print(s);
		
		//����������i + j + k;
		int tmp1 = 1,temp2 = 2,z = 3;
		System.out.println(temp2 + tmp1 + z);
		System.out.println("tmp is" + tmp1 + temp2 + z);
	
		//����һЩ����
		s = "welcome".toUpperCase();
		System.out.println(s);
		System.out.println(s.toLowerCase());
		
		//Scanner input = new Scanner(System.in);
		//System.out.println("Enter a Line:");
		//String s1 = input.nextLine();
		String s1 = "welcome to java";
		System.out.println("what you java input is:\n" + s1);
		int ans = "Welcome to java".compareTo(s1);
		if(ans < 0)System.out.println("������Ĵ�");
		ans = "Welcome to java".compareToIgnoreCase(s1);
		if(ans == 0)System.out.println("һ��������");
	
		System.out.println(s1.startsWith("we"));
		System.out.println(s1.indexOf("o",2));
	
		String name = "Kim Jones";
		int k = name.indexOf(" ");
		String firstName = name.substring(0, k);
		String lastName = name.substring(k+ 1);
		System.out.println(firstName);
		System.out.println("lastname is "+lastName);
		
		String num = "123";//
		
		double j = Double.parseDouble(num);
		System.out.println(j);
		
		System.out.println("\t Wel \t".trim());
	}
}
