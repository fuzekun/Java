package exeSystem;

/*
*
*   char -> int -> long -> float -> double -> Character -> serializable/ comparable
*  -> Object -> char..
* */
public class Overload {
    public static void sayHello (char... arg) {
        System.out.println("hello char...");
    }
    public static void sayHello (Object arg) {
        System.out.println("Hello object");
    }

    public static void sayHello (int arg) {
        System.out.println("Hello int");
    }

//    public static void sayHello(char arg) {
//        System.out.println("Hello char");
//    }

    public static void sayHello (Character arg) {
        sayHello("Hello Character");
    }

    public static void main(String[] args) {
        sayHello('a');
    }
}
