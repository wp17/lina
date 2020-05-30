package com.github.wp17.lina.game.module.db.provider;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.game.GameApplication;
import com.github.wp17.lina.game.module.cache.CacheKey;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.game.module.db.ds.DbContextHolder;
import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;
import com.github.wp17.lina.game.module.db.mapper.role.RoleInfoMapper;
import com.github.wp17.lina.game.module.execute.ExecutorModule;
import com.github.wp17.lina.game.module.db.DBModule;
import com.github.wp17.lina.game.module.db.RoleDbConfig;
import com.github.wp17.lina.game.module.id.IDModule;
import com.github.wp17.lina.game.module.log.LogModule;
import com.github.wp17.lina.game.module.msg.GameMessageModule;
import com.github.wp17.lina.game.spring.SpringContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleInfoDataProvider {
    private RoleInfoDataProvider() {
    }

    private static final RoleInfoDataProvider instance = new RoleInfoDataProvider();

    public static RoleInfoDataProvider getInstance() {
        return instance;
    }

//    private SqlSessionFactory factory = DBModule.getInstance().getRoleSF();

    private RoleInfoMapper getMapper() {
        return DBModule.getInstance().getRoleMapper(RoleInfoMapper.class);
//        return factory.openSession().getMapper(RoleInfoMapper.class); 会造成连接池耗尽
    }

    public RoleInfo selectRoleInfoByUserId(String userId) {
        for (int i = 0; i < RoleDbConfig.roleDbCount; i++) {
            DbContextHolder.setDbType(i);
            for (int j = 0; j < RoleDbConfig.roleInfoTableCount; j++) {
                RoleInfo roleInfo = getMapper().selectByUserId(userId, j);
                if (Objects.nonNull(roleInfo)) return roleInfo;
            }
        }
        return null;
    }

    public void addRoleInfoAsync(final RoleInfo roleInfo) {
        ExecutorModule.getInstance().addDbTask(() -> {
            addRoleInfo(roleInfo);
        });
    }

    public void addRoleInfo(final RoleInfo roleInfo) {
        int dsId = RoleDbConfig.getDsId(roleInfo.getId());
        DbContextHolder.setDbType(dsId);

        int tableId = RoleDbConfig.getTableId(roleInfo.getId());
        roleInfo.setTableId(tableId);
        getMapper().insert(roleInfo);
    }

    public RoleInfo selectById(Long id) {
        int dsId = RoleDbConfig.getDsId(id);
        DbContextHolder.setDbType(dsId);

        int tableId = RoleDbConfig.getTableId(id);
        return getMapper().selectByPrimaryKey(id, tableId);
    }

    public List<RoleInfo> selectAll() {
        List<RoleInfo> roleInfos = new ArrayList<>();
        for (int i = 0; i < RoleDbConfig.roleDbCount; i++) {
            DbContextHolder.setDbType(i);
            for (int j = 0; j < RoleDbConfig.roleInfoTableCount; j++) {
                roleInfos.addAll(getMapper().selectAll(j));
            }
        }
        return roleInfos;
    }

    public void update(RoleInfo roleInfo) {
        int dsId = RoleDbConfig.getDsId(roleInfo.getId());
        DbContextHolder.setDbType(dsId);

        int tableId = RoleDbConfig.getTableId(roleInfo.getId());
        roleInfo.setTableId(tableId);
        getMapper().updateByPrimaryKey(roleInfo);
        CacheModule.getInstance().invalidate(roleInfo.getId(), CacheKey.role_info_cache);
    }

    public void delete(Long id) {
        int dsId = RoleDbConfig.getDsId(id);
        DbContextHolder.setDbType(dsId);

        int tableId = RoleDbConfig.getTableId(id);
        getMapper().deleteByPrimaryKey(id, tableId);
        CacheModule.getInstance().invalidate(id, CacheKey.role_info_cache);
    }

    public static void main(String[] args) throws InterruptedException {
        GameApplication.main(args);

        for (int i = 0; i < 10; i++) {
            RoleInfo info = new RoleInfo();
            info.setId(IDModule.getInstance().genRoleId());
            info.setNickname("111");
            info.setUserId(""+info.getId());
            RoleInfoDataProvider.getInstance().addRoleInfoAsync(info);

            System.out.println(JSONObject.toJSONString(info));

            Thread.sleep(1000);
        }
    }
}
