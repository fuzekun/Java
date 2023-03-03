package com.example.demo.aync;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import java.util.HashMap;
import java.util.Map;


public class EventModel {
    private EventType type;
    private int actorId;
    private int articleId;
    private Map<String,String> exts;

    public EventModel(){
        this.exts = new HashMap<>();
    }
    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }

    public EventModel setExts(String key,String value){
        exts.put(key,value);
        return this;
    }

    public String getExts(String key){
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getArticleId() {
        return articleId;
    }

    public EventModel setArticleId(int articleId) {
        this.articleId = articleId;
        return this;
    }
}
