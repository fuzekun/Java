package solveWrong;

import java.util.Scanner;

public class TestCatch {
	
	static int quotient(int number1,int number2){
		if(number2 == 0) {
			throw new ArithmeticException("��0����a");
		}
		return number1 / number2;
	}
	public static void Main() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("Enter two integers");
		int number1 = input.nextInt();
		int number2 = input.nextInt();
		input.close();
		try {
			
			int result = quotient(number1,number2);
			System.out.println("����"+result);
		}
		catch(ArithmeticException ex) {
			//System.out.println(ex.getMessage());
		}
		System.out.println("Execution continue");
	}
	public static void Test() {
		try {
			int value = 50;
			if(value < 40) {
				throw new Exception("value is too small");
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("Continue after the catch block");
	}
	
	public static boolean isNumber(String token) {
		try {
			Double.parseDouble(token);
			return true;
		}
		catch(NumberFormatException ex) {
			return false;
		}
	}
	
	public static boolean isNumber2(String token) {
		char[] ch = token.toCharArray();
		for(int i = 0; i < ch.length;i++) {
			if(!(Character.isDigit(ch[i]) || ch[i]== '.') )return false;
		}
		return true;
	}
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		if(isNumber("fda")) {
			System.out.print("������");
		}
		else System.out.println("��������");
		if(isNumber2("123.0")) {
			System.out.println("������");
		}
		else {
			System.out.println("��������");
		}
		//Main();
		//Test();
	}

}
