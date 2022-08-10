package Template;

import java.util.ArrayList;

public class Stack<E> {
	ArrayList<E> list = new ArrayList<>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public int getSize(){
		return list.size();
	}
	public E peek(){
		if(list.isEmpty()) {
			System.out.println("Õ»¿Õ");
			E o = null;
			return o;
		}
		return list.get(list.size() - 1);
	}
	public E pop()throws StackEmptyException{
		if(list.isEmpty()) {
			throw new StackEmptyException();
		}
		E o =  list.get(list.size() - 1);
		list.remove(o);
		return o;
	}
	public void push(E o){
		list.add(o);
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Stack" + list.toString();
	}
}
