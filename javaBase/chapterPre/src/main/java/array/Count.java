package array;

public class Count {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] chars = CreateArray();
		System.out.println("The Lower Letters are");
		printArray(chars);
		
		int[] count = countLetters(chars);
		System.out.println();
		System.out.println("The occurrences of each letter are:");
		displayCount(count);
	}
	static char[] CreateArray() {
		char[] chars = new char[50];
		for(int i = 0;i < chars.length;i++) {
			chars[i] = getRandomCharacter.getRandomeLowerCharacter();
		}
		return chars;
	}
	static void printArray(char[] chars) {
		for(int i = 0;i < chars.length;i++) {
			if((i + 1) % 20 == 0) {
				System.out.println(chars[i]);
			}
			else System.out.print(chars[i] + " ");
		}
	}
	//
	static int[] countLetters(char[] chars){
		int n = chars.length;
		int[] count = new int[26];
		for(int i = 0;i < n;i++) {
			count[chars[i] - 'a']++;
		}
		return count;
	}
	static void displayCount(int[] count) {
		int n = count.length;
		for(int i = 0;i < n;i++) {
			if((i + 1) % 10 == 0) {
				System.out.println(count[i] + " "+ (char)(i + 'a'));
			}
			else System.out.print(count[i] + " " + (char)(i + 'a') + " ");
		}
	}
}
