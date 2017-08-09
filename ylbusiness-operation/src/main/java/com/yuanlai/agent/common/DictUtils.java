package com.yuanlai.agent.common;

import com.yuanlai.agent.constants.TpConstant;
import com.yuanlai.agent.entity.Dict;
import com.yuanlai.agent.entity.DictObj;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictUtils {
	private static Map<String, DictObj> dictMap = new HashMap<String, DictObj>();
	private static final Logger log = LoggerFactory.getLogger(DictUtils.class);
	
	/**
	 * 鏇存柊瀛楀吀瀵硅薄鑷冲彉閲廙ap涓�
	 * 
	 * @param dictList
	 * @return
	 */
	public static int updateDictMap(List<Dict> dictList){
		Map<String, DictObj> dictTempMap = new HashMap<String, DictObj>();
		DictObj dRootObj = new DictObj();
		dRootObj.setAttrMap("DICT_NAME", "ROOT");
		dRootObj.setAttrMap("DICT_CODE", "0");
		dRootObj.setAttrMap("DICT_LEVEL", "0");
		dRootObj.setAttrMap("DICT_ID", "0");

		dictTempMap.put("0", dRootObj);

		DictObj dChildObj = new DictObj();
		for (Dict dict : dictList) {
			dChildObj = new DictObj();
			dChildObj.setAttrMap("DICT_LEVEL", dict.getDict_level());
			dChildObj.setAttrMap("PARENT_ID", dict.getParent_id());
			dChildObj.setAttrMap("DICT_ID", dict.getDict_id());
			dChildObj.setAttrMap("ABR", dict.getAbr());
			dChildObj.setAttrMap("DICT_CODE", dict.getDict_code());
			dChildObj.setAttrMap("DICT_NAME", dict.getDict_name());
			dChildObj.setAttrMap("DICT_VALUE", dict.getDict_value());
			dChildObj.setAttrMap("SEQ_NUM", dict.getSeq_num());

			addChild2Root(dRootObj, dChildObj);
		}

		dictMap = dictTempMap;
		log.info("鏋勫缓瀵硅薄dictMap瀹屾瘯锛�");
		return 0;
	}

	/**
	 * 鑾峰緱鏁翠釜瀛楀吀Map
	 * @return
	 */
	public static Map<String, DictObj> getDictMap() {
		return dictMap;
	}

	/**
	 * 鑾峰緱瀛楀吀Code瀵瑰簲鐨勫璞�
	 * @param dictObj
	 * @param sDictCode
	 * @return
	 */
	public static DictObj getDictObjByCode(DictObj dictObj, String sDictCode) {
		if (null == dictObj) {
			return null;
		}

		DictObj dictObjRes = null;
		String sDictIdTemp = dictObj.getAttrMap("DICT_CODE");
		if (sDictCode.equals(sDictIdTemp)) {
			return dictObj;
		}
		for (DictObj dictObjTemp : dictObj.getChildList()) {
			dictObjRes = getDictObjByCode(dictObjTemp, sDictCode);
			if (null != dictObjRes) {
				return dictObjRes;
			}
		}
		return dictObjRes;
	}

	/**
	 * 灏嗗瓙鑺傜偣娣诲姞鍒扮埗鑺傜偣涓�
	 * @param
	 * */
	public static void addChild2Root(DictObj dRootObj, DictObj dChildObj) {
		// 鏌ユ壘瀛愯妭鐐圭殑涓婄骇鐖惰妭鐐�
		DictObj dParentObj = findParent(dRootObj, dChildObj);
		if(dParentObj != null){
			// 灏嗗瓙鑺傜偣娣诲姞鍒颁笂绾х埗鑺傜偣鐨勫瓙鍒楄〃涓�
			dParentObj.setChild(dChildObj);
		}
	}

	/**
	 * 鏌ユ壘瀛愯妭鐐圭殑涓婄骇鐖惰妭鐐� 鍓嶆彁锛氫緷璧栦簬鏌ヨ鏃剁殑椤哄簭 璇ヨ妭鐐逛竴瀹氫細瀛樺湪
	 * @param dRootObj
	 *            鏍硅妭鐐�
	 * @param dRootObj
	 *            鏌ユ壘鐖惰妭鐐圭殑瀛愯妭鐐�
	 * */
	public static DictObj findParent(DictObj dRootObj, DictObj dChildObj) {
		DictObj dParentObj = null;
		// 鏌ユ壘瀛愯妭鐐�
		String sParentId = dChildObj.getAttrMap("PARENT_ID");
		String sRootId = dRootObj.getAttrMap("DICT_ID");
		if (null != sParentId && sParentId.equals(sRootId)) {
			return dRootObj;
		}
		for (DictObj dTempObj : dRootObj.getChildList()) {
			dParentObj = findParent(dTempObj, dChildObj);
			if (null != dParentObj) {
				break;
			}
		}
		return dParentObj;
	}
	
	public static String getDictName(String sLanguage,DictObj dictObj, String sVal) {
		String sRes = "";
		if(null==dictObj || StringUtils.isEmpty(sVal)){
			return sRes;
		}
		List<DictObj> childList = dictObj.getChildList();
		String sValue           = null;
		String sName            = null;
		for(DictObj dictChild:childList){
			sValue = dictChild.getAttrMap("DICT_VALUE");
			sName  = dictChild.getAttrMap("DICT_NAME");//榛樿鏄剧ず涓枃
			
			//鏍规嵁鍓嶇璇█绫诲瀷锛岃繑鍥炰笉鍚岀殑璇█鏄剧ず
			if(TpConstant.SYS_LANGUAGE_EN.equalsIgnoreCase(sLanguage)){
				sName  = dictChild.getAttrMap("ABR");//鏄剧ず缈昏瘧鍐呭
			}
			
			if(sVal.equals(sValue)){
				sRes = sName;
				break;
			}
		}
		return sRes;
	}
	
	public static String getDictVal(DictObj dictObj, String sName) {
		String sRes = "";
		if(null==dictObj || StringUtils.isEmpty(sName)){
			return sRes;
		}
		List<DictObj> childList = dictObj.getChildList();
		if(childList.size()==0){
			return sRes;
		}
		String sValueTemp       = null;
		String sNameTemp        = null;
		for(DictObj dictChild:childList){
			sValueTemp = dictChild.getAttrMap("DICT_VALUE");
			sNameTemp  = dictChild.getAttrMap("DICT_NAME");
			if(sName.trim().equals(sNameTemp.trim())){
				return sValueTemp;
			}
			sRes = getDictVal(dictChild,sName);
			if(StringUtils.isNotEmpty(sRes)){
				break;
			}
		}
		return sRes;
	}
	
	public static String getDictVal(DictObj dictObj, String sName,String sLevel) {
		String sRes = "";
		if(null==dictObj || StringUtils.isEmpty(sName)){
			return sRes;
		}
		List<DictObj> childList = dictObj.getChildList();
		if(childList.size()==0){
			return sRes;
		}
		String sValueTemp       = null;
		String sNameTemp        = null;
		String sLevelTemp       = null;
		for(DictObj dictChild:childList){
			sValueTemp = dictChild.getAttrMap("DICT_VALUE");
			sNameTemp  = dictChild.getAttrMap("DICT_NAME");
			sLevelTemp = dictChild.getAttrMap("DICT_LEVEL");
			if(sName.trim().equals(sNameTemp.trim()) && sLevel.trim().equals(sLevelTemp)){
				return sValueTemp;
			}
			sRes = getDictVal(dictChild,sName,sLevel);
			if(StringUtils.isNotEmpty(sRes)){
				break;
			}
		}
		return sRes;
	}
	
	/**
	 * @desc 鑾峰緱鍒楀悕涓殑鍚嶇О
	 * @param sColName eg:STATUS_CC_ZJLX 
	 * @return STATUS
	 * */
	public static String getAddNodeName(String sColName){
		String sRes = "";
		if(null==sColName){
			return sRes;
		}
		if(sColName.toUpperCase().indexOf("_CC_")!=-1){
			String[] arr = sColName.toUpperCase().split("_CC_");
			sRes = arr[0].trim();
		}
		return sRes;
	}
	
	/**
	 * @desc 鑾峰緱鍒楀悕涓殑鏁版嵁瀛楀吀DICT_CODE
	 * @param sColName eg:STATUS_CC_ZJLX 
	 * @return ZJLX
	 * */
	public static String getDictType(String sColName){
		String sRes = "";
		if(null==sColName){
			return sRes;
		}
		if(sColName.toUpperCase().indexOf("_CC_")!=-1){
			String[] arr = sColName.toUpperCase().split("_CC_");
			sRes = arr[arr.length-1].trim();
		}
		return sRes;
	}
	
	/**
	 * @desc 瀵逛紶鍏ョ殑鏁版嵁杩涜鏄熷彿(*)鍔犲瘑澶勭悊
	 * @param sStr 
	 * @return 鍔犲瘑鍚庣殑鏁版嵁
	 * */
	public static String getHideStr(String sStr){
		String sRes = "";
		if(null == sStr||0==sStr.length()){
			return sStr;
		}
		int iStr = sStr.length();
		if(1==iStr){
			return "*";
		}else if(2==iStr){
			return sStr.substring(0, 1)+"*";
		}else if(3==iStr){
			return sStr.substring(0, 1)+"*"+sStr.substring(2, 3);
		}else if(4==iStr){
			return sStr.substring(0, 1)+"**"+sStr.substring(3, 4);
		}
		
		if(iStr>4 && iStr<=8){
			return sStr.substring(0, 2)+"****"+sStr.substring(iStr-2, iStr);
		}
		
		if(iStr>8){
			return sStr.substring(0, 4)+"****"+sStr.substring(iStr-4, iStr);
		}
		
		return sRes;
	}
	
	public static void clearDictMap(){
		dictMap = null;
		dictMap = new HashMap<String, DictObj>();
	}
}
