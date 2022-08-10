package Thread;

import java.util.Arrays;

public class ProCus{
	public static void main(String[] args) throws InterruptedException {
		Bar bar = new Bar(6);//开辟十个大小的仓库;
		//生产者生产
		Producer p1 = new Producer(bar,1);
		Producer p2 = new Producer(bar, 2);
		
		//消费者消费
		Customer c1 = new Customer(bar,1);
		
		//创建线程
		Thread thread1 = new Thread(p1);
		Thread thread2 = new Thread(c1);
		Thread thread3 = new Thread(p2);
		//启动线程，开始生产和消费
		thread3.start();//如果去掉notify就会死锁，先让它满了
		Thread.sleep(1000);//先产满
		thread2.start();
		thread1.start();
		
	}
}

class Huowu{
	int id;
	public Huowu(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	@Override
	public String toString() {
		return "Huowu [id=" + id + "]";
	}
	public Huowu() {
		// TODO Auto-generated constructor stub
	}
}

class Bar{
	public Bar(int size) {
		// TODO Auto-generated constructor stub
		this.size = size;
		list = new Huowu[size];
	}
	@Override
	public String toString() {
		return "Bar [index=" + index + ", size=" + size + ", list=" + Arrays.toString(list) + "]";
	}
	int index  = 0;
	int size = 10;//这个size是仓库的容量的大小
	Huowu[] list =  null;
	public synchronized void push(Huowu h) throws InterruptedException {
		while(index == size) {
			//如果空了进程挂起等待被唤醒，资源会被释放
			this.wait();
		}
		this.notify();
		list[index++] = h;
	}
	public synchronized Huowu pop() throws InterruptedException {
		while(index == 0) {
			this.wait();
		}
		this.notify();
		return list[--index];
	}
	public int getIndex() {
		return index;
	}
	public int getSize() {
		return size;
	}
}

class Producer implements Runnable{
	private Bar bar;
	int id;
	public Producer(Bar b,int id) {
		// TODO Auto-generated constructor stub
		bar = b;
		this.id = id;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			produce();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void produce() throws InterruptedException {
		//一个生产者生产6个
		for(int i = 0;i < 6;i++) {
			Huowu h = new Huowu(i);//生产
			System.out.println("生产者" + id +"生产了" + h);
			bar.push(h);		//放入仓库
		}
	}
}

class Customer implements Runnable{
	private Bar bar;
	int id = 0; 
	public Customer(Bar bar,int id) {
		// TODO Auto-generated constructor stub
		this.bar = bar;
		this.id = id;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			custome();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void custome() throws InterruptedException {//一个消费者消费三个货物
		for(int i = 0;i < 6;i++) {
			Huowu tmp = bar.pop();//消费
			System.out.println("消费者" + id  + "消费了" + tmp);
		}
	}
}

