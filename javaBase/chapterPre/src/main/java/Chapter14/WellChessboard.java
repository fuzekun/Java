package Chapter14;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class WellChessboard extends Application
{
	public static void main(String[] args) {
		Application.launch(args);
	}
    @Override
    public void start(Stage primaryStage)
    {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(8, 8, 8, 8));
        pane.setHgap(8);
        pane.setVgap(8);

        for (int column = 0; column < 3; column++)
        {
            for (int row = 0; row < 3; row++)
            {
                int i = (int)(Math.random() * 3);
                if (i != 2)
                {
                    pane.add(getNode(i), column, row);
                }
            }
        }

        Scene scene = new Scene(pane);
        primaryStage.setTitle("WellChessboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //随机生成井字游戏中的操作
    public ImageView getNode(int i)
    {
        if (i == 0)
        {
            return new ImageView("Image/o.gif");
        }
        else
        {
            return new ImageView("Image/x.gif");
        }
    }
    
}