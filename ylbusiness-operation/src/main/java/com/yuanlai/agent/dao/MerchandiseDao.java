package com.yuanlai.agent.dao;

import java.util.List;
import java.util.Map;

public interface MerchandiseDao {
	public List<Map<String, Object>> queryList(Map<String, Object> param) throws Exception;
	public int deleteEnerty(Map<String, Object> param) throws Exception;
	public int insertEntity(Map<String, Object> param) throws Exception;
}