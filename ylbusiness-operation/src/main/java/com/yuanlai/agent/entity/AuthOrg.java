package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthOrg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 上级机构ID
     */
    private String parentOrgId;

    /**
     * 机构名
     */
    private String orgName;

    /**
     * 机构描述
     */
    private String orgDesc;
    
    /**
     * 支付宝费率
     */
    private String aliRate;
    
    /**
     * 微信费率
     */
    private String wxRate;
    
    /**
     * 回调地址
     */
    private String returnUrl;

    /**
     * 是否使用
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

    

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId == null ? null : parentOrgId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc == null ? null : orgDesc.trim();
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

	public String getAliRate() {
		return aliRate;
	}

	public void setAliRate(String aliRate) {
		this.aliRate = aliRate;
	}

	public String getWxRate() {
		return wxRate;
	}

	public void setWxRate(String wxRate) {
		this.wxRate = wxRate;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Override
	public String toString() {
		return "AuthOrg [orgId=" + orgId + ", parentOrgId=" + parentOrgId + ", orgName=" + orgName + ", orgDesc="
				+ orgDesc + ", aliRate=" + aliRate + ", wxRate=" + wxRate + ", returnUrl=" + returnUrl + ", isUse="
				+ isUse + ", creObj=" + creObj + ", creTim=" + creTim + ", updObj=" + updObj + ", updTim=" + updTim
				+ "]";
	}
    
}