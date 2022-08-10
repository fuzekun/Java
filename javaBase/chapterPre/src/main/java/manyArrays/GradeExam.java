package manyArrays;

public class GradeExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[][] answers = {
				{'a','b','c'},
				{'b','c','d'},
				{'b','c','d'},
			};
		char[] keys = {'a','b','c'};
		for(int i = 0;i < answers.length;i++) {
			int count = 0;
			for(int j = 0;j < answers[i].length;j++) {
				if(answers[i][j] == keys[i])
					count++;
			}
			System.out.println(count);
		}
		char[][]answer = {
				{'a','b','a'},
				{'b','b','a'},
				{'b','a','a'},
		};
		int[] stu = new int[3];  
		for(int i = 0;i < answer.length;i++) {
			for(int j = answer.length - 1;j >= 0;j--) {
				if(answer[i][j] == 'a') {
					stu[i] = stu[i] | 1 << j;
				}
			}
		}
	}
}
