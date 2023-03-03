package com.example.demo.model;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/*
*
* 通过setAttribute进行页面的显示，通过本对象装载所需要的信息,相当于一个socket
*
* */

public class ViewObject {
    private Map<String,Object> objects = new HashedMap();

    public void set(String key, Object value){
        objects.put(key,value);
    }

    public Object get(String key){
        return objects.get(key);
    }
}
