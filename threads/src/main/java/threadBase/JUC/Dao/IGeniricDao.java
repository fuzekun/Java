package threadBase.JUC.Dao;

import org.apache.ibatis.annotations.Select;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 12:26
 * @Description:
 * 通用类，使用注解开发
 */
public interface IGeniricDao<T, K> {

    @Select("")
    public void select();
}
