package TryAndLog;

import com.sun.istack.internal.localization.NullLocalizable;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import javax.imageio.IIOException;
import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReThrow {

    /*
    *
    *   调用lock必须式显式的调用unlock。否则不会解锁对象
    * */
    @Test
    public void testLock() {
        Lock myLock = new ReentrantLock();
        try {
            myLock.lock();
        } catch (Exception e) {
            throw e;
        } finally {
            myLock.unlock();
        }
    }

    @Test
    public void testJdk7TryWith() {

        try(InputStream inputStream = new FileInputStream(new File("test"))) {

        } catch(Exception e) {
            e.printStackTrace();

        }
    }
    /*
    *
    *   由于第一个异常可能是根本原因，那么怎么进行捕获呢？
    * */
    public static void main(String[] args) throws Exception {

        InputStream in = null;
        Exception ex = null;

        try {
            in = new FileInputStream(new File("tes"));
        } catch (FileNotFoundException e) {
            ex = e;
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                if (ex == null) {
                    System.out.println("有这个文件就不抛出了");
                    throw e;
                }
            } catch (NullPointerException e) { // 因为in会出现空指针，抑制第一个文件的抛出
                if (ex == null) {
                    System.out.println("有这个文件就不抛出了");
                }
            }
        }
    }
}
