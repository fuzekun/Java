package array;

public class binarySearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("fda");
		int[] list = {2,4,5,7,10,11,34,45,50,60,69,70,79};
		int j = binarySearch2(list ,2);
		int i = binarySearch2(list,11);
		int k = binarySearch2(list,55);
		System.out.println(j + " " + i + " " +  k);
	}
	public static int binarySearch2(int[] list,int key) {
		int low = 0;
		int high = list.length - 1;
		
		while(high >= low) {
			int mid = (low + high) / 2;
			if(key < list[mid])
				high = mid - 1;
			else if(key == list[mid])
				return mid;
			else low = mid + 1;
		}
		return -low- 1;
	}
}
