package com.yuanlai.agent.dao;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.AuthOrg;

import java.util.List;
import java.util.Map;

public interface AuthOrgDao extends BaseDao<AuthOrg, String> {
	/**
	 * 禁用/启用机构
	 * @param isUse
	 * @param orgId
	 * @return
	 */
	int disOrEnable(String isUse, String orgId, String updObj, String updTim);
	/**
	 * 按照上级机构id查询
	 * @param parentOrgId
	 * @return
	 */
	List<AuthOrg> selectByParentOrgId(String parentOrgId);
	
	List<Map<String, Object>> selectOrgTreeForTable_parent(Map<String, Object> map);
	
	List<Map<String, Object>> selectOrgTreeForTable_child(Map<String, Object> map);
	
	int countOrgTreeForTable_child(Map<String, Object> map);
	/**
	 * 查询重复
	 * @param authOrg
	 * @param orgId 机构编号
	 * @param parentOrgId 上级机构编号
	 * @param orgName 机构名称
	 * @return
	 */
	List<AuthOrg> selectRepeatForAdd(AuthOrg authOrg);
	
	List<AuthOrg> selectRepeatForUpdate(AuthOrg authOrg);
}