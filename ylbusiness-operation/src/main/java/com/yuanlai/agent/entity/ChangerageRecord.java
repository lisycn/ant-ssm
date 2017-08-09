package com.yuanlai.agent.entity;

public class ChangerageRecord {

	private String changerateid; // 修改费率主键
	private String account; // 账号
	private String password; // 密码
	private String wxRate; // 微信费率
	private String aliRate; // 支付宝费率
	private String channelCode; // 通道码
	private String privatekey; // 密钥
	private String respcode; // 通道返回码
	private String respinfo; // 通道返回原因
	private String channel; // 通道标识
	private String accesspart; // 接入方
	private String createtime; // 创建时间
	private String token;
	private String accesspartRole; // 控制权限用

	public String getChangerateid() {
		return changerateid;
	}

	public void setChangerateid(String changerateid) {
		this.changerateid = changerateid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWxRate() {
		return wxRate;
	}

	public void setWxRate(String wxRate) {
		this.wxRate = wxRate;
	}

	public String getAliRate() {
		return aliRate;
	}

	public void setAliRate(String aliRate) {
		this.aliRate = aliRate;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getRespinfo() {
		return respinfo;
	}

	public void setRespinfo(String respinfo) {
		this.respinfo = respinfo;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccesspartRole() {
		return accesspartRole;
	}

	public void setAccesspartRole(String accesspartRole) {
		this.accesspartRole = accesspartRole;
	}

}
