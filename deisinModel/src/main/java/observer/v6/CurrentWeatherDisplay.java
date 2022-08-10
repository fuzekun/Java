package observer.v6;

import java.util.Observable;
import java.util.Observer;

public class CurrentWeatherDisplay implements Observer {

    Observable observable; // 这个可以获取主题，实现定向的拉取

    private float temperature;
    private float humidity;
    private float pressure;

    // 绑定观察的主题
    public CurrentWeatherDisplay (Observable observable) {
        this.observable = observable;
        // 主题放入一个观察者
        observable.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        Observable o2 = new WeatherData();
        if (o instanceof WeatherData) {                 // 根据被观察的是不是weath进行决定是否更新
            WeatherData weatherData = (WeatherData)o;
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
