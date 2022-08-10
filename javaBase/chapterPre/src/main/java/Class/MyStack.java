package Class;

import java.util.ArrayList;

/*
 * 使用队列写stack;
 * 用来存储对象
 * */
public class MyStack {
	private ArrayList<Object>list = new ArrayList<>();
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public int getSize() {
		return list.size();
	}
	
	public Object peek() {
		return list.get(getSize() - 1);
	}
	
	public void push(Object o) {
		list.add(o);
	}
	public Object pop() throws StackEmptyException{
		if(!isEmpty()) {
			Object object = list.get(getSize() - 1);
			list.remove(getSize() - 1);
			return object;
		}
		else throw new StackEmptyException();
	}

	@Override
	public String toString() {
		return "MyStack [list=" + list + "]";
	}
	
}
