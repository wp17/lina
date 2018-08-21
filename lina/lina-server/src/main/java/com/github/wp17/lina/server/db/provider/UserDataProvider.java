package com.github.wp17.lina.server.db.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.org.wgpg.lina.db.mapper.user.UserMapper;
import cn.org.wgpg.lina.db.model.user.User;
import cn.org.wgpg.lina.db.source.DbContextHolder;

@Repository
public class UserDataProvider {
	@Autowired private UserMapper mapper;
	
	@Value("#{userDbConfig.user_db_count}")
	private int user_db_count;
	
	public int insert(User user) {
		int dbType = (int) (user.getId()%user_db_count);
		DbContextHolder.setDbType(dbType);
		return mapper.insert(user);
	}
	
	@Transactional(transactionManager = "dataSourceTransactionManager_user", rollbackFor = Exception.class)
	public int insert(List<User> users) {
		users.forEach(user ->{
			int dbType = (int) (user.getId()%user_db_count);
			DbContextHolder.setDbType(dbType);
			mapper.insert(user);
		});
		return users.size();
	}
}
