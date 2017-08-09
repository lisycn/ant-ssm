package com.yuanlai.agent.service;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.entity.AuthOrg;

import java.util.Map;

/**
 * @author chen_yq add 2016.04.29
 */
public interface AuthOrgService {
	
	/**
	 * 分页查询机构信息
	 * @param authOrg
	 * @param pageSize 每页条数（默认15条）
	 * @param pageNum  当前页数（默认第1页）
	 * @return list:列表<br>total:记录数
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectByPage(AuthOrg authOrg, int pageSize, int pageNum) throws TdRuntimeException;
	/**
	 * 添加机构信息
	 * @param authOrg orgId,parentOrgId,orgName 必传
	 * @return
	 * @throws TdRuntimeException
	 */
	public void add(AuthOrg authOrg) throws TdRuntimeException;
	/**
	 * 修改机构信息
	 * @param authOrg orgId必传
	 * @return
	 * @throws TdRuntimeException
	 */
	public void update(AuthOrg authOrg) throws TdRuntimeException;
	/**
	 * 禁用/启用用户
	 * @param isUse 0-禁用 1-启用
	 * @param orgId 机构id
	 * @return
	 * @throws TdRuntimeException
	 */
	public void disOrEnable(String isUse, String orgId, String updObj) throws TdRuntimeException;
	/**
	 * 删除机构
	 * @param orgId
	 * @throws TdRuntimeException
	 */
	public void delByOrgId(String orgId) throws TdRuntimeException;
	/**
	 * 查询单个机构信息
	 * @param orgId
	 * @return
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectByOrgId(String orgId) throws TdRuntimeException;
	/**
	 * 查询机构的树形表格
	 * @param authOrg orgId（可选，默认顶级机构）
	 * @return
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectOrgTreeTable(AuthOrg authOrg) throws TdRuntimeException;
	/**
	 * 查询有效机构的下拉单树形结构
	 * @param authOrg
	 * @return
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectDropdownListTree(AuthOrg authOrg)throws TdRuntimeException;
}
