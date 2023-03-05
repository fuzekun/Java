package com.example.demo.Service;

import com.example.demo.Utils.RedisKeyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private JedisService jedisService;

    public int getLikeStatus(int userId,int articleId){
        String likeKey = RedisKeyUntil.getLikeKey(articleId);
        if(jedisService.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUntil.getDisLikeKey(articleId);
        return jedisService.sismember(disLikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId,int articleId){
        String likeKey = RedisKeyUntil.getLikeKey(articleId);
        //判断是否已经点赞或者点差评了 (点赞没问题，不重复因为set中不允许有重复的元素，点差评不行)
        int status = getLikeStatus(userId, articleId);
        if(status == 1 || status == -1) return jedisService.scard(likeKey);
        jedisService.sadd(likeKey,String.valueOf(userId));
        return jedisService.scard(likeKey);
    }

    public long dislike(int userId,int articleId){
        String dislikeKey = RedisKeyUntil.getDisLikeKey(articleId);
        int status = getLikeStatus(userId, articleId);
        if(status == 1 || status == -1)return  jedisService.scard(dislikeKey);//判断是否已经点过
        jedisService.sadd(dislikeKey,String.valueOf(userId));
        return jedisService.scard(dislikeKey);
    }

    public long getLikeCount(int articleId){
        String likeKey = RedisKeyUntil.getLikeKey(articleId);
        return jedisService.scard(likeKey);
    }
    public long getDisLikeCount(int articleId){
        String dislikeKey = RedisKeyUntil.getDisLikeKey(articleId);
        return jedisService.scard(dislikeKey);
    }
}
