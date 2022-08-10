package array;

public class studyarry {

	
	static void print(int[] a) {
		for(int i = 0;i < a.length;i++) {
			System.out.println(a[i]);
		}
	}
	static void Init(int [] a) {
		for(int i = 0;i < a.length;i++) {
			int tmp = (int)(Math.random()*3);
			 a[i] = tmp;//0到100的随机数
		}
	}
	static void sum(int [] a) {
		int ans = 0;
		for(int i = 0;i < a.length;i++) {
			ans += a[i];
		}
		System.out.println(ans);
	}
	static void max(int [] a) {
		int maxnum = 0;
		for(int i = 0;i < a.length;i++) {
			maxnum = Math.max(a[i], maxnum);
		}
		System.out.println(maxnum);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = new int[10];
		Init(a);
		print(a);
		sum(a);
		max(a);
		
	}
}
