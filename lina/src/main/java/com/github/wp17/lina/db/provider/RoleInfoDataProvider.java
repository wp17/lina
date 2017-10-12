package com.github.wp17.lina.db.provider;

import org.apache.ibatis.session.SqlSessionFactory;

import com.github.wp17.lina.db.DBModule;
import com.github.wp17.lina.db.mybatis.dao.RoleInfoMapper;
import com.github.wp17.lina.db.mybatis.model.RoleInfo;
import com.github.wp17.lina.db.mybatis.model.RoleInfoSharding;

public class RoleInfoDataProvider {
	private RoleInfoDataProvider(){}
	private static final RoleInfoDataProvider instance = new RoleInfoDataProvider();
	public static RoleInfoDataProvider getInstance() {
		return instance;
	}
	
	private SqlSessionFactory factory = DBModule.getInstance().getRoleSF();
	
	private RoleInfoMapper getMapper(){
		return factory.openSession().getMapper(RoleInfoMapper.class);
	}
	
	public void addRoleInfo(RoleInfo roleInfo, Integer tableId){
		RoleInfoSharding infoSharding = new RoleInfoSharding();
		getMapper().insertSelective(infoSharding);
	}
	
	public RoleInfo selectById(String id, Integer tableId){
		return getMapper().selectByPrimaryKey(id, tableId);
	}
}
