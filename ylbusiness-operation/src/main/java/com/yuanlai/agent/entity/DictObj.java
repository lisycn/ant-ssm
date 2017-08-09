package com.yuanlai.agent.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class DictObj implements Serializable {
	private Map<String, String> attrMap = new HashMap<String, String>();
	private List<DictObj> childList = new ArrayList<DictObj>();

	// Set attribute value
	public void setAttrMap(String sKey, String sVal) {
		attrMap.put(sKey, sVal);
	}

	// Get attribute value
	public String getAttrMap(String sKey) {
		return attrMap.get(sKey);
	}

	// Get attr's Map
	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public List<DictObj> getChildList() {
		return childList;
	}

	public void setChildList(List<DictObj> childList) {
		this.childList = childList;
	}

	// Add child CodeObj
	public void setChild(DictObj co) {
		childList.add(co);
	}

	@Override
	public String toString() {
		return "属性：" + attrMap.toString() + " 子节点数量：" + childList.size();
	}
}
