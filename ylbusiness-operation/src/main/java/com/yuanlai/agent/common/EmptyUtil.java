package com.yuanlai.agent.common;

import java.util.Collection;
import java.util.Map;

/**
 * 空对象校验类
 * 
 * @author duxury
 * @version 1.0.0 2016-04-21
 *
 */
public class EmptyUtil {

	/**
	 * 是否为空检验
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		boolean result = false;
		if (null == obj) {
			result = true;
		} else {
			if ((obj instanceof String) && (((String) obj).length() == 0)) {
				result = true;
			} else if ((obj instanceof Collection<?>) && (((Collection<?>) obj).size() == 0)) {
				result = true;
			} else if ((obj instanceof Map<?, ?>) && (((Map<?, ?>) obj).size() == 0)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 是否不为空检验
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	/**
	 * 参数校验
	 * 
	 * @param params
	 * @param args
	 * @return
	 */
	public static String paramsJudge(Map<String, Object> params, String... args) {
		String result = null;
		if (args.length == 0) {
			return result;
		}

		if (isNull(params)) {
			return args[0];
		}

		for (int i = 0; i < args.length; i++) {
			if (isNull(params.get(args[i]))) {
				result = args[i];
				break;
			}
		}

		return result;
	}

	/**
	 * 字符串格式化
	 */
	public static String formatString(Object text) {
		return (text == null ? "" : text.toString().trim());
	}
	
}
