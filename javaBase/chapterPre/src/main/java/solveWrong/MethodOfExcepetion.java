package solveWrong;

/*
 * �����쳣throws �����������
 * 
 * 
 * */

public class MethodOfExcepetion {

	private static int sum(int[] list) throws IndexOutOfBoundsException{
		int ans = 0;
		for(int i = 0;i <= list.length;i++) {
			ans += list[i];
		}
		return ans;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
 		try {
			System.out.println(sum(new int[]{1,2,3,4,5,6}));
			System.out.println(1/0);//��û�б�ִ��
			System.out.println(5/1);
		}
 		catch(ArithmeticException e) {
 			System.out.println("��0����");
 		}
 		
 		catch(RuntimeException e) {
 			System.out.println("printStackTrace");
 			e.printStackTrace();
 			System.out.println("toString " + e.toString());
 			System.out.println("getMessage " + e.getMessage());
 			StackTraceElement[] s = e.getStackTrace();
 			for(int i = 0;i < s.length;i++) {
 				System.out.print("Method " + s[i].getMethodName());
 				System.out.print("(" + s[i].getClassName() + ":");
 				System.out.println(s[i].getLineNumber() + ")");
 			}
 		}
 		catch(Exception e){
 			System.out.println("����c");
 		}
 		finally {
			System.out.println("�����������");
		}
 		System.out.println("��ֹ");
	}
}