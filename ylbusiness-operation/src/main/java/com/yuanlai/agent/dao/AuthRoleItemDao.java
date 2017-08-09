package com.yuanlai.agent.dao;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.AuthRoleItemKey;

import java.util.List;

public interface AuthRoleItemDao extends BaseDao<AuthRoleItemKey, AuthRoleItemKey> {
	int insertMulti(List<AuthRoleItemKey> list);
	int deleteByRoleId(String roleId);
	/**
	 * 查询叶子节点
	 * @param roleId
	 * @return
	 */
	List<String> selectLeafItmIdByRoleId(String roleId);
	
	int deleteByItemId(String itmId);
}