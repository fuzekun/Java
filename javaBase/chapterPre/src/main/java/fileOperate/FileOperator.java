package fileOperate;
import  java.io.File;
public class FileOperator {
	
	public static void test1() {
		File dummyFile = new File("D:\\各种文件\\java文件\\java学习.txt");
		boolean fileExists = dummyFile.exists();
		if(fileExists) {
			System.out.println("The java学习 file exits");
		}
		else {
			System.out.println("The java学习 file not exits.");
		}
	}
	
	static void printFilePath(String pathname) {
		File file  = new File(pathname);
		System.out.println("File Name" + file.getName());
		System.out.println("File exits :" + file.exists());
		System.out.println("Absolute Path" +  file.getAbsolutePath());
	}
	
	static void test2() {
		File file = new java.io.File("E:\\eclipse安装与Java文件\\java\\src\\fileOperate\\java.png");
		System.out.println("Does file exists? "+file.exists());
		System.out.println("The file has " + file.length() + "bytes");
		System.out.println("power" + " read" +" write");
		System.out.println("      " + file.canRead() + "   " + file.canWrite());
		System.out.println("Is it a directory? " + file.isDirectory());
		System.out.println("Is it a file " + file.isFile());
		System.out.println("Is it absolute?" + file.isAbsolute());
		System.out.println("Is it hidden?" + file.isHidden());
		System.out.println("Absolutely path is "+file.getAbsolutePath());
		System.out.println("last modified on " + new java.util.Date(file.lastModified()));
	}
	
	static void WriteData(){
		
	}
	
	static void testWriteAndRead() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
		test2();
		printFilePath("fileOperator");
	}
}
