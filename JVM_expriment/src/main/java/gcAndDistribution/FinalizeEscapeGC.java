package gcAndDistribution;

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
        /*调用这个方法之后把引用重新给挂到自己身上，
        那么这个对象就又有了强引用，
        就可达，
        就不会被垃圾回收器回收。
        */
    }

    public static void main(String[] args) throws Throwable{
        SAVE_HOOK = new FinalizeEscapeGC();

        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead");
        }

        // 只能调用一个finalize方法，所以就算再次把这个对象指向也没有什么用了
        SAVE_HOOK = null;

        System.gc();
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            System.out.println("I am still alive");
        } else {
            System.out.println("no i am dead");
        }

    }
}
