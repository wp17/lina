package com.github.wp17.lina.game.module.db.mapper.role;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleInfoMapperExtends {
    @Select("select * from role_info_#{tableId} where user_id = #{userId}")
    RoleInfo selectByUserId(@Param("userId") String userId, @Param("tableId") Integer tableId);
}
