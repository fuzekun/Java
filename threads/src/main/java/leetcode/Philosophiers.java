package leetcode;

import java.util.concurrent.Semaphore;

/*
*
*   哲学家进餐问题
*
* 1. 只拿左手边的筷子
* 2. 只允许四个人吃饭。
* 3. 直接锁房子。 -- 会造成资源的紧张。
*  */
public class Philosophiers {
    private Semaphore s = new Semaphore(4);     // 最多4个人
    public Semaphore[] chi = new Semaphore[5];         // 5爽筷子
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        s.acquire();
        chi[philosopher].acquire();     // 拿起左筷子
//        Thread.sleep(100);
        chi[(philosopher + 1) % 5].acquire();     // 拿起右筷子
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();                      //吃饭
        putLeftFork.run();
        putRightFork.run();
        chi[philosopher].release();     // 放下左筷子
        chi[(philosopher + 1) % 5].release();     // 放下右筷子
        s.release();
    }


    public static void main(String[] args) {
        Philosophiers p = new Philosophiers();
        for (int i = 0; i < 5; i++) {
            p.chi[i] = new Semaphore(1);
        }
        for (int i = 0; i < 5; i++) {
                new Thread(()->{
                    try {
                        p.wantsToEat(1,
                                ()->{
                                    System.out.println(Thread.currentThread().getName() + "拿起左筷子");
                                },
                                ()->{
                                    System.out.println(Thread.currentThread().getName() + "拿起右筷子");
                                },
                                ()->{
                                    System.out.println(Thread.currentThread().getName() + "吃饭");
                                },
                                ()->{
                                    System.out.println(Thread.currentThread().getName() + "放下左筷子");
                                },
                                ()->{
                                    System.out.println(Thread.currentThread().getName() + "放下右筷子");
                                }
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
        }
    }
}

