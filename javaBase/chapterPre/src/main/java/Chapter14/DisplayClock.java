package Chapter14;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import sun.management.Sensor;

public class DisplayClock extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ClockPane clock = new ClockPane();
        String timeStr = clock.getHour() + ":" + clock.getMinite() + ":" +
                clock.getSecond();
        Label currentTime = new Label(timeStr);

        //在面板的边上放上时钟和文字
        BorderPane pan = new BorderPane();
        pan.setCenter(clock);
        pan.setBottom(currentTime);
        BorderPane.setAlignment(currentTime, Pos.TOP_CENTER);

        //创建一个舞台，然后把黑板放在舞台上.
        Scene scene = new Scene(pan, 250, 250);
        primaryStage.setTitle("DisplayClock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
