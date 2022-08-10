package Chapter14;
import java.awt.Insets;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 330, Color.WHITE);
        
        GridPane gridpane = new GridPane();
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        final ImageView imv = new ImageView();
        final Image image2 = new Image(Main.class.getResourceAsStream("button.png"));
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();
        
        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 1, 1);
        
        
        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}