package com.yuanlai.agent.common;

import com.tangdi.def.utils.common.TdNumberUtil;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class TdCommonUtil {
	private static int NUMBERPAGE = 10;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> getReqMap(HttpServletRequest request) {
		Map resMap = new HashMap();
		Enumeration enumList = request.getParameterNames();
		String sName = null;
		String sVal = null;
		while (enumList.hasMoreElements()) {
			sName = (String) enumList.nextElement();
			sVal = request.getParameter(sName);
			resMap.put(sName, sVal);
			resMap.put(sName.toUpperCase(), sVal);
		}
		return resMap;
	}

	public static int getNumStart(Map<String, String> reqMap, int iTotal) {
		int iPageNum = 1;
		int iNumPerPage = NUMBERPAGE;

		String sTemp = null;
		if (null != (sTemp = (String) reqMap.get("PAGENUM"))) {
			iPageNum = Integer.parseInt(sTemp);
		}
		if (null != (sTemp = (String) reqMap.get("NUMPERPAGE"))) {
			iNumPerPage = Integer.parseInt(sTemp);
		}

		int iRes = 0;
		iRes = (iPageNum - 1) * iNumPerPage + 1;
		if (iRes > iTotal) {
			iRes = iTotal;
		}
		return iRes;
	}

	public static int getNumEnd(Map<String, String> reqMap, int iTotal) {
		int iPageNum = 1;
		int iNumPerPage = NUMBERPAGE;

		String sTemp = null;
		if (null != (sTemp = (String) reqMap.get("PAGENUM"))) {
			iPageNum = Integer.parseInt(sTemp);
		}
		if (null != (sTemp = (String) reqMap.get("NUMPERPAGE"))) {
			iNumPerPage = Integer.parseInt(sTemp);
		}

		int iRes = 0;
		iRes = iPageNum * iNumPerPage;
		if (iRes > iTotal) {
			iRes = iTotal;
		}
		return iRes;
	}

	public static Map<String, String> setNumStart(Map<String, String> reqMap, int iTotal) {
		reqMap.put("startRow", getNumStart(reqMap, iTotal) + "");
		return reqMap;
	}

	public static Map<String, String> setNumEnd(Map<String, String> reqMap, int iTotal) {
		reqMap.put("endRow", getNumEnd(reqMap, iTotal) + "");
		return reqMap;
	}

	public static String getSeqId(String sSeqId) {
		String sRes = sdf.format(new Date());
		sRes = sRes + getRandom(6);
		return sRes;
	}

	public static String getRandom(int iLen) {
		Random random = new Random();
		long lNum = random.nextLong();
		String sRes = lNum + "";
		sRes = sRes.replaceAll("-", "");
		if (sRes.length() < iLen) {
			for (int i = 1; i <= iLen; ++i) {
				sRes = sRes + sRes;
			}
		}
		sRes = sRes.substring(0, iLen);
		return sRes;
	}

	public static String getDateTime() {
		return sdf.format(new Date()).substring(0, 14);
	}

	public static String getRelPath(String sStr) {
		String sRes = "";
		String sWebapps = File.separator + "wtpwebapps" + File.separator;
		if (sStr.toLowerCase().indexOf(sWebapps) == -1) {
			sWebapps = File.separator + "webapps" + File.separator;
		}
		if (sStr.toLowerCase().indexOf(sWebapps) != -1) {
			int iIndex = sStr.toLowerCase().indexOf(sWebapps);
			sRes = sStr.substring(iIndex + sWebapps.length() - 1);
		}
		return sRes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getReqIDList(Map<String, String> paramMap) {
		List resList = new ArrayList();
		Set keySet = paramMap.keySet();
		Iterator it = keySet.iterator();
		String sName = null;
		String sVal = null;
		while (it.hasNext()) {
			sName = (String) it.next();
			if (sName.indexOf("FJID_") != -1)
				;
			sVal = (String) paramMap.get(sName);
			resList.add(sVal);
		}

		return resList;
	}
	/**
	 * 是否为空检验
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj){
		boolean result = false;
		if(null == obj){
			result= true;
		}else{
			if((obj instanceof String) && (((String)obj).length() == 0)){
				result = true;
			}else if((obj instanceof Collection<?>) && (((Collection<?>)obj).size() == 0)){
				result = true;
			}else if((obj instanceof Map<?, ?>) && (((Map<?, ?>)obj).size() == 0)){
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 是否不为空检验
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj){
		return !isNull(obj);
	}
	
	/**
	 * 获取开始条数和每页大小
	 * @param params
	 * @return 
	 * Map<String,Object>
	 */
	public static Map<String,Object> getPageInfo(Map<String,Object> params){
		//分页
		int start = 0;
		int limit = 0;
		//获取每页多少条
		if(params.get("pageSize") ==null || !TdNumberUtil.isNumber((String) params.get("pageSize"))){
			limit = 15;
		}else{
			limit = Integer.parseInt((String) params.get("pageSize"));
		}
		//获取开始条
		if(params.get("pageNum") ==null || !TdNumberUtil.isNumber((String) params.get("pageNum"))){
			start =(1-1)*limit;
		}else{
			start = (Integer.parseInt((String) params.get("pageNum"))-1)*limit;
		}
		
		params.put("start", start);
		params.put("limit", limit);
		
		return params;
		
	}
	public static Map getParameterMap(HttpServletRequest request) {
		Map parameterMap = request.getParameterMap();
		Map returnMap = new HashMap();
		// 返回值Map
		Iterator entries = parameterMap.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
