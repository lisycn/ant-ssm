package com.yuanlai.agent.entity;

import java.util.Date;

import com.yuanlai.agent.model.QueryModel;

public class CodePayReport extends QueryModel{
    private Integer reportId;

    private String accessPart;

    private Date transTime;

    private String channelCode;

    private Double amount;

    private Double fee;

    private Double hfFee;

    private Double sourceProfit;

    private Double accessProfit;

    private Date reportTime;
    
    private String orderId;
    
    private String account;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getAccessPart() {
        return accessPart;
    }

    public void setAccessPart(String accessPart) {
        this.accessPart = accessPart;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getHfFee() {
        return hfFee;
    }

    public void setHfFee(Double hfFee) {
        this.hfFee = hfFee;
    }

    public Double getSourceProfit() {
        return sourceProfit;
    }

    public void setSourceProfit(Double sourceProfit) {
        this.sourceProfit = sourceProfit;
    }

    public Double getAccessProfit() {
        return accessProfit;
    }

    public void setAccessProfit(Double accessProfit) {
        this.accessProfit = accessProfit;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}