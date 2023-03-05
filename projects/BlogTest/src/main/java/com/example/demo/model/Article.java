package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by tuzhenyu on 17-8-13.
 * @author tuzhenyu
 */
@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String describes;
    private String content;
    private Date createdDate;
    private int commentCount;
    private String category;
    private int authorid;

    public Article() {
    }

    public Article(String title, String describes, String content, Date createdDate, int commentCount, String category,
                   int authorid) {
        this.title = title;
        this.describes = describes;
        this.content = content;
        this.createdDate = createdDate;
        this.commentCount = commentCount;
        this.category = category;
        this.authorid = authorid;

    }
}
