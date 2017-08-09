package com.yuanlai.agent.service;

import com.tangdi.def.utils.exception.TdRuntimeException;

import java.util.Map;

/**
 * @author chen_yq add 2016.04.27
 *
 */
public interface AuthSysInfService {
	/**
	 * 查询全部系统信息
	 * @return list:列表
	 * @throws Exception
	 */
	public Map<String, Object> selectAll()throws TdRuntimeException;
	/**
	 * 按照sysId查询系统信息
	 * @param sysId
	 * @return authSysInf
	 * @throws Exception
	 */
	public Map<String, Object> selectBySysId(String sysId)throws TdRuntimeException;

}
