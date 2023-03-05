package com.example.demo.aync;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.Service.JedisService;
import com.example.demo.Utils.RedisKeyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    private JedisService jedisService;

    public void fireEvent(EventModel eventModel){
        String json = JSONObject.toJSONString(eventModel);
        String key = RedisKeyUntil.getEventQueue();
        jedisService.lpush(key,json);
    }
}
