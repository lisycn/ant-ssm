package com.yuanlai.agent.entity;

import java.util.List;

public class MyNode {

	private String key;
    private String itmId;
    private String parentItmId;
    private String sysId;
    private String itmTyp;
    private String itmName;
    private String itmDesc;
    private String itmUrl;
    private String isUse;
    private String creObj;
    private String creTim;
    private String updObj;
    private String updTim;
	private List<MyNode> children;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getItmId() {
		return itmId;
	}
	public void setItmId(String itmId) {
		this.itmId = itmId;
	}
	public String getParentItmId() {
		return parentItmId;
	}
	public void setParentItmId(String parentItmId) {
		this.parentItmId = parentItmId;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getItmTyp() {
		return itmTyp;
	}
	public void setItmTyp(String itmTyp) {
		this.itmTyp = itmTyp;
	}
	public String getItmName() {
		return itmName;
	}
	public void setItmName(String itmName) {
		this.itmName = itmName;
	}
	public String getItmDesc() {
		return itmDesc;
	}
	public void setItmDesc(String itmDesc) {
		this.itmDesc = itmDesc;
	}
	public String getItmUrl() {
		return itmUrl;
	}
	public void setItmUrl(String itmUrl) {
		this.itmUrl = itmUrl;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getCreObj() {
		return creObj;
	}
	public void setCreObj(String creObj) {
		this.creObj = creObj;
	}
	public String getCreTim() {
		return creTim;
	}
	public void setCreTim(String creTim) {
		this.creTim = creTim;
	}
	public String getUpdObj() {
		return updObj;
	}
	public void setUpdObj(String updObj) {
		this.updObj = updObj;
	}
	public String getUpdTim() {
		return updTim;
	}
	public void setUpdTim(String updTim) {
		this.updTim = updTim;
	}
	public List<MyNode> getChildren() {
		return children;
	}
	public void setChildren(List<MyNode> children) {
		this.children = children;
	}
}
