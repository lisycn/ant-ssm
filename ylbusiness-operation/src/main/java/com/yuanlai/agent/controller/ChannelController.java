package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.tangdi.def.base.redis.TdRedisService;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.entity.*;
import com.yuanlai.agent.service.SourceService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名称：ChannelController
 * 类描述：查询数据控制层
 */
@SuppressWarnings("Duplicates")
@Controller
@RequestMapping("/channelController")
public class ChannelController extends TdBaseController {


	//注入服务层
	@Resource
	private SourceService sourceService;
	@Resource
	private TdRedisService tdRedisService;



	/**
	 * 分页查询回调记录
	 * @param callbackRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "callbackRecord/query")
	@ResponseBody
	public Map<String, Object> queryCallBackRecordList(CallbackRecord callbackRecord, int pageSize, int pageNum) throws Exception {
		//放入权限控制
		String orgId;
		String token = callbackRecord.getToken();
		if(null != token && !"".equals(token)){
			Map<String, Object> userMap = (Map<String, Object>) tdRedisService.getObject(token);
			orgId = userMap.get(AuthConstants.ORG_ORGID).toString();
			if(!orgId.equals("")){
				callbackRecord.setAccesspartRole(orgId);
			}
		}else{
			log.error("分页查询回调记录,获取token失败！");
			throw new Exception("分页查询回调记录,获取token失败！");
		}

		Map<String, Object> map = sourceService.getCallBackListPage(callbackRecord,pageSize, pageNum);
		return genSuccessResult(map);
	}


	/**
	 * 分页查询修改记录
	 * @param changerageRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changerateRecord/query")
	@ResponseBody
	public Map<String, Object> queryChangerateRecordList(ChangerageRecord changerageRecord, int pageSize, int pageNum) throws Exception {
		//放入权限控制
		String orgId;
		String token = changerageRecord.getToken();
		if(null != token && !"".equals(token)){
			Map<String, Object> userMap = (Map<String, Object>) tdRedisService.getObject(token);
			orgId = userMap.get(AuthConstants.ORG_ORGID).toString();
			if(!orgId.equals("")){
				changerageRecord.setAccesspartRole(orgId);
			}
		}else{
			log.error("分页查询回调记录,获取token失败！");
			throw new Exception("分页查询回调记录,获取token失败！");
		}
		Map<String, Object> map = sourceService.getChangerateListPage(changerageRecord, pageSize, pageNum);
		return genSuccessResult(map);
	}


	/**
	 * 分页查询一码付开通记录
	 * @param codepayRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "codepayRecord/query")
	@ResponseBody
	public Map<String, Object> queryCodepayRecordList(CodepayRecord codepayRecord, int pageSize, int pageNum) throws Exception {
		//放入权限控制
		String orgId;
		String token = codepayRecord.getToken();
		if(null != token && !"".equals(token)){
			Map<String, Object> userMap = (Map<String, Object>) tdRedisService.getObject(token);
			orgId = userMap.get(AuthConstants.ORG_ORGID).toString();
			if(!orgId.equals("")){
				codepayRecord.setAccesspartRole(orgId);
			}
		}else{
			log.error("分页查询回调记录,获取token失败！");
			throw new Exception("分页查询回调记录,获取token失败！");
		}
		Map<String, Object> map = sourceService.getCodepayListPage(codepayRecord, pageSize, pageNum);
		return genSuccessResult(map);
	}

	/**
	 * 分页密钥下载记录
	 * @param downloadkeysRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "downloadkeysRecord/query")
	@ResponseBody
	public Map<String, Object> queryDownloadkeysRecordList(DownloadkeysRecord downloadkeysRecord, int pageSize, int pageNum) throws Exception {
		//放入权限控制
		String orgId;
		String token = downloadkeysRecord.getToken();
		if(null != token && !"".equals(token)){
			Map<String, Object> userMap = (Map<String, Object>) tdRedisService.getObject(token);
			orgId = userMap.get(AuthConstants.ORG_ORGID).toString();
			if(!orgId.equals("")){
				downloadkeysRecord.setAccesspartRole(orgId);
			}
		}else{
			log.error("分页查询回调记录,获取token失败！");
			throw new Exception("分页查询回调记录,获取token失败！");
		}
		Map<String, Object> map = sourceService.getDownloadkeysListPage(downloadkeysRecord, pageSize, pageNum);
		return genSuccessResult(map);
	}


	/**
	 * 分页查询注册记录
	 * @param regesitorRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "regesitorRecord/query")
	@ResponseBody
	public Map<String, Object> queryRegesitorsRecordList(RegesitorRecord regesitorRecord, int pageSize, int pageNum) throws Exception {
		//放入权限控制
		String orgId;
		String token = regesitorRecord.getToken();
		if(null != token && !"".equals(token)){
			Map<String, Object> userMap = (Map<String, Object>) tdRedisService.getObject(token);
			orgId = userMap.get(AuthConstants.ORG_ORGID).toString();
			if(!orgId.equals("")){
				regesitorRecord.setAccesspartRole(orgId);
			}
		}else{
			log.error("分页查询回调记录,获取token失败！");
			throw new Exception("分页查询回调记录,获取token失败！");
		}
		Map<String, Object> map = sourceService.getRegesitorListPage(regesitorRecord, pageSize, pageNum);
		return genSuccessResult(map);
	}


	/**
	 * 分页查询验卡记录
	 * @param validcardRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validcardRecord/query")
	@ResponseBody
	public Map<String, Object> queryValidcardRecordList(ValidcardRecord validcardRecord, int pageSize, int pageNum) throws Exception {
		//放入权限控制
		String orgId;
		String token = validcardRecord.getToken();
		if(null != token && !"".equals(token)){
			Map<String, Object> userMap = (Map<String, Object>) tdRedisService.getObject(token);
			orgId = userMap.get(AuthConstants.ORG_ORGID).toString();
			if(!orgId.equals("")){
				validcardRecord.setAccesspartRole(orgId);
			}
		}else{
			log.error("分页查询回调记录,获取token失败！");
			throw new Exception("分页查询回调记录,获取token失败！");
		}
		Map<String, Object> map = sourceService.getValidcardListPage(validcardRecord, pageSize, pageNum);
		return genSuccessResult(map);
	}
	/**
	 * 商户手机号记录
	 * @param userinfoRecord
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userinfoRecord/query")
	@ResponseBody
	public Map<String, Object> queryUserInfoRecordList(UserInfo userinfo, int pageSize, int pageNum) throws Exception {

		Map<String, Object> map = sourceService.getUserInfoListPage(userinfo, pageSize, pageNum);
		return genSuccessResult(map);
	}

}
