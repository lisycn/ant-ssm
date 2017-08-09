package com.yuanlai.agent.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.tangdi.def.base.redis.TdRedisService;
import com.yuanlai.agent.entity.AgentInf;
import com.yuanlai.agent.service.NextAgentService;

/**
 * 
 * 
 * @author Color
 *
 */
@RestController
@RequestMapping("agent")
public class NextAgentController extends BaseController{

	public final Logger log = LoggerFactory.getLogger(NextAgentController.class);

	@Resource
	private NextAgentService nextAgentService;
	@Resource
	private TdRedisService tdRedisService;
	
	@RequestMapping(value = "nextAgent/nextAgentList")
	public Object queryNextAgentList(AgentInf agentInf) {
	    if (!StringUtils.isEmpty(agentInf.getToken())) {
			@SuppressWarnings("unchecked") Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(agentInf.getToken());
			agentInf.setOriginAgentId(usrInfo.get("agentId").toString());
		}else{
			genErrorResult("未获取到代理商id！");
		}
		//如果查询的是总代理商编号的话，那么进行子集代理商查询
		if(null !=agentInf.getAgentIdT() && !"".equals(agentInf.getAgentIdT())){
			String agentIdTotal = agentInf.getAgentIdT();
			agentInf.setAgentId(agentIdTotal);
			//这个参数标示left join传入真值，进行下级子代理商的消费查询
			agentInf.setAgentIdT(agentIdTotal);
		}else if(null !=agentInf.getAgentNameT() && !"".equals(agentInf.getAgentNameT())){
			//如果是根据总代理商名字查询的话，那么进行子代理商查询
			String agentNameTotal = agentInf.getAgentNameT();
			agentInf.setAgentName(agentNameTotal);
			//这个参数标示left join传入真值，进行下级子代理商的消费查询
			agentInf.setAgentNameT(agentNameTotal);
		}else if(null !=agentInf.getLogonNameT() && !"".equals(agentInf.getLogonNameT())){
			//如果是根据总代理商登录账号查询的话，那么进行子代理商查询
			String logonNameT = agentInf.getLogonNameT();
			agentInf.setLogonName(logonNameT);
			//这个参数标示left join传入真值，进行下级子代理商的消费查询
			agentInf.setLogonNameT(logonNameT);
		}else if(null !=agentInf.getAgentName() && !"".equals(agentInf.getAgentName())){
			//存在agentName的话，不查询下级代理商信息
			agentInf.setAgentIdT("xxxx_____xxxx");
		}else if(null !=agentInf.getAgentId() && !"".equals(agentInf.getAgentId())){
			//存在agentId的话，不查询下级代理商信息
			agentInf.setAgentIdT("xxxx_____xxxx");
		}else if(null !=agentInf.getLogonName() && !"".equals(agentInf.getLogonName())){
			//存在logonName的话，不查询下级代理商信息
			agentInf.setAgentIdT("xxxx_____xxxx");
		}else if(null !=agentInf.getAgentStatus() && !"".equals(agentInf.getAgentStatus())){
			if("2015000000".equals(agentInf.getOriginAgentId())){
				//不查询下级代理商信息
				agentInf.setAgentIdT("xxxx_____xxxx");
			}else{
				//查询下级代理商信息
				agentInf.setAgentId(agentInf.getOriginAgentId());
				agentInf.setAgentIdT(agentInf.getOriginAgentId());
			}
		}else if(null !=agentInf.getFrozState() && !"".equals(agentInf.getFrozState())){
			if("2015000000".equals(agentInf.getOriginAgentId())){
				//不查询下级代理商信息
				agentInf.setAgentIdT("xxxx_____xxxx");
			}else{
				//查询下级代理商信息
				agentInf.setAgentId(agentInf.getOriginAgentId());
				agentInf.setAgentIdT(agentInf.getOriginAgentId());
			}
		}else if(!"2015000000".equals(agentInf.getOriginAgentId())) {
			agentInf.setAgentId(agentInf.getOriginAgentId());
			//这个参数标示left join传入真值，进行下级子代理商的消费查询
			agentInf.setAgentIdT(agentInf.getOriginAgentId());
		}else{
			//如果为代理商用户，需要传入这个参数，否则的话，默认一个值，让left join查不到数据
			//这个常量不要随意修改，在mapper.xml中会用到
			agentInf.setAgentIdT("xxxx_____xxxx");
		}
		try {
			log.debug("查询参数:{}", agentInf.toString());
			PageInfo<AgentInf> list = nextAgentService.getPageList(agentInf);
			log.debug("返回结果:{}", list);
			return genSuccessResult(list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return genErrorResult("代理商查询错误！");
	}
}
