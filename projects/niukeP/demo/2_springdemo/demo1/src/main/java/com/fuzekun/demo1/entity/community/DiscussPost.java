package com.fuzekun.demo1.entity.community;


import lombok.Data;

import java.util.Date;

public class DiscussPost {

  private long id;
  private long userId;
  private String title;
  private String content;
  private long type;
  private long status;
  private Date createTime;
  private long commentCount;
  private double score;

  public DiscussPost(long id, long userId, String title, String content, long type, long status, Date createTime, long commentCount, double score) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.content = content;
    this.type = type;
    this.status = status;
    this.createTime = createTime;
    this.commentCount = commentCount;
    this.score = score;
  }

  public long getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public long getType() {
    return type;
  }

  public long getStatus() {
    return status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public long getCommentCount() {
    return commentCount;
  }

  public double getScore() {
    return score;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setType(long type) {
    this.type = type;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public void setCommentCount(long commentCount) {
    this.commentCount = commentCount;
  }

  public void setScore(double score) {
    this.score = score;
  }

  @Override
  public String toString() {
    return "DiscussPost{" +
            "id=" + id +
            ", userId=" + userId +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", type=" + type +
            ", status=" + status +
            ", createTime=" + createTime +
            ", commentCount=" + commentCount +
            ", score=" + score +
            '}';
  }
}
