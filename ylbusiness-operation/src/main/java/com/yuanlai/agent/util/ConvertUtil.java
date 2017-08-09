package com.yuanlai.agent.util;

import com.tangdi.def.utils.common.TdCommUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供转换功能使用
 * @author Jason Meng
 */
public class ConvertUtil {

	private static final String UNDERLINE = "_";

	private static final Pattern p = Pattern.compile("_[a-z]");
	
	/**
	 * 将传入的Map的Key值转为驼峰命名，并保留原值
	 * 
	 * @param paramMap
	 * @return
	 */
	public static Map<String, Object> getCamelMap(Map<String, Object> paramMap) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (null == paramMap) {
			return null;
		}
		Set<String> keySet = paramMap.keySet();
		Iterator<String> it = keySet.iterator();
		String sKey = null;
		Object oVal = null;
		while (it.hasNext()) {
			sKey = it.next();
			oVal = paramMap.get(sKey);
			sKey = underLine2Camel(sKey);
			resMap.put(sKey, oVal);
		}
		resMap.putAll(paramMap);
		return resMap;
	}
	
	/**
	 * 将传入的List的Key值转为驼峰命名，并保留原值
	 * 
	 * @param list
	 * @return
	 */
	public static Set<String> getCamelList(Set<String> list) {
		Set<String> tempSet = new HashSet<String>();
		if (null == list) {
			return null;
		}
		
		Iterator<String> it = list.iterator();
		String sKey = null;
		while (it.hasNext()) {
			sKey = it.next();
			sKey = underLine2Camel(sKey);
			tempSet.add(sKey);
		}
		list.addAll(tempSet);
		return list;
	}
	

	/**
	 * 将传入的Map的Key值转为驼峰命名，不保留原值
	 * 
	 * @param paramMap
	 * @return
	 */
	public static Map<String, Object> getCamelMapNoRepeat(Map<String, Object> paramMap) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (null == paramMap) {
			return null;
		}

		Set<String> keySet = paramMap.keySet();
		Iterator<String> it = keySet.iterator();
		String sKey = null;
		Object oVal = null;
		while (it.hasNext()) {
			sKey = it.next();
			oVal = paramMap.get(sKey);
			resMap.remove(sKey);
			sKey = underLine2Camel(sKey);

			resMap.put(sKey, oVal);
		}
		resMap.putAll(paramMap);

		return resMap;
	}

	/**
	 * 将传入的Map的Key值转为下划线命名，并保留原值
	 * 
	 * @param paramMap
	 * @return
	 */
	public static Map<String, Object> getUnderLineMap(Map<String, Object> paramMap) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (null == paramMap) {
			return null;
		}

		Set<String> keySet = paramMap.keySet();
		Iterator<String> it = keySet.iterator();
		String sKey = null;
		Object oVal = null;
		while (it.hasNext()) {
			sKey = it.next();
			oVal = paramMap.get(sKey);

			sKey = camel2UnderLine(sKey);
			resMap.put(sKey.toUpperCase(), oVal);
		}

		resMap.putAll(paramMap);

		return resMap;
	}

	/**
	 * 将传入的Map的Key值转为下划线命名，并不保留原值
	 * 
	 * @param paramMap
	 * @return
	 */
	public static Map<String, Object> getUnderLineMapNoRepeat(Map<String, Object> paramMap) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (null == paramMap) {
			return null;
		}

		Set<String> keySet = paramMap.keySet();
		Iterator<String> it = keySet.iterator();
		String sKey = null;
		Object oVal = null;
		while (it.hasNext()) {
			sKey = it.next();
			oVal = paramMap.get(sKey);

			sKey = camel2UnderLine(sKey);
			resMap.put(sKey.toUpperCase(), oVal);
		}

		paramMap.clear();
		paramMap.putAll(resMap);

		return paramMap;
	}

	/**
	 * 将传入列表中的Map的Key值转为下划线命名，并保留原值
	 * 
	 * @param paramMap
	 * @return
	 */
	public static List<Map<String, Object>> getUnderLineListMap(List<Map<String, Object>> paramListMap) {
		if (null == paramListMap) {
			return paramListMap;
		}
		List<Map<String, Object>> resListMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> tempMap : paramListMap) {
			resListMap.add(getUnderLineMapNoRepeat(tempMap));
		}

		paramListMap.clear();
		paramListMap.addAll(resListMap);
		return resListMap;
	}

	public static void main(String[] args) {
		// 将查询结果进行码表格式化
//		List<Map<String, Object>> listMap2 = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map2 = null;
//
//		map2 = new HashMap<String, Object>();
//		map2.put("firstName", "张三");
//		map2.put("usrAddress", "常熟路");
//		listMap2.add(map2);
//
//		map2 = new HashMap<String, Object>();
//		map2.put("age", "18");
//		map2.put("usrFirstName", "Jason Meng");
//		listMap2.add(map2);
//
//		System.out.println(ConvertUtil.getUnderLineListMap(listMap2));
//		System.out.println(listMap2);
		Set<String> list = new HashSet<String>();
		list.add("AA_BB");
		list.add("BB_CC");
		list.add("CC_DD");
		list.add("DD_EE");
		list.add("EE_FF");
		System.out.println(getCamelList(list));
	}

	/**
	 * 根据是否包含下划线，将字符串进行下划线或驼峰自动转换
	 * 
	 * @param sStr
	 * @return
	 */
	public static String camelAndUnderLine(String sStr) {
		String sRes = "";
		if (null == sStr) {
			return sStr;
		}
		if (sStr.indexOf(UNDERLINE) != -1) {
			sRes = underLine2Camel(sStr);
		} else {
			sRes = camel2UnderLine(sStr);
		}
		return sRes;
	}

	/**
	 * 将驼峰转换为下划线
	 * 
	 * @param sStr
	 * @return
	 */
	public static String camel2UnderLine(String sStr) {
		String sRes = "";
		if (sStr == null || "".equals(sStr.trim())) {
			return "";
		}
		int len = sStr.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = sStr.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		sRes = sb.toString();
		return sRes;
	}

	/**
	 * 将下划线转换为驼峰
	 * 
	 * @param sStr
	 * @return
	 */
	public static String underLine2Camel(String sStr) {
		if (null == sStr) {
			return null;
		}
		sStr = sStr.toLowerCase();

		String sRes = "";
		Matcher m = p.matcher(sStr);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String firstChar = m.group().substring(1, 2);
			m.appendReplacement(sb, firstChar.toUpperCase());
		}
		m.appendTail(sb);
		sRes = sb.toString();

		return sRes;
	}

	/**
	 * @desc 获得列名中的名称
	 * @param sColName
	 *            eg:STATUS_CC_ZJLX
	 * @return STATUS
	 */
	// private static String getAddNodeName(String sColName) {
	// String sRes = "";
	// if (null == sColName) {
	// return sRes;
	// }
	// if (sColName.toUpperCase().indexOf("_CC_") != -1) {
	// String[] arr = sColName.toUpperCase().split("_CC_");
	// sRes = arr[0].trim();
	// }
	// return sRes;
	// }

	/**
	 * @author Jason
	 * @desc 获得列名中的数据字典DICT_CODE
	 * @param sColName
	 *            eg:STATUS_CC_ZJLX
	 * @return ZJLX
	 */
	// private static String getDictType(String sColName) {
	// String sRes = "";
	// if (null == sColName) {
	// return sRes;
	// }
	// if (sColName.toUpperCase().indexOf("_CC_") != -1) {
	// String[] arr = sColName.toUpperCase().split("_CC_");
	// sRes = arr[arr.length - 1].trim();
	// }
	// return sRes;
	// }

	/**
	 * 将查询的结果集进行码表格式化操作 将_HIDE字段通过隐藏进行替换
	 * 
	 * @param listMap
	 * @return
	 */
	public static List<Map<String, Object>> formatHideList(List<Map<String, Object>> listMap) {
		if (null == listMap) {
			return listMap;
		}
		List<Map<String, Object>> listMapRes = new ArrayList<Map<String, Object>>();
		Set<String> keySet = null;
		Iterator<String> it = null;
		String sKey = null;
		// String sName = null;
		Object oVal = null;
		String sVal = null;
		for (Map<String, Object> mapTemp : listMap) {
			Map<String, Object> mapNew = new HashMap<String, Object>();
			mapNew.putAll(mapTemp);
			listMapRes.add(mapNew);

			keySet = mapTemp.keySet();
			it = keySet.iterator();

			while (it.hasNext()) {
				sKey = it.next().toUpperCase();
				oVal = mapTemp.get(sKey);

				if (null == sKey || !sKey.endsWith("_HIDE")) {
					continue;
				}

				// sName = sKey;
				sVal = getHideStr((String) oVal);

				if (null == sVal) {
					sVal = (String) oVal;
				}

				mapNew.put(sKey, sVal);
			}
		}

		return listMapRes;
	}

	/**
	 * @desc 对传入的数据进行星号(*)加密处理
	 * @param sStr
	 * @return 加密后的数据
	 */
	private static String getHideStr(String sStr) {
		String sRes = "";
		if (null == sStr || 0 == sStr.length()) {
			return sStr;
		}
		int iStr = sStr.length();
		if (1 == iStr) {
			return "*";
		} else if (2 == iStr) {
			return sStr.substring(0, 1) + "*";
		} else if (3 == iStr) {
			return sStr.substring(0, 1) + "*" + sStr.substring(2, 3);
		} else if (4 == iStr) {
			return sStr.substring(0, 1) + "**" + sStr.substring(3, 4);
		}

		if (iStr > 4 && iStr <= 8) {
			return sStr.substring(0, 2) + "****" + sStr.substring(iStr - 2, iStr);
		}

		if (iStr > 8) {
			return sStr.substring(0, 4) + "****" + sStr.substring(iStr - 4, iStr);
		}

		return sRes;
	}

	/**
	 * 通过卡BIN的银行编号 获取系统码表中的银行编号信息
	 * 
	 * @param sIssno
	 * @return
	 */
	public static String getBankCodeByIssno(String sIssno) {
		String sRes = "";
		if (StringUtils.isEmpty(sIssno)) {
			return sRes;
		}
		if (sIssno.length() >= 4) {
			sIssno = sIssno.substring(0, 4);
		}
		sRes = TdCommUtil.getProperty("BANK_CODE_" + sIssno);
		return sRes;
	}

	/**
	 * s => hh时mm分ss秒
	 * 
	 * @param sIssno
	 * @return
	 */
	public static String secTraHouMinSec(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99时59分59秒";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
}
