package Class;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//����ջ
		try {
			testStackInterger();
		}
		catch (StackOverflowError e) {
			// TODO: handle exception
			System.out.println(e.toString());
			System.out.println("ջ���");
		}
		catch (StackEmptyException e) {
			// TODO: handle exception
			//e.printStackTrace();
			//System.out.println(e.getMessage());
			System.out.println(e.toString());
			StackTraceElement[] elements = e.getStackTrace();
			for(int i = 0;i < elements.length;i++) {
				System.out.print("������ļ���" + elements[i].getFileName());
				System.out.println(",��������" +elements[i].getLineNumber());
			}
		}
		try {
			testMyStack();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
	}

	static void testStackInterger() throws StackEmptyException,StackOverflowError{
		StackOfInteger stack = new StackOfInteger();
		//int a = stack.pop();
		//System.out.println(a);
		stack.push(1);
		stack.push(2);
		if(stack.empty())System.out.println("ջ����");
		for(int i = 0;i <= 16;i++) {
			stack.push(i);
		}
		for(int i = 0;i < 18;i++) {//����ĳ�19�ͻ�ջ���
			System.out.println(stack.pop());
		}
		for(int i = 0;i <= 100;i++) {
			stack.push(i);
		}
		for(int i = 0;i <= 100;i++) {
			System.out.println(stack.pop());
		}
	}
	
	static void testMyStack() throws StackEmptyException,StackOverflowError{
		MyStack stack = new MyStack();
		for(int i = 0;i < 10;i++) {
			Integer a = new Integer(i);
			stack.push(a);
		}
		for(int i = 0;i < 11;i++) {
			Integer b = (Integer) stack.pop();
			System.out.println(b.toString());
		}
	}
}
