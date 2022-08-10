package homework;

import java.util.Scanner;


public class homework5 {

	//习题5.1
	static void Statistic(){
		int data = 1;
		Scanner scanner = new Scanner(System.in);
		int cntR = 0;
		int cntF = 0;
		double sum = 0;
		while(data != 0) {
			System.out.println("输入一个整数");
			data = scanner.nextInt();
			if(data > 0) {
				cntR++;
			}
			else cntF++;
			sum += (double)data;
		}
		System.out.println("总的值为"+sum);
		System.out.println("平均值为"+sum / (cntR + cntF));
		System.out.println("正数个数:" + cntR + " 负数个数:" + cntF);
		scanner.close();
	}
	
	//习题5.2
	static void repeteAdd() {
		long startTime = System.currentTimeMillis();
		int cnt = 1;
		while(cnt<=10) {
			int add1 = (int)(1 + Math.random()*15);
			int add2 = (int)(1 + Math.random()*15);
			
			try{
				add1 = add1 + add2;
			}
			catch(Exception e) {
				System.out.println("计算错误");
			}
			cnt++;
		}
		long endTime = System.currentTimeMillis();
		long timeLong = endTime - startTime;
		//long类型时间差转为double类型时间差，单位毫秒
		double time= Double.parseDouble(Long.toString(timeLong));
		System.out.print("程序运行了"+ time + "ms" );
	}
	
	//习题5.3
	static void printChange() {
		System.out.println("千克 	磅");
		for(int i = 1;i < 200;i++) {
			System.out.printf("%d\t%.1f\n",i,(float)(i*2.2));
		}
	}
	//习题5.7
	static void CaculateMoney() {
		double spend = 10000.0;
		double sum = 0;
		for(int i = 1;i < 10;i++) {
			spend = spend * (i + 0.5);
			sum += spend;
		}
		System.out.printf("学费是%.2f  总花费是%.2f",spend,sum);
	}
	static StCo max(StCo a,StCo b) {
		return a.score > b.score ?a :b;
	}
	static boolean Change(StCo a,StCo b) {
		String aname = a.name;
		String bname = b.name;
		if(aname.equals(bname)) {
			return false;
		}
		else return true;
	}
	//习题5.9 ：简单的动态规划
	static void HightStudent() {
		System.out.print("请输入学生的个数:");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		System.out.println("请依次输入学生的姓名和成绩");
		StCo studentScore = new StCo();
		StCo max1 = new StCo(0.0);//表示到i的第一大
		StCo max2 = new StCo(0.0);//表示到i的第二大
		for(int i = 0;i < num;i++) {
			studentScore.name = sc.nextLine();
			studentScore.score = sc.nextDouble();
			StCo tmpCo = max1;
			max1 = max(max1,studentScore);
			if(Change(max1, studentScore)) {//如果交换了这个学生成绩是当前最大，那么以前的最大值就成了第二大
				max2 = tmpCo;
			}
			else max2 = max(max2,studentScore);//如果没交换，还需要和第二大相比较
		}
		System.out.println("成绩最高的学生是"+max1.name);
		System.out.println("成绩第二高的学生是"+max2.name);
		sc.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Statistic();
		repeteAdd();
		printChange();
		CaculateMoney();
		HightStudent();
	}
}
class StCo{
	public String name;
	public double score ;
	public StCo() {}
	public StCo(double d) {
		score = d;
	}
}
