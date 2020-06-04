package com.github.wp17.lina.game.module.cache.obj;

import com.github.wp17.lina.common.cache.ICacheObj;
import com.github.wp17.lina.common.cache.InfoClass;
import com.github.wp17.lina.game.module.cache.CacheKey;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;
import com.github.wp17.lina.game.module.db.provider.RoleInfoDataProvider;

@InfoClass(RoleInfo.class)
public class UserInfoCache implements ICacheObj<RoleInfo, String> {
    private volatile RoleInfo roleInfo;

    @Override
    public void loadDB(String username) {
        roleInfo = RoleInfoDataProvider.getInstance().selectRoleInfoByUserId(username);
    }

    @Override
    public void loadRedis(String id) {
        roleInfo = CacheModule.getInstance().loadRedis(id, CacheKey.user_info_cache);
    }

    @Override
    public RoleInfo get() {
        return roleInfo;
    }

    @Override
    public void set(RoleInfo data) {
        this.roleInfo = data;
    }

    @Override
    public boolean isLoad() {
        return null != roleInfo;
    }
}
