package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthUsr implements Serializable {
    /**
     * 用户ID
     */
    private String usrId;

    /**
     * 所属机构ID
     */
    private String orgId;

    /**
     * 用户名
     */
    private String usrName;

    /**
     * 密码
     */
    private String usrPsw;

    /**
     * 真实姓名
     */
    private String usrRealName;

    /**
     * 用户描述
     */
    private String usrDesc;

    /**
     * 用户状态：0-禁用，1-启用
     */
    private String usrStatus;

    /**
     * 用户上次登录成功时间
     */
    private String lastLoginTime;

    /**
     * 用户登录失败次数
     */
    private String failLoginTimes;

    /**
     * 创建人
     */
    private String creObj;

    /**
     * 创建时间
     */
    private String creTim;

    /**
     * 更新人
     */
    private String updObj;

    /**
     * 更新时间
     */
    private String updTim;

    private static final long serialVersionUID = 1L;


    public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId == null ? null : usrId.trim();
	}

	public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName == null ? null : usrName.trim();
    }

    public String getUsrPsw() {
        return usrPsw;
    }

    public void setUsrPsw(String usrPsw) {
        this.usrPsw = usrPsw == null ? null : usrPsw.trim();
    }

    public String getUsrRealName() {
        return usrRealName;
    }

    public void setUsrRealName(String usrRealName) {
        this.usrRealName = usrRealName == null ? null : usrRealName.trim();
    }

    public String getUsrDesc() {
        return usrDesc;
    }

    public void setUsrDesc(String usrDesc) {
        this.usrDesc = usrDesc == null ? null : usrDesc.trim();
    }

    public String getUsrStatus() {
        return usrStatus;
    }

    public void setUsrStatus(String usrStatus) {
        this.usrStatus = usrStatus == null ? null : usrStatus.trim();
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime == null ? null : lastLoginTime.trim();
    }

    public String getFailLoginTimes() {
        return failLoginTimes;
    }

    public void setFailLoginTimes(String failLoginTimes) {
        this.failLoginTimes = failLoginTimes == null ? null : failLoginTimes.trim();
    }

    public String getCreObj() {
        return creObj;
    }

    public void setCreObj(String creObj) {
        this.creObj = creObj == null ? null : creObj.trim();
    }

    public String getCreTim() {
        return creTim;
    }

    public void setCreTim(String creTim) {
        this.creTim = creTim == null ? null : creTim.trim();
    }

    public String getUpdObj() {
        return updObj;
    }

    public void setUpdObj(String updObj) {
        this.updObj = updObj == null ? null : updObj.trim();
    }

    public String getUpdTim() {
        return updTim;
    }

    public void setUpdTim(String updTim) {
        this.updTim = updTim == null ? null : updTim.trim();
    }

	@Override
	public String toString() {
		return "AuthUsr [usrId=" + usrId + ", orgId=" + orgId + ", usrName=" + usrName + ", usrPsw=" + usrPsw
				+ ", usrRealName=" + usrRealName + ", usrDesc=" + usrDesc + ", usrStatus=" + usrStatus
				+ ", lastLoginTime=" + lastLoginTime + ", failLoginTimes=" + failLoginTimes + ", creObj=" + creObj
				+ ", creTim=" + creTim + ", updObj=" + updObj + ", updTim=" + updTim + "]";
	}
    
}