package com.yuanlai.agent.common;

import com.tangdi.def.utils.exception.TdRuntimeException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * @author chen_yq add 2016.04.27
 */
public class AuthCommonUtil {
	/**
	 * 处理分页的参数信息
	 * @param map
	 * @param pageSize每页记录数默认10条
	 * @param pageNum 默认页数第1页
	 * @return
	 */
	public static Map<String, Object> setPageParam(Map<String, Object> map,int pageSize, int pageNum){
		if(pageSize <=0 ){//设置默认条数
			pageSize = AuthConstants.INT_10;
		}
		if(pageNum <=0 ){//设置默认页数
			pageNum = AuthConstants.INT_1;
		}
		map.put(AuthConstants.COMMON_START, (pageNum-1)*pageSize);
		map.put(AuthConstants.COMMON_LIMIT, pageSize);
		return map;
	}
	/**
	 * 处理带分割符的字符串
	 * @param str 001,002,003
	 * @param Separator 分隔符
	 * @return List<String>
	 */
	public static List<String> handleIds(String str,String separator)throws TdRuntimeException{
		if(str==null || "".equals(str=str.trim())){
			throw new TdRuntimeException("str不能为空");
		}
		String[] ary = str.split(separator);
		return Arrays.asList(ary);
	}
	/**
	 * 处理带分割符的字符串
	 * @param str 001,002,003
	 * @param Separator 分隔符
	 * @param key str处理成list后，放入map的key
	 * @return
	 * @throws TdRuntimeException
	 */
	public static Map<String, Object> handleIdsForMap(String str,String separator,String key)throws TdRuntimeException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = handleIds(str,separator);
		map.put(key, list);
		return map;
	}
	/**
	 * 拼装成带分隔符的String
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String handleListForStr(List<String> list,String separator){
		StringBuilder sb = new StringBuilder();
		if(list==null || list.size()<=0){
			return sb.toString();
		}
		for(String s : list){
			sb.append(s).append(separator);
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	public static Map<String, Object> httpRequestToMap(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String key = params.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}
}
