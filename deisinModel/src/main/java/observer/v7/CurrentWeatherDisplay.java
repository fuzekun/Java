package observer.v7;


import java.util.Observable;

public class CurrentWeatherDisplay implements Observer{
    private float temperature;
    private float humidity;
    private float pressure;

    // 绑定观察的主题
    public CurrentWeatherDisplay () {

    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof WeatherData) {
            WeatherData weatherData = (WeatherData)subject;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            this.pressure = weatherData.getPressure();
            display();
        }

    }

    public void display() {
        System.out.println("温度:" + this.temperature +
                " 湿度: " + humidity + " 压力:" + this.pressure);
    }
}
