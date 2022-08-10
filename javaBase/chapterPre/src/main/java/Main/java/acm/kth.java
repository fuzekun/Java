package acm;

import java.util.*;

public class kth {

	
	static void find(int[] a,int l,int r,int k) {//�������ҵ�k������
		if(l == r)return ;//��һ�����ַ���
		int imp = a[l];//�����Ϊ����
		int i = l,j = r;
		while(i != j) {//���ұ߿�ʼ�������п��ܴ�
			while(a[j] >= imp && i < j) j++;//���ұ���һ��������С��
			while(a[i] <= imp && i < j)i++;//�������һ����������
			if(i < j) {
				int tmp = a[j];
				a[j] = a[i];
				a[i] = tmp;
			}
		}
		a[l] = a[i];
		a[i] = imp;//�����λ
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
