package homework;

import java.util.Scanner;


public class homework5 {

	//ϰ��5.1
	static void Statistic(){
		int data = 1;
		Scanner scanner = new Scanner(System.in);
		int cntR = 0;
		int cntF = 0;
		double sum = 0;
		while(data != 0) {
			System.out.println("����һ������");
			data = scanner.nextInt();
			if(data > 0) {
				cntR++;
			}
			else cntF++;
			sum += (double)data;
		}
		System.out.println("�ܵ�ֵΪ"+sum);
		System.out.println("ƽ��ֵΪ"+sum / (cntR + cntF));
		System.out.println("��������:" + cntR + " ��������:" + cntF);
		scanner.close();
	}
	
	//ϰ��5.2
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
				System.out.println("�������");
			}
			cnt++;
		}
		long endTime = System.currentTimeMillis();
		long timeLong = endTime - startTime;
		//long����ʱ���תΪdouble����ʱ����λ����
		double time= Double.parseDouble(Long.toString(timeLong));
		System.out.print("����������"+ time + "ms" );
	}
	
	//ϰ��5.3
	static void printChange() {
		System.out.println("ǧ�� 	��");
		for(int i = 1;i < 200;i++) {
			System.out.printf("%d\t%.1f\n",i,(float)(i*2.2));
		}
	}
	//ϰ��5.7
	static void CaculateMoney() {
		double spend = 10000.0;
		double sum = 0;
		for(int i = 1;i < 10;i++) {
			spend = spend * (i + 0.5);
			sum += spend;
		}
		System.out.printf("ѧ����%.2f  �ܻ�����%.2f",spend,sum);
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
	//ϰ��5.9 ���򵥵Ķ�̬�滮
	static void HightStudent() {
		System.out.print("������ѧ���ĸ���:");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		System.out.println("����������ѧ���������ͳɼ�");
		StCo studentScore = new StCo();
		StCo max1 = new StCo(0.0);//��ʾ��i�ĵ�һ��
		StCo max2 = new StCo(0.0);//��ʾ��i�ĵڶ���
		for(int i = 0;i < num;i++) {
			studentScore.name = sc.nextLine();
			studentScore.score = sc.nextDouble();
			StCo tmpCo = max1;
			max1 = max(max1,studentScore);
			if(Change(max1, studentScore)) {//������������ѧ���ɼ��ǵ�ǰ�����ô��ǰ�����ֵ�ͳ��˵ڶ���
				max2 = tmpCo;
			}
			else max2 = max(max2,studentScore);//���û����������Ҫ�͵ڶ�����Ƚ�
		}
		System.out.println("�ɼ���ߵ�ѧ����"+max1.name);
		System.out.println("�ɼ��ڶ��ߵ�ѧ����"+max2.name);
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
