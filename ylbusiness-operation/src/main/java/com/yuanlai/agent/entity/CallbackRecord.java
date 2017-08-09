package com.yuanlai.agent.entity;

public class CallbackRecord{
	
	private Integer id;
	
	private String orderId;
	
	private String respCode;
	
	private String respInfo;
	
	private String amount;
	
	private String wXOrderNo;
	
	private String status;

	private String token;

	private String channel;

	private String accesspart;

	private String createtime;

	private String accesspartRole;  //控制权限用

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getwXOrderNo() {
		return wXOrderNo;
	}

	public void setwXOrderNo(String wXOrderNo) {
		this.wXOrderNo = wXOrderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAccesspart() {
		return accesspart;
	}

	public void setAccesspart(String accesspart) {
		this.accesspart = accesspart;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getAccesspartRole() {
		return accesspartRole;
	}

	public void setAccesspartRole(String accesspartRole) {
		this.accesspartRole = accesspartRole;
	}
	

	
}
