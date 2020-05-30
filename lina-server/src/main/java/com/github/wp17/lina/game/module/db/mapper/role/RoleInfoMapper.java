package com.github.wp17.lina.game.module.db.mapper.role;
import org.apache.ibatis.annotations.Param;

import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;
import java.util.List;

public interface RoleInfoMapper extends RoleInfoMapperExtends {
    int deleteByPrimaryKey(Long id, @Param("tableId") Integer tableId);

    int insert(RoleInfo record);

    RoleInfo selectByPrimaryKey(Long id, @Param("tableId") Integer tableId);

    List<RoleInfo> selectAll(@Param("tableId") Integer tableId);

    int updateByPrimaryKey(RoleInfo record);
}
