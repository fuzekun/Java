package array;

public class testForWhile {

	static int sum(int begin_1,int end_1) {
		int sum = 0;
		for(int i = begin_1;i <= end_1;i++) {
			sum += i;
		}
		return sum;
	}
	
	static int sum2(int begin_1,int end_1) {
		int sum = 0,i = 1;
		while(i <= end_1) {
			sum += i;
			i++;
		}
		return sum;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(sum(1,100));
		System.out.println(sum2(1,100));
		
	}

}
