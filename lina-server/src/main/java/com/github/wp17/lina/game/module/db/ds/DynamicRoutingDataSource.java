package com.github.wp17.lina.game.module.db.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType(); 
	}
}
