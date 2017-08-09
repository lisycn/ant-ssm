package com.yuanlai.agent.service;

import java.util.List;
import java.util.Map;


public interface IFileDownloadService {
	
	/**
	 * 文件下载接口
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> download(Map<String, Object> param);

}
