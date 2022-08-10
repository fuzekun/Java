package InterfrenceAndABS;
public class ComparableRectange extends Rectangle implements Comparable<ComparableRectange>{

	public ComparableRectange(double a,double b) {
		super(a,b);
	}
	@Override
	public int compareTo(ComparableRectange o) {
		// TODO Auto-generated method stub
		if(getArea() > o.getArea())return 1;
		else if(getArea() == o.getArea())return 0;
		else return -1;
	}
	@Override
	public String toString() {
		return super.toString() + "Area" + getArea();
	}
	
}
