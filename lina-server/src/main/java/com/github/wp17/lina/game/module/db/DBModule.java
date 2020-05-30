package com.github.wp17.lina.game.module.db;

import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;
import com.github.wp17.lina.game.module.execute.ExecutorModule;
import com.github.wp17.lina.game.module.id.IDModule;
import com.github.wp17.lina.game.module.log.LogModule;
import com.github.wp17.lina.game.spring.SpringContext;
import org.mybatis.spring.SqlSessionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class DBModule implements AbsModule {
    private DBModule() {
    }

    private static final DBModule instance = new DBModule();

    public static DBModule getInstance() {
        return instance;
    }

//    private ApplicationContext context;
//
//    private SqlSessionFactory roleSF;

    protected SqlSessionTemplate roleTemplate;

    public <T> T getRoleMapper(Class<T> clazz) {
        return roleTemplate.getMapper(clazz);
    }


    @Override
    public void init() {
        roleTemplate = SpringContext.getInstance().getBean(RoleDbConfig.role_sst, SqlSessionTemplate.class);
    }

    @Override
    public void shutdown() {
    }

    @Override
    public int order() {
        return ModuleInitOrder.highest;
    }

    public static void main(String[] args) throws SQLException {
        LogModule.getInstance().init();
        SpringContext.getInstance().init();
        ExecutorModule.getInstance().init();
        DBModule.getInstance().init();
        IDModule.getInstance().init();

        Connection connection = DBModule.getInstance().roleTemplate
                .getSqlSessionFactory().openSession().getConnection();
        for (int i = 0; i < RoleDbConfig.roleDbCount; i++) {
            connection.prepareCall("drop database if exists lina_role_" + i).execute();

            connection.prepareCall("create database lina_role_" + i).execute();
            connection.prepareCall("use lina_role_" + i).execute();
            for (int j = 0; j < RoleDbConfig.roleInfoTableCount; j++) {
                connection.prepareCall("CREATE TABLE role_info_" + j + "(\n" +
                        "`id`  bigint(20) UNSIGNED NOT NULL ,\n" +
                        "`user_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,\n" +
                        "`nickname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,\n" +
                        "`icon`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,\n" +
                        "`sex`  tinyint(4) NULL DEFAULT NULL ,\n" +
                        "`level`  int(11) NULL DEFAULT 0 ,\n" +
                        "`create_time`  bigint(20) NULL DEFAULT NULL ,\n" +
                        "`update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,\n" +
                        "PRIMARY KEY (`id`)\n" +
                        ")\n" +
                        "ENGINE=InnoDB\n" +
                        "DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci\n" +
                        "ROW_FORMAT=COMPACT\n" +
                        ";").execute();
            }
        }
    }
}
