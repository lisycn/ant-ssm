package com.yuanlai.agent.dao;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.AuthRole;

import java.util.List;
import java.util.Map;

public interface AuthRoleDao extends BaseDao<AuthRole, String> {
	/**
	 * 修改角色状态
	 * @param map isUse角色状态，list:角色id集合
	 * @return
	 */
	int disOrEnable(String isUse, String roleId, String updObj, String updTim);
	
	/**
	 * 按照roleId查询角色
	 * @param roleId
	 * @return 角色信息和关联的系统信息
	 */
	Map<String, Object> selectByRoleId(String roleId);
	/**
	 * 查询全部有效的角色
	 * @return
	 */
	List<Map<String, Object>> selectAllValidRole(int limit);
	/**
	 * 查询全部有效的角色的总数
	 * @return
	 */
	int countAllValidRole();
	/**
	 * 分页查询统计
	 * @param map
	 * @return
	 */
	int countByPager(Map<String, Object> map);
	/**
	 * 查询角色，
	 * @param map
	 * @param list ROLE_ID 集合
	 * @return
	 */
	List<Map<String, Object>> selectByMap(Map<String, Object> map);
	/**
	 * 查询角色记录数
	 * @param map
	 * @return
	 */
	int countByMap(Map<String, Object> map);
	/**
	 * 按照系统编号查询有效角色
	 * @param sysId
	 * @return
	 */
	List<Map<String, Object>> selectValidBySysId(Map<String, Object> map);
	/**
	 *  按照系统编号查询有效角色总数
	 * @param sysId
	 * @return
	 */
	int countValidBySysId(String sysId);
	/**
	 * 查询重复
	 * @param authRole
	 * @param sysId 系统id
	 * @param roleName 角色名
	 * @param roleId 角色ID，可选
	 * @return
	 */
	List<AuthRole> selectRepeat(AuthRole authRole);
}