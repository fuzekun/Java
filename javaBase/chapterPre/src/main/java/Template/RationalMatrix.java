package Template;


public class RationalMatrix extends GenericMartix<Rational>{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rational[][] rational1 = new Rational[3][3];
		Rational[][] rational2 = new Rational[3][3];
		for(int i = 0;i < 3;i++) {
			for(int j = 0;j < 3;j++) {
				rational1[i][j] = new Rational(i + 1,j + 1);
				rational2[i][j] = new Rational(i + 2,j + 2);
			}
		}
		RationalMatrix rationalMatrix = new RationalMatrix();
		Number[][] result = rationalMatrix.addMartix(rational1, rational2);
		rationalMatrix.printResult(result);
	}

	@Override
	protected Rational add(Rational o1, Rational o2) {
		// TODO Auto-generated method stub
		return o1.add(o2);
	}

	@Override
	protected Rational multiply(Rational o1, Rational o2) {
		// TODO Auto-generated method stub
		return o1.add(o2);
	}

	@Override
	protected Rational zero() {
		// TODO Auto-generated method stub
		Rational zero = new Rational();//³õÊ¼»¯Îª0;
		return zero;
	}

}
