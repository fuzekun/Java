package Thread;

import sun.security.krb5.internal.TGSRep;

import javax.sound.midi.Track;

/*
*
* 	实现思路
* 1. 线程1启动，使用12方法，获取o1的锁，之后等待1s，等待线程2创建并且获取o2的锁。
* 2. 线程2启动，使用21方法，获取o2的锁，之后等待1s,等待线程1创建并且获取o2的锁。
* 3. 主线程在创建o2的时候，需要
*
* */


// 可以不用实现接口，直接作为一个"资源"类，提供锁变量即可。
public class TestDeadLock{
	public int flag;
	public Object o1 = new Object(),o2 = new Object();

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		TestDeadLock test = new TestDeadLock();
		// 更简单的实现逻辑，直接创建新的

		new Thread(()->{
			synchronized (test.o1) {
				System.out.println("线程" + Thread.currentThread().getName() + "锁住了o1, 等待o2");
				try {
					Thread.sleep(1000);	// 等待o2的创建，并获取锁
//				o1.wait();			// 使用o1可以释放锁，这样线程2就可以完成了,但是thread1这个时候处于锁的状态
				} catch (Exception e) {
					e.printStackTrace();
				}
				synchronized (test.o2) {
					System.out.println("线程" + Thread.currentThread().getName() + "执行完成");
				}
			}
		}).start();

		new Thread(()->{
			synchronized (test.o2) {
				System.out.println("线程" + Thread.currentThread().getName() + "锁住了o2, 等待o1");
				synchronized (test.o1) {
					System.out.println("线程" + Thread.currentThread().getName() + "执行完成");
//				o1.notify();		// 如果不notify,那么线程1一直锁在o1的阻塞队列上
				}
			}
		}).start();
	}

}
