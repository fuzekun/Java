package CollectionLearn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class CountKeyWords {
	public static void main(String[] args) throws Exception {
		System.out.println(countKeyWords(new File("d:\\tmp.txt")));
	}
	public static int countKeyWords(File file)throws Exception{
		ArrayList<String> keywords = new ArrayList<>();
		String s;
		keywords.add("Add");
		keywords.add("sub");
		keywords.add("If");
		Reader reader = new FileReader(file);
		while(reader.read() != -1) {
			System.out.println();
		}
		BufferedReader bufferedInputStream = new BufferedReader(reader);
		int cnt = 0;
		while((s = bufferedInputStream.readLine()).length() > 0) {
			String[] str = s.split(" ");
			for(int i = 0;i < str.length;i++) {
				if(keywords.contains(str[i])) {
					cnt++;
				}
			}

		}
		Scanner sc = new Scanner(file);
		while(sc.hasNext()) {
			s = sc.nextLine();
			String[] str = s.split(" ");
			for(int i = 0;i < str.length;i++) {
				if(keywords.contains(str[i])) {
					cnt++;
				}
			}
		}
		return cnt;
	}
}
