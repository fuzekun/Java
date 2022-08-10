package classLoader;

public class HelloWorld {

    public void say() {
        System.out.println("I am V1");
    }

    public static void main(String[] args) {
        new HelloWorld().say();
    }
}
