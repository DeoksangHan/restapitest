package com.hds.restapitest.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MybatisConfig {

    @Value("${spring.datasource.jdbc-url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String dbClassName;

    @Value("${spring.mybatis.config-location}")
    private String configLocation;
    @Value("${spring.mybatis.mapper-locations}")
    private String mapperLocations;

    
    
    @Bean
    public DataSource dataSource() {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(dbUsername);
        hikariConfig.setPassword(dbPassword);

      //hikariConfig.addDataSourceProperty("jdbc-url", dbUrl);
      //hikariConfig.setDataSourceClassName(dbClassName);
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setDriverClassName(dbClassName);
        
        hikariConfig.setLeakDetectionThreshold(2000);
        hikariConfig.setMaximumPoolSize(30);
        hikariConfig.setPoolName("peterPool");

        final HikariDataSource dataSources = new HikariDataSource(hikariConfig);
        return dataSources;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        //sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml"));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
        sessionFactory.setVfs(SpringBootVFS.class);

        return sessionFactory.getObject();
    }
}