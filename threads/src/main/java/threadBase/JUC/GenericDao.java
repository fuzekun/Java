package threadBase.JUC;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import threadBase.JUC.model.Student;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 14:50
 * @Description:
 */
public class GenericDao {
    private SqlSession buildSession() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = factory.openSession();
        return session;
    }

    public void insert(Student student) throws IOException{
        SqlSession session = buildSession();
         session.insert("testStudent.insert", student);
        session.commit();           // 显示提交任务
        session.close();
    }

    public void update(Student student) throws IOException{
        SqlSession session = buildSession();
        // 更新
        int num = session.update("testStudent.update");
        System.out.println(num);
        session.commit();
        session.close();
    }

    public Student getById(int id) throws IOException{
        SqlSession session = buildSession();
        Student student = session.selectOne("testStudent.getById", 1);
        session.close();
        return student;
    }
}
