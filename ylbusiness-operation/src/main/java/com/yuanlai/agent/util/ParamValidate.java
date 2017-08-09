package com.yuanlai.agent.util;


import java.util.HashMap;
import java.util.Map;

/**
 * Map中的必需字段效验
 * 
 * @author zhengqiang
 * 
 */

public class ParamValidate {

	public static Map<String,Object> doing(Map<String, Object> param, String... keys) {
		Map<String, Object> returnMap = new HashMap<>();
		String result = "1";
		String msgError = "必填字段效验不通过";
		for (String key : keys) {
			if (!param.containsKey(key) || param.get(key) == null || param.get(key).toString().trim().equals("")) {
				result = "-1";
				msgError += ":"+key+";";
			}
		}
		if ("1".equals(result)) {
			//如果校验成功的话
			returnMap.put("result", result);
			returnMap.put("msg", "校验成功!");
		} else {
			//返回失败信息
			returnMap.put("result", result);
			returnMap.put("msg", msgError);
		}
		return returnMap;
	}
}
