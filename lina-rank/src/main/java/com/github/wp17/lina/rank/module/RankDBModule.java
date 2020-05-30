package com.github.wp17.lina.rank.module;

import com.github.wp17.lina.rank.db.RankDBConfig;
import com.github.wp17.lina.rank.spring.RankSpringContext;
import org.mybatis.spring.SqlSessionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class RankDBModule {
    private RankDBModule () {}
    private static final RankDBModule instance = new RankDBModule();
    public static final RankDBModule getInstance() {
        return instance;
    }

    private SqlSessionTemplate rankTemplate;

    public void init() {
        rankTemplate = RankSpringContext.getInstance().getBean(RankDBConfig.rank_sst, SqlSessionTemplate.class);
    }

    public <T> T getMapper(Class<T> clazz) {
        return rankTemplate.getMapper(clazz);
    }

    private static void dbCreate() throws SQLException {
        RankSpringContext.getInstance().init();
        RankDBModule.getInstance().init();
        Connection connection = RankDBModule.getInstance().rankTemplate
                .getSqlSessionFactory().openSession().getConnection();

        for (int i = 0; i < 4; i++) {
            connection.prepareCall("create database test_"+i).execute();
            connection.prepareCall("use test_" +i).execute();

            for (int j = 0; j < 16; j++) {
                connection.prepareCall("CREATE TABLE test_"+ j +"\n(" +
                        "`role_id`  bigint(255) NOT NULL ,\n" +
                        "`score`  int(255) NOT NULL ,\n" +
                        "`time`  bigint(255) NOT NULL ,\n" +
                        "PRIMARY KEY (`role_id`)\n" +
                        ")\n" +
                        "ENGINE=InnoDB\n" +
                        "DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci\n" +
                        "ROW_FORMAT=COMPACT\n" +
                        ";").execute();
            }
        }
    }
    public static void main(String[] args){

    }

}
