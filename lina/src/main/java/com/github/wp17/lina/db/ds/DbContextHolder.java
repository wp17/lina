package com.github.wp17.lina.db.ds;

public class DbContextHolder {

	private static final ThreadLocal<Integer> contextHolder = new ThreadLocal<Integer>();

	public static void setDbType(int dbType) {
		if (getDbType() == null) {
			contextHolder.set(-1);
		}
		if (dbType == -1 || dbType == getDbType()) {
			return;
		}
		contextHolder.set(dbType);
	}

	public static Integer getDbType() {
		return contextHolder.get();
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}