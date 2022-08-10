package acm;
import java.util.Scanner;

public class printTenGraph {

	static void test1() {
		int k;
	    final int maxn = 30 * 2 + 6;
	    
	    char[] rec = new char[maxn];
	    char[][] mem = new char[maxn][maxn];
	    Scanner input = new Scanner(System.in);
	    
	    k = input.nextInt();
	    input.close();
	    int n = 4 * k + 5;
	    
	    //初始化
	    rec[0] = rec[1] = '.';
	    mem[0][0] = mem[0][1] = '.';
	    for(int i = 2;i < n/2 + 1;i++){
	        rec[i] = '$';
	        mem[0][i] = '$';
	    }

	    //输出上边和中间
	    for(int i = 0;i < n / 2+1;i++){
	        //输出左边一半和中间
	        for(int j = 0;j < n/2+1;j++){
	            System.out.print(rec[j]);
	        }
	        //输出右边一半
	        for(int j = n / 2 - 1;j >=0;j--){
	        	System.out.print(rec[j]);
	        }
	        System.out.println();

	        //保存结果到记忆数组
	        for(int j = 0;j < n / 2 + 1;j++){
	            mem[i+1][j] = mem[i][j];
	        }
	        //更新
	        if(i % 2 == 0){//偶数行
	            for(int j = 0;j < n/2+1;j++){
	                if(rec[j] == '$')
	                    while(rec[++j] == '$'&&j < n / 2 + 1){rec[j] = '.';mem[i+1][j] = '.';}
	            }
	        }
	        if(i % 2 == 1){//奇数行
	            for(int j = 0;j < n / 2+1;j++){
	                if(rec[j] == '.'){
	                    if(j == 0){rec[j] = '$';mem[i+1][j] = '$';}
	                    while(rec[++j] == '.'&&j < n / 2+1){rec[j] = '$';mem[i+1][j] = '$';}
	                }
	            }
	        }
	    }

	    //输出下边的
	    for(int i = n / 2 - 1; i >= 0;i--){
	        for(int j = 0;j < n / 2 + 1;j++)System.out.print(mem[i][j]);
	        for(int j = n / 2 - 1;j >= 0;j--)System.out.print(mem[i][j]);
	        System.out.println();
	    }
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
	}
}


