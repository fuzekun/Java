package Web;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IdentifiHostName {
	public static void main(String[] args) {
		String host = "localhost";
		try {
			InetAddress address = InetAddress.getByName(host);
			System.out.println("主机名字是:" + address.getHostName());
			System.out.println("主机的IP地址是:" + address.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
