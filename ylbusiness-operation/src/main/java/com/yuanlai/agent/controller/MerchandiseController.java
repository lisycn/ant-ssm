package com.yuanlai.agent.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tangdi.def.base.redis.TdRedisService;
import com.tangdi.def.utils.common.IdWorker;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.TdCommonUtil;
import com.yuanlai.agent.entity.AgentInf;
import com.yuanlai.agent.entity.MerchandiseInf;
import com.yuanlai.agent.service.MerchandiseService;
import com.yuanlai.agent.service.MerchandiseTypeService;
import com.yuanlai.agent.util.DateUtils;

/**
 * 
 * 
 * @author Color
 *
 */
@RestController
@RequestMapping("business")
public class MerchandiseController extends BaseController{

	public final Logger log = LoggerFactory.getLogger(MerchandiseController.class);

	@Resource
	private MerchandiseTypeService merchandiseTypeService ;
	@Resource
	private TdRedisService tdRedisService;
	@Resource
	private MerchandiseService merchandiseService;
	
	@RequestMapping(value="queryList")
	public Object queryPrizeList(MerchandiseInf merchandiseInf,AgentInf agentInf){
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		merchandiseInf.setBusinessId(businessId);
		try {
			Map<String, Object> map = AuthBeanUtil.toMap(merchandiseInf);
			Map<String, Object> resultMap = merchandiseService.queryList(map);
			log.info("查询列表返回数据："+ resultMap);
			return genSuccessResult(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表错误！"+e.getMessage());
		}
		return genErrorResult("查询错误");
	}
	
	@RequestMapping(value="deleteEnerty")
	@ResponseBody
	public Object deleteEnerty(String [] merchandiseId,AgentInf agentInf){
		log.info("传递的参数为{}"+merchandiseId);
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		
		try {
			for (String string : merchandiseId) {
				Map<String, Object> map=new HashMap<>();
				map.put("businessId", businessId);
				map.put("merchandiseId", string);	
				merchandiseService.deleteEnerty(map);
			}
			return genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除失败"+e.getMessage());
		}
		return genErrorResult("删除失败！");
	}
	@RequestMapping(value="insertEntity")
	@ResponseBody
	public Object insertEntity(HttpServletRequest request,AgentInf agentInf){
		Map<String, Object> parameterMap = TdCommonUtil.getParameterMap(request);
		log.info("传递的参数为{}"+parameterMap);
		@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
		String businessId=usrInfo.get("usrName").toString();
		parameterMap.put("businessId", businessId);
		parameterMap.put("createTime", DateUtils.GETDATE());
		parameterMap.put("merchandiseId", IdWorker.getWorkerId());
		try {
			merchandiseService.insertEntity(parameterMap);
			return genSuccessResult("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加失败"+e.getMessage());
		}
		return genErrorResult("添加失败！");
	}
	
}
