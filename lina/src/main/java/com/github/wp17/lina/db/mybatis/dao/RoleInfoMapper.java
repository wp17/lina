package com.github.wp17.lina.db.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import com.github.wp17.lina.db.mybatis.model.RoleInfo;

public interface RoleInfoMapper {
	int deleteByPrimaryKey(@Param("roleUuid")String roleUuid, @Param("tableId")int tableId);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(@Param("roleUuid")String roleUuid, @Param("tableId")int tableId);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKeyWithBLOBs(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);
}