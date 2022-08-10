package Thread;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FlashText extends Application{
	private String text = "";
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		StackPane pane = new StackPane();
		Label lblText = new Label("Program is fun");
		pane.getChildren().add(lblText);

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					while(true) {
						if(lblText.getText().trim().length() == 0) {
							text = "welcome";
						}
						else text = "";
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								lblText.setText(text);
							}
						});
						Thread.sleep(200);
					}
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}).start();
		Scene scene = new Scene(pane,200,50);
		primaryStage.setTitle("FlashText");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		FlashText flashText = new FlashText();

		flashText.launch(args);
		
	}
}
