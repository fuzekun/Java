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

import javax.sql.DataSource;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 17:59
 * @Description: 第一个数据源的配置
 */
@Configuration
// 指定接口包，数据源
@MapperScan(basePackages = "com.springboot.dto.test", sqlSessionFactoryRef = "testSqlSessionFactory")
public class TestDataSourceConfig {
    // 指定扫描文件
    private static final String MAPPER_LOCATION = "classpath*:mapper/test/*.xml";


    @Bean(name = "testDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理器
     */
    @Bean(name = "testTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("testDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = "testSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("bookStoreDataSource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(TestDataSourceConfig.MAPPER_LOCATION);
        sessionFactoryBean.setMapperLocations(resources);
        return sessionFactoryBean.getObject();
    }
}