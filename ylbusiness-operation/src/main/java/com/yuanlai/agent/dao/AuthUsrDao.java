package com.yuanlai.agent.dao;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.AuthUsr;

import java.util.List;
import java.util.Map;

public interface AuthUsrDao extends BaseDao<AuthUsr, String> {
	int deleteByUsrIds(List<String> usrIds);
	/**
	 * usrIds(001,002,003),usrStatus查询
	 * @return
	 */
	List<AuthUsr> selectStatusUsr(Map<String, Object> map);
	int disOrEnable(String usrStatus, String usrId, String updObj, String updTim);
	/**
	 * 按用户名查询
	 * @param usrName
	 * @return
	 */
	AuthUsr selectByUsrName(String usrName);
	/**
	 * 查询用户当前角色
	 * @param usrId
	 * @return usrId,roleId,roleName
	 */
	List<Map<String, Object>> selectUsrCurrRole(String usrId);
	/**
	 * 查询用户信息及关联的机构信息
	 * @param usrId
	 * @return
	 */
	Map<String, Object> selectByUsrId(String usrId);
	/**
	 * 查询用户登录时的权限
	 * @param map :usrId,parentItmId
	 * @return
	 */
	List<Map<String, Object>> selectUsrAuthForLogin(Map<String, Object> map);
	
	/**
	 * 根据用户获取系统信息
	 * @param map
	 * @return
	 */
	List getSysInfByUsr(Map<String, Object> map);
	
	int updateOrgIdEmpty(String orgId);
}