package com.fuzekun.demo1.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 15:43
 * @Description:
 */

@Configuration
@MapperScan(basePackages = {"com.fuzekun.demo1.mapper.second"}, sqlSessionFactoryRef = "secondSqlFactory")
public class SencodDataSourceConf {

    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondSqlFactory")
    public SqlSessionFactory secondFactory(@Qualifier("secondDataSource") DataSource source) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(source);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/second/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "secondTarcactionManager")
    public DataSourceTransactionManager senconManager(@Qualifier("secondDataSource") DataSource source) {
        return new DataSourceTransactionManager(source);
    }

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("secondSqlFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
