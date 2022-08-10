package single;

/**
 * @author: Zekun Fu
 * @date: 2022/5/23 9:59
 * @Description: 双锁检测，如果不加volatile线程不安全。
 *                  因为会访问到未初始化的对象
 */
public class Doubl_Lock_Check {
    private static Doubl_Lock_Check instance; // 线程不安全
    private volatile static Doubl_Lock_Check instance2; // 线程安全
    private Doubl_Lock_Check(){}            // 私有构造器

    public static Doubl_Lock_Check getInstance() {
        if (instance == null) {
            synchronized (Doubl_Lock_Check.class) {
                instance = new Doubl_Lock_Check();
            }
        }
        return instance;
    }

    public static Doubl_Lock_Check getInstance2() {
        if (instance2 == null) {
            synchronized (Doubl_Lock_Check.class) {
                instance2 = new Doubl_Lock_Check();
            }
        }
        return instance2;
    }

    public static void main(String[] args) {

    }
}
