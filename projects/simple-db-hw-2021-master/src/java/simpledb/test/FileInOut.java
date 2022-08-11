package simpledb.test;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class FileInOut {
    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        File file = new File("in.txt");
        File file2 = new File("D:\\projects\\java\\simple-db-hw-2021-master\\src\\java\\simpledb\\test\\ou.txt");


        try {
            FileWriter fw = new FileWriter(file2, true);
            PrintWriter printWriter = new PrintWriter(fw);
            Scanner cin = new Scanner(file);
            char q[] = new char[50];
            while(cin.hasNext()) {
                String s = cin.nextLine();
                System.out.printf(s);
                fw.write(s);
            }
            cin.close();
            printWriter.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }


    }
}