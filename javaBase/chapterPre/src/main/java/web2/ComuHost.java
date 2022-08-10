package web2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ComuHost extends Application{
	  //���������
	  DataOutputStream toServer = null;
	  DataInputStream fromServer = null;
	  Socket socket = null;
	  String s = "";
	 
	  @Override // Override the start method in the Application class
	  public void start(Stage primaryStage) {
	    // Panel p to hold the label and text field
	    BorderPane paneForTextField = new BorderPane();
	    paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
	    paneForTextField.setStyle("-fx-border-color: green");
	    paneForTextField.setLeft(new Label("��������"));
	    TextField tf = new TextField();
	    tf.setAlignment(Pos.BOTTOM_LEFT);
	    paneForTextField.setCenter(tf);
	    
	    BorderPane mainPane = new BorderPane();
	    // Text area to display contents
	     TextArea ta = new TextArea();
	    
	    mainPane.setCenter(new ScrollPane(ta));
	    mainPane.setTop(paneForTextField);
	    
	    // Create a scene and place it in the stage
	    Scene scene = new Scene(mainPane, 450, 200);
	    primaryStage.setTitle("Client"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	    //���ӷ�����
	    try {
	      socket = new Socket("localhost", 8001);
	      fromServer = new DataInputStream(socket.getInputStream());
	      toServer = new DataOutputStream(socket.getOutputStream());
	    }
	    catch (IOException ex) {
	    //  ta.appendText(ex.toString() + '\n');
	      ex.printStackTrace();
	    }
	    
	    //����һ���µ��̵߳ȴ�����
	    new Thread(new Input(ta)).start();

	    //���������Ϣ
	    tf.setOnAction(e -> {
	    try {
	        String str = tf.getText();
	        tf.setText("");
	        ta.appendText("			" + new Date() + "\n");
	        ta.appendText("��:" + str + "\n");
	        toServer.writeUTF(str);
	        toServer.flush();
	      }
	      catch (IOException ex) {
	       // System.err.println(ex);
	      	ex.printStackTrace();
	      }
	    });
	  }
	  
	  //����һ����������ȴ�����
	  class Input implements Runnable{
		  TextArea ta = null;
		  public Input(TextArea ta) {
			// TODO Auto-generated constructor stub
			  this.ta = ta;
		  }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				//ѭ������
				while(true) {
					s = fromServer.readUTF();
					ta.appendText("			"+new Date() + "\n");
					ta.appendText("TA:" + s + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  }


	public static void main(String[] args) {
		ComuHost.launch(args);
	}
}