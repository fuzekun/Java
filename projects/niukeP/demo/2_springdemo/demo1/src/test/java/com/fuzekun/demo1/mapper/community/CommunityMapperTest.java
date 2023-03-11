package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.Demo1Application;
import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.entity.community.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 19:52
 * @Description:
 *
 *
 * 很明显的一个问题，是不是SQL语句出现了错误？或者返回的类型不对？
 *
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class CommunityMapperTest {

    @Autowired
    DiscusPostMapper mapper;

    @Autowired
    UserMapper2 userMapper;
    @Test
    public void test() {

// 创建时间和userid有问题，一个是Null,一个是0,为什么转化会出现错误呢？

        // 因为@Data注解出现了问题

        List<DiscussPost> lst = mapper.selectDiscusPosts(101, 0, 10);
        for (DiscussPost x : lst) {
            System.out.println(x);
        }
        System.out.println(lst.size());
//        DiscussPost d = mapper.findById(1);
        int rows = mapper.selectDiscusPostRows(101);
        System.out.println(rows);

        DiscussPost one = mapper.selectById(112);
        System.out.println(one);
//        List<Map<String, Object>>discussPosts = new ArrayList<>();
//        if (lst != null) {
//            for (DiscussPost post: lst) {
//                Map<String, Object> mp = new HashMap<>();
//                mp.put("post", post);
//                User user = userMapper.findById(0);
//                mp.put("user", user);
//                System.out.println(user + " " + post.getUserId());
//                discussPosts.add(mp);
//            }
//        }

//        User user = userMapper.findById(1);
//        System.out.println(user);
    }

}
