package leetcode;

public class ThreadLocalTest {



        private static ThreadLocal<String> localVar = new ThreadLocal<String>();    // threadlocal的全局变量
        private static String s;            // 普通的全局变量

        static void print(String str) {
            //打印当前线程中本地内存中本地变量的值

            System.out.println(str + " :" + localVar.get());
            //清除本地内存中的本地变量
            localVar.remove();
            System.out.println(s);
        }
        public static void main(String[] args) throws InterruptedException {

            new Thread(new Runnable() {
                public void run() {
                    s = "I am A";
                    ThreadLocalTest.localVar.set("local_A");
                    try {
                        Thread.sleep(1000);
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                    print("A");
                    //打印本地变量
                    System.out.println("after remove : " + localVar.get());

                }
            }).start();


            new Thread(new Runnable() {
                public void run() {
                    ThreadLocalTest.localVar.set("local_B");
                    s = "I am B";
                    print("B");

                    System.out.println("after remove : " + localVar.get());

                }
            }).start();
        }

}
