package com.yuanlai.agent.dao;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.AuthUsrRoleKey;

import java.util.List;
import java.util.Map;

public interface AuthUsrRoleDao extends BaseDao<AuthUsrRoleKey, AuthUsrRoleKey> {
	/**
	 * 查询用户已有角色id
	 * @param usrId
	 * @return
	 */
	List<String> selectUsrCurrValidRoleId(String usrId);
	int insertMulti(List<AuthUsrRoleKey> list);
	int deleteByUsrId(String usrId);
	/**
	 * 查询用户的角色及菜单信息
	 * @param usrId
	 * @param sysId
	 * @return
	 */
	List<Map<String, Object>> selectUsrRoleAndItem(String usrId, String sysId);
}