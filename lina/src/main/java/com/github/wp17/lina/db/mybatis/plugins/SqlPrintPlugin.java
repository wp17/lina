package com.github.wp17.lina.db.mybatis.plugins;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;

import com.github.wp17.lina.log.LoggerProvider;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SqlPrintPlugin implements Interceptor {
	private static final Logger logger = LoggerProvider.getLogger(SqlPrintPlugin.class);
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
	    BoundSql boundSql = statementHandler.getBoundSql();
	    if (boundSql != null) {
		    String sql = boundSql.getSql();
		    logger.info(sql);
	    }
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		 return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties p) {
	}
}
