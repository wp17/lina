package com.github.wp17.lina.game.module.cache.obj;

import com.github.wp17.lina.common.cache.InfoClass;
import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;
import com.github.wp17.lina.game.module.cache.CacheKey;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.common.cache.ICacheObj;
import com.github.wp17.lina.game.module.db.provider.RoleInfoDataProvider;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@InfoClass(RoleInfo.class)
public class RoleInfoCache implements ICacheObj<RoleInfo, Long> {
    private volatile RoleInfo roleInfo;

    @Override
    public void loadDB(Long id) {
        roleInfo = RoleInfoDataProvider.getInstance().selectById(id);
    }

    @Override
    public void loadRedis(Long id) {
        roleInfo = CacheModule.getInstance().loadRedis(id, CacheKey.role_info_cache);
    }

    @Override
    public RoleInfo get() {
        return roleInfo;
    }

    @Override
    public void set(RoleInfo data) {
        roleInfo = data;
    }

    @Override
    public boolean isLoad() {
        return Objects.nonNull(roleInfo);
    }
}
