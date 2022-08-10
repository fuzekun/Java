package CollectionLearn;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import InterfrenceAndABS.ComparableRectange;
import InterfrenceAndABS.GeometericObject;
import InterfrenceAndABS.Rectangle;

public class StudyQueue {
	public static void main(String[] args) {
		Queue<String>queue = new LinkedList<String>();
		queue.offer("fda");
		queue.offer("fd");
		queue.offer("a");
		queue.offer("fa");
		while(queue.size()>0) {
			System.out.print(queue.remove() + " ");
		}
		System.out.println();
		testPri();
	}
	public  static void  testPri() {
		PriorityQueue<String>queue1 = new PriorityQueue<>();
		queue1.offer("fda");
		queue1.offer("da");
		queue1.offer("fa");
		queue1.offer("fd");
		System.out.println("使用自然比较器的优先队列");
		while(queue1.size() > 0) {
			System.out.print(queue1.remove()+" ");
		}
		System.out.println();
		PriorityQueue<GeometericObject>queue2= new PriorityQueue<>(3,new GeometricObjectComparator());
		queue2.offer(new Rectangle(1,3));
		queue2.offer(new Rectangle(3,4));
		queue2.offer(new Rectangle(1,4));
		System.out.println("使用使用比较器进行比较");
		while(queue2.size() > 0) {
			System.out.println(queue2.remove());
		}
	}
}
