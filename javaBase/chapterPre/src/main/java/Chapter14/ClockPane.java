package Chapter14;

import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ClockPane extends Pane {
    private int hour;
    private int minite;
    private int second;
    //�б����Ĵ�С
    private double w = 250, h = 250;

    /**ʹ�����ڵ�ʱ����ΪĬ�ϵĹ��캯��**/
    public ClockPane() {
        setCurrentTime();
        paintClock();
    }

    public ClockPane(int hour, int minite, int second) {
        this.hour = hour;
        this.minite = minite;
        this.second = second;
        paintClock();       //�ػ�ʱ��
    }

    public int getHour() {
        return hour;
    }

    public int getMinite() {
        return minite;
    }

    public int getSecond() {
        return second;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinite(int minite) {
        this.minite = minite;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setCurrentTime() {
        Calendar calenda = new GregorianCalendar();
        this.hour = calenda.get(Calendar.HOUR_OF_DAY);
        this.minite = calenda.get(Calendar.MINUTE);
        this.second = calenda.get(Calendar.SECOND);

    }

    protected void paintClock() {
        //��ʼ��ʱ�ӵ�λ�ò���
        double clockRadius = Math.min(w, h) * 0.8 * 0.5;
        double centerX = w / 2;
        double centerY = h / 2;

        //��һ��Բ
        Circle circle = new Circle(centerX, centerY, clockRadius);
        circle.setFill(Color.WHITE);        //�ڲ����
        circle.setStroke(Color.BLACK);      //����������,��Χ
        Text t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
        Text t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
        Text t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
        Text t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");

        //��������
        //���Ϸ���
        //����ʱ��

        getChildren().clear();
        getChildren().addAll(circle, t1, t2, t3, t4); //Ӧ�ðѸ��������
    }
}
