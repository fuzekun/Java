package Template;

public class GenericSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] inArray = {new Integer(2),new Integer(3),new Integer(-1)};
		Double[] douArray = {new Double(2.0),new Double(3.0),new Double(1.0)};
		
		/*
		int[] list = {1,3,-4};
		sort(list);
		*/

		sort(inArray);
		sort(douArray);
		
		printList(inArray);
		printList2(douArray);
	}
	public  static <E extends Comparable<E>> void sort(E[] list) {
		E currentMin;
		int currentMinIndex;
		for(int i = 0;i < list.length - 1;i++) {
			currentMin = list[i];
			currentMinIndex = i;
			for(int j = i + 1;j < list.length;j++) {
				if(currentMin.compareTo(list[j]) > 0) {
					currentMin = list[j];
					currentMinIndex = j;
				}
			}
			if(currentMinIndex != i) {
				list[currentMinIndex] = list[i];
				list[i] = currentMin;
			}
		}
	}
	public static<E> void printList2(E[] list){
		for(int i = 0;i < list.length;i++) {
			System.out.print(list[i] + " ");
		}
		System.out.println();
	}
	public static void printList(Object[] list) {
		for(int i = 0;i < list.length;i++) {
			System.out.print(list[i] + " ");
		}
		System.out.println();
	}
}
