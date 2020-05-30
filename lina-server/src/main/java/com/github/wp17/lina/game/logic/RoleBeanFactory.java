package com.github.wp17.lina.game.logic;

import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;
import com.github.wp17.lina.game.module.id.IDModule;

public class RoleBeanFactory {
    public static RoleInfo newRoleInfo(String userId, String nickName, String icon, byte sex ) {
        RoleInfo roleInfo = new RoleInfo();

        long id = IDModule.getInstance().genRoleId();
        roleInfo.setId(id);

        roleInfo.setUserId(userId);
        roleInfo.setNickname(nickName);
        roleInfo.setLevel(0);
        roleInfo.setIcon(icon);
        roleInfo.setSex(sex);
        roleInfo.setCreateTime(System.currentTimeMillis());

        return roleInfo;
    }
}
