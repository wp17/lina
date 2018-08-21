package cn.org.wgpg.lina.db.source;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType(); 
	}
}
