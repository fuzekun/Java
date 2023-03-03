package com.example.demo.Dao;

import com.example.demo.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tuzhenyu on 17-8-13.
 * @author tuzhenyu
 */
@Mapper
@Component
public interface ArticleDao {
    String TABLE_NAEM = " article ";
    String INSERT_FIELDS = " title, describes, content, created_Date, comment_Count, category, authorid";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    String ARTICLE_FIELDS = " id, title, describes, content, created_Date, comment_Count , category ";

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM})
    List<Article>findAll();

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{title},#{describes},#{content}" +
            ",#{createdDate},#{commentCount},#{category}, #{authorid})"})
    int insertArticle(Article article);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where id=#{id}"})
    Article selectById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"order by id desc limit #{offset},#{limit}"})
    List<Article> selectLatestArticles(@Param("offset") int offset, @Param("limit") int limit);

    //建立了索引所以会很快
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where category=#{category} order by id desc limit #{offset},#{limit}"})
    List<Article> selecttArticlesByCategory(@Param("category") String category, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from",TABLE_NAEM,"where category=#{category}"})
    int getArticleCountByCategory(@Param("category") String category);

    @Select({"select count(id) from",TABLE_NAEM})
    int getArticleCount();

    @Update({"update",TABLE_NAEM,"set comment_count = #{commentCount} where id = #{questionId}"})
    void updateCommentCount(@Param("questionId") int questionId, @Param("commentCount") int commentCount);

    @Select({"select count(id) from article where id in (select article_id from article_tag where tag_id=#{tagId})"})
    int selectArticleCountByTagId(@Param("tagId") int tagId);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);

    @Select({"select",ARTICLE_FIELDS,"from article where id in (select article_id from article_tag where tag_id=#{tagId}) limit #{offset},#{limit}"})
    List<Article> selectByTagId(@Param("tagId") int tagId,@Param("offset") int offset, @Param("limit") int limit);


    @Update({"update",TABLE_NAEM,"set title = #{title}, describes = #{describes}, content = #{content}, category = #{category}", "where id = #{articleId}"})
    void updateArticle(int articleId,String title, String describes, String content, String category);


    //在删除文章之后进行级联更新
    @Select({"CALL mdftag"})
    void modtag();


}
