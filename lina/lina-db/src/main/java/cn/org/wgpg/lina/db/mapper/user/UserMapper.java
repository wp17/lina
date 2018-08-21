package cn.org.wgpg.lina.db.mapper.user;

import cn.org.wgpg.lina.db.model.user.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}