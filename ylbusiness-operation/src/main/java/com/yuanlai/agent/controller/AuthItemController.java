package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.tangdi.def.constant.Constant;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.entity.AuthItem;
import com.yuanlai.agent.service.AuthItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author chen_yq add 2016.04.26
 */

@RequestMapping(value="/authItem/")
@Controller
public class AuthItemController extends TdBaseController{
	@Autowired
	private AuthItemService authItemService;
	
	@RequestMapping(value = "selectByPage")
	@ResponseBody
	public Map<String, Object> selectByPage(AuthItem authItem, int pageSize, int pageNum)throws TdRuntimeException{
		try {
			Map<String, Object> map = authItemService.selectByPage(authItem,pageSize,pageNum);
			return genSuccessResult(map);
		} catch (Exception e) {//TODO test
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "add")
	@ResponseBody
	public Map<String, Object> add(AuthItem authItem, HttpServletRequest request)throws TdRuntimeException{
		Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
		authItem.setCreObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
		try {
			authItemService.add(authItem);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(AuthItem authItem, HttpServletRequest request)throws TdRuntimeException{
		Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
		authItem.setUpdObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
		try {
			authItemService.update(authItem);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
	@RequestMapping(value = "disOrEnable")
	@ResponseBody
	public Map<String, Object> disOrEnable(
			@RequestParam("isUse") String isUse,
			@RequestParam("itmId") String itmId,
			HttpServletRequest request)throws TdRuntimeException{
		try {
			Map<String, Object> usrInfo = (Map<String, Object>)request.getAttribute(Constant.USR_INFO);
			String currUsername = String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME));
			authItemService.disOrEnable(isUse, itmId,currUsername);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
		
	}
	@RequestMapping(value = "delByItmId")
	@ResponseBody
	public Map<String, Object> delByItmId(@RequestParam("itmId") String itmId)throws TdRuntimeException{
		try {
			authItemService.delByItmId(itmId);
			return genSuccessResult();
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
		
	}
	@RequestMapping(value = "selectByItmId")
	@ResponseBody
	public Map<String, Object> selectByItmId(String itmId)throws TdRuntimeException{
		try {
			Map<String, Object> map = authItemService.selectByItmId(itmId);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
		
	}
	
	
	@RequestMapping(value = "selectItemTreeTable")
	@ResponseBody
	public Map<String, Object> selectItemTreeTable(AuthItem authItem)throws TdRuntimeException{
		try {
			Map<String, Object> map = authItemService.selectItemTreeTable(authItem);
			return genSuccessResult(map);
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
	}
}
