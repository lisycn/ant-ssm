package com.yuanlai.agent.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanlai.agent.dao.MerchandiseDao;
import com.yuanlai.agent.service.MerchandiseService;

@Service
public class MerchandiseServiceImpl implements MerchandiseService{
	
	@Autowired
	private MerchandiseDao dao;

	@Override
	public Map<String,Object> queryList(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		map.put("list", dao.queryList(param));
		return map;
	}

	@Override
	public int deleteEnerty(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return dao.deleteEnerty(param);
	}

	@Override
	public int insertEntity(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return dao.insertEntity(param);
	}

}
