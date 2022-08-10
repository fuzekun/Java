package Template;

public class AnyWildCardDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<Integer>intstack = new Stack<>();
		intstack.push(1);
		intstack.push(3);
		intstack.push(-1);
		double max = max(intstack);
		System.out.println("最大值是:" + max);
	}
	public static double max(Stack<? extends Number> stack) {
		double max = stack.peek().doubleValue();
		for(int i = 0;i < stack.getSize() - 1;i++) {
			max = Math.max(stack.peek().doubleValue(),max);
		}
		return max;
	}
}
