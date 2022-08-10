package Class;

/*
 * 这是用数组写的一个java的栈
 * 还需要用列表写一个java的栈
 * 以及队列的实现
 * */
public class StackOfInteger {
	private int[] Elements;
	private int size;
	private static final int DEFUALT_CAPTION = 16;
	public StackOfInteger() {
		this(DEFUALT_CAPTION);//this关键字用于调用另一个构造方法
		this.size = 0;
	}
	
	public StackOfInteger(int defualtCaption) {
		// TODO Auto-generated constructor stub
		Elements = new int[defualtCaption];
	}
	
	public void push(int value)throws StackOverflowError {
		if(size >= Elements.length) {
			System.out.println("原来的大小不够，重新分配二倍大小");
			int[] tmp = new int[Elements.length * 2];
			int j  = 0;
			for(int i = 0;i < Elements.length;i++) {
				tmp[j++] = Elements[i];
			}
			/*System.arraycopy(Elements, 0, tmp, 0, Elements.length);*/
			Elements = tmp;
		}
		Elements[size++] = value;
		if(size >= 100) throw new StackOverflowError();
	}
	
	public int pop() throws StackEmptyException{
		if(!empty()) {//需要判断否则不对
			return Elements[--size]; 
		}
		else throw new StackEmptyException();
	}
	
	public boolean empty() {
		return size == 0;//真的精炼
	}
	public int getSize() {
		return size;
	}
	
}
