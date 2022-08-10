package solveWrong;
public class Baulk {

	private static void solve()throws RuntimeException{
		try {
			String message = "西红柿：2.99元/500克";
			String[] strArr = message.split(":");
			String unitPriceStr = strArr[2].substring(0,4);
			double weight = 650;
			double unitPriceDou = Double.parseDouble(unitPriceStr);
			System.out.println(message + ",顾客买了" + weight + "克西红柿，需支付 " + (float)(weight / 500 * unitPriceDou) + "元");
			}catch(Exception e) {
				e.printStackTrace();//打印异常信息
			}
		System.out.println("\\");//输出提示信息
		try {
			
		}catch(ArrayIndexOutOfBoundsException aiobe) {//捕捉数组元素下标越界异常
			aiobe.printStackTrace();
			System.out.println(aiobe.getMessage());
			System.out.println(aiobe.toString());
		}catch(Exception e) {//捕捉与易产生的异常类型相匹配的异常对象
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		solve();
	}
}
