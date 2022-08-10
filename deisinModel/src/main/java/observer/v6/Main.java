package observer.v6;



public class Main {
    public static void main(String[] args) {
        WeatherData w = new WeatherData();
        w.addObserver(new CurrentWeatherDisplay(w));
        w.setMeasurements(1,3,4);       // 一旦改变就直接展示了

    }
}
