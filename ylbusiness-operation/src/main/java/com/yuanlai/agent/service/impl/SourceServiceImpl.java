package com.yuanlai.agent.service.impl;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.AuthCommonUtil;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.dao.*;
import com.yuanlai.agent.entity.*;
import com.yuanlai.agent.service.SourceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("Duplicates")
@Service
public class SourceServiceImpl implements SourceService {
	
	@Resource
	private CallbackRecordDao callbackRecordDao;
	
	@Resource
	private ChangerateRecordDao changerateRecordDao;
	
	@Resource
	private CodepayRecordDao codepayRecordDao;
	
	@Resource
	private DownloadkeysRecordDao downloadkeysRecordDao;
	
	@Resource
	private RegesitorRecordDao regesitorRecordDao;
	
	@Resource
	private ValidcardRecordDao validcardRecordDao;

	@Resource
	private UserInfoRecordDao userinfoRecordDao;


	private static Logger log = LoggerFactory.getLogger(SourceServiceImpl.class);

	
	
	/**
	 * 分页查询回调记录
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCallBackListPage(CallbackRecord callBackRecord, int pageSize, int pageNum) {
		log.info("分页查询回调记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(callBackRecord);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = callbackRecordDao.selectByPager(paraMap);
			int total = callbackRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}
	}

	
	/**
	 * 分页查询修改费率记录
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getChangerateListPage(ChangerageRecord changerageRecord, int pageSize, int pageNum) throws Exception {
		log.info("分页查询修改费率记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(changerageRecord);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = changerateRecordDao.selectByPager(paraMap);
			int total = changerateRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}
	}
	
	


	/**
	 * 分页查询一码付开通记录
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCodepayListPage(CodepayRecord codepayRecord, int pageSize, int pageNum) throws Exception {
		log.info("分页查询一码付开通记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(codepayRecord);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = codepayRecordDao.selectByPager(paraMap);
			int total = codepayRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}
	}
	
	

	/**
	 * 分页查询密钥下载记录
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDownloadkeysListPage(DownloadkeysRecord downloadkeysRecord, int pageSize, int pageNum) throws Exception {
		log.info("分页查询下载密钥记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(downloadkeysRecord);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = downloadkeysRecordDao.selectByPager(paraMap);
			int total = downloadkeysRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}



	}
	
	

	/**
	 * 分页查询注册记录
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getRegesitorListPage(RegesitorRecord regesitorRecord, int pageSize, int pageNum) throws Exception {
		log.info("分页查询注册记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(regesitorRecord);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = regesitorRecordDao.selectByPager(paraMap);
			int total = regesitorRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}


	}
	
	

	/**
	 * 分页查询验卡记录
	 * 
	 * @param Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getValidcardListPage(ValidcardRecord validcardRecord, int pageSize, int pageNum) throws Exception {
		log.info("分页查询验卡记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(validcardRecord);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = validcardRecordDao.selectByPager(paraMap);
			int total = validcardRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}
	}

	/**
	 * 商户记录查询信息
	 */
	public Map<String, Object> getUserInfoListPage(UserInfo userinfo, int pageSize, int pageNum) throws Exception{
		log.info("分页查询商户记录");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(userinfo);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		try {
			log.info("paraMap:" + paraMap.toString());
			Map<String, Object> data = new HashMap<>();
			List<Map<String, Object>> retList = userinfoRecordDao.selectByPager(paraMap);
			int total = userinfoRecordDao.countByCondition(paraMap);
			data.put(AuthConstants.COMMON_LIST, retList);
			data.put(AuthConstants.COMMON_TOTAL, total);
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new TdRuntimeException("数据库错误");
		}
	}

}
