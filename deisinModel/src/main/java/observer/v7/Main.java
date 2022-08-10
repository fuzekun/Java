package observer.v7;


/*
*
*
*   和版本4是一套的版本
* */
public class Main {

    public static void main(String[] args) {
        WeatherData w = new WeatherData();
        w.addObserver(new CurrentWeatherDisplay());
        w.setMeasurements(2,3,4);
    }
}
