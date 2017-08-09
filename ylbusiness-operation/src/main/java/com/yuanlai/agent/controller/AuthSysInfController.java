package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.yuanlai.agent.service.AuthSysInfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 
 * @author chen_yq add 2016.04.27
 */
@RequestMapping(value="/authSysInf/")
@Controller
public class AuthSysInfController extends TdBaseController{

	@Autowired
	private AuthSysInfService authSysInfService;
	
	@RequestMapping(value = "selectAll")
	@ResponseBody
	public Map<String, Object> selectAll(){
		try {
			Map<String, Object> map = authSysInfService.selectAll();
			return genSuccessResult(map);
		} catch (Exception e) {//TODO test
			return genErrorResult(e.getMessage());
		}
	}
}
