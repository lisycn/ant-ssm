package com.yuanlai.agent.interceptor;

import com.tangdi.def.base.redis.TdRedisService;
import com.tangdi.def.utils.common.TdCommUtil;
import com.tangdi.def.utils.common.TdJsonUtil;
import com.yuanlai.agent.common.AuthConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AuthSsoCasInterceptor implements HandlerInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(AuthSsoCasInterceptor.class);
	
	private String[] excluded = new String[]{};
	
	public void setExcluded(String excluded) {
		this.excluded = excluded.split(",");
	}

	@Autowired
	private TdRedisService tdRedisService;

	public boolean preHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj)
			throws Exception {
		String sp = httpservletrequest.getServletPath();
		for (int i = 0; i < excluded.length; i++) {
			if (sp.contains(excluded[i].replace("*", ""))) {
				return true;
			}
		}
		
		Map rsp = new HashMap();
		String token = httpservletrequest.getParameter(AuthConstants.COMMON_TOKEN);
		log.info("token="+token);
		if (token != null && !("").equals(token)) {
			Map map = (Map) tdRedisService.getObject(token);
			log.info("map="+map);
			if (map == null) {
				rsp = TdCommUtil.genErrorResult("登录超时，请重新登录");
				writeRsp(httpservletresponse, rsp);
				return false;
			}
			String usrStatus = String.valueOf(map.get(AuthConstants.USR_USRSTATUS));
			if(!AuthConstants.STRING_1.equals(usrStatus)){//1为启用
				rsp = TdCommUtil.genErrorResult("用户被禁用");
				writeRsp(httpservletresponse, rsp);
				return false;
			}
			String usrId = String.valueOf(map.get(AuthConstants.USR_USRID));
			Object oldTokenObj = tdRedisService.getObject(usrId);
			if(oldTokenObj == null){
				rsp = TdCommUtil.genErrorResult("登录超时，请重新登录");
				writeRsp(httpservletresponse, rsp);
				return false;
			}
			String oldTokenStr = oldTokenObj.toString();
			if(!token.equals(oldTokenStr)){
				rsp = TdCommUtil.genErrorResult("您已在其他地方登录，即将返回登录页面");
				writeRsp(httpservletresponse, rsp);
				return false;
			}
			httpservletrequest.setAttribute(AuthConstants.COMMON_USRINFO, map);
			log.info("sso校验成功");
			tdRedisService.saveObject(token, map, 30 * 60); //重新保存登录信息，相对于重设过期时间
			tdRedisService.saveObject(usrId, token, 30 * 60);
			//后台管理系统、门户商户、门户用户。需保证usrId唯一
			return true;
		} else {
			rsp = TdCommUtil.genErrorResult("未登录，请先登录");
			writeRsp(httpservletresponse, rsp);
			return false;
		}
	}
	
	public void writeRsp(HttpServletResponse response, Map map){
		response.setContentType("application/json;charset=UTF-8");
		map.put("rspCod", "_SSO_ERR");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(TdJsonUtil.jsonFromObject(map));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error("响应异常"+e.getMessage());
		}
	}

	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object obj, Exception exception) throws Exception {
		
	}
}
