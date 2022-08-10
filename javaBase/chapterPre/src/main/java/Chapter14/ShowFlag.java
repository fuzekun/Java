package Chapter14;

//将image文件夹中的照片加载出来
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class ShowFlag extends Application
{
  @Override
  public void start(Stage primaryStage)
  {
      GridPane pane = new GridPane();
      pane.setPadding(new Insets(5, 5, 5, 5));
      pane.setHgap(10);
      pane.setVgap(10);
      ImageView p1 = new ImageView("flag5.jpg");

      pane.add(p1, 0, 0);

      Scene scene = new Scene(pane);
      primaryStage.setTitle("ShowFlag");
      primaryStage.setScene(scene);
      primaryStage.show();
  }
  public static void main(String[] args) {
	  Application.launch(args);
  }
}