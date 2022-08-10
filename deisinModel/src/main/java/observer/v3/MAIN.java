package observer.v3;

/*
*
*
*   观察者和责任链模式的结合
*
* 由用户自己决定是否进行这个事件的处理。
* 这样原来系统的代码就不需要进行改变了。
*
* */
import java.lang.reflect.InvocationHandler;

public class MAIN {

    public static void main(String[] args) {
        Source source = new Source();

        // 此处添加两个观察者
        source.addObserver(new Observer() {
            @Override
            public void doit(Event e) {     // 专注处理第一种事件
                if (!(e instanceof NumEvent)) return;
                NumEvent ee = (NumEvent) e;
                System.out.println("观察者0进行处理");
                switch (ee.getKey_press()){
                    case 0:
                        source.setState(0);
                        System.out.println("按下了0键");break;
                    case 1:
                        source.setState(1);
                        System.out.println("按下了1键");break;
                    case 2:
                        source.setState(2);
                        System.out.println("按下了2键");break;
                    case 3:
                        source.setState(3);
                        System.out.println("按下了3键");break;
                }
                System.out.println("sorce.init state = " + source.getState());
            }
        }).addObserver(new Observer() {
            @Override
            public void doit(Event e) {     // 专注处理第二种事件
                if (!(e instanceof DirEvent)) return;
                DirEvent ee = (DirEvent)e;
                System.out.println("观察者1： 开启一个新的线程进行处理");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        String dir = ee.getKey_press();
                        source.setDir(dir);
                        System.out.println("按下了" + dir + "键");
                        System.out.println("处理完成");
                        System.out.println("source.state = " + source.getState());
                    }
                }, "A").start();
            }
        });

        source.notifyAllOberver();


        //
        /*public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
        这里的三个参数:
           proxy是代理对象，也就是所谓的事件源
           调用了Proxy的某个对象就可以

         */

    }
}
