package com.yuanlai.agent.service;

import java.util.Map;

/**
 * 下载中心接口
 * @version 1.0.0,2016年9月27日 
 * @author yaorp
 */
public interface IDownloadService {
	
	/**
	 * 预下载数据保存
	 * @param params
	 * @return 
	 * Map<String,Object>
	 */
	public Map<String,Object> preDownLoad(Map<String, Object> params);
	
	/**
	 * 查询下载列表
	 * @param params
	 * @return 
	 * RespInfo
	 */
	public Map<String,Object> qryDownloadList(Map<String, Object> params);

	/**
	 * 删除下载中心当前记录
	 * @param params
	 * @return 
	 * RespInfo
	 */
	public Map<String,Object> delCurrRecord(Map<String, Object> params);
	
	/**
	 * 下载流文件
	 * @param params
	 * @return 
	 * RespInfo
	 */
	public Map<String,Object> getStreamFile(Map<String, Object> params);
	
	/**
	 * 删除下载到本地服务器的附件
	 * @param sVersion
	 * @param params
	 * @return 
	 * RespInfo
	 */
	public Map<String,Object> delLocalFile(String sVersion, Map<String, Object> params);
}
