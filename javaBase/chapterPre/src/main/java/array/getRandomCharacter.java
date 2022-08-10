package array;

public class getRandomCharacter {
	public static char getRandomeCharacter(char ch1,char ch2) {
		return (char)(ch1 + Math.random() * (ch2 - ch1 + 1));
	}
	public static char getRandomeCharacter() {
		return getRandomeCharacter('\u0000','\uFFFF');
	}
	public static char getRandomeLowerCharacter() {
		return getRandomeCharacter('a','z');
	}
	public static char getRandomeUpperCharacter() {
		return getRandomeCharacter('A','Z');
	}
	public static char getRandomeDigitalCharacter() {
		return getRandomeCharacter('0','9');
	}
}
