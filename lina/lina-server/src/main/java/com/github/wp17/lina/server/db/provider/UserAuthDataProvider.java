package com.github.wp17.lina.server.db.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import cn.org.wgpg.lina.db.mapper.role.UserAuthMapper;
import cn.org.wgpg.lina.db.model.role.UserAuth;
import cn.org.wgpg.lina.db.source.DbContextHolder;

@Repository
public class UserAuthDataProvider {
	@Autowired private UserAuthMapper mapper;
	
	@Value("#{roleDbConfig.role_db_count}")
	private int role_db_count;
	
	public int insert(UserAuth userAuth) {
		int dbType = (int) (userAuth.getId()%role_db_count);
		DbContextHolder.setDbType(dbType);
		return mapper.insert(userAuth);
	}

}
