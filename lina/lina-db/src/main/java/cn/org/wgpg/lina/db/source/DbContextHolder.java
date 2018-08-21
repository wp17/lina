package cn.org.wgpg.lina.db.source;

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
