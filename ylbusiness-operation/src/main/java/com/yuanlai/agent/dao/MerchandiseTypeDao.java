package com.yuanlai.agent.dao;




import java.util.List;
import java.util.Map;
import com.yuanlai.agent.entity.MerchandiseTypeInf;

public interface MerchandiseTypeDao extends BaseDao<MerchandiseTypeInf, String> {
	public int deleteEnertyById(MerchandiseTypeInf merchandiseTypeInf);
	public MerchandiseTypeInf selectEntity(MerchandiseTypeInf merchandiseTypeInf);
	public List<Map<String,Object>> allType(String param);
}