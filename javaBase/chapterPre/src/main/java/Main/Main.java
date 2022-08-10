package Main;

import java.util.ArrayList;
import java.util.Scanner;

//时间复杂度是9！ * 81
public class Main {
	private static int[][] graph = new int[10][10];
	private static boolean flag = false;
	static int getBegin(int x) {
		if(x <= 3)return 1;
		else if(x <= 6)return 4;
		else return 7;
	}
	static ArrayList<Integer> result(int h,int l) {
		boolean[] vis = new boolean[10];
		for(int i = 1;i <= 9;i++) {
			vis[i] = false;
		}
		for(int i = 1;i <= 9;i++) {
			vis[graph[h][i]] = true;
		}
		for(int i = 1;i <= 9;i++) {
			vis[graph[i][l]] = true;
		}
		int beginx = getBegin(h);
		int beginy = getBegin(l);
		for(int i = beginx;i < beginx + 3;i++) {
			for(int j = beginy;j < beginy + 3;j++) {
				vis[graph[i][j]]=true;
			}
		}
		ArrayList<Integer>list = new ArrayList<>();
		for(int i = 1;i <= 9;i++) {
			if(!vis[i])list.add(i);
		}
		return list;
	}
	static void findans(int x,int y) {
		if(x == 9 && y == 9) {//找到结果
			for(int i = 1;i <= 9;i++) {
				for(int j = 1;j <= 9;j++) {
					System.out.print(graph[i][j]+ " ");
				}
				System.out.println();
			}
			flag = true;
			return ;
		}
		if(x > 9 || y > 9) {
			return;
		}
//		if(x == 2 && y == 9)
//		{
//			for(int i = 0;i < 9;i++){
//			System.out.print(graph[1][i] + " ");
//		}
		if(graph[x][y] == 0) {//如果这个数字没有得到
			for(int i:result(x,y)) {
				graph[x][y] = i;
				if(y < 9) {//找右边的答案
					findans(x, y+1);
					if(flag)break;
				}
				else {
					findans(x+1, 1);
					if(flag)break;
				}
			}
			graph[x][y] = 0;
		}
		else {
			if(x == 2 && y == 4)System.out.println("这个数不等于0");
			if(y < 9) {//找右边的答案
				findans(x, y+1);
				if(flag);
			}
			else {
				findans(x+1, 1);
				if(flag);
			}
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int i = 1;i <= 9;i++) {
			for(int j = 1;j <= 9;j++) {
				int a = sc.nextInt();
				graph[i][j] = a;
			}
		}
		sc.close();
		findans(1,1);
		if(flag == false) {
			System.out.println("没答案");
		}
	}
}
/*
0 0 0 0 7 0 0 4 5
4 7 0 0 0 0 8 0 0
8 5 1 0 0 0 0 0 0
0 8 5 0 0 2 0 0 0
0 0 0 5 9 0 0 6 0
2 1 0 0 0 4 0 0 0
5 0 0 4 0 0 0 0 3
0 0 2 0 0 0 0 0 7
0 0 0 6 0 0 0 5 4
*/
/*
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
*/
/*
0 0 0 0 6 8 0 3 0
1 9 0 0 0 0 0 0 0
8 0 3 1 0 0 2 0 0
4 0 0 0 5 1 0 6 0
7 0 0 0 2 0 0 0 4
0 0 0 0 7 0 8 0 0
0 1 0 0 0 5 0 0 7
0 0 4 0 0 0 0 0 0
0 5 0 0 3 0 1 0 0
*/
/*
0 0 0 7 0 0 0 2  0
2 0 0 0 0 6 0 0 3
0 5 0 0 0 0 9 4 0
0 1 0 0 0 0 0 0 0
0 0 3 0 2 0 0 1 0
9 2 8 5 0 0 7 3 0
0 7 0 9 0 0 0 5 0
8 0 0 2 0 7 4 0 0 
0 0 9 0 0 3 0 0 7
*/
/*
9 1 7 2 5 4 0 0 0 
4 0 2 0 8 0 0 0 0 
6 5 0 0 0 3 4 0 0
0 0 3 0 9 0 2 5 6
5 0 0 7 0 0 3 0 9
2 0 0 0 0 5 0 7 1 
0 2 0 5 3 0 7 6 0
3 7 0 1 6 0 0 6 8
0 0 0 0 0 0 0 3 0
*/