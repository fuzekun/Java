package SomeDo;

public class �߼�һ��Ķ��� {
	/*
	 * ̰���㷨
	 * �˴�����
	 * ��������
	 * ���ֱ�������
	 * */
	
	//�ݹ����׳�
	int jiecheng(int a) {
		if(a == 0)return 1;
		else return jiecheng(a - 1) * a;
	}
	
	//�������
	void printf(int a) {
		System.out.println(a);
	}
	
	//����������
	/*void quick_sort(int []A,int l,int r){
	    if(l == r)return ;
	    int imp = A[l];             //����
	    int p = l,q = r;
	    while(q != p){
	        while(A[q] >= imp && q > p) {
	        	q--;
	        }
	        while(A[p] <= imp && q > p) {
	        	p++;
	        }
	        if(q > p){
	            int tmp = A[q];
	            A[q] = A[p];
	            A[p] = tmp;
	        }
	    }
	    A[l] = A[q];
	    A[p] = imp;
	    int m = (l + r)>>1;
	    quick_sort(A,l,m);
	    quick_sort(A,m+1,r);
	}*/
	
	//�˴�����
	/*void chengchuan() {
		int []a = {1,2,3,50,7,6,3,4,5,5,6};
		quick_sort(a,1,11);
		for(int i = 0;i < 11;i++) {
			System.out.print(a[i] + " ");
		}
	}
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		�߼�һ��Ķ��� tmp = new �߼�һ��Ķ���();
		tmp.printf(4);
		
		long d1 = System.currentTimeMillis();
		System.out.println(tmp.jiecheng(10));
		long d2 = System.currentTimeMillis();
		System.out.printf("���е�ʱ����:%s%n",d2 - d1);
		System.out.println(d2);
		System.out.println(d1);
		/*BigInteger bi = new BigInteger("1");
		for(int i = 1;i <= 100;i++) {
			bi = bi.multiply(new BigInteger(""+i));
			System.out.println(i+"!="+bi);
		}*/
	}

}
