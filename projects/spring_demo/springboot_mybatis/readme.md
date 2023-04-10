

## 整合mybatis

[mybatis的多数据源配置](https://juejin.cn/post/7025511138364227621)

> 1. 编写yrm文件，配置数据源信息，注意使用的是jdbcurl，不是url
> 2. 编写config类注入数据源、SqlSessionFactory、以及配置接口文件和映射文件的位置信息。
> 3. 创建实体、接口类、mapper文件。**注意接口类的名称不能一样**，


```java
spring:
  datasource:
    bookstore:
      jdbcurl: jdbc:mysql://localhost:3306/bookstore2?serverTimezone=UTC    # 这个是jdbcurl不是url。
      username: root
      password: 1230
      driver-class-name: com.mysql.cj.jdbc.Driver
    test:
      jdbcurl: jdbc:mysql://localhost:3306/test?serverTimezone=UTC
      username: root
      password: 1230
      driver-class-name: com.mysql.cj.jdbc.Driver

```

- @MapperScan用来指定[接口类]、[SqlSession]信息
- 然后创建数据源
- 创建SqlSession工厂，配置映射文件位置。**注意这个getResources方法的使用。不是getResource。**
- 创建事务管理工厂

```java
package com.springboot.config;

import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resources;
import javax.sql.DataSource;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 17:59
 * @Description: 第一个数据源的配置
 */
@Configuration
// 指定接口包，数据源
@MapperScan(basePackages = "com.springboot.dto.bookstore", sqlSessionFactoryRef = "bookStoreSqlSessionFactory")
public class BookStoreDataSourceConfig {
    // 指定扫描文件
    private static final String MAPPER_LOCATION = "classpath*:mapper/bookStore/*.xml";


    @Primary
    @Bean(name = "bookStoreDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.bookstore")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 事务管理器
     */
    @Bean(name = "bookStoreTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("bookStoreDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "bookStoreSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("bookStoreDataSource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(BookStoreDataSourceConfig.MAPPER_LOCATION);
        sessionFactoryBean.setMapperLocations(resources);
        return sessionFactoryBean.getObject();
    }
}

```