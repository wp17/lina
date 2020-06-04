package com.github.wp17.lina.game.logic.manager;

import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.game.module.cache.CacheKey;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;

public class RoleInfoManager {
    private final Role role;

    public RoleInfoManager(Role role) {
        this.role = role;
    }

    RoleInfo getRoleInfo() {
        RoleInfo info = CacheModule.getInstance().get(role.getRoleUuid(), CacheKey.role_info_cache);
        return info;
    }
}
