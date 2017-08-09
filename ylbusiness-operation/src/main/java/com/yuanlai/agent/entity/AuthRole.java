package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthRole implements Serializable {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 系统ID
     */
    private String sysId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 是否使用 0-禁用 1-启用
     */
    private String isUse;

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


    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

	public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId == null ? null : sysId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse == null ? null : isUse.trim();
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
		return "AuthRole [roleId=" + roleId + ", sysId=" + sysId + ", roleName=" + roleName + ", roleDesc=" + roleDesc
				+ ", isUse=" + isUse + ", creObj=" + creObj + ", creTim=" + creTim + ", updObj=" + updObj + ", updTim="
				+ updTim + "]";
	}

    
}