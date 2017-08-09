package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.tangdi.def.constant.Constant;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.entity.AuthOrg;
import com.yuanlai.agent.service.AuthOrgService;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/authOrg/")
@Controller
public class AuthOrgController extends TdBaseController{
	@Autowired
	private AuthOrgService authOrgService;
	
	@RequestMapping(value = "selectByPage")
	@ResponseBody
	public Map<String, Object> selectByPage(AuthOrg authOrg, int pageSize, int pageNum)throws TdRuntimeException{
		try {
			Map<String, Object> map = authOrgService.selectByPage(authOrg, pageSize, pageNum);
			return genSuccessResult(map);
		} catch (Exception e) {//TODO test
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "add")
	@ResponseBody
	public Map<String, Object> add(AuthOrg authOrg, HttpServletRequest request)throws TdRuntimeException{
		Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
		authOrg.setCreObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
		try {
			authOrgService.add(authOrg);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(AuthOrg authOrg, HttpServletRequest request)throws TdRuntimeException{
		Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
		authOrg.setUpdObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
		try {
			authOrgService.update(authOrg);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "disOrEnable")
	@ResponseBody
	public Map<String, Object> disOrEnable(String isUse,String orgId, HttpServletRequest request)throws TdRuntimeException{
		try {
			Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
			String currUsername = String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME));
			authOrgService.disOrEnable(isUse,orgId,currUsername);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}

	@RequestMapping(value = "delByOrgId")
	@ResponseBody
	public Map<String, Object> delByOrgId(String orgId)throws TdRuntimeException{
		try {
			authOrgService.delByOrgId(orgId);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}

	
	@RequestMapping(value = "selectByOrgId")
	@ResponseBody
	public Map<String, Object> selectByOrgId(String orgId)throws TdRuntimeException{
		try {
			Map<String, Object> map = authOrgService.selectByOrgId(orgId);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "selectOrgTreeTable")
	@ResponseBody
	public Map<String, Object> selectOrgTreeTable(AuthOrg authOrg)throws TdRuntimeException{
		try {
			Map<String, Object> map = authOrgService.selectOrgTreeTable(authOrg);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "selectDropdownListTree")
	@ResponseBody
	public Map<String, Object> selectDropdownListTree(AuthOrg authOrg)throws TdRuntimeException{
		try {
			Map<String, Object> map = authOrgService.selectDropdownListTree(authOrg);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	
}
