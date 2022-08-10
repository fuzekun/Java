package threadBase.JUC;

import java.util.concurrent.locks.StampedLock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/26 16:50
 * @Description: jdk8自带的示例
 */
public class StampedDemo {
    //一个点的x，y坐标
    private double x,y;
    private final StampedLock sl = new StampedLock();

    //【写锁(排它锁)】
    void move(double deltaX,double deltaY) {// an exclusively locked method
        /**stampedLock调用writeLock和unlockWrite时候都会导致stampedLock的stamp值的变化
         * 即每次+1，直到加到最大值，然后从0重新开始
         **/
        long stamp =sl.writeLock(); //写锁
        try {
            x +=deltaX;
            y +=deltaY;
        } finally {
            sl.unlockWrite(stamp);//释放写锁
        }
    }

    //【乐观读锁】
    double distanceFromOrigin() { // A read-only method
        /**
         * tryOptimisticRead是一个乐观的读，使用这种锁的读不阻塞写
         * 每次读的时候得到一个当前的stamp值（类似时间戳的作用）
         */
        long stamp = sl.tryOptimisticRead();
        //这里就是读操作，读取x和y，因为读取x时，y可能被写了新的值，所以下面需要判断
        double currentX = x, currentY = y;
        /**如果读取的时候发生了写，则stampedLock的stamp属性值会变化，此时需要重读，
         * 再重读的时候需要加读锁（并且重读时使用的应当是悲观的读锁，即阻塞写的读锁）
         * 当然重读的时候还可以使用tryOptimisticRead，此时需要结合循环了，即类似CAS方式
         * 读锁又重新返回一个stampe值*/
        if (!sl.validate(stamp)) {//如果验证失败（读之前已发生写）
            stamp = sl.readLock(); //悲观读锁
            try {
                currentX = x;
                currentY = y;
            }finally{
                sl.unlockRead(stamp);//释放读锁
            }
        }
        //读锁验证成功后执行计算，即读的时候没有发生写
        return Math.sqrt(currentX *currentX + currentY *currentY);
    }

    //【悲观读锁】
    // 如果在原点就进行移动, 不在原点就不移动
    void moveIfAtOrigin(double newX, double newY) { // upgrade
        // 读锁（这里可用乐观锁替代）
        long stamp = sl.readLock();
        try {
            //循环，检查当前状态是否符合
            while (x == 0.0 && y == 0.0) {
                /**
                 * 转换当前读戳为写戳，即上写锁
                 * 1.写锁戳，直接返回写锁戳
                 * 2.读锁戳且写锁可获得，则释放读锁，返回写锁戳
                 * 3.乐观读戳，当立即可用时返回写锁戳
                 * 4.其他情况返回0
                 */
                long ws = sl.tryConvertToWriteLock(stamp);
                //如果写锁成功
                if (ws != 0L) {
                    stamp = ws;// 替换票据为写锁
                    x = newX;//修改数据
                    y = newY;
                    break;
                }
                //转换为写锁失败
                else {
                    //释放读锁
                    sl.unlockRead(stamp);
                    //获取写锁（必要情况下阻塞一直到获取写锁成功）
                    stamp = sl.writeLock();
                }
            }
        } finally {
            //释放锁（可能是读/写锁）
            sl.unlock(stamp);
        }
    }
}