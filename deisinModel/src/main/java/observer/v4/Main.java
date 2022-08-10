package observer.v4;

import observer.v3.Source;

/*
*
*   写一个异步的修改器。
*
* 这个是传统的观察者模式，只有观察者和被观察者。
* 两者之间是比较紧的耦合。
* 但是被观察者不应该给观察者提供修改自己状态的方法。
* */
public class Main {


    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.add(new Observer() {
            @Override
            public void doit(Subject subject) {
                System.out.println("当前的状态是" + subject.getState());

                System.out.println("修改的太慢了， 开一个新的线程");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("正在修改主题的一些状态...");
                        try {
                            Thread.sleep(1000);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        subject.setState(10);
                        System.out.println("现在修改状态成了:" + subject.getState());
                    }
                }).start();

                System.out.println("在这里可以直接返回了，不用等待他是否修改完成了");
            }
        });
        subject.notifyAllObservers();
    }
}
