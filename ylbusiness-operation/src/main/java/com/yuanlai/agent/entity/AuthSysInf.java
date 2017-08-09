package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthSysInf implements Serializable {
    /**
     * 系统ID eg:001-互联网支付 002-收单 003-手刷
     */
    private String sysId;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统url
     */
    private String sysUrl;

    /**
     * 系统描述
     */
    private String sysDesc;

    private static final long serialVersionUID = 1L;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId == null ? null : sysId.trim();
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    public String getSysUrl() {
        return sysUrl;
    }

    public void setSysUrl(String sysUrl) {
        this.sysUrl = sysUrl == null ? null : sysUrl.trim();
    }

    public String getSysDesc() {
        return sysDesc;
    }

    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc == null ? null : sysDesc.trim();
    }
}