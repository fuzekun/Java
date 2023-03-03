package com.example.demo.Service;

import com.example.demo.Dao.ArticleDao;
import com.example.demo.Dao.ArticleTagDao;
import com.example.demo.Eception.BaseException;
import com.example.demo.model.Article;
import com.example.demo.model.ArticleTag;
import com.example.demo.model.ViewObject;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService{

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    //如果成功发牛文章id失败返回0
    public int addArticle(Article article){
        return articleDao.insertArticle(article) > 0?article.getId():0;
    }

    //查找所有文章
    public List<Article> findAll(){return articleDao.findAll();}

    //通过文章id查找文章
    public Article getArticleById(int gId){
        return articleDao.selectById(gId);
    }

    public int getArticleCount(){
        return articleDao.getArticleCount();
    }

    //通过文章的类别获取文章数量
    public int getArticleCountByCategory(String category){
        return articleDao.getArticleCountByCategory(category);
    }

    public int getArticlCountByTag(int tagId){
        return articleDao.selectArticleCountByTagId(tagId);
    }

    public List<Article> getLatestArticles(int offset,int limite){
        return articleDao.selectLatestArticles(offset,limite);
    }

    public List<Article>getArticleByCategory(String category,int offset,int limit){
        return articleDao.selecttArticlesByCategory(category,offset,limit);
    }

    public List<Article>getArticleByTag(int tagId,int offset,int limit){
        return articleDao.selectByTagId(tagId,offset,limit);
    }

    //删除文章，返回信息
    public ViewObject delArticle(int articleId){
        ViewObject viewObject = new ViewObject();
        int msg = 0;
        try{
            articleDao.deleteById(articleId);
            //级联更新评论以及标签表中的内容
            //由于建立了触发器，这个功能作废
//            articleDao.modtag();
            msg = 1;
        }catch (Exception e){
            msg = 0;
        }
        finally {
            viewObject.set("msg",msg);
            return viewObject;
        }
    }

    //通过文章的类型寻找文章数量
    public int findCountByKind(String kind){
        int res = 0;
        try{
            res = articleDao.getArticleCountByCategory(kind);
        }catch (Exception e){
            return 0;
        }
        return res;
    }

    //通过文章类型寻找文章
    public List<Article> findByCategory(String category,int offset, int limit){
        return articleDao.selecttArticlesByCategory(category,0, 4);
    }

    public void updateArtcle(int articleId,Article newArticle){
        articleDao.updateArticle(articleId,newArticle.getTitle(),
                                newArticle.getDescribes(),
                                newArticle.getContent(),
                                newArticle.getCategory());
    }

    public void updateCommentCount(int articleId,int count){
        articleDao.updateCommentCount(articleId,count);
    }
}
