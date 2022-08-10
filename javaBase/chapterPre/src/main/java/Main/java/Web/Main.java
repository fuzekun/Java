package Web;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("student.dat",true));
        StudentAddress studentAddress = new StudentAddress("fuzkun", "wudaokou", "beijign", "283", "222");
        objectOutputStream.writeObject(studentAddress);
        objectOutputStream.writeObject(studentAddress);
        objectOutputStream.writeObject(studentAddress);
        
        objectOutputStream.writeObject(null);
        InputStream inputStream2 = new FileInputStream("student.dat");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream2);
        Object object;
        try {
        	while(inputStream2.available() > 0) {
        		object = objectInputStream.readObject();
        		System.out.println(object);
        	}
        }catch (EOFException e) {
			// TODO: handle exception
        	System.out.println("∂¡ÕÍ¡À");
        }
        objectInputStream.close();
        objectOutputStream.close();
       
	}
}
