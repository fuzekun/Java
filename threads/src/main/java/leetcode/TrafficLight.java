package leetcode;


import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class TrafficLight {


    /*
    *
    *
    * 公平的线程的顺序
    * 1. 先竞争锁，得到锁的直接修改成自己想要的
    * 2. 然后通过。
    *
    *
    *
    *
    *
    *
    *
    * 最笨的实现
    *   1. 每一个车过来的时候，都获取一个信号量
    *   2. 可以读取了
    *
    *
    *
    *
    *  state = 0 表示南北路上的车辆是可以通过的
    *  state = 1表示东西路上的车辆可以通过
    * 什么时候转换呢？
    * 1. 如果此时另一条路上没有正在同行的车辆，随便转化
    * 2.
    *
    *
    *
    * 读者写者问题。
    *
    * 并行的读，互斥的写。
    * 1. cntN表示正在南北路上的车辆
    * 2. cntE表示正太东西路上的车辆
    *
    * 如果cntN > 0 直接通过，
    * 如果cntN == 0, 先获取信号灯
    *   - 如果是0直接通过
    *   - 判断cntE == 0？
    *       - 如果是，获取
    *
    *
    *
   *
    * 如果是绿灯直接通过。
    *
    *
    *
    * */

    private int state = 0;
    Semaphore s = new Semaphore(1);

    public TrafficLight() {

    }

    public void carArrived(
            int carId,           // ID of the car
            int roadId,          // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
            int direction,       // Direction of the car
            Runnable turnGreen,  // Use turnGreen.run() to turn light to green on current road
            Runnable crossCar    // Use crossCar.run() to make car cross the intersection
    ) {
        try {
            s.acquire(1);
            if (direction == 1 || direction == 2) {
                if (state != 0) {
                    state = 0;
                    turnGreen.run();
                }
                crossCar.run();
            }
            else {
                if (state != 1) {
                    state = 1;
                    turnGreen.run();
                }
                crossCar.run();
            }
            s.release(1);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}