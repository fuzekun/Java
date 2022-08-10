package compiler.nameCheckP;

/*
*
*   java中变长参数的实现
*
* */
public class ChangeLenArgs {

    private static void print(String ... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    public static void main(String[] args) {
        print("你好");
        print("你也好", "小明");
    }
}
