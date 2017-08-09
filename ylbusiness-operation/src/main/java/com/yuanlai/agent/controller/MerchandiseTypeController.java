package com.yuanlai.agent.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.tangdi.def.base.redis.TdRedisService;
import com.tangdi.def.utils.common.*;
import com.yuanlai.agent.entity.AgentInf;
import com.yuanlai.agent.entity.MerchandiseTypeInf;
import com.yuanlai.agent.service.MerchandiseTypeService;



/**
 * 公告
 * 
 * @author Color
 *
 */
@RestController
@RequestMapping("business")
public class MerchandiseTypeController extends BaseController{

	public final Logger log = LoggerFactory.getLogger(MerchandiseTypeController.class);

	@Resource
	private MerchandiseTypeService merchandiseTypeService ;
	@Resource
	private TdRedisService tdRedisService;
	
	@RequestMapping(value = "merchandiseTypeList")
	public Object queryNoticeList(MerchandiseTypeInf merchandiseTypeInf,AgentInf agentInf) {
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		merchandiseTypeInf.setBusinessId(businessId);
		try {
			log.debug("查询参数:{}", merchandiseTypeInf.toString());
			PageInfo<MerchandiseTypeInf> list = merchandiseTypeService.getPageList(merchandiseTypeInf);
			log.debug("返回结果:{}", list);
			return genSuccessResult(list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return genErrorResult("操作失败！");
	}
	
	@RequestMapping(value = "addMerchandiseType")
	@ResponseBody
	public Object addNotice(MerchandiseTypeInf merchandiseTypeInf,AgentInf agentInf) {
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		merchandiseTypeInf.setBusinessId(businessId);
		try {
				merchandiseTypeInf.setMerchandiseId(IdWorker.getWorkerId());
				merchandiseTypeService.addEntity(merchandiseTypeInf);
				log.info("新增成功");
				return genSuccessResult("新增成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return genErrorResult("添加异常");
	}
	@RequestMapping(value = "deleteMerchandiseType")
	@ResponseBody
	public Object deleteNotice(String [] merchandiseId,HttpServletRequest request,AgentInf agentInf) throws Exception {
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();	
		try {
			for (String string : merchandiseId) {
				MerchandiseTypeInf merchandiseTypeInf=new MerchandiseTypeInf();
				merchandiseTypeInf.setBusinessId(businessId);
				merchandiseTypeInf.setMerchandiseId(string);
				merchandiseTypeService.deleteEnertyById(merchandiseTypeInf);
			}
		} catch (Exception e) {
			log.error("删除失败：" + e.getMessage());
			return genErrorResult("删除失败:" + e.getMessage());
		}
		log.info("删除成功");
		return genSuccessResult("删除成功！");
	}
	
	@RequestMapping(value = "queryMerchandiseTypeById")
	@ResponseBody
	public Object queryNoticeById(String merchandiseId,AgentInf agentInf) throws Exception {
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		MerchandiseTypeInf merchandiseType=new MerchandiseTypeInf();
		merchandiseType.setBusinessId(businessId);
		merchandiseType.setMerchandiseId(merchandiseId);
		try{
			log.info("查询详情的参数为："+ merchandiseId);
            MerchandiseTypeInf merchandiseTypeInf  =  merchandiseTypeService.selectEntity(merchandiseType);            
			log.info("返回的参数为{}："+ merchandiseTypeInf);
			return genSuccessResult(merchandiseTypeInf);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询信息失败！"+e.getMessage());
		}
		return genErrorResult("查询信息失败！");
	}
	
	@RequestMapping(value = "updateMerchandiseById")
	@ResponseBody
	public Object updateNoticeById(MerchandiseTypeInf merchandiseTypeInf,AgentInf agentInf) throws Exception {
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		merchandiseTypeInf.setBusinessId(businessId);
		try{
			log.info("修改的参数为{}："+ merchandiseTypeInf);
			merchandiseTypeService.modifyEntity(merchandiseTypeInf);
			return genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改失败！"+e.getMessage());
		}
		return genErrorResult("修改失败！");
	}	
	
}
