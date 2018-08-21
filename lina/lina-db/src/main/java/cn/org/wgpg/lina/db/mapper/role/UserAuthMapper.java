package cn.org.wgpg.lina.db.mapper.role;

import cn.org.wgpg.lina.db.model.role.UserAuth;
import java.util.List;

public interface UserAuthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAuth record);

    UserAuth selectByPrimaryKey(Long id);

    List<UserAuth> selectAll();

    int updateByPrimaryKey(UserAuth record);
}