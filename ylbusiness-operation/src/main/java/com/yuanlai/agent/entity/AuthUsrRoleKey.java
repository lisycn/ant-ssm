package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthUsrRoleKey implements Serializable {
    /**
     * 用户ID
     */
    private String usrId;

    /**
     * 角色ID
     */
    private String roleId;

    private static final long serialVersionUID = 1L;

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId == null ? null : usrId.trim();
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

}