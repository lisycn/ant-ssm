package com.yuanlai.agent.entity;

import java.io.Serializable;

public class AuthItem implements Serializable {
    /**
     * 菜单ID
     */
    private String itmId;

    /**
     * 上级菜单ID
     */
    private String parentItmId;
    /**
     * 菜单等级
     */
    private String level;
    
    /**
     * 系统ID
     */
    private String sysId;

    /**
     * 菜单类型：1-菜单，2-按钮
     */
    private String itmTyp;
    
    /**
     * 是否是叶子节点：0-否,1-是
     */
    private String isLeaf;
    
    /**
     * 菜单名称
     */
    private String itmName;

    /**
     * 菜单描述
     */
    private String itmDesc;

    /**
     * 菜单URL
     */
    private String itmUrl;

    /**
     * 是否使用 0-禁用 1-启用
     */
    private String isUse;
    /**
     * 菜单图标
     */
    private String icon;
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

    
    
    public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getItmId() {
        return itmId;
    }

    public void setItmId(String itmId) {
        this.itmId = itmId == null ? null : itmId.trim();
    }

    public String getParentItmId() {
        return parentItmId;
    }

    public void setParentItmId(String parentItmId) {
        this.parentItmId = parentItmId == null ? null : parentItmId.trim();
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId == null ? null : sysId.trim();
    }

    public String getItmTyp() {
        return itmTyp;
    }

    public void setItmTyp(String itmTyp) {
        this.itmTyp = itmTyp == null ? null : itmTyp.trim();
    }

    
    
    public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf == null ? null : isLeaf.trim();
	}

	public String getItmName() {
        return itmName;
    }

    public void setItmName(String itmName) {
        this.itmName = itmName == null ? null : itmName.trim();
    }

    public String getItmDesc() {
        return itmDesc;
    }

    public void setItmDesc(String itmDesc) {
        this.itmDesc = itmDesc == null ? null : itmDesc.trim();
    }

    public String getItmUrl() {
        return itmUrl;
    }

    public void setItmUrl(String itmUrl) {
        this.itmUrl = itmUrl == null ? null : itmUrl.trim();
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
		return "AuthItem [itmId=" + itmId + ", parentItmId=" + parentItmId + ", sysId=" + sysId + ", itmTyp=" + itmTyp
				+ ", isLeaf=" + isLeaf + ", itmName=" + itmName + ", itmDesc=" + itmDesc + ", itmUrl=" + itmUrl
				+ ", isUse=" + isUse + ", creObj=" + creObj + ", creTim=" + creTim + ", updObj=" + updObj + ", updTim="
				+ updTim + "]";
	}
    
    
}