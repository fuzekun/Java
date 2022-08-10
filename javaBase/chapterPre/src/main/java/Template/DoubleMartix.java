package Template;


class DoubleMartix extends GenericMartix<Double>{

	@Override
	protected Double add(Double o1, Double o2) {
		// TODO Auto-generated method stub
		
		return o1.doubleValue() + o2.doubleValue();
	}

	@Override
	protected Double multiply(Double o1, Double o2) {
		// TODO Auto-generated method stub
		return o1.doubleValue() * o2.doubleValue();
	}

	@Override
	protected Double zero() {
		// TODO Auto-generated method stub
		return 0.0;
	}
	public static void main(String[] args) {
		Double[][] martix = {
			{1.0,3.0,4.0},
			{1.0,3.0,4.0},
			{1.0,3.0,4.0},
		};
		DoubleMartix doubleMartix = new DoubleMartix();
		Number[][] result = doubleMartix.addMartix(martix, martix);
		doubleMartix.printResult(result);
	}
	
}