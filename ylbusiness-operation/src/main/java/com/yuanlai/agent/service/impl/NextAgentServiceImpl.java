package com.yuanlai.agent.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanlai.agent.dao.BaseDao;
import com.yuanlai.agent.dao.NextAgentDao;
import com.yuanlai.agent.entity.AgentInf;
import com.yuanlai.agent.service.NextAgentService;
import com.yuanlai.agent.service.base.AbstractService;




@Service
public class NextAgentServiceImpl extends AbstractService<AgentInf, String> implements NextAgentService{
	
	@Autowired
	private NextAgentDao dao;

	@Override
	public BaseDao<AgentInf, String> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
}
