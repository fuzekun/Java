package Web;


import java.io.*;
import java.net.*;

public class MyServer {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8000);
			Socket socket = serverSocket.accept();
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			while(true) {
				double x = dataInputStream.readDouble();
				double ans = Math.PI * x * x;
				dataOutputStream.writeDouble(ans);	
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
