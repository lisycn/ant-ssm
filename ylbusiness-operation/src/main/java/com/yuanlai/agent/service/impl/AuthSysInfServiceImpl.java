package com.yuanlai.agent.service.impl;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.dao.AuthSysInfDao;
import com.yuanlai.agent.entity.AuthSysInf;
import com.yuanlai.agent.service.AuthSysInfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen_yq add 2016.04.27
 *
 */
@Service
public class AuthSysInfServiceImpl implements AuthSysInfService {

	@Autowired
	private AuthSysInfDao authSysInfDao;
	@Override
	public Map<String, Object> selectAll() throws TdRuntimeException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AuthSysInf> list = authSysInfDao.selectAll();
		if(list==null || list.size()<=0){
			throw new TdRuntimeException("未查询到记录");
		}
		map.put(AuthConstants.COMMON_LIST, list);
		return map;
	}
	@Override
	public Map<String, Object> selectBySysId(String sysId) throws TdRuntimeException {
		Map<String, Object> map = new HashMap<String, Object>();
		AuthSysInf sysInf = authSysInfDao.selectByPrimaryKey(sysId);
		if(sysInf == null){
			throw new TdRuntimeException("未查询到 "+sysId+" 的信息");
		}
		map.put(AuthConstants.AUTHSYSINF, sysInf);
		return map;
	}
}
