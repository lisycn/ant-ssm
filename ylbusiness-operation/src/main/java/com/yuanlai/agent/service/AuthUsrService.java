package com.yuanlai.agent.service;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.entity.AuthUsr;

import java.util.List;
import java.util.Map;

/**
 * 用户管理Service接口
 * @author chen_yq add 2016.04.26
 */
public interface AuthUsrService {
	/**
	 * 用户登录
	 * @param usrName
	 * @param sysId
	 * @return
	 * @throws TdRuntimeException
	 */
	public AuthUsr login(String usrName, String usrPwd)throws TdRuntimeException;
	
	
	/**
	 * 根据用户获取系统
	 * @param map
	 * @return
	 * @throws TdRuntimeException
	 */
	public List getSysInfByUsr(Map map)throws TdRuntimeException;
	
	/**
	 * 用户获取菜单
	 * @param usrName
	 * @param sysId
	 * @return
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> getMenuBySys(String usrName, String sysId)throws TdRuntimeException;
	
	/**
	 * 分页查询
	 * @param authUsr  AuthUsr实体类
	 * @param pageSize 每页记录数（默认15条）
	 * @param pageNum  当前页数（默认第1页）
	 * @return list:列表<br>total:记录数
	 * @throws TdRuntimeException
	 */
	public Map<String, Object> selectByPage(AuthUsr authUsr, int pageSize, int pageNum)throws TdRuntimeException;
	/**
	 * 添加用户
	 * @param authUsr AuthUsr实体类（usrName,usrRealName必填）
	 * @return 用户密码
	 */
	public String add(AuthUsr authUsr)throws TdRuntimeException;
	/**
	 * 更新用户信息 
	 * @param authUsr AuthUsr实体类（usrid必填）
	 * @return
	 */
	public void update(AuthUsr authUsr)throws TdRuntimeException;
	/**
	 * 根据用户id删除用户
	 * @param usrId
	 * @return
	 */
	public void delByUsrId(String usrId)throws TdRuntimeException;
	/**
	 * 根据一组用户id删除用户
	 * @param usrIds 一组用户usrId：001,002,003
	 * @return
	 */
	public void delByUsrIds(String usrIds)throws TdRuntimeException;
	/**
	 * 按照用户ID查询
	 * @param usrId
	 * @return authUsr:用户信息<br>usrCurrRoleList:用户当前角色
	 * @throws Exception
	 */
	public Map<String, Object> selectByUsrId(String usrId)throws TdRuntimeException;
	/**
	 * 禁用/启用用户
	 * @param usrStatus 状态
	 * @param usrId 用户id
	 * @throws TdRuntimeException
	 */
	public void disOrEnable(String usrStatus, String usrId, String updObj)throws TdRuntimeException;
	/**
	 * 管理员重置用户密码，默认td888888
	 * @param authUsr
	 * @param authUsr usrId 必输
	 * @return 新密码
	 * @throws TdRuntimeException
	 */
	public String resetUsrPwd(AuthUsr authUsr)throws TdRuntimeException;
	/**
	 * 用户首次登录修改密码
	 * @param authUsr usrId,usrPsw必填
	 * @throws TdRuntimeException
	 */
	public void updatePwdForFirstLogin(AuthUsr authUsr)throws TdRuntimeException;
	/**
	 * 用户修改密码
	 * @param authUsr
	 * @param oldPwd
	 * @throws TdRuntimeException
	 */
	public void updatePwd(AuthUsr authUsr, String oldPwd)throws TdRuntimeException;
	/**
	 * 查询用户角色
	 * @param usrId
	 * @return 用户当前角色ID(001,002,003)<br>
	 * @throws TdRuntimeException
	 */
	public String selectUsrRole(String usrId)throws TdRuntimeException;
	
	public void unLock(String usrId)throws TdRuntimeException;
}
