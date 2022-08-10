package IOstream;

import java.io.*;
import java.util.LinkedHashSet;

public class TestObjectIo {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("d:\\test.dat"));
		LinkedHashSet<String>set1 = new LinkedHashSet<>();
		set1.add("New York");
		LinkedHashSet<String>set2 = (LinkedHashSet<String>) set1.clone();
		set1.add("adad");
		output.writeObject(set1);
		output.writeObject(set2);
		output.close();
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("d:\\test.dat"));
		set1 = (LinkedHashSet<String>)input.readObject();
		set2 = (LinkedHashSet<String>) input.readObject();
		System.out.println(set1);
		System.out.println(set2);
	}
}
