package com.yuanlai.agent.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域访问通用拦截器
 * @author li.sy
 * 2016-04:25 22:18:12
 */
public class AllowOriginIntecepror implements HandlerInterceptor {
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*"); // 允许跨域的url
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"); // 允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,accept,rsp-auth,req-auth,request-tk"); // 允许跨域的请求头
		response.addHeader("Access-Control-Max-Age", "3600");
		if("OPTIONS".equals(request.getMethod())){
			return false;
		}
		return true;
	}
}