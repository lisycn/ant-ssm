package com.yuanlai.agent.service;

import org.springframework.web.multipart.MultipartFile;

import com.yuanlai.agent.entity.PubAttachment;

import java.util.List;
import java.util.Map;


public abstract interface IPubAttachmentService{
	public abstract int updateNewAttachment(Map<String, String> paramMap);
  
	public abstract int updateNewPkId(Map<String, String> paramMap);

	public abstract int updateHasSaveAttachment(PubAttachment paramAttachment);

	public abstract List<PubAttachment> getList(Map<String, String> paramMap);

	public abstract Map<String, String> queryAttachmentMap(Map<String, String> paramMap);
	public abstract Map<String, Object> updAttachById(Map<String, Object> paramMap) throws Exception;

	public abstract void saveFile(MultipartFile paramMultipartFile, String paramString1, String paramString2, PubAttachment paramAttachment, String sFileType, String prefixPicture)
		throws Exception;

	public abstract void saveFile(MultipartFile paramMultipartFile, String paramString1, String paramString2, PubAttachment paramAttachment)
			throws Exception;
	/**
	 * 附件展示用
	 * @param PKID,TABLENAME
	 * @return
	 */
	public abstract Map<String, Object> queryAttachMap(Map<String, String> paramMap) throws Exception;
	//查询条件可扩展
	public abstract Map<String, Object> queryAttachMapNew(Map<String, Object> paramMap) throws Exception;
  
	/**
	 * 清除无效的附件
	 * @param params
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 */
	public Map<String,Object> clearInvalidAttach(String sVersion, Map<String, Object> params) throws Exception;

    public abstract int updateHasSaveEntity(PubAttachment paramAttachment) throws Exception;
	public abstract int insertEntity(PubAttachment paramAttachment) throws Exception;
	
	public abstract String saveAttachmentToFast(String sFjPath)throws Exception;
	
	public abstract String getAttachmentFromFast(String sFastId, String sRelativePath)throws Exception;
	
	/**
	 * 获取商户证书
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public abstract String getAttFromFastOfCert(String sFastId, String sRelativePath)throws Exception;
	
	/**
	 * 查询excel附件信息
	 * @param sVersion
	 * @param params
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 */
	public Map<String,Object> qryExcelAttach(String sVersion, Map<String, Object> params) throws Exception;
	
	/**
	 * 根据ID修改附件记录
	 * @param sVersion
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateAttachStsById(String sVersion, Map<String, Object> params)throws Exception;
}
