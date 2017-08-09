package com.yuanlai.agent.service;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.entity.AuthRole;

import java.util.List;
import java.util.Map;

/**
 * 角色Service
 * @author chen_yq add 2016.04.29
 *
 */
public interface AuthRoleService {
	/**
	 * 分页查询角色
	 * @param authRole
	 * @param pageSize 每页条数（默认15条）
	 * @param pageNum  当前页数（默认第1页）
	 * @return list:列表<br>total:记录数
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectByPage(AuthRole authRole, int pageSize, int pageNum) throws TdRuntimeException;
	/**
	 * 添加角色
	 * @param authRole roleId系统生成;sysId、roleName必填
	 * @return
	 * @throws TdRuntimeException
	 */
	public void add(AuthRole authRole) throws TdRuntimeException;
	/**
	 * 修改角色
	 * @param authRole roleId,roleName,sysId必传
	 * @return
	 * @throws TdRuntimeException
	 */
	public void update(AuthRole authRole) throws TdRuntimeException;
	/**
	 * 按照主键roleId删除角色
	 * @param roleId
	 * @return
	 * @throws TdRuntimeException
	 */
	public void delByRoleId(String roleId) throws TdRuntimeException;
	/**
	 * 禁用/启用角色
	 * @param isUse 0-禁用，1-启用
	 * @param roleId 角色id
	 * @return
	 * @throws TdRuntimeException
	 */
	public void disOrEnable(String isUse, String roleId, String updObj) throws TdRuntimeException;
	/**
	 * 查询单个角色信息
	 * @param roleId
	 * @return 角色信息及关联的系统信息
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectByRoleId(String roleId) throws TdRuntimeException;
	/**
	 * 分配权限预处理
	 * @param roleId
	 * @return roleCurrItemIdList：角色当前的菜单id集合<br>itemTree+系统编号:系统的权限
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> assignAuthPre(String roleId) throws TdRuntimeException;
	/**
	 * 分配权限
	 * @param roleId 角色id
	 * @param itmIds 001,002,003
	 * @throws TdRuntimeException
	 */
	public void assignAuth(String roleId, String itmIds) throws TdRuntimeException;
	
	/**
	 * 角色分配预处理
	 * @param usrId
	 * @return usrCurrRoleIdList:用户当前角色ID集合<br>
	 * 		   allRoleList:全部角色信息
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> assignRolePre(String usrId)throws TdRuntimeException;
	/**
	 * 角色分配
	 * @param usrId
	 * @param roleIds 001,002,003
	 * @throws TdRuntimeException
	 */
	public void assignRole(String usrId, String roleIds)throws TdRuntimeException;
	/**
	 * 按照系统编号查询有效角色,(dubbo调用)
	 * @param sysId
	 * @return
	 * @throws TdRuntimeException
	 */
	public List<Map<String, Object>> selectValidBySysId(String sysId)throws TdRuntimeException;
	/**
	 * 按照map查询,(dubbo调用)
	 * @return
	 */
	public List<Map<String, Object>> selectByMap(Map<String, Object> map)throws TdRuntimeException;
//	public void delByRoleIds(String roleIds) throws TdRuntimeException;
}
