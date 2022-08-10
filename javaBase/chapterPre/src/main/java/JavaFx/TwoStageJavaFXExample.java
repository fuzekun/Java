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
        btn.setText("去第二舞台");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("转向第二舞台");
                //primaryStage.hide();
                newStage();
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("第一舞台");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //显示第二个舞台
    public void newStage(){
        Stage secondaryStage=new Stage();
        Button btn1=new Button();
        StackPane root1=new StackPane();
        btn1.setText("欢迎来到第二舞台");
 
        btn1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("欢迎来到第二舞台");
            }        
        });
       
        root1.getChildren().add(btn1);
        Scene secondaryScene=new Scene(root1,500,250);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("第二舞台");
        secondaryStage.show();
 
    }
 
    public static void main(String[] args) {
        launch(args);
    }
    
}