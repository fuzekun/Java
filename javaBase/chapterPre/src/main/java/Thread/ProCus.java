package Thread;

import java.util.Arrays;

public class ProCus{
	public static void main(String[] args) throws InterruptedException {
		Bar bar = new Bar(6);//����ʮ����С�Ĳֿ�;
		//����������
		Producer p1 = new Producer(bar,1);
		Producer p2 = new Producer(bar, 2);
		
		//����������
		Customer c1 = new Customer(bar,1);
		
		//�����߳�
		Thread thread1 = new Thread(p1);
		Thread thread2 = new Thread(c1);
		Thread thread3 = new Thread(p2);
		//�����̣߳���ʼ����������
		thread3.start();//���ȥ��notify�ͻ�����������������
		Thread.sleep(1000);//�Ȳ���
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
	int size = 10;//���size�ǲֿ�������Ĵ�С
	Huowu[] list =  null;
	public synchronized void push(Huowu h) throws InterruptedException {
		while(index == size) {
			//������˽��̹���ȴ������ѣ���Դ�ᱻ�ͷ�
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
		//һ������������6��
		for(int i = 0;i < 6;i++) {
			Huowu h = new Huowu(i);//����
			System.out.println("������" + id +"������" + h);
			bar.push(h);		//����ֿ�
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
	public  void custome() throws InterruptedException {//һ��������������������
		for(int i = 0;i < 6;i++) {
			Huowu tmp = bar.pop();//����
			System.out.println("������" + id  + "������" + tmp);
		}
	}
}

