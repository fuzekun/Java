package Web;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class ComMul extends Application{
	private TextArea ta = new TextArea();
	private ArrayList<Socket>Sockets = new ArrayList<>();
	//private Map<Integer, Socket>map = new HashMap<Integer, Socket>();
	//private int cnt = 0;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	    // ������ϴ���һ������
		Scene scene = new Scene(new ScrollPane(ta),450,200);//��������
	    primaryStage.setTitle("MultiThreadServer"); // ��������
	    primaryStage.setScene(scene); // ������������Ļ��
	    primaryStage.show();//չʾ
	    
	    //�����̼߳���Ȼ�󴴽����߳�
	    new Thread(() ->{
	    	try {
	    		@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(8001);
	    		ta.appendText(new Date() + " ����������\n");
	    		while(true) {
	    			Socket socket = serverSocket.accept();
	    			Sockets.add(socket);
	    			new Thread(new Hindled(socket)).start();
	    		}
	    	}catch (Exception e) {
				// TODO: handle exception
	    		e.printStackTrace();
	    	}
	    }).start();
	}
	//������������
	class Hindled implements Runnable{
		private Socket mySocket = null;
		public Hindled(Socket mySocket) {
			// TODO Auto-generated constructor stub
			this.mySocket = mySocket;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int size = Sockets.size();
				DataInputStream inputStream = new DataInputStream(mySocket.getInputStream());
				ArrayList<DataOutputStream>outputStreams = new ArrayList<>();
				for(Socket socket : Sockets) {
					if(socket != mySocket) {
						outputStreams.add(new DataOutputStream(socket.getOutputStream()));
					}
				}
				//�ӿͻ��õ���Ϣ��д��ȥ
				while(true) {
					String s = inputStream.readUTF();
                    for(DataOutputStream outputStream : outputStreams) {
                		outputStream.writeUTF(s);
	                }
	            	if(size != Sockets.size()) {
	            		for(int i = size;i < Sockets.size();i++) {//�϶���ԭ���Ĵ��ˣ�����һ��
	            			outputStreams.add(new DataOutputStream(Sockets.get(i).getOutputStream()));
	            		}
	            		size = Sockets.size();
	            	}
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	class Output implements Runnable{
		private Socket mySocket;
		public Output(Socket mysocket) {
			// TODO Auto-generated constructor stub
			this.mySocket = mysocket;
		}
		@Override
		public void run() {
			ArrayList<InputStream>ins = new ArrayList<>();
	        try
	        {
	        	DataOutputStream dataOutputStream = new DataOutputStream(mySocket.getOutputStream());
	        	int size = Sockets.size();
				for(Socket socket :Sockets) {
					if(socket != mySocket) {
						ins.add(socket.getInputStream());
					}
				}
				ta.appendText("\n" + size + " " + ins.size());
	            int recvMsgSize = 0;
	            byte[] recvBuf = new byte[1024];
	            while(true){
	            	//��ѯ������
	            	for(InputStream in :ins) {
		                while((recvMsgSize=in.read(recvBuf))!=-1){
		                    byte[] temp = new byte[recvMsgSize];
		                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
		                    dataOutputStream.write(recvBuf);
		                }
	            	}
	            	if(size != Sockets.size()) {
	            		for(int i = size;i < Sockets.size();i++) {
	            			ins.add(Sockets.get(i).getInputStream());
	            		};
	            		size = Sockets.size();
	            	}
	            }
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}	
	}
	public static void main(String[] args) {
		launch(args);
	}
}
