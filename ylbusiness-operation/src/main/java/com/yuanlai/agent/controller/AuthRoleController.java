package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.tangdi.def.constant.Constant;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.entity.AuthRole;
import com.yuanlai.agent.service.AuthRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * @author chen_yq add 2016.04.29
 *
 */
@RequestMapping(value="/authRole/")
@Controller
public class AuthRoleController extends TdBaseController{
	@Autowired
	@Qualifier("authRoleServiceImpl")
	private AuthRoleService authRoleService;
	
	@RequestMapping(value = "selectByPage")
	@ResponseBody
	public Map<String, Object> selectByPage(AuthRole authRole, int pageSize, int pageNum)throws TdRuntimeException{
		try {
			Map<String, Object> map = authRoleService.selectByPage(authRole,pageSize,pageNum);
			return genSuccessResult(map);
		} catch (Exception e) {//TODO test
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "add")
	@ResponseBody
	public Map<String, Object> add(AuthRole authRole, HttpServletRequest request)throws TdRuntimeException{
		Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
		authRole.setCreObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
		try {
			authRoleService.add(authRole);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(AuthRole authRole, HttpServletRequest request)throws TdRuntimeException{
		Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
		authRole.setUpdObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
		try {
			authRoleService.update(authRole);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "delByRoleId")
	@ResponseBody
	public Map<String, Object> delByRoleId(String roleId)throws TdRuntimeException{
		try {
			authRoleService.delByRoleId(roleId);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "disOrEnable")
	@ResponseBody
	public Map<String, Object> disOrEnable(String isUse,String roleId, HttpServletRequest request)throws TdRuntimeException{
		try {
			Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
			String currUsername = String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME));
			authRoleService.disOrEnable(isUse, roleId,currUsername);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "selectByRoleId")
	@ResponseBody
	public Map<String, Object> selectByRoleId(String roleId)throws TdRuntimeException{
		try {
			Map<String, Object> map = authRoleService.selectByRoleId(roleId);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "assignAuthPre")
	@ResponseBody
	public Map<String, Object> assignAuthPre(String roleId)throws TdRuntimeException{
		try {
			Map<String, Object> map = authRoleService.assignAuthPre(roleId);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "assignAuth")
	@ResponseBody
	public Map<String, Object> assignAuth(String roleId,String itmIds)throws TdRuntimeException{
		try {
			authRoleService.assignAuth(roleId, itmIds);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "assignRolePre")
	@ResponseBody
	public Map<String, Object> assignRolePre(String usrId)throws TdRuntimeException{
		try {
			Map<String, Object> map = authRoleService.assignRolePre(usrId);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "assignRole")
	@ResponseBody
	public Map<String, Object> assignRole(String usrId,String roleIds)throws TdRuntimeException{
		try {
			authRoleService.assignRole(usrId, roleIds);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
}
