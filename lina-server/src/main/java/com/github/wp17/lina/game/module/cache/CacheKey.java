package com.github.wp17.lina.game.module.cache;

import com.github.wp17.lina.common.cache.ICacheObj;
import com.github.wp17.lina.game.module.cache.obj.RoleInfoCache;
import com.github.wp17.lina.game.module.cache.obj.UserInfoCache;

public enum CacheKey {

    role_info_cache(RoleInfoCache.class),
    user_info_cache(UserInfoCache.class);

    Class<? extends ICacheObj> clazz;

    CacheKey(Class<? extends ICacheObj> clazz) {
        this.clazz = clazz;
    }
}
