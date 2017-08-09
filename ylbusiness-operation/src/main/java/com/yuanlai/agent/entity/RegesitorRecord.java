package com.yuanlai.agent.entity;

public class RegesitorRecord {

	private String regesitorid; // 主键

	private String account; // 账号

	private String password; // 密码

	private String accesspart; // 接入方

	private String channel; // 渠道方

	private String respcode; // 通道返回码

	private String respinfo; // 返回信息

	private String createtime; // 创建时间

	private String token;

	private String accesspartRole; // 控制权限用

	public String getRegesitorid() {
		return regesitorid;
	}

	public void setRegesitorid(String regesitorid) {
		this.regesitorid = regesitorid;
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

	public String getAccesspart() {
		return accesspart;
	}

	public void setAccesspart(String accesspart) {
		this.accesspart = accesspart;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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