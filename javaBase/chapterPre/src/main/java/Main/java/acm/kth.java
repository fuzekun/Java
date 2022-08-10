package acm;

import java.util.*;

public class kth {

	
	static void find(int[] a,int l,int r,int k) {//从左到右找第k大数字
		if(l == r)return ;//就一个数字返回
		int imp = a[l];//左边作为枢轴
		int i = l,j = r;
		while(i != j) {//从右边开始，否则有可能错
			while(a[j] >= imp && i < j) j++;//从右边找一个比枢轴小的
			while(a[i] <= imp && i < j)i++;//从左边找一个比枢轴大的
			if(i < j) {
				int tmp = a[j];
				a[j] = a[i];
				a[i] = tmp;
			}
		}
		a[l] = a[i];
		a[i] = imp;//枢轴归位
		if(i == k) {
			return ;
		}
		int m = (l + r)>>1;
		
		if(m > k)find(a,l,m,k);
		else find(a,m+1,r,k);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("fda");
		Scanner input  = new Scanner(System.in);
		int n = input.nextInt();
		int m = input.nextInt();
		input.close();
		final int maxn = 100000 + 5;
		int[] a = new int[maxn];
		for(int i = 0;i < n;i++) {
			a[i] = input.nextInt();
		}
		for(int i = 0;i < m;i++) {
			int l = input.nextInt();
			int r = input.nextInt();
			int k = input.nextInt();
			find(a,l,r,k);
			System.out.println(a[k]);
		}
	}

}
