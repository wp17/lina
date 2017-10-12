package com.github.wp17.lina.db.ds;

import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	private int serverCount = 0;

	private int tableCount = 0;

	public int getServerCount() {
		return serverCount;
	}

	public void setServerCount(int serverCount) {
		this.serverCount = serverCount;
	}

	public int getTableCount() {
		return tableCount;
	}

	public void setTableCount(int tableCount) {
		this.tableCount = tableCount;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return DbContextHolder.getDbType();
	}

	@Override
	public Logger getParentLogger() {
		return null;
	}
}