package com.github.wp17.lina.game.module.db.ds;

public class DbContextHolder {
	private static ThreadLocal<Integer> contextHolder = new ThreadLocal<Integer>();  
	  
    public static Integer getDbType() {  
        return contextHolder.get();  
    }  
  
    public static void clearDbType() {  
        contextHolder.remove();  
    } 
    
    public static void setDbType(Integer dbType) {
    	contextHolder.set(dbType);
    }
}
