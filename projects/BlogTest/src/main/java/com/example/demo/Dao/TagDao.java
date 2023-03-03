package com.example.demo.Dao;

import com.example.demo.model.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tuzhenyu on 17-8-14.
 * @author tuzhenyu
 */
@Mapper
@Component
public interface TagDao {
    String TABLE_NAEM = " tag ";
    String INSERT_FIELDS = " name, count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    String TAG_FIELDS = " id, name, count ";
    String ARTICLE_FIELDS = " id, title, describes, content, created_Date, comment_Count , category ";


    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{name},#{count})"})
    int insertTag(Tag tag);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where name=#{name}"})
    Tag selectByName(String name);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM})
    List<Tag> selectAll();

    @Select({"select count(id) from",TABLE_NAEM})
    int getTagCount();

    @Update({"update",TABLE_NAEM,"set count = #{count} where id = #{tagId}"})
    void updateCount(@Param("tagId") int tagId, @Param("count") int count);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);


    @Select({"select",TAG_FIELDS,"from tag where id in (select tag_id from article_tag where article_id=#{articleId})"})
    List<Tag> selectByArticleId(int articleId);
}
