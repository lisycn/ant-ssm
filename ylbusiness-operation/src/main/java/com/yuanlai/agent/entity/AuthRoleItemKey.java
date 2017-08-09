package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthRoleItemKey implements Serializable {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String itmId;

    private static final long serialVersionUID = 1L;


    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

	public String getItmId() {
        return itmId;
    }

    public void setItmId(String itmId) {
        this.itmId = itmId == null ? null : itmId.trim();
    }
}