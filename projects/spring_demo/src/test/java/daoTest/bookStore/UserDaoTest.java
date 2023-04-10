package daoTest.bookStore;

import com.springboot.SpringBootDemoApplication;
import com.springboot.dto.bookStore.BookStoreUserMapper;
import com.springboot.entity.bookStore.User;
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
    private BookStoreUserMapper mapper;


    @Test
    public void findAllTest() {
        List<User> users = mapper.findAllUser();
        for (User user: users) {
            System.out.println(user);
        }
    }
}
