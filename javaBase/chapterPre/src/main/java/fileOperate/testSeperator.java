package fileOperate;

import java.util.Scanner;

public class testSeperator {

	static void testScanner() {
		String s = System.getProperty("line.separator");
		char[] ch = s.toCharArray();
		for(int i = 0;i < ch.length;i++) {
			System.out.println((int)ch[i]);
		}
		System.out.println((int)'\r' + " " + (int)'\n');
		System.out.println(s);
		Scanner scanner = new Scanner(System.in);
		int x = scanner.nextInt();
		String string = scanner.nextLine();
		System.out.println(x);
		System.out.println(string);
		scanner.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testScanner();
	}

}
