package com.yuanlai.agent.service;

import java.util.List;
import java.util.Map;

import com.yuanlai.agent.entity.MerchandiseTypeInf;
import com.yuanlai.agent.service.base.BaseService;

/**
 * 
 * @author limiao
 * @version 1.0
 *
 */
public interface MerchandiseTypeService extends BaseService<MerchandiseTypeInf, String> {
	public int deleteEnertyById(MerchandiseTypeInf merchandiseTypeInf);
	public MerchandiseTypeInf selectEntity(MerchandiseTypeInf merchandiseTypeInf);
	
}
