package solveWrong;

public class CircleError {

	private static int numberOfCircle;
	private double R;
	
	public CircleError()throws NewError {
		// TODO Auto-generated constructor stub
		this.R = -1.0;
	}
	public CircleError(double R)throws NewError {
		// TODO Auto-generated constructor stub
		this.setR(R);
		numberOfCircle++;
	}
	public void setR(double R)throws NewError {
		if(R >= 0)this.R = R;
		else throw new NewError(R);
	}
	
	public static int getNuberOfCircle() {
		return numberOfCircle;
	}
	public double getR() {
		return this.R;
	}
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		try{
			CircleError C = new CircleError();
			System.out.println(C.getR());
			C = new CircleError(1.5);
			C = new CircleError(-1);
		}
		catch(RuntimeException e) {
			System.out.println(e);
		}
		catch(NewError e) {
			System.out.println(e + " in NewError");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
