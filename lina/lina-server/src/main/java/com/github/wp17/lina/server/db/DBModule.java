package com.github.wp17.lina.server.db;
//package com.github.wp17.lina.db;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import com.github.wp17.lina.common.module.IModule;
//import com.github.wp17.lina.common.module.ModuleInitOrder;
//
//public class DBModule implements IModule {
//	private DBModule(){}
//	private static final DBModule instance = new DBModule();
//	public static DBModule getInstance() {
//		return instance;
//	}
//	
//	private ApplicationContext context;
//	
//	private SqlSessionFactory roleSF;
//	
//	@Override
//	public void init() {
//		roleSF = getBean(SqlSessionFactory.class);
//	}
//	
//	public <T> T getBean(Class<T> clazz){
//		if (null == context) {
//			synchronized (this) {
////				if (null == context) {
////					context = new FileSystemXmlApplicationContext("msconfig/applicationContext.xml");
////				}
//				
//				if (null == context) {
//					context = new AnnotationConfigApplicationContext(RoleDbConfig.class, UserDbConfig.class);
//				}
//			}
//		}
//		return context.getBean(clazz);
//	}
//
//	@Override
//	public void destory() {
//
//	}
//	
//	@Override
//	public int order() {
//		return ModuleInitOrder.dbModule;
//	}
//	
//	public SqlSessionFactory getRoleSF() {
//		return roleSF;
//	}
//
//	public void setRoleSF(SqlSessionFactory roleSF) {
//		this.roleSF = roleSF;
//	}
//}
