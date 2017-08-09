package com.yuanlai.agent.dao;

import java.util.List;
import java.util.Map;

public interface PubDownloadInfDao {
	
	/**
	 * 更新序列编号
	 * @param params
	 * @return 
	 * int
	 */
	public void updSeqSign(Map<String, Object> params);
	
	/**
	 * 查询本次需要下载的文件
	 * @param params
	 * @return 
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryNotCre(Map<String, Object> params);
	
	/**
	 * 查询订单列表
	 * @param params
	 * @return 
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> qryOrdMainInfList(Map<String, Object> params);
		
	/**
	 * 更新下载状态、文件路径、文件名称
	 * @param params
	 * @return 
	 * int
	 */
	public int updateFileStatus(Map<String, Object> params);
	
	/**
	 * 查询下载列表
	 * @param params
	 * @return 
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> qryDownloadList(Map<String, Object> params);
	
	/**
	 * 下载流文件
	 * @param params
	 * @return 
	 * Map<String,Object>
	 */
	public Map<String,Object> getStreamFile(Map<String, Object> params);
	public abstract int deleteByPrimaryKey(Object obj);

    public abstract int insert(Object obj);

    public abstract int insertSelective(Object obj);

    public abstract Object selectByPrimaryKey(Object obj);

    public abstract int updateByPrimaryKeySelective(Object obj);

    public abstract int updateByPrimaryKey(Object obj);

    public abstract List selectAll();

    public abstract List selectByPager(Map map);

    public abstract List selectByCondition(Map map);

    public abstract int countByCondition(Map map);
}