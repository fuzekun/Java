package observer.v5;
/**
 *
 *
 * * 需求：
 * * 1. 观察者，添加进去的时候，把自己对应的事件告诉主题
 * * 2. 如果是自己感兴趣的主题,主题再通知
 */
public class Main {

    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.add(new Observer() {            // 对NumEvent感兴趣
            @Override
            public boolean interst(Event e) {
                return e instanceof NumEvent;
            }

            @Override
            public void doit(Event e) {
                NumEvent ee = (NumEvent) e;
                System.out.println("观察者0进行处理");
                switch (ee.getKey_press()){
                    case 0:
                        subject.setState(0);
                        System.out.println("按下了0键");break;
                    case 1:
                        subject.setState(1);
                        System.out.println("按下了1键");break;
                    case 2:
                        subject.setState(2);
                        System.out.println("按下了2键");break;
                    case 3:
                        subject.setState(3);
                        System.out.println("按下了3键");break;
                }
                System.out.println("subjec.state = " + subject.getState());
            }
        });

        subject.add(new Observer() {
            @Override
            public boolean interst(Event e) {
                return e instanceof DirEvent;   // 对DirEvent感兴趣
            }

            @Override
            public void doit(Event e) {
                DirEvent ee = (DirEvent)e;
                System.out.println("观察者1： 开启一个新的线程进行处理");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String dir = ee.getKey_press();
                        subject.setDir(dir);
                        System.out.println("按下了" + dir + "键");
                        System.out.println("处理完成");
                        System.out.println("source.dir = " + subject.getDir());
                    }
                }).start();
            }
        });

        subject.notifyAllObservers();
    }
}
