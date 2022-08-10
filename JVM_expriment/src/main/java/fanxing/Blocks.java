package fanxing;

import java.io.File;
import java.util.Scanner;

public abstract class Blocks {
    public abstract void body() throws Exception; // 抛出一个受检查异常

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwsAs(Throwable e) throws T {
        throw (T) e;        //  T是一个Error的情况下, e是一个Exception竟然没问题
    }

    public Thread toThread() {
        return new Thread() {
            @Override
            public void run() {
                try{
                    body();
                }catch (Exception t) {

                    Blocks.<Error>throwsAs(t);
                }
            }
        };
    }


    public static void main(String[] args) {
        new Blocks() {
            @Override
            public void body() throws Exception {
                Scanner in =  new Scanner(new File("test"),"UTF-8");
                while(in.hasNext()) {
                    System.out.println(in.next());
                }
            }
        }.toThread().run();


    }
}
