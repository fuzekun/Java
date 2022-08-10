package JavaFx;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TwoStageJavaFXExample extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("ȥ�ڶ���̨");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("ת��ڶ���̨");
                //primaryStage.hide();
                newStage();
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("��һ��̨");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //��ʾ�ڶ�����̨
    public void newStage(){
        Stage secondaryStage=new Stage();
        Button btn1=new Button();
        StackPane root1=new StackPane();
        btn1.setText("��ӭ�����ڶ���̨");
 
        btn1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("��ӭ�����ڶ���̨");
            }        
        });
       
        root1.getChildren().add(btn1);
        Scene secondaryScene=new Scene(root1,500,250);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("�ڶ���̨");
        secondaryStage.show();
 
    }
 
    public static void main(String[] args) {
        launch(args);
    }
    
}