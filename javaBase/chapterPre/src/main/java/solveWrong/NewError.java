package solveWrong;

public class NewError extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double r;
	public NewError(double r) {
		super("Ivalid radius " + r);
		this.r = r;
	}
	public double getR() {
		return r;
	}

}
