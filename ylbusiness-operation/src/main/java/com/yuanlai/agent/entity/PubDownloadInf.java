package com.yuanlai.agent.entity;

import java.io.Serializable;

public class PubDownloadInf implements Serializable {
    private String id;

    private String sysId;

    private String exportType;

    private String exportParam;

    private String fileName;

    private String filePath;

    private String authObject;

    private String authObjectId;

    private String fileStatus;

    private String isDel;

    private String saveDays;

    private String createO;

    private String createT;

    private String exportSeqsign;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId == null ? null : sysId.trim();
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType == null ? null : exportType.trim();
    }

    public String getExportParam() {
        return exportParam;
    }

    public void setExportParam(String exportParam) {
        this.exportParam = exportParam == null ? null : exportParam.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getAuthObject() {
        return authObject;
    }

    public void setAuthObject(String authObject) {
        this.authObject = authObject == null ? null : authObject.trim();
    }

    public String getAuthObjectId() {
        return authObjectId;
    }

    public void setAuthObjectId(String authObjectId) {
        this.authObjectId = authObjectId == null ? null : authObjectId.trim();
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus == null ? null : fileStatus.trim();
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }

    public String getSaveDays() {
        return saveDays;
    }

    public void setSaveDays(String saveDays) {
        this.saveDays = saveDays == null ? null : saveDays.trim();
    }

    public String getCreateO() {
        return createO;
    }

    public void setCreateO(String createO) {
        this.createO = createO == null ? null : createO.trim();
    }

    public String getCreateT() {
        return createT;
    }

    public void setCreateT(String createT) {
        this.createT = createT == null ? null : createT.trim();
    }

    public String getExportSeqsign() {
        return exportSeqsign;
    }

    public void setExportSeqsign(String exportSeqsign) {
        this.exportSeqsign = exportSeqsign == null ? null : exportSeqsign.trim();
    }
}