package InterfrenceAndABS;

/*
 * ≤‚ ‘≥ÈœÛ¿‡ºÃ≥–
 * 
 * */
public abstract class GeometericObject {
	private String color ;
	private boolean filled;
	private java.util.Date dataCreated;
	protected  GeometericObject() {
		this.dataCreated = new java.util.Date();
	}
	protected  GeometericObject(String coString,boolean filled) {
		dataCreated = new java.util.Date();
		this.color = coString;
		this.filled = filled;
	}
	public  String getColor() {
		return color;
	}
	public boolean isFilled() {
		return filled;
	}
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	public java.util.Date getDataCreated() {
		return dataCreated;
	}
	public void setDataCreated(java.util.Date dataCreated) {
		this.dataCreated = dataCreated;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "GeometericObject [color=" + color + ", filled=" + filled + ", dataCreated=" + dataCreated + "]";
	}
	public abstract double getArea();
	
	public abstract double getPerimeter();
	
	public static void  dis() {
		System.out.println("fda");
	}
}
