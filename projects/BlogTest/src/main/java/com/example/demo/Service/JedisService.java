package com.example.demo.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Component
public class JedisService implements InitializingBean {
    private JedisPool pool;//用来连接redis

    @Value("${myredis.host}")
    private String redis_host;
    @Value("${myredis.port}")
    private String redis_port;
    @Value("${myredis.password}")
    private String redis_password;

    private static final Logger log = LoggerFactory.getLogger(JedisService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        String host = "redis://" + redis_host + ":" + redis_port;
        log.debug("redis_host = " + host);
        pool = new JedisPool(host);
    }

    public Jedis getJedis(){
        Jedis jedis = pool.getResource();
        jedis.auth(redis_password);
        return jedis;
    }

    public Transaction multi(Jedis jedis){
        return jedis.multi();
    }

    public List<Object> exec(Transaction tx, Jedis jedis){
        try{
            return tx.exec();
        }finally {
            try {
                if (tx!=null)
                    tx.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            if (tx!=null)
                jedis.close();
        }
    }

    public void put(String key, String value){
        Jedis jedis = getJedis();
        jedis.set(key,value);
        jedis.close();
    }

    //给每个键增加一
    public void incr(String key){
        Jedis jedis = getJedis();
        jedis.incr(key);
        jedis.close();
    }
    //键值减少一
    public void decr(String key){
        Jedis jedis = getJedis();
        jedis.decr(key);
        jedis.close();
    }

    public String get(String key){
        Jedis jedis = getJedis();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public void sadd(String key,String value){
        Jedis jedis = getJedis();
        jedis.sadd(key,value);
        jedis.close();
    }

    public void srem(String key,String value){
        Jedis jedis = getJedis();
        jedis.srem(key,value);
        jedis.close();
    }

    public boolean sismember(String key,String value){
        Jedis jedis = getJedis();
        boolean result = jedis.sismember(key,value);
        jedis.close();

        return result;
    }

    public long scard(String key){
        Jedis jedis = getJedis();
        long result = jedis.scard(key);
        jedis.close();

        return result;
    }

    public void lpush(String key,String value){
        Jedis jedis = getJedis();
        jedis.lpush(key,value);
        jedis.close();
    }

    public List<String> brpop(int time, String key){
        Jedis jedis = getJedis();
        List<String> list = jedis.brpop(time,key);
        jedis.close();
        return list;
    }

    public long zadd(String key,double score,String value){
        Jedis jedis = getJedis();
        long result = jedis.zadd(key,score,value);
        jedis.close();

        return result;
    }

    public double zincrby(String key,String value){
        Jedis jedis = getJedis();
        double result = jedis.zincrby(key,1,value);
        jedis.close();

        return result;
    }

    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis = getJedis();
        Set<String> set = jedis.zrevrange(key,start,end);
        jedis.close();
        return set;
    }

    public long zcard(String key){
        Jedis jedis = getJedis();
        long result = jedis.zcard(key);
        jedis.close();

        return result;
    }

    public Double zscore(String key,String member){
        Jedis jedis = getJedis();
        Double result = jedis.zscore(key,member);
        jedis.close();

        return result;
    }

    public long delete(String key){
        Jedis jedis = getJedis();
        long res = jedis.del(key);
        jedis.close();
        return res;
    }

    //用来删除
    public long zrem(String key,String number){
        Jedis jedis = getJedis();
        long result = jedis.zrem(key,number);
        jedis.close();
        return result;
    }

}
