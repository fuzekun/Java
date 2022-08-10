package Template;

public class Rational extends Number implements Comparable<Rational>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int num;//分子
	int den;//分每
	
	@Override
	public String toString() {
		return num + "/" + den;
	}
	
	public Rational() {//初始化为0);
		// TODO Auto-generated constructor stub
		num = 0;
		den = 1;
	}
	public Rational(int num, int den) {
		super();
		this.num = num;
		this.den = den;
		if(den == 0) {
			System.out.println("除0错误");
			this.den = 1;
		}
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return (int)(num / den);
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return (long)(num / den);
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return (float)(num / den);
	}

	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return (double)(num / den);
	}

	@Override
	public int compareTo(Rational o) {
		// TODO Auto-generated method stub
		//大于返回1，小于返回-1，等于返回0;
		if(this.doubleValue() < o.doubleValue())return -1;
		else if(this.doubleValue() == o.doubleValue())return 0;
		else return 1;
	}
	
	//加减乘乘除四则运算
	public Rational add(Rational o) {
		int minLCM = den / gcd(den, o.den) * o.den;//先除后乘防止溢出
		Rational ans = new Rational();
		ans.den = minLCM;
		ans.num = minLCM / den * num + minLCM / o.den * o.num;
		return ans;
	}
	public Rational sub(Rational o) {
		int minLCM = den / gcd(den, o.den) * o.den;//先除后乘防止溢出
		Rational ans = new Rational();
		ans.den = minLCM;
		ans.num = minLCM / den * num - minLCM / o.den * o.num;
		return ans;
	}
	public  Rational multiply(Rational o) {
		Rational ans = new Rational();
		ans.num = num * o.num;
		ans.den = den * o.den;
		int gdc = gcd(ans.num, ans.den);
		ans.num /= gdc;
		ans.den /= gdc;
		return ans;
	}
	public Rational div(Rational o) {
		int tmp = o.den;
		o.den = o.num;
		o.num =tmp;
		return this.multiply(o);
	}
	public int gcd(int a,int b) {
		return a % b == 0? a:gcd(b,a % b);
	}
	
	public static void main(String[] args) {
		//用来测试compareTo的返回值情况
		Integer i = new Integer(3);
		Integer k = new Integer(2);
		System.out.println(i.compareTo(k));
		Rational rational = new Rational(1,2);
		System.out.println(rational);
	}
}
