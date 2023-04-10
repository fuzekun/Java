package daoTest.test;

import com.springboot.SpringBootDemoApplication;
import com.springboot.dto.test.UserMapper;
import com.springboot.entity.test.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 16:54
 * @Description:
 */
@SpringBootTest(classes = {SpringBootDemoApplication.class})
@RunWith(SpringRunner.class)        // 和spring的主类一起运行
public class UserDaoTest {

    @Autowired
    private UserMapper mapper;


    @Test
    public void findAllTest() {
        List<User> users = mapper.findAllUsers();
        for (User user: users) {
            System.out.println(user);
        }
    }
}
