package com.github.wp17.lina.game.module.cache;

import com.github.wp17.lina.common.cache.ICacheObj;
import com.github.wp17.lina.game.module.cache.obj.RoleInfoCache;

public enum CacheKey {

    role_info_cache(RoleInfoCache.class);

    Class<? extends ICacheObj> clazz;

    CacheKey(Class<? extends ICacheObj> clazz) {
        this.clazz = clazz;
    }
}
