package manyArrays;

public class CreateAndFind {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] distance = {
				{0,938,785,826},
				{983,56,65,996},
				{56,56,82,94},
		};
		System.out.println(distance[1][2]);
		System.out.println(distance[1].length);
		int[][] triangleArray = {
				{1,2,3,4,5},
				{1,2,3,4},
				{1,2,3},
				{1,2},
				{1},
		};
		for(int i = 0;i < triangleArray.length;i++) {
			for(int j = 0;j < triangleArray[i].length;j++) {
				System.out.print(triangleArray[i][j] + " ");
			}
			System.out.println();
		}
	}
}

