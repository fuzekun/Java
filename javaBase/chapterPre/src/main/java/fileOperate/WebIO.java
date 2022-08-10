package fileOperate;


import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class WebIO {

	
	static void read() {
		try {
			java.net.URL url = new java.net.URL("http://www.baidu.com");
			int count = 0;
			Scanner scanner = new Scanner(url.openStream());
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				count++;
				File file = new File("txt\\pachong.txt");
				try(PrintWriter printWriter = new PrintWriter(file)){
					printWriter.println(line);
				}
			}
			scanner.close();
			System.out.println("总共有" + count+"行");	
		}catch (java.net.MalformedURLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (java.io.IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	static void WebCraweler() {
		Scanner input= new java.util.Scanner(System.in);
		System.out.println("Enter a URL");
		String url  = input.nextLine();
		crawler(url);//遍历从开始网页上找到的链接
		input.close();
	}
	
	static void crawler(String startURL) {
		ArrayList<String >listOfPendingURLs = new ArrayList<>();
		ArrayList<String>listOfTraversedURLs = new ArrayList<>();
		
		listOfPendingURLs.add(startURL);
		while(!listOfPendingURLs.isEmpty() && listOfTraversedURLs.size() <= 10) {
			String urlString = listOfPendingURLs.remove(0);
			if(!listOfTraversedURLs.contains(urlString)) {
				listOfTraversedURLs.add(urlString);
				System.out.println("Crawl " + urlString);
				
				for(String s:getSubURLs(urlString)) {
					if(!listOfTraversedURLs.contains(s))
						listOfPendingURLs.add(s);
				}
			}
		}
		System.out.println(listOfTraversedURLs.size());
	}
	
	static ArrayList<String>getSubURLs(String urlString){
		ArrayList<String>list = new ArrayList<>();
		try {
			java.net.URL url = new java.net.URL(urlString);
			Scanner scanner=new Scanner(url.openStream());
			int current = 0;
			while(scanner.hasNext()) {
				String lineString = scanner.nextLine();
				current = lineString.indexOf("http:",current);
				while(current > 0) {
					int endIndex = lineString.indexOf("\"",current);
					if(endIndex > 0) {
						//System.out.println(lineString.substring(current,endIndex));
						list.add((String) lineString.subSequence(current, endIndex));
						current = lineString.indexOf("http:",endIndex);
					}
					else current=-1;
				}
			}
			scanner.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error : "  + e.getMessage());
		}
		return list;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		read();
		WebCraweler();
	}

}
