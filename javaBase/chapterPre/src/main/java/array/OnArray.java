package array;

public class OnArray {

	static void PrintArray(int[] a) {
		for(int i = 0;i < a.length;i++) {
			System.out.print(" " + a[i]);
		}
	}
	
	static void reverse(int[] a) {
		int n = a.length;
		for(int i = 0;i < n / 2;i++) {
			int tmp = a[i];
			a[i] = a[n - i];
			a[n - i - 1] = tmp;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[]a = {1,3,53,6};
		//怎么逆序一个数组
		reverse(a);
		PrintArray(a);
	}

}
