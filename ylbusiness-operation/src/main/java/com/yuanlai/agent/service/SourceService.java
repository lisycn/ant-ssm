package com.yuanlai.agent.service;

import java.util.Map;

import com.yuanlai.agent.entity.*;




public interface SourceService {

	/**
	 * 分页查询回调记录
	 * 
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCallBackListPage(CallbackRecord callbackRecord, int pageSize, int pageNum) throws Exception;
	
	
	

	/**
	 * 分页查询修改费率记录
	 * 
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getChangerateListPage(ChangerageRecord changerageRecord, int pageSize, int pageNum) throws Exception;
	
	

	/**
	 * 分页查询一码付开通记录
	 * 
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCodepayListPage(CodepayRecord codepayRecord,  int pageSize, int pageNum) throws Exception;
	
	

	/**
	 * 分页查询密钥下载记录
	 * 
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getDownloadkeysListPage(DownloadkeysRecord downloadkeysRecord,int pageSize, int pageNum) throws Exception;
	

	/**
	 * 分页查询注册记录
	 * 
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getRegesitorListPage(RegesitorRecord regesitorRecord, int pageSize, int pageNum) throws Exception;
	

	/**
	 * 分页查询验卡记录
	 *
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getValidcardListPage(ValidcardRecord validcardRecord, int pageSize, int pageNum) throws Exception;

	/**
	 * 分页查询商户信息记录
	 *
	 * @param user
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getUserInfoListPage(UserInfo userinfo, int pageSize, int pageNum) throws Exception;

}
