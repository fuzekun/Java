package observer.v6;



import java.util.List;
import java.util.Observable;

// 主题，也就是被观察者
public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public WeatherData() {}

    /*
    *
    *
    *           for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
            *
            * 这里的update(this, arg) 是把被观察者，也就是主题传给了被观察者。
            * 这样被观察者，可以改变观察者的状态也可以获取新的信息，来决定自身的变化
    * */

    public void measurementsChanged() {
        setChanged();
        notifyObservers();                  // 这里实现一个推的方式
    }
    public void setMeasurements(float t, float h, float p) {
        this.humidity = h;
        this.temperature = t;
        this.pressure = p;
        measurementsChanged();
    }
}


