package threadBase.JUC;
import com.mysql.cj.Session;
import com.sun.javafx.image.impl.ByteIndexed;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import threadBase.JUC.model.Student;
import threadBase.model.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 11:22
 * @Description:
 */
public class TestGenericDao {



    public static void main(String[] args) throws Exception{


        GenericDao g = new GeneritDaoCached();

        // 查询三次
        Student stu = g.getById(1);
        System.out.println(stu);
        stu = g.getById(1);
        System.out.println(stu);
        stu = g.getById(1);
        System.out.println(stu);
        // 更新一次
        Student updateS = new Student(1, "fzk", "177");
        g.update(updateS);
    }


}
@Slf4j(topic = "c.GeCached")
class GeneritDaoCached extends GenericDao {
    private GenericDao dao = new GenericDao();
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private Map<Integer, Object>sqlMap = new Hashtable<>(); // 使用id作为缓存

    @Override
    public Student getById(int id) throws IOException {
        rw.readLock().lock();
        try {
            // 先从缓存中找，找到了直接返回
            if (sqlMap.containsKey(id)) {
                log.debug("缓存中存在，不用查询数据库了");
                return (Student) sqlMap.get(id);
            }
        }finally {
            rw.readLock().unlock();
        }

        rw.writeLock().lock();
        try {
            // 多个线程，使用双重检查, 节约了资源
            if (sqlMap.containsKey(id))
                return (Student) sqlMap.get(id);

            Student stu = super.getById(id);
            sqlMap.put(id, stu);
            // 如果没有，查询数据库
            return stu;
        }
        finally {
            rw.writeLock().unlock();
        }
    }

    @Override
    public void update(Student student) throws IOException {
        rw.writeLock().lock();
        try {
            // 先更新后移除，加上锁就无所为了
            super.update(student);
            sqlMap.remove(student.getId());
        } finally {
            rw.writeLock().unlock();
        }
    }
}
