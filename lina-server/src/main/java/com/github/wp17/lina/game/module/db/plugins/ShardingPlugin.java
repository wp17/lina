package com.github.wp17.lina.game.module.db.plugins;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.game.module.db.BaseDomain;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
        args = {Connection.class, Integer.class})})
public class ShardingPlugin implements Interceptor {
    private final Logger log = LoggerProvider.getLogger(ShardingPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        Method method = invocation.getMethod();

        MappedStatement mappedStatement = null;
        if (statementHandler instanceof RoutingStatementHandler) {
            StatementHandler delegate =
                (StatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
                mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
        } else {
            mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(
                            statementHandler, "mappedStatement");
        }

//        String mapperId = mappedStatement.getId();

        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = statementHandler.getBoundSql().getSql();
        if (log.isDebugEnabled()) {
            log.debug("Original Sql : " + sql);
        }
        Object params = statementHandler.getBoundSql().getParameterObject();
        int tableId = 0;
        if (params instanceof BaseDomain) {
            tableId = ((BaseDomain) params).getTableId();
        }else if (params instanceof MapperMethod.ParamMap) {
            if (((MapperMethod.ParamMap) params).containsKey("tableId")) {
                tableId = (Integer) ((MapperMethod.ParamMap) params).get("tableId");
            }
        }

        sql = convert(sql, tableId);
        if (log.isDebugEnabled()) {
            log.debug("Converted Sql : " + sql);
        }
        ReflectHelper.setValueByFieldName(statementHandler
                .getBoundSql(), "sql", sql);

        return invocation.proceed();
    }

    private String convert(String sql, int tableId) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);
        for (String tableName : tableList) {
            sql = sql.replaceAll(tableName, tableName + "_" + tableId);
        }
        return sql;
    }
}
