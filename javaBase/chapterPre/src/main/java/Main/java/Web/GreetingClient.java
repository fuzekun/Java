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
         System.out.println("���ӵ�������" + serverName + " ���˿ںţ�" + port);
		@SuppressWarnings("resource")
		Socket client = new Socket(serverName, port);
         System.out.println("Զ��������ַ��" + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         while(true) {
             System.out.println("���������");
             @SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			double a = sc.nextDouble();
             System.out.println("�뾶��" + a);
             out.writeDouble(a);
             InputStream inFromServer = client.getInputStream();
             DataInputStream in = new DataInputStream(inFromServer);
             System.out.println("��������Ӧ�� " + in.readUTF());
         }
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}