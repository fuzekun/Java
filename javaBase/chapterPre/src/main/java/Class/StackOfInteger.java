package Class;

/*
 * ����������д��һ��java��ջ
 * ����Ҫ���б�дһ��java��ջ
 * �Լ����е�ʵ��
 * */
public class StackOfInteger {
	private int[] Elements;
	private int size;
	private static final int DEFUALT_CAPTION = 16;
	public StackOfInteger() {
		this(DEFUALT_CAPTION);//this�ؼ������ڵ�����һ�����췽��
		this.size = 0;
	}
	
	public StackOfInteger(int defualtCaption) {
		// TODO Auto-generated constructor stub
		Elements = new int[defualtCaption];
	}
	
	public void push(int value)throws StackOverflowError {
		if(size >= Elements.length) {
			System.out.println("ԭ���Ĵ�С���������·��������С");
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
		if(!empty()) {//��Ҫ�жϷ��򲻶�
			return Elements[--size]; 
		}
		else throw new StackEmptyException();
	}
	
	public boolean empty() {
		return size == 0;//��ľ���
	}
	public int getSize() {
		return size;
	}
	
}
