package com.example.demo.dao;

import com.example.demo.Service.JedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

/**
 * @author: Zekun Fu
 * @date: 2023/3/5 19:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private JedisService jedisService;

    @Test
    public void testConnect() {
        Jedis jedis = jedisService.getJedis();
    }
}
