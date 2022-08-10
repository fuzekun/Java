package Web;
import java.net.*;
import java.util.Scanner;
import java.io.*;
 
public class GreetingClient
{
   public static void main(String [] args)
   {
      String serverName = "localhost";
      int port =  8000;
      try
      {
         System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
		@SuppressWarnings("resource")
		Socket client = new Socket(serverName, port);
         System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         while(true) {
             System.out.println("请输入面积");
             @SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			double a = sc.nextDouble();
             System.out.println("半径是" + a);
             out.writeDouble(a);
             InputStream inFromServer = client.getInputStream();
             DataInputStream in = new DataInputStream(inFromServer);
             System.out.println("服务器响应： " + in.readUTF());
         }
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}