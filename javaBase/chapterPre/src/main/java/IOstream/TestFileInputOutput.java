package IOstream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestFileInputOutput {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			File inFile = new File("copy.txt");
			File outFile = new File("copy2.txt");
			if(!inFile.exists()) {
				inFile.createNewFile();
				FileWriter fw = new FileWriter(inFile,true);
				fw.append("fdac");
				fw.append("Fdsa");
				fw.close();
				/*Scanner scanner = new Scanner(inFile);
				int a = scanner.nextInt();*/
			}
			if(!outFile.exists()) {
				outFile.delete();
			}
			FileInputStream finS = new FileInputStream(inFile);
			FileOutputStream foutS = new FileOutputStream(outFile);
			int c;
			while ((c = finS.read()) != -1) {
				System.out.print((char)c);
				foutS.write(c);
			}
			finS.close();
			foutS.close();
			System.out.println("文件复制成功");
		} catch (IOException e) {
			System.err.println("FileStreamsTest: " + e);
		}
		InputStreamReader sin = new InputStreamReader(System.in);
		BufferedReader bin = new BufferedReader(sin);
		FileWriter out = new FileWriter("myfile.txt",true);
		BufferedWriter bout = new BufferedWriter(out);
		String s;
		while ((s = bin.readLine())!=null) {
			System.out.println(s);
			bout.write(s, 0, s.length());
			bout.newLine();
			bout.flush();
		}
		bout.close();
	}

}
