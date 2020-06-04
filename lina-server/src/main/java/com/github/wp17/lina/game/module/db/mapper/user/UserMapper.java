package com.github.wp17.lina.game.module.db.mapper.user;
import org.apache.ibatis.annotations.Param;

import com.github.wp17.lina.game.module.db.mapper.user.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("tableId") Integer tableId);

    int insert(User record);

    User selectByPrimaryKey(@Param("id") Long id, @Param("tableId") Integer tableId);

    List<User> selectAll(@Param("tableId") Integer tableId);

    int updateByPrimaryKey(User record);
}
