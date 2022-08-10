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
	    // 文字区域
	    TextArea ta = new TextArea();

	    // 在舞台上创建背景
	    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
	    primaryStage.setTitle("Server"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	    //场景线程
	    new Thread( () -> {
	      try {
	        // 创建一个服务套接字
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8001);
	        Platform.runLater(() ->
	          ta.appendText("Server started at " + new Date() + '\n'));
	        while(true) {
		        //创建两个客户端监听器,可以创建n个服务监听器，然后配对
		        socket1 = serverSocket.accept();
		        socket2 = serverSocket.accept();
		        // 使用回调函数，如果匹配成功，原来的函数就返回一句话。
		          Platform.runLater(() -> {
		            ta.appendText("匹配成功");
		          });
		          
		          //创建一个线程用来服务两个用户
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
					Messagefrom1 = inputStream1.readUTF();//从1读入信息
					outputStream.writeUTF(Messagefrom1);//写入2
//					//从2读入信息写进1，这样就会堵塞
//					String Messagefrom2 = inputStream2.readUTF();
//					outputStream2.writeUTF(Messagefrom2);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//输出线程
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
					message = inputStream.readUTF();//从2读入信息
					outputStream.writeUTF(message);//写入1
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
