package com.github.wp17.lina.game.module.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.github.wp17.lina.common.util.Property;
import com.github.wp17.lina.game.module.db.ds.DynamicRoutingDataSource;
import com.github.wp17.lina.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:db/role_db.properties")

@MapperScan(basePackages = "com.github.wp17.lina.game.module.db.mapper.role",
        sqlSessionTemplateRef = RoleDbConfig.role_sst)// sqlSessionFactoryRef = RoleDbConfig.role_ssf,
//@ComponentScan("com.github.wp17.lina.game.module.db")
public class RoleDbConfig {

    public static final String role_sst = "role_sst";
    //	public static final String role_ssf = "role_ssf";
    public static final String role_ds_dynamic = "dynamic_role";

    @Value("${mybatis.role.mapperLocations}")
    private String mapperLocations;

    @Value("${mybatis.role.configLocation}")
    private String configLocation;

    public static int roleDbCount;

    @Value("${role.db.count}")
    public void setRoleDbCount(int roleDbCount) {
        RoleDbConfig.roleDbCount = roleDbCount;
    }

    public static int roleInfoTableCount;

    @Value("${role.info.count}")
    public void setRoleInfoTableCount(int roleInfoTableCount) {
        RoleDbConfig.roleInfoTableCount = roleInfoTableCount;
    }


    @Bean(name = "role_ds_0")
    public DataSource datasource0() throws IOException {
        return new HikariDataSource(config("spring.datasource.role0"));
    }

    @Bean(name = "role_ds_1")
    public DataSource datasource1() throws IOException {
        return new HikariDataSource(config("spring.datasource.role1"));
    }

    @Bean(name = "role_ds_2")
    public DataSource datasource2() throws IOException {
        return new HikariDataSource(config("spring.datasource.role2"));
    }

    @Bean(name = "role_ds_3")
    public DataSource datasource3() throws IOException {
        return new HikariDataSource(config("spring.datasource.role3"));
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Property roleDBProperty() throws IOException {
        Property property = new Property("db/role_db.properties");
        property.init();
        return property;
    }

    private HikariConfig config(String prefix) throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        Properties properties = PropertyUtil.copy(roleDBProperty().getProperties(), prefix);
        hikariConfig.setJdbcUrl(properties.getProperty("jdbc-url"));
        hikariConfig.setUsername(properties.getProperty("username"));
        hikariConfig.setPassword(properties.getProperty("password"));
        hikariConfig.setDriverClassName(properties.getProperty("driver-class-name"));
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(60000);
        hikariConfig.setValidationTimeout(3000);
        hikariConfig.setMaxLifetime(60000);
        return hikariConfig;
    }

    @Bean(role_ds_dynamic)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DynamicRoutingDataSource dataSourceDynamic() throws IOException {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(0, datasource0());
        dataSources.put(1, datasource1());
        dataSources.put(2, datasource2());
        dataSources.put(3, datasource3());

        dataSource.setTargetDataSources(dataSources);
        dataSource.setDefaultTargetDataSource(datasource0());

        return dataSource;
    }

    //    @Bean(role_ssf)
    public SqlSessionFactory createRoleSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceDynamic());
//        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperResources = resolver.getResources(mapperLocations);

        Resource configLocationResource = resolver.getResource(configLocation);
        sqlSessionFactoryBean.setConfigLocation(configLocationResource);

        sqlSessionFactoryBean.setMapperLocations(mapperResources);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(role_sst)
    public SqlSessionTemplate createRoleSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(createRoleSessionFactory());
    }

    @Bean("dataSourceTransactionManager_role")
    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier(role_ds_dynamic) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static int getDsId(long id) {
        return (int) (id % RoleDbConfig.roleDbCount);
    }

    public static int getTableId(long id) {
        return (int) (id / RoleDbConfig.roleDbCount % RoleDbConfig.roleInfoTableCount);
    }
}
