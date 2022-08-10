package threadBase.JUC.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 11:49
 * @Description:
 * 为了测试缓存，需要用到数据库
 * 为了使用数据库，使用mybatis
 * 为了测试mybatis，使用创建实体类
 * 为了实体类，创建了这个东西
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    int id;
    String name;
    String tel;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
