package CollectionLearn;

import java.util.Stack;

public class EvaluateExpression {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		if(args.length != 1) {
//			System.out.println("���ʽ����");
//			System.exit(1);
//		}
		try {
			System.out.println(evalueateExpression("(1 + 2) * 3 "));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//������ʽ
	public static int evalueateExpression(String expression) {
		
		//������ʽջ
		Stack<Integer>operandStack = new Stack<>();
		
		//���������ջ
		Stack<Character>operatorStack = new Stack<>();
		
		//����ո�
		expression = insertBlanks(expression);
		//�ֿ��������Ͳ�����
		String[] tokens = expression.split(" ");
		for(String token:tokens) {
			if(token.length() == 0)
				continue;
			else if(token.charAt(0) == '+' || token.charAt(0) == '-') {
				while(!operatorStack.isEmpty() && (
						operatorStack.peek() == '+'||
						operatorStack.peek() == '-'||
						operatorStack.peek() == '*'||
						operatorStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.push(token.charAt(0));
			}
			else if(token.charAt(0) == '*'|| token.charAt(0) == '/') {
				while(!operatorStack.isEmpty() && (
						operatorStack.peek() == '*'||
						operatorStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.push(token.charAt(0));
			}
			else if(token.trim().charAt(0) == '('){
				operatorStack.push('(');
			}
			else  if(token.trim().charAt(0) == ')') {
				while(operatorStack.peek() != '(') {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.pop();
			}
			else {
				operandStack.push(new Integer(token));
			}
		}
		while(!operatorStack.isEmpty()) {
			processAnOperator(operandStack, operatorStack);
		}
		return operandStack.pop();
	}
	public  static  void processAnOperator(Stack<Integer>operandStack,Stack<Character>operatorStack) {
		char op = operatorStack.pop();
		int op1 = operandStack.pop();
		int op2 = operandStack.pop();
		if(op == '+') {
			operandStack.push(op1 + op2);
		}
		if(op == '-') {
			operandStack.push(op1 - op2);
		}
		if(op == '*') {
			operandStack.push(op1 * op2);
		}
		if(op == '/') {
			operandStack.push(op1 / op2);
		}
		
	}
	public static String insertBlanks(String s) {
		String result = "";
		for(int i = 0;i < s.length();i++) {
			if(s.charAt(i) == '(' || s.charAt(i) == ')' ||
					s.charAt(i) == '+' || s.charAt(i) == '-'||
					s.charAt(i) == '*' || s.charAt(i) == '/')
				result += " " + s.charAt(i) + " ";
			else result += s.charAt(i);
		}
		return result;
	}
}
