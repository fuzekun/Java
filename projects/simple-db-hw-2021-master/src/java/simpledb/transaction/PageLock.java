package simpledb.transaction;

/**
 * @author: Zekun Fu
 * @date: 2023/3/14 15:36
 * @Description:
 */
// 锁
public class PageLock{
    public static final int SHARE = 0;
    public static final int EXCLUSIVE = 1;
    private TransactionId tid;
    private int type;
    public PageLock(TransactionId tid, int type){
        this.tid = tid;
        this.type = type;
    }
    public TransactionId getTid(){
        return tid;
    }
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
}
