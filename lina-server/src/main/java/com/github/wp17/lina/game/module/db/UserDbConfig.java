//package com.github.wp17.lina.server.db;
//
//import javax.sql.DataSource;
//
//import com.github.wp17.lina.server.db.ds.DynamicRoutingDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
////import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
////import org.springframework.boot.context.properties.ConfigurationProperties;
////import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.Scope;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@PropertySource("classpath:db/user_db.properties")
//@MapperScan(basePackages = "com.github.wp17.lina.server.db.mapper.user",
//sqlSessionFactoryRef = UserDbConfig.user_ssf, sqlSessionTemplateRef = UserDbConfig.user_sst)
//public class UserDbConfig {
//
//	public static final String user_sst = "user_sst";
//	public static final String user_ssf = "user_ssf";
//	public static final String user_ds_dynamic = "dynamic_user";
//
//	@Value("${mybatis.mapperLocations}")
//	private String mapperLocations;
//
//	@Value("${mybatis.configLocation}")
//	private String configLocation;
//
//	@Value("${mybatis.user.db-count}")
//	public int user_db_count;
//
////	@Bean(name = "user_ds_0")
////    @ConfigurationProperties(prefix = "spring.datasource.user-0")
////    public DataSource datasource0() {
////        return DataSourceBuilder.create().build();
////    }
////
////    @Bean(name = "user_ds_1")
////    @ConfigurationProperties(prefix = "spring.datasource.user-1")
////    public DataSource datasource1() {
////        return  DataSourceBuilder.create().build();
////    }
//
//    @Bean(user_ds_dynamic)
//    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public DynamicRoutingDataSource dataSourceDynamic() {
//        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
////
////        Map<Object, Object> dataSources = new HashMap<Object,Object>();
////        dataSources.put(0, datasource0());
////        dataSources.put(1, datasource1());
////
////        dataSource.setTargetDataSources(dataSources);
////        dataSource.setDefaultTargetDataSource(datasource0());
//
//        return dataSource;
//    }
//
//    @Bean(user_ssf)
//    public SqlSessionFactory createUserSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSourceDynamic());
////        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] mapperResources = resolver.getResources(mapperLocations);
//
//        Resource configLocationResource = resolver.getResource(configLocation);
//        sqlSessionFactoryBean.setConfigLocation(configLocationResource);
//
//        sqlSessionFactoryBean.setMapperLocations(mapperResources);
//
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(user_sst)
//    public SqlSessionTemplate createRoleSqlSessionTemplate() throws Exception {
//    	return new SqlSessionTemplate(createUserSessionFactory());
//    }
//
//    @Bean("dataSourceTransactionManager_user")
//    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier(user_ds_dynamic) DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
