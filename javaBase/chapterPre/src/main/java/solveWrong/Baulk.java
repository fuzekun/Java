package solveWrong;
public class Baulk {

	private static void solve()throws RuntimeException{
		try {
			String message = "��������2.99Ԫ/500��";
			String[] strArr = message.split(":");
			String unitPriceStr = strArr[2].substring(0,4);
			double weight = 650;
			double unitPriceDou = Double.parseDouble(unitPriceStr);
			System.out.println(message + ",�˿�����" + weight + "������������֧�� " + (float)(weight / 500 * unitPriceDou) + "Ԫ");
			}catch(Exception e) {
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
		System.out.println("\\");//�����ʾ��Ϣ
		try {
			
		}catch(ArrayIndexOutOfBoundsException aiobe) {//��׽����Ԫ���±�Խ���쳣
			aiobe.printStackTrace();
			System.out.println(aiobe.getMessage());
			System.out.println(aiobe.toString());
		}catch(Exception e) {//��׽���ײ������쳣������ƥ����쳣����
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		solve();
	}
}
