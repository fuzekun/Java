# Mybatis基操





# 1. 导入依赖

```xml
<!--导入数据库的和mybatis的jar包-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.9</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.27</version>
        </dependency>
```



# 2. jdbc.properties

```properties
# jdbc的连接信息
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost/yshop2?useUnicode=true&characterEncoding=utf-8
jdbc.username=root
jdbc.password=1230
```



# 3. SqlMapConfig

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--读取数据文件的位置-->
    <properties resource="jdbc.properties"></properties>
    <settings>
        <!-- 打印查询语句 -->
        <setting name="logImpl" value="STDOUT_LOGGING" />
    </settings>

    <typeAliases>
        <!-- 		<typeAlias type="com.gezhi.pojo.User" alias="User"/> -->
        <package name="com.gezhi.pojo.User"/>
    </typeAliases>
    <!--部署环境下的配置

        POOLED使用数据库的连接池。
    -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC" />
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}" />
                <property name="url" value="${jdbc.url}" />
                <property name="username" value="${jdbc.username}" />
                <property name="password" value="${jdbc.password}" />
            </dataSource>
        </environment>
    </environments>
    <!--注册mapper-->
    <mappers>
        <mapper resource="StudentMapper.xml" />
    </mappers>

</configuration>


```



# 4. Student.Mapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="testStudent">
    <!-- 配置数据库字段和实体属性之间的映射关系
        定义resultMap-->
<!--    <resultMap id="order_list_map" type="order">-->
<!--        &lt;!&ndash; id用于映射主键 &ndash;&gt;-->
<!--        <id property="id" column="id"/>-->
<!--        &lt;!&ndash; 普通属性映射 &ndash;&gt;-->
<!--        <result property="userId" column="user_id"/>-->
<!--        <result property="number" column="number"/>-->
<!--        <result property="createtime" column="createtime"/>-->
<!--        <result property="note" column="note"/>-->
<!--    </resultMap>-->
    <!--其中的id相当于方法的名称-->
   <!--可以配置这个地方作为mysql的方言，从而进行类型检查-->
    <select id = "getById" parameterType="int" resultType="threadBase.JUC.model.Student" >
            select *
            from student
            where id = #{id}
     </select>
</mapper>
```





# 5. 代码语句

```java
package threadBase.JUC;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import threadBase.JUC.model.Student;


import java.io.InputStream;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 11:22
 * @Description:
 */
public class TestGenericDao {
    public static void main(String[] args) throws Exception{
        // 1. 流读取
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2. 工厂创建
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // 3. 会话建立
        SqlSession session = factory.openSession();
        // 4. 执行代码
        Student student = session.selectOne("testStudent.getById", 1);
        System.out.println(student);
        // 5. 关闭流
        session.close();
    }
}

```







## 增加



- 需要显示提交任务，否则会rolling back

```java
package threadBase.JUC;
import com.mysql.cj.Session;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import threadBase.JUC.model.Student;
import threadBase.model.Test;


import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Zekun Fu
 * @date: 2022/6/24 11:22
 * @Description:
 */
public class TestGenericDao {
    private SqlSession buildSession() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession session = factory.openSession();
        return session;
    }


    public static void main(String[] args) throws Exception{
        TestGenericDao dao = new TestGenericDao();
        SqlSession session = dao.buildSession();
        Student student = session.selectOne("testStudent.getById", 1);
        System.out.println(student);
        Student newStudent = new Student(3, "zhaozhaozhao", "168");
        session.insert("testStudent.insert", newStudent);
        session.commit();           // 显示提交任务
        session.close();
    }
}

```



## 更新

