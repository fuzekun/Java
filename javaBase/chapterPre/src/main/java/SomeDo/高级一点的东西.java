package SomeDo;

public class 高级一点的东西 {
	/*
	 * 贪心算法
	 * 乘船问题
	 * 背包问题
	 * 部分背包问题
	 * */
	
	//递归计算阶乘
	int jiecheng(int a) {
		if(a == 0)return 1;
		else return jiecheng(a - 1) * a;
	}
	
	//输出函数
	void printf(int a) {
		System.out.println(a);
	}
	
	//快速排序函数
	/*void quick_sort(int []A,int l,int r){
	    if(l == r)return ;
	    int imp = A[l];             //枢轴
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
	
	//乘船问题
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
		高级一点的东西 tmp = new 高级一点的东西();
		tmp.printf(4);
		
		long d1 = System.currentTimeMillis();
		System.out.println(tmp.jiecheng(10));
		long d2 = System.currentTimeMillis();
		System.out.printf("运行的时间是:%s%n",d2 - d1);
		System.out.println(d2);
		System.out.println(d1);
		/*BigInteger bi = new BigInteger("1");
		for(int i = 1;i <= 100;i++) {
			bi = bi.multiply(new BigInteger(""+i));
			System.out.println(i+"!="+bi);
		}*/
	}

}
