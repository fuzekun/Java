package Web;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MultiThreadServer extends Application {
  // 创建文本区域
  private TextArea ta = new TextArea();
  private int clientNo = 0;

  @Override
  public void start(Stage primaryStage) {
    // 在面板上创建一个背景
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("MultiThreadServer"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    new Thread( () -> {
      try {
        // 创建一个服务套接字
        @SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(8000);
        ta.appendText("MultiThreadServer started at " 
          + new Date() + '\n');
    
        //循环监听创建新线程
        while (true) {
          Socket socket = serverSocket.accept();
    
          // 增加连接数目
          clientNo++;
          
          //显示
          Platform.runLater( () -> {
            ta.appendText("Starting thread for client " + clientNo +
              " at " + new Date() + '\n');
            InetAddress inetAddress = socket.getInetAddress();
            ta.appendText("Client " + clientNo + "'s host name is "
              + inetAddress.getHostName() + "\n");
            ta.appendText("Client " + clientNo + "'s IP Address is "
              + inetAddress.getHostAddress() + "\n");
          });
          
          //创建新线程
          new Thread(new HandleAClient(socket)).start();
        }
      }
      catch(IOException ex) {
        System.err.println(ex);
      }
    }).start();
  }
  
  // Define the thread class for handling new connection
  class HandleAClient implements Runnable {
    private Socket socket; // A connected socket

    public HandleAClient(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      try {
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
        
        while (true) {
          double radius = inputFromClient.readDouble();
          double area = radius * radius * Math.PI;

          //发送面积
          outputToClient.writeDouble(area);
          
          Platform.runLater(() -> {
            ta.appendText("radius received from client: " +
              radius + '\n');
            ta.appendText("Area found: " + area + '\n');
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
