package com.yuanlai.agent.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public interface MerchandiseService {
	public Map<String, Object> queryList(Map<String, Object> param) throws Exception;
	public int deleteEnerty(Map<String, Object> param) throws Exception;
	public int insertEntity(Map<String, Object> param) throws Exception;
}
