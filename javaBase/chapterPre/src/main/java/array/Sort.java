package array;


public class Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] list = {1,9,3,4,5.5,5.6};
		SelectSort(list);
		for(int i = 0;i < list.length;i++) {
			System.out.print(list[i] + " ");
		}
	}
	
	static void SelectSort(double[] list) {
		for(int i = 0;i < list.length - 1;i++) {
			double currentMin = list[i];
			int currentMinIndex = i;
			for(int j = i + 1;j < list.length;j++) {
				if(currentMin > list[j]) {
					currentMinIndex = j;
				}
			}
			if(currentMinIndex != i) {
				list[currentMinIndex] = list[i];
				list[i] = currentMin;
			}
		}
	}
}