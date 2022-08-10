package Thread;

import sun.security.krb5.internal.TGSRep;

import javax.sound.midi.Track;

/*
*
* 	ʵ��˼·
* 1. �߳�1������ʹ��12��������ȡo1������֮��ȴ�1s���ȴ��߳�2�������һ�ȡo2������
* 2. �߳�2������ʹ��21��������ȡo2������֮��ȴ�1s,�ȴ��߳�1�������һ�ȡo2������
* 3. ���߳��ڴ���o2��ʱ����Ҫ
*
* */


// ���Բ���ʵ�ֽӿڣ�ֱ����Ϊһ��"��Դ"�࣬�ṩ���������ɡ�
public class TestDeadLock{
	public int flag;
	public Object o1 = new Object(),o2 = new Object();

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		TestDeadLock test = new TestDeadLock();
		// ���򵥵�ʵ���߼���ֱ�Ӵ����µ�

		new Thread(()->{
			synchronized (test.o1) {
				System.out.println("�߳�" + Thread.currentThread().getName() + "��ס��o1, �ȴ�o2");
				try {
					Thread.sleep(1000);	// �ȴ�o2�Ĵ���������ȡ��
//				o1.wait();			// ʹ��o1�����ͷ����������߳�2�Ϳ��������,����thread1���ʱ��������״̬
				} catch (Exception e) {
					e.printStackTrace();
				}
				synchronized (test.o2) {
					System.out.println("�߳�" + Thread.currentThread().getName() + "ִ�����");
				}
			}
		}).start();

		new Thread(()->{
			synchronized (test.o2) {
				System.out.println("�߳�" + Thread.currentThread().getName() + "��ס��o2, �ȴ�o1");
				synchronized (test.o1) {
					System.out.println("�߳�" + Thread.currentThread().getName() + "ִ�����");
//				o1.notify();		// �����notify,��ô�߳�1һֱ����o1������������
				}
			}
		}).start();
	}

}
