package web2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CommunicateSever extends Application{
	private Socket socket1 = null;
	private Socket socket2 = null;
	public void start(Stage primaryStage) {
	    // ��������
	    TextArea ta = new TextArea();

	    // ����̨�ϴ�������
	    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
	    primaryStage.setTitle("Server"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	    //�����߳�
	    new Thread( () -> {
	      try {
	        // ����һ�������׽���
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8001);
	        Platform.runLater(() ->
	          ta.appendText("Server started at " + new Date() + '\n'));
	        while(true) {
		        //���������ͻ��˼�����,���Դ���n�������������Ȼ�����
		        socket1 = serverSocket.accept();
		        socket2 = serverSocket.accept();
		        // ʹ�ûص����������ƥ��ɹ���ԭ���ĺ����ͷ���һ�仰��
		          Platform.runLater(() -> {
		            ta.appendText("ƥ��ɹ�");
		          });
		          
		          //����һ���߳��������������û�
		          new Thread(new HindleCu(socket1, socket2)).start();
	        }

	      }
	      catch(IOException ex) {
	        ex.printStackTrace();
	      }
	    }).start();
	}
	class HindleCu implements Runnable{
		private Socket soc1 ;
		private Socket soc2 ;
		public HindleCu(Socket socket1,Socket socket2) {
			// TODO Auto-generated constructor stub
			soc1 = socket1;
			soc2 = socket2;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				DataInputStream inputStream1 = new DataInputStream(soc1.getInputStream());
				DataOutputStream outputStream = new DataOutputStream(soc2.getOutputStream());
				String Messagefrom1 = null;
				new Thread(new Output(soc1, soc2)).start();
				while(true) {
					Messagefrom1 = inputStream1.readUTF();//��1������Ϣ
					outputStream.writeUTF(Messagefrom1);//д��2
//					//��2������Ϣд��1�������ͻ����
//					String Messagefrom2 = inputStream2.readUTF();
//					outputStream2.writeUTF(Messagefrom2);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//����߳�
	class Output implements Runnable{
		Socket socket1,socket2;
		public Output(Socket socket1,Socket socket2) {
			// TODO Auto-generated constructor stub
			this.socket1 = socket1;
			this.socket2 = socket2;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				DataInputStream inputStream = new DataInputStream(socket2.getInputStream());
				DataOutputStream outputStream = new DataOutputStream(socket1.getOutputStream());
				String message;
				while(true) {
					message = inputStream.readUTF();//��2������Ϣ
					outputStream.writeUTF(message);//д��1
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
