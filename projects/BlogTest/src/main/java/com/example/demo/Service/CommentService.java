package com.example.demo.Service;

import com.example.demo.Dao.CommentDao;
import com.example.demo.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SensitiveService sensitiveService;

    public void addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        commentDao.insertComment(comment);
    }

    public List<Comment>getCommentByArticleId(int articleId){
        return commentDao.selectCommentsByArticleId(articleId);
    }

    public int getCommentCount(int articleId){
        return commentDao.getCommentCountByArticleId(articleId);
    }

    public void deleteComment(int commentId){
         commentDao.deleteById(commentId);
    }

    public Comment getCommnetById(int commentId){
        return commentDao.seletById(commentId);
    }
}
