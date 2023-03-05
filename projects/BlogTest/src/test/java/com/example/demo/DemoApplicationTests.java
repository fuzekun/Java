package com.example.demo;

import com.example.demo.Dao.ArticleDao;
import com.example.demo.Repositry.*;
import com.example.demo.Service.UserService;
import com.example.demo.model.Article;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    CommetRepository commetRepository;
//    @Autowired
//    TagRepository tagRepository;
//    @Autowired
//    ArticleTagRepository articleTagRepository;
//    @Autowired
//    ArticleRepository articleRepository;
//    @Autowired
//    ArchiveRepository archiveRepository;
//    @Autowired
//    LoginTicketRepository loginTicketRepository;
//    @Autowired
//    UserService userService;
//

    @Autowired
    ArticleDao articleDao;
//    @Test
//    public void getArticle(){
//        List<Article> articles= articleRepository.findAll();
//        try {
//            Writer writer = new FileWriter(new File("E://article.txt"));
//            BufferedWriter bufferedWriter = new BufferedWriter(writer);
//            for(Article article : articles){
//                bufferedWriter.write(article.getContent());
//                bufferedWriter.flush();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Test
    public void contextLoads() {
        articleDao.modtag();            //测试调用存储过程;
    }

}
