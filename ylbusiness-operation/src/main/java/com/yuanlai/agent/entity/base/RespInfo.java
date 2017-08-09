package com.yuanlai.agent.entity.base;

/**
 * 响应信息
 * 
 * @author Lee
 *
 */
public class RespInfo {
	
	private String rspCod;
	
	private String rspMsg;

	private Object rspData;

	public String getRspCod() {
		return rspCod;
	}

	public void setRspCod(String rspCod) {
		this.rspCod = rspCod;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public Object getRspData() {
		return rspData;
	}

	public void setRspData(Object rspData) {
		this.rspData = rspData;
	}
}
