package com.yuanlai.agent.entity;

import java.io.Serializable;

public class PubAttachment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String moduleName;
	private String tableName;
	private String pkId;
	private String lx;
	private String orderNum;
	private String fjName;
	private String fjPath;
	private String fjo;
	private String fjt;
	private String sfsx;

	public String getSfsx() {
		return this.sfsx;
	}

	public void setSfsx(String sfsx) {
		this.sfsx = sfsx;
	}

	public PubAttachment() {
	}

	public PubAttachment(String id, String moduleName, String tableName, String pkId, String lx, String orderNum,
			String fjName, String fjPath, String fjo, String fjt) {
		this.id = id;
		this.moduleName = moduleName;
		this.tableName = tableName;
		this.pkId = pkId;
		this.lx = lx;
		this.orderNum = orderNum;
		this.fjName = fjName;
		this.fjPath = fjPath;
		this.fjo = fjo;
		this.fjt = fjt;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPkId() {
		return this.pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getLx() {
		return this.lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getFjName() {
		return this.fjName;
	}

	public void setFjName(String fjName) {
		this.fjName = fjName;
	}

	public String getFjPath() {
		return this.fjPath;
	}

	public void setFjPath(String fjPath) {
		this.fjPath = fjPath;
	}

	public String getFjo() {
		return this.fjo;
	}

	public void setFjo(String fjo) {
		this.fjo = fjo;
	}

	public String getFjt() {
		return this.fjt;
	}

	public void setFjt(String fjt) {
		this.fjt = fjt;
	}

	public String toString() {
		System.out.printf("id=%s,tableName=%s,pkId=%s,lx=%s,orderNum=%s,fjName=%s,fjPath=%s,fjo=%s,fjt=%s,sfsx=%s",
				new Object[] { this.id, this.tableName, this.pkId, this.lx, this.orderNum, this.fjName, this.fjPath,
						this.fjo, this.fjt, this.sfsx });

		return super.toString();
	}
}