package homework;



public class homework {
	//ϰ��1.1
	static void print() {
		System.out.println("ϰ��1.1");
		System.out.println("Welcome to java");
		System.out.println("Welcome to Computer Science");
		System.out.println("Programming is fun\n");
	}

	//ϰ��1.4
	static void printTable() {
		System.out.println("ϰ��1.4");
		System.out.println("a\ta^2\ta^3");
		for(int i = 1;i <= 4;i++) {
			for(int j = 1;j <= 3;j++) {
				System.out.print((int)(Math.pow(i, j)));
				if(j != 3)System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//ϰ��1.5
	static void printAns() {
		System.out.println("ϰ��1.5");
		System.out.println((9.5 * 4.5 - 2.5 * 3) / (45.5 - 3.5));
		System.out.println();
	}
	
	//ϰ��1.6
	static void averSpeed() {
		System.out.println("ϰ��1.12");
		double s = 24 * 1600;
		double t = 1 * 3600 + 40 * 60 + 35;
		System.out.println("ƽ���ٶ�Ϊ:");
		System.out.println(s / t);
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		print();
		printTable();
		printAns();
		averSpeed();
	}
}
