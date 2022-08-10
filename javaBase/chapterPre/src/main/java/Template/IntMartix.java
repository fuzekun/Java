package Template;
class IntMartix extends GenericMartix<Integer>{

	@Override
	protected Integer add(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		return o1 + o2;
	}

	@Override
	protected Integer multiply(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		return o1 * o2;
	}

	@Override
	protected Integer zero() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static void main(String[] args) throws RuntimeException{
		IntMartix intMartix = new IntMartix();
		Integer[][] maxtrix1 = new Integer[][]{
				{1,2,3},
				{4,5,6},
				{7,8,9},
		};
		Integer[][] maxtrix2 = new Integer[][]{
				{1,2,3},
				{4,5,6},
				{7,8,9},
		};
		//Integer[][] result = intMartix.addMartix(maxtrix1, maxtrix2);
		Number[][] result2 = intMartix.multiplyMartix(maxtrix1, maxtrix2);
		
		//数组没法强转
		//Integer[][] integers = (Integer[][]) result2;
		
		intMartix.printResult(result2);
	}
}