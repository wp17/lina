package com.github.wp17.lina.server.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import cn.org.wgpg.lina.db.source.DynamicRoutingDataSource;

@Configuration
@PropertySource("classpath:config/db/role_db.properties")
@MapperScan(basePackages = "cn.org.wgpg.lina.db.mapper.role", 
sqlSessionFactoryRef = RoleDbConfig.role_ssf, sqlSessionTemplateRef = RoleDbConfig.role_sst)
public class RoleDbConfig {
	
	public static final String role_sst = "role_sst";
	public static final String role_ssf = "role_ssf";
	public static final String role_ds_dynamic = "dynamic_role";
	
	@Value("${mybatis.role.mapperLocations}")
	private String mapperLocations;
	
	@Value("${mybatis.role.configLocation}")
	private String configLocation;
	
	@Value("${mybatis.role.db-count}")
	public int role_db_count;

	@Bean(name = "role_ds_0")  
    @ConfigurationProperties(prefix = "spring.datasource.role-0")
    public DataSource datasource0() {
        return DataSourceBuilder.create().build();  
    }  
  
    @Bean(name = "role_ds_1")
    @ConfigurationProperties(prefix = "spring.datasource.role-1")
    public DataSource datasource1() {  
        return  DataSourceBuilder.create().build();  
    }
    
    @Bean(name = "role_ds_2")  
    @ConfigurationProperties(prefix = "spring.datasource.role-2")
    public DataSource datasource2() {
        return DataSourceBuilder.create().build();  
    }  
  
    @Bean(name = "role_ds_3")
    @ConfigurationProperties(prefix = "spring.datasource.role-3")
    public DataSource datasource3() {  
        return  DataSourceBuilder.create().build();  
    }
    
    @Bean(role_ds_dynamic)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DynamicRoutingDataSource dataSourceDynamic() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();  
  
        Map<Object, Object> dataSources = new HashMap<Object,Object>();  
        dataSources.put(0, datasource0());  
        dataSources.put(1, datasource1());
        dataSources.put(2, datasource2());  
        dataSources.put(3, datasource3());
  
        dataSource.setTargetDataSources(dataSources);  
        dataSource.setDefaultTargetDataSource(datasource0());  
  
        return dataSource;
    } 
    
    @Bean(role_ssf)
    public SqlSessionFactory createRoleSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceDynamic());
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        
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
}
