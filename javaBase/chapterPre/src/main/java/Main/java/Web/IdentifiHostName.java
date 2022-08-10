package Web;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IdentifiHostName {
	public static void main(String[] args) {
		String host = "localhost";
		try {
			InetAddress address = InetAddress.getByName(host);
			System.out.println("����������:" + address.getHostName());
			System.out.println("������IP��ַ��:" + address.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
