package com.yuanlai.agent.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回对象集合(dubbo接口使用该类，传入所有参数及其父类和属性均需实现序列化,否则会出错)
 * 
 * @author duxury
 * @version 1.0.0 2016-04-25
 */
public class RespInfo implements Serializable {

	/**
	 * 反序列化使用
	 */
	private static final long serialVersionUID = 1L;

	protected Map<String, Object> map = new HashMap<String, Object>();

	protected Map<String, Object> params = new HashMap<String, Object>();

	public RespInfo() {
		super();
		this.map.put("rspCod", "99999999");
	}

	public RespInfo(String rspCod) {
		this.map.put("rspCod", rspCod);
	}

	public RespInfo(String rspCod, String rspMsg) {
		this.map.put("rspCod", rspCod);
		this.map.put("rspMsg", rspMsg);
	}

	public RespInfo(String rspCod, Object obj) {
		this.map.put("rspCod", rspCod);
		this.params.put("OBJ", obj);
	}

	public RespInfo(String rspCod, String rspmsg, List<Map<String, Object>> resList) {
		this.map.put("rspCod", rspCod);
		this.params.put("DATA", resList);
	}

	public String getRspCode() {
		return (null != this.map.get("rspCod")) ? (String) this.map.get("rspCod") : null;
	}

	public void setRspCode(String rspCod) {
		this.map.put("rspCod", rspCod);
	}

	public String getRspMsg() {
		return (null != this.map.get("rspMsg")) ? (String) this.map.get("rspMsg") : null;
	}

	public void setRspMsg(String rspMsg) {
		this.map.put("rspMsg", rspMsg);
	}

	public void putVal(String key, Object val) {
		this.params.put(key, val);
	}

	public Object getVal(String key) {
		return this.params.get(key);
	}

	public Object getObj() {
		return this.params.get("OBJ");
	}

	public void setObj(Object obj) {
		this.params.put("OBJ", obj);
	}

	public Map<String, Object> getParamMap() {
		return new HashMap<String, Object>(params);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getResList() {
		List<Map<String, Object>> data = null;
		try {
			data = (List<Map<String, Object>>) this.params.get("DATA");
		} catch (Exception e) {
			return new ArrayList<Map<String, Object>>();
		}
		return data;
	}

	public void setResList(List<Map<String, Object>> resList) {
		this.params.put("DATA", resList);
	}

	public Map<String, Object> toMap() {
		this.map.put("rspData", this.params);
		return this.map;
	}

	public void putAll(Map<String, ?> map) {
		this.params.putAll(map);
	}

	public String toString() {
		return toMap().toString();
	}

}
