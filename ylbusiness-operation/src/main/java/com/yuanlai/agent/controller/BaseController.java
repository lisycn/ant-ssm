package com.yuanlai.agent.controller;

import org.apache.commons.lang.StringUtils;

import com.yuanlai.agent.entity.base.RespInfo;



/**
 * 基础控制器
 * 
 * @author Lee
 *
 */
public class BaseController {

	protected RespInfo genSuccessResult() {
		return genSuccessResult(null);
	}

	protected RespInfo genSuccessResult(Object rspObj) {
		RespInfo resp = new RespInfo();
		resp.setRspCod("000000");
		resp.setRspMsg("操作成功");
		if (rspObj != null) {
			resp.setRspData(rspObj);
		}
		return resp;
	}

	protected RespInfo genErrorResult() {
		return genErrorResult(null);
	}

	protected RespInfo genErrorResult(String errMsg) {
		RespInfo resp = new RespInfo();
		resp.setRspCod("999999");
		if (StringUtils.isEmpty(errMsg)) {
			resp.setRspMsg("交易失败");
		} else {
			resp.setRspMsg(errMsg);
		}
		return resp;
	}

}
