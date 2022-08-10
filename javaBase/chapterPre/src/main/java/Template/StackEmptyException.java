package Template;

public class StackEmptyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StackEmptyException() {
		// TODO Auto-generated constructor stub
		super();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + ":Stack is Empty!";
	}

}
