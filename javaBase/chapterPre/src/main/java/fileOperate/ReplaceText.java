package fileOperate;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class ReplaceText {
	
	static void testRelace() {
		String string1 = "25 5 8 3";
		String s2 = string1.replaceAll("5", "3");
		System.out.println(s2);
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//testRelace();
		if(args.length!=4) {
			System.out.println("Usage :java Replace sourceFile targetFile oldStr newStr");
			System.exit(1);
		}
		//Check if source file exists;
		File file = new File(args[0]);
		if(!file.exists()) {
			System.out.println("Source file " + args[0] +" does not exist");
			System.exit(2);
		}
		//Check if target file exists
		File targetFile = new File(args[1]);
		if(targetFile.exists()) {
			System.out.println("Target file "+args[1] + " has already exists");
		}
		
		try(
				//Create input and output file
				Scanner scanner = new Scanner(file);
				PrintWriter output  = new PrintWriter(targetFile);
				){

			while(scanner.hasNext()) {
				String st1 = scanner.nextLine();
				String st2 = st1.replaceAll(args[2], args[3]);				
				output.println(st2);
			}
		}
		System.out.println("finally");

		}
}
