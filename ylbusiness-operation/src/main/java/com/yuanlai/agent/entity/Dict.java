package com.yuanlai.agent.entity;

import java.io.Serializable;

public class Dict implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String dict_id;
	private String parent_id;
	private String dict_code;
	private String dict_value;
	private String dict_name;
	private String abr;
	private String hlp;
	private String seq_num;
	private String dict_level;
	private String status;
	private String statusCh;
	public String getDict_id() {
		return dict_id;
	}
	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getDict_code() {
		return dict_code;
	}
	public void setDict_code(String dict_code) {
		this.dict_code = dict_code;
	}
	public String getDict_value() {
		return dict_value;
	}
	public void setDict_value(String dict_value) {
		this.dict_value = dict_value;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public String getAbr() {
		return abr;
	}
	public void setAbr(String abr) {
		this.abr = abr;
	}
	public String getHlp() {
		return hlp;
	}
	public void setHlp(String hlp) {
		this.hlp = hlp;
	}
	public String getSeq_num() {
		return seq_num;
	}
	public void setSeq_num(String seq_num) {
		this.seq_num = seq_num;
	}
	public String getDict_level() {
		return dict_level;
	}
	public void setDict_level(String dict_level) {
		this.dict_level = dict_level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusCh() {
		return statusCh;
	}
	public void setStatusCh(String statusCh) {
		this.statusCh = statusCh;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}