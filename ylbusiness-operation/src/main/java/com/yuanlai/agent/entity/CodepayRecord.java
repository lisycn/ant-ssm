package com.yuanlai.agent.entity;

public class CodepayRecord {


    private String payid;  //支付订单号

    private String account;  //账号

    private String amount;  //交易金额

    private String channelCode;  //渠道代码

    private String privatekey;  //秘钥

    private String qrcodeurl;  //二维码地址

    private String orderid;  //订单ID

    private String proid;  //扫码订单编号

    private String respcode;  //通道返回码

    private String respinfo;  //通道返回信息

    private String trancode;  //交易结果返回码

    private String traninfo;  //交易结果返回码描述

    private String channel;  //通道标识

    private String accesspart;  //接入方

    private String createtime;  //创建时间

    private String returnurl;  //回调地址

    private String transtatus;  //交易状态:01未上传 02未交易 03交易成功 04交易失败 05可疑

    private String thorderid;  //微信 支付宝 等

    private String accessid;  //下游ID

    private String fee;  //手续费

    private String paytime; //支付时间

    private String token;

    private String accesspartRole;  //控制权限用

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccesspartRole() {
        return accesspartRole;
    }

    public void setAccesspartRole(String accesspartRole) {
        this.accesspartRole = accesspartRole;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getRespinfo() {
        return respinfo;
    }

    public void setRespinfo(String respinfo) {
        this.respinfo = respinfo;
    }

    public String getTrancode() {
        return trancode;
    }

    public void setTrancode(String trancode) {
        this.trancode = trancode;
    }

    public String getTraninfo() {
        return traninfo;
    }

    public void setTraninfo(String traninfo) {
        this.traninfo = traninfo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccesspart() {
        return accesspart;
    }

    public void setAccesspart(String accesspart) {
        this.accesspart = accesspart;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReturnurl() {
        return returnurl;
    }

    public void setReturnurl(String returnurl) {
        this.returnurl = returnurl;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }

    public String getThorderid() {
        return thorderid;
    }

    public void setThorderid(String thorderid) {
        this.thorderid = thorderid;
    }

    public String getAccessid() {
        return accessid;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

}