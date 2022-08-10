package leetcode;

public class FuzzBuzzMain {

    public static void main(String[] args) throws Exception{
        //FizzBuzz f = new FuzzBuzz2(15); // 多态的简单应用，直接使用配置文件就行了，复用了业务的逻辑代码，库的代码可以不用改变。
        FizzBuzz f = (FizzBuzz) Class.forName(args[0]).getDeclaredConstructor(int.class).newInstance(Integer.valueOf(args[1]));


        new Thread(()->{

            try {
                f.fizz(()->{            // 打印fizz
                    System.out.print("fizz ");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{

            try {
                f.buzz(()->{
                    System.out.print("buzz "); // 打印Buzz
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{

            try {

                f.fizzbuzz(()->{            // 打印fizzbuzz
                    System.out.print("fizzbuzz ");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{

            try {
                f.number((x)-> System.out.print(x + " "));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
