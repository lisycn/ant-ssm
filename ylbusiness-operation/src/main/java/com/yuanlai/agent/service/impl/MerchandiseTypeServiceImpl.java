package com.yuanlai.agent.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanlai.agent.dao.BaseDao;
import com.yuanlai.agent.dao.MerchandiseTypeDao;
import com.yuanlai.agent.entity.MerchandiseTypeInf;
import com.yuanlai.agent.service.MerchandiseTypeService;
import com.yuanlai.agent.service.base.AbstractService;




@Service
public class MerchandiseTypeServiceImpl extends AbstractService<MerchandiseTypeInf, String> implements MerchandiseTypeService{
	
	@Autowired
	private MerchandiseTypeDao dao;

	@Override
	public BaseDao<MerchandiseTypeInf, String> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	

	@Override
	public int deleteEnertyById(MerchandiseTypeInf merchandiseTypeInf) {
		// TODO Auto-generated method stub
		return dao.deleteEnertyById(merchandiseTypeInf);
	}



	@Override
	public MerchandiseTypeInf selectEntity(MerchandiseTypeInf merchandiseTypeInf) {
		// TODO Auto-generated method stub
		return dao.selectEntity(merchandiseTypeInf);
	}

	
}
