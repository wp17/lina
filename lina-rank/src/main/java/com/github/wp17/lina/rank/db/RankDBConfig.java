package com.github.wp17.lina.rank.db;

import com.github.wp17.lina.common.util.Property;
import com.github.wp17.lina.rank.spring.RankSpringContext;
import com.github.wp17.lina.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db/rank_db.properties")
@MapperScan(basePackages = "com.github.wp17.lina.rank.db.mapper", sqlSessionTemplateRef = RankDBConfig.rank_sst)
public class RankDBConfig {
    public static final String rank_sst = "rank_sst";

    @Value("${mybatis.rank.mapperLocations}")
    private String mapperLocations;

    @Value("${mybatis.rank.configLocation}")
    private String configLocation;

    @Bean(name = "rank_ds")
    public DataSource rankDatasource() throws IOException {
        return new HikariDataSource(config("spring.datasource.rank"));
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Property rankDBProperty() throws IOException {
        Property property = new Property("db/rank_db.properties");
        property.init();
        return property;
    }

    private HikariConfig config(String prefix) throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        Properties properties = PropertyUtil.copy(rankDBProperty().getProperties(), prefix);
        hikariConfig.setJdbcUrl(properties.getProperty("jdbc-url"));
        hikariConfig.setUsername(properties.getProperty("username"));
        hikariConfig.setPassword(properties.getProperty("password"));
        hikariConfig.setDriverClassName(properties.getProperty("driver-class-name"));
        hikariConfig.setIdleTimeout(60000);
        hikariConfig.setConnectionTimeout(60000);
        hikariConfig.setValidationTimeout(5000);
        hikariConfig.setMinimumIdle(5);
//        hikariConfig.setMaxLifetime(60000);
        return hikariConfig;
    }

    public SqlSessionFactory createRankSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(rankDatasource());

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperResources = resolver.getResources(mapperLocations);

        Resource configLocationResource = resolver.getResource(configLocation);
        sqlSessionFactoryBean.setConfigLocation(configLocationResource);

        sqlSessionFactoryBean.setMapperLocations(mapperResources);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(rank_sst)
    public SqlSessionTemplate createRankSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(createRankSessionFactory());
    }
}
