package com.yuanlai.agent.service.impl;

import com.tangdi.def.base.redis.TdRedisService;
import com.tangdi.def.utils.common.TdNumberUtil;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.tangdi.tdcomm.ext.utils.SpringContext;
import com.yuanlai.agent.common.DictUtils;
import com.yuanlai.agent.constants.TpConstant;
import com.yuanlai.agent.constants.TpErrorCode;
import com.yuanlai.agent.dao.PubDictDao;
import com.yuanlai.agent.entity.Dict;
import com.yuanlai.agent.entity.DictObj;
import com.yuanlai.agent.entity.RespInfo;
import com.yuanlai.agent.service.IPubDictService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//import com.tangdi.svr.constant.TpConstant;

/**
 * 数据字典接口实现类
 * @author
 */
@Service
public class PubDictServiceImpl implements IPubDictService {

	private static boolean bFlag = false;
	private final Logger log = LoggerFactory.getLogger(getClass());
//	@Autowired//不适用注入方式，是为了避免在系统启动时将不必要的服务删除  网关中引入了td-service，但是不需要使用redis服务。
//	private TdRedisService tdRedisService;
	@Autowired
	private PubDictDao pubDictDao;

	private static final String DICT_CODE_PRE = "DICT_CODE_";

	/**
	 * 获取并更新最新的码值列表
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Dict> getList() {
		return getList(null);
	}

	/**
	 * 获取并更新最新的码值列表
	 *
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<Dict> getList(Dict entity) {
		log.info("开始初始化数据字典...");
		// 设置默认数据字典对象
		if (null == entity) {
			entity = new Dict();
		}
		// 获取最新的码值信息
		List<Dict> dictList = null;
		try {
			dictList = pubDictDao.selectList(entity);
			log.debug("数据字典=[{}]", dictList);
		} catch (Exception e) {
			log.error("查询字典表出现错误！", e);
		}

		if (!bFlag) {
			log.info("开始同步数据字典...");
			// 更新字典Map
			DictUtils.updateDictMap(dictList);

			Map<String, DictObj> dictMap = DictUtils.getDictMap();

			// 将Map添加到Redis中
			setDict2Redis(dictMap);
			// bFlag = true;
			log.info("初始化数据字典表成功！");

			// 清空内存中的数据 释放资源
			DictUtils.clearDictMap();
		}
		return dictList;
	}

	/**
	 * 数据字典更新到Redis
	 * @param dictMap
	 */
	public void setDict2Redis(Map<String, DictObj> dictMap) {
		DictObj dictRoot = dictMap.get("0");
		List<DictObj> dictList = dictRoot.getChildList();
		for (DictObj dictChild : dictList) {
			SpringContext.getBean(TdRedisService.class).deleteObject(DICT_CODE_PRE + dictChild.getAttrMap("DICT_CODE"));
			SpringContext.getBean(TdRedisService.class).saveObject(
					DICT_CODE_PRE + dictChild.getAttrMap("DICT_CODE"),
					dictChild, 0);
		}
	}

	/**
	 * 通过数据字典类型那个和值 获取数据字典对应的 中文名称
	 * @param sDictCode
	 * @param sChildDictVal
	 * @return
	 */
	public String getDictName(String sDictCode, String sChildDictVal) {
		DictObj dictObj = (DictObj) SpringContext.getBean(TdRedisService.class).getObject(DICT_CODE_PRE + sDictCode);
		return DictUtils.getDictName(TpConstant.SYS_LANGUAGE_ZH,dictObj, sChildDictVal);
	}

	/**
	 * 通过数据字典类型那个和值 获取数据字典对应的 中文名称
	 * 首先从本地的临时Map中获取码表数据，如果没有获取，则从redis中再次获取，避免每个循环中循环与redis交互
	 * @param sDictCode
	 * @param sChildDictVal
	 * @param tempMap
	 * @return
	 */
	public String getDictNameFromMap(String sLanguage,String sDictCode, String sChildDictVal,Map<String, DictObj> tempMap) {
		DictObj dictObj = tempMap.get(sDictCode);
		if(null == dictObj){
			dictObj = (DictObj) SpringContext.getBean(TdRedisService.class).getObject(DICT_CODE_PRE + sDictCode);
			//将redis中查询的数据更新到临时map中
			tempMap.put(sDictCode, dictObj);
		}
		return DictUtils.getDictName(sLanguage,dictObj, sChildDictVal);
	}

	/**
	 * 通过数据字典类型那个和中文名称 获取数据字典对应的 值
	 * @param sDictCode
	 * @param sChildDictName
	 * @return
	 */
	@Override
	public String getDictVal(String sDictCode, String sChildDictName) {
		DictObj dictObj = (DictObj) SpringContext.getBean(TdRedisService.class).getObject(DICT_CODE_PRE + sDictCode);
		return DictUtils.getDictVal(dictObj, sChildDictName);
	}

	/**
	 * 通过数据字典类型和中文名称 获取数据字典对应的 值
	 * @param sDictCode
	 * @param sChildDictName
	 * @param sChildDictLevel
	 * @return
	 */
	@Override
	public String getDictVal(String sDictCode, String sChildDictName,String sChildDictLevel) {
		DictObj dictObj = (DictObj) SpringContext.getBean(TdRedisService.class).getObject(DICT_CODE_PRE + sDictCode);
		return DictUtils.getDictVal(dictObj, sChildDictName,sChildDictLevel);
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param extMap
	 * 	      language:zh|en
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatDictListByLanguage(Map<String,String> extMap,List<Map<String, Object>> listMap) {
		if (null == listMap) {
			return listMap;
		}

		String sLanguage = extMap.get(TpConstant.SYS_LANGUAGE_NAME);

		List<Map<String, Object>> listMapRes = new ArrayList<Map<String, Object>>();
		Set<String> keySet  = null;
		Iterator<String> it = null;
		String sKey         = null;
		String sName        = null;
		String sDictType    = null;
		Object oVal         = null;
		String sVal         = null;
		//临时数据字典内容，将需要的码表需要提前进行获取，下面的数据此Map中获取 减少与redis的交互次数
		Map<String, DictObj> tempDictMap = getDictTemp(listMap);
		for (Map<String, Object> mapTemp : listMap) {
			Map<String, Object> mapNew = new HashMap<String, Object>();
			mapNew.putAll(mapTemp);
			listMapRes.add(mapNew);

			keySet = mapTemp.keySet();
			it = keySet.iterator();

			while (it.hasNext()) {
				sKey = it.next().toUpperCase();
				oVal = mapTemp.get(sKey);

				if (null == sKey || sKey.indexOf("_CC_") == -1) {
					continue;
				}

				sName     = DictUtils.getAddNodeName(sKey);
				sDictType = DictUtils.getDictType(sKey);
				sVal      = getDictNameFromMap(sLanguage,sDictType, (String) oVal,tempDictMap);

				if (null == sVal) {
					sVal = (String) oVal;
				}

				mapNew.put(sName + "_ZH", sVal);
				// mapNew.put(sKey , sVal);
			}
		}
		tempDictMap = null;//将临时map置空
		listMap.removeAll(listMap);
		listMap.addAll(listMapRes);
		return listMapRes;
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatDictList(List<Map<String, Object>> listMap) {
		Map<String, String> extMap = new HashMap<String,String>();
		extMap.put(TpConstant.SYS_LANGUAGE_NAME, TpConstant.SYS_LANGUAGE_ZH);
		return formatDictListByLanguage(extMap,listMap);
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param extMap
	 *        language:zh|en
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatMultiDictListByLanguage(Map<String,String> extMap,List<Map<String, Object>> listMap, String reg) {
		if (null == listMap) {
			return listMap;
		}

		String sLanguage = extMap.get(TpConstant.SYS_LANGUAGE_NAME);

		List<Map<String, Object>> listMapRes = new ArrayList<Map<String, Object>>();
		Set<String> keySet  = null;
		Iterator<String> it = null;
		String sKey         = null;
		String sName        = null;
		String sDictType    = null;
		Object oVal         = null;
		String sVal         = null;
		//临时数据字典内容，将需要的码表需要提前进行获取，下面的数据此Map中获取 减少与redis的交互次数
		Map<String, DictObj> tempDictMap = getDictTemp(listMap);
		for (Map<String, Object> mapTemp : listMap) {
			Map<String, Object> mapNew = new HashMap<String, Object>();
			mapNew.putAll(mapTemp);
			listMapRes.add(mapNew);

			keySet = mapTemp.keySet();
			it = keySet.iterator();

			while (it.hasNext()) {
				sVal = "";
				sKey = it.next().toUpperCase();
				oVal = mapTemp.get(sKey);

				if (null == sKey || sKey.indexOf("_CC_") == -1) {
					continue;
				}

				sName     = DictUtils.getAddNodeName(sKey);
				sDictType = DictUtils.getDictType(sKey);
				if (oVal instanceof String) {
					String[] dictKeys = ((String) oVal).split(reg);
					for (String key : dictKeys) {
						sVal = sVal + reg + getDictNameFromMap(sLanguage,sDictType, key,tempDictMap);
					}
				}

				if (null == sVal || sVal.length() <= 0) {
					sVal = (String) oVal;
				} else {
					sVal = sVal.substring(1);
				}

				mapNew.put(sName + "_ZH", sVal);
				// mapNew.put(sKey , sVal);
			}
		}
		tempDictMap = null;//将临时Map置空
		listMap.removeAll(listMap);
		listMap.addAll(listMapRes);
		return listMapRes;
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatMultiDictList(List<Map<String, Object>> listMap, String reg) {
		Map<String, String> extMap = new HashMap<String,String>();
		extMap.put(TpConstant.SYS_LANGUAGE_NAME, TpConstant.SYS_LANGUAGE_ZH);
		return formatMultiDictListByLanguage(extMap,listMap,reg);
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段 将查询的结果集进行码表格式化操作
	 * 将_HIDE字段通过隐藏进行替换
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatAllListByLanguage(Map<String,String> extMap,List<Map<String, Object>> listMap) {
		if (null == listMap) {
			return listMap;
		}

		String sLanguage = extMap.get(TpConstant.SYS_LANGUAGE_NAME);

		List<Map<String, Object>> listMapRes = new ArrayList<Map<String, Object>>();
		Set<String> keySet  = null;
		Iterator<String> it = null;
		String sKey         = null;
		String sName        = null;
		String sDictType    = null;
		Object oVal         = null;
		String sVal         = null;
		//临时数据字典内容，将需要的码表需要提前进行获取，下面的数据此Map中获取 减少与redis的交互次数
		Map<String, DictObj> tempDictMap = getDictTemp(listMap);
		for (Map<String, Object> mapTemp : listMap) {
			Map<String, Object> mapNew = new HashMap<String, Object>();
			mapNew.putAll(mapTemp);
			listMapRes.add(mapNew);

			keySet = mapTemp.keySet();
			it     = keySet.iterator();

			while (it.hasNext()) {
				sKey = it.next().toUpperCase();
				oVal = mapTemp.get(sKey);

				if (null == sKey) {
					continue;
				}

				if (sKey.indexOf("_CC_") != -1) {
					sName     = DictUtils.getAddNodeName(sKey);
					sDictType = DictUtils.getDictType(sKey);
					sVal      = getDictNameFromMap(sLanguage,sDictType, (String) oVal,tempDictMap);

					if (null == sVal) {
						sVal = (String) oVal;
					}

					mapNew.put(sName + "_ZH", sVal);
					// mapNew.put(sKey , sVal);
				} else if (sKey.endsWith("_HIDE")) {
					sName = sKey;
					sVal = DictUtils.getHideStr((String) oVal);
					if (null == sVal) {
						sVal = (String) oVal;
					}
					mapNew.put(sKey, sVal);
				}
			}
		}
		tempDictMap = null;//将临时map置空
		listMap.removeAll(listMap);
		listMap.addAll(listMapRes);
		return listMapRes;
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段 将查询的结果集进行码表格式化操作
	 * 将_HIDE字段通过隐藏进行替换
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatAllList(List<Map<String, Object>> listMap) {
		Map<String, String> extMap = new HashMap<String,String>();
		extMap.put(TpConstant.SYS_LANGUAGE_NAME, TpConstant.SYS_LANGUAGE_ZH);
		return formatAllListByLanguage(extMap,listMap);
	}

	@Override
	public Map<String, Object> formatDictMap(Map<String, Object> paramMap) {
		if (null == paramMap) {
			return paramMap;
		}
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		listMap.add(paramMap);
		listMap = formatDictList(listMap);
		return listMap.get(0);
	}

	/**
	 * 将查询的结果集进行码表格式化操作 将_HIDE字段通过隐藏进行替换
	 *
	 * @param listMap
	 * @return
	 */
	public static List<Map<String, Object>> formatHideList(
			List<Map<String, Object>> listMap) {
		if (null == listMap) {
			return listMap;
		}
		List<Map<String, Object>> listMapRes = new ArrayList<Map<String, Object>>();
		Set<String> keySet = null;
		Iterator<String> it = null;
		String sKey = null;
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
				sVal = DictUtils.getHideStr((String) oVal);
				if (null == sVal) {
					sVal = (String) oVal;
				}
				mapNew.put(sKey, sVal);
			}
		}

		return listMapRes;
	}

	@Override
	public List<DictObj> getDictCodeList(String sDictCode) {
		DictObj dictObj = (DictObj) SpringContext.getBean(TdRedisService.class).getObject(DICT_CODE_PRE
				+ sDictCode);
		if (null != dictObj) {
			return dictObj.getChildList();
		}
		return null;
	}

	@Override
	public RespInfo selectDictList(Map<String, String> params)
			throws TdRuntimeException {

		// log.info("开始查询码表信息====================");
		RespInfo info = new RespInfo();
		String dictCode = (String) params.get("dictCode");
		String dictName = (String) params.get("dictName");
		params.put("dictCode", dictCode);
		params.put("dictName", dictName);
		int total = pubDictDao.selectDictListCount(params);
		info.putVal("total", total);
		// 获取开始条数和每页大小
		params.putAll(getPageInfo(params));
		List<Map<String, Object>> parentItems = pubDictDao
				.selectDictList(params);

		log.info("开始查询码表信息====================" + parentItems.toString());
		List<Map<String, Object>> childItems = pubDictDao
				.selectDictsList(params);

		log.info("开始查询码表信息====================" + childItems.toString());

		List<Map<String, Object>> itemTree = getItemTree(parentItems,
				childItems);

		info.setResList(itemTree);
		// log.info("查询结束===============");

		return info;
	}

	public List<Map<String, Object>> getItemTree(
			List<Map<String, Object>> parentItems,
			List<Map<String, Object>> childItems) throws TdRuntimeException {

		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		log.info(" ---------------parentItems的size: " + parentItems.size());
		for (Map<String, Object> parentItem : parentItems) {
			menu.add(getMenuForOneParent(parentItem, childItems));
		}
		return menu;
	}

	private Map<String, Object> getMenuForOneParent(
			Map<String, Object> parentItem, List<Map<String, Object>> childItems) {
		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < childItems.size(); i++) {
			Map<String, Object> childItem = childItems.get(i);
			if (parentItem.get("DICT_ID").equals(childItem.get("PARENT_ID"))) {
				menu.add(getMenuForOneParent(childItem, childItems));
			}
		}
		if (menu.size() > 0) {
			parentItem.put("children", menu);// 有子节点设置children
			for (int i = 0; i < menu.size(); i++) {// 移除已组装过的子节点
				childItems.remove(menu.get(i));
			}
		}
		return parentItem;
	}

	@Override
	public RespInfo updatePubDictInf(Map<String, String> inf) throws Exception {
		RespInfo info = new RespInfo();
		if (inf.get("dict_id") == null || inf.get("dict_name") == null) {
			info.setRspCode(TpErrorCode.PARAMSISNOTEXIT);
			info.setRspMsg("必填参数不能为空");
			log.info(info.getRspCode() + " -- " + info.getRspMsg());
			return info;
		}
		String dict_level = (String) inf.get("dict_level");
		if (dict_level.equals("1")) {
			int count = pubDictDao.selectPubDictByCode_upd(inf);
			if (count > 0) {
				info.setRspCode(TpErrorCode.RCS_EC_RECORD_ISEXIST);
				info.setRspMsg("该参数值已经存在");
				log.info(info.getRspCode() + " -- " + info.getRspMsg());
				return info;
			}
		} else {
			if (inf.get("dict_value") == null) {
				info.setRspCode(TpErrorCode.PARAMSISNOTEXIT);
				info.setRspMsg("必填参数不能为空");
				log.info(info.getRspCode() + " -- " + info.getRspMsg());
				return info;
			} else {
				int count4name = pubDictDao.selectPubDictByCodeName_upd(inf);
				if (count4name > 0) {
					info.setRspCode(TpErrorCode.RCS_EC_RECORD_ISEXIST);
					info.setRspMsg("该参数名已经存在");
					log.info(info.getRspCode() + " -- " + info.getRspMsg());
					return info;
				}

				int count = pubDictDao.selectPubDictByCodeValue_upd(inf);
				if (count > 0) {
					info.setRspCode(TpErrorCode.RCS_EC_RECORD_ISEXIST);
					info.setRspMsg("该记录已经存在");
					log.info(info.getRspCode() + " -- " + info.getRspMsg());
					return info;
				}
			}
		}
		int res = pubDictDao.updatePubDictInf(inf);
		if (res == 0) {
			info.setRspCode(TpErrorCode.RECORD_NOT_EXSIT);
			info.setRspMsg("该记录不存在");
			log.info(info.getRspCode() + " -- " + info.getRspMsg());
		} else if (res == 1) {
			info.setRspMsg("更新成功");
			info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
		} else {
			info.setRspCode(TpErrorCode.DATABASEERROR);
			info.setRspMsg("数据库异常");
			log.error(info.getRspCode() + " -- " + info.getRspMsg());
		}
		return info;
	}

	@Override
	public RespInfo addPubDictInf(Map<String, String> inf) throws Exception {
		RespInfo info = new RespInfo();
		String dict_level = (String) inf.get("dict_level");

		if (!dict_level.equals("1")) {
			if (inf.get("parent_id") == null || inf.get("dict_code") == null) {
				info.setRspCode(TpErrorCode.PARAMSISNOTEXIT);
				info.setRspMsg("参数编码不能为空");
				log.info(info.getRspCode() + " -- " + info.getRspMsg());
				return info;
			}
		}
		int seq_num = 0;
		// 添加一级码表
		if (dict_level.equals("1")) {
			if (inf.get("dict_name") == null || inf.get("dict_code") == null) {
				info.setRspCode(TpErrorCode.PARAMSISNOTEXIT);
				info.setRspMsg("参数名称或者编码不能为空");
				log.info(info.getRspCode() + " -- " + info.getRspMsg());
				return info;
			}
			// 查询是否已经存在记录，若存在不能进行添加，不存在则可进行添加
			int count1 = pubDictDao.selectPubDictByCode((String) inf
					.get("dict_code"));
			if (count1 > 0) {
				info.setRspCode(TpErrorCode.RCS_EC_RECORD_ISEXIST);
				info.setRspMsg("该参数编码已经存在");
				log.info(info.getRspCode() + " -- " + info.getRspMsg());
			} else {
				int dict_id = pubDictDao.selectPubDictByDictIdMax() + 1;
				inf.put("dict_id", String.valueOf(dict_id));
				log.info(" -- dict_id" + dict_id);
				inf.put("parent_id", "0");
				inf.put("seq_num", "1");
				inf.put("status", "1");
				int res = pubDictDao.addPubDictInf(inf);
				if (res == 0) {
					info.setRspCode(TpErrorCode.RECORD_NOT_EXSIT);
					info.setRspMsg("该记录不存在");
					log.info(info.getRspCode() + " -- " + info.getRspMsg());
				} else if (res == 1) {
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				} else {
					info.setRspCode(TpErrorCode.DATABASEERROR);
					info.setRspMsg("数据库异常");
					log.error(info.getRspCode() + " -- " + info.getRspMsg());
				}
			}
		}
		// 添加二级或者三级级码表
		if (!dict_level.equals("1")) {
			String parent_id = (String) inf.get("parent_id");
			String keysStr = (String) inf.get("keysStr");
			if (!(keysStr == null) && !(keysStr.equals(""))) {
				String[] keysArry = keysStr.split(",");
				for (String keyArry : keysArry) {
					// 根据参数编码和参数值判断是否已经存在记录
					String dict_name = (String) inf.get("DICT_NAME" + "_"
							+ keyArry);
					String dict_value = (String) inf.get("DICT_VALUE" + "_"
							+ keyArry);
					String dict_abr = (String) inf.get("DICT_ABR" + "_"
							+ keyArry);
					Map<String, String> dictSelMap = new HashMap<String, String>();
					log.info("********************** dict_code"
							+ inf.get("dict_code"));
					dictSelMap.put("dict_code", inf.get("dict_code"));
					dictSelMap.put("dict_value", dict_value);
					dictSelMap.put("dict_name", dict_name);
					// dictSelMap.put("dict_level",inf.get("dict_level"));
					int count4name = pubDictDao
							.selectPubDictByCodeName(dictSelMap);
					if (count4name > 0) {
						return new RespInfo(TpErrorCode.RCS_EC_RECORD_ISEXIST,
								"该参数名称已经存在");
					}

					int count2 = pubDictDao
							.selectPubDictByCodeValue(dictSelMap);
					if (count2 > 0) {
						info.setRspCode(TpErrorCode.RCS_EC_RECORD_ISEXIST);
						info.setRspMsg("该参数值已经存在");
						log.info(info.getRspCode() + " -- " + info.getRspMsg());
					} else {
						int dict_id_detail = pubDictDao
								.selectPubDictByDictIdMax() + 1;
						int count = pubDictDao
								.selectPubDictBySeqNumCount(parent_id);
						if (count <= 0) {
							seq_num = 1;
						} else {
							seq_num = pubDictDao
									.selectPubDictBySeqNumMax(parent_id) + 1;
						}
						log.info(" -- seq_num" + seq_num);
						// 将参数值设置到dictMap里面，其实应该设置到实体类中
						Map<String, String> dictMap = new HashMap<String, String>();
						dictMap.put("dict_id", String.valueOf(dict_id_detail));
						dictMap.put("dict_code", inf.get("dict_code"));
						dictMap.put("parent_id", parent_id);
						dictMap.put("seq_num", String.valueOf(seq_num));
						dictMap.put("dict_level", dict_level);
						dictMap.put("status", "1");
						dictMap.put("dict_name", dict_name);
						dictMap.put("dict_value", dict_value);
						dictMap.put("abr", dict_abr);
						dictMap.put("dict_level", inf.get("dict_level"));
						int res = pubDictDao.addPubDictInf(dictMap);
						if (res == 0) {
							info.setRspCode(TpErrorCode.RECORD_NOT_EXSIT);
							info.setRspMsg("该记录不存在");
							log.info(info.getRspCode() + " -- "
									+ info.getRspMsg());
						} else if (res == 1) {
							info.setRspMsg("更新成功");
							info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
						} else {
							info.setRspCode(TpErrorCode.DATABASEERROR);
							info.setRspMsg("数据库异常");
							log.error(info.getRspCode() + " -- "
									+ info.getRspMsg());
						}

					}
				}
			} else {
				info.setRspCode(TpErrorCode.PARAMSISNOTEXIT);
				info.setRspMsg("参数名称或参数值不能为空");
				log.info(info.getRspCode() + " -- " + info.getRspMsg());
				return info;
			}

		}

		return info;
	}

	// 递归筛选需要删除的级联码表数据
	public List<Map<String, Object>> delItemTree(
			List<Map<String, Object>> parentItems,
			List<Map<String, Object>> childItems,
			List<Map<String, Object>> allTtems) throws TdRuntimeException {

		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		log.info(" ---------------parentItems的size: " + parentItems.size());
		for (Map<String, Object> parentItem : parentItems) {
			allTtems.add(parentItem);
			menu.add(delMenuForOneParent(parentItem, childItems, allTtems));
		}
		return allTtems;
	}

	// 递归筛选需要删除的级联码表数据
	private Map<String, Object> delMenuForOneParent(
			Map<String, Object> parentItem,
			List<Map<String, Object>> childItems,
			List<Map<String, Object>> allTtems) {
		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < childItems.size(); i++) {
			Map<String, Object> childItem = childItems.get(i);
			if (parentItem.get("DICT_ID").equals(childItem.get("PARENT_ID"))) {
				log.info(" ---------------需要删除的级联1111111111111: " + "DICT_ID="
						+ childItem.get("DICT_ID") + "编码DICT_CODE="
						+ childItem.get("DICT_CODE") + "级别DICT_LEVEL="
						+ childItem.get("DICT_LEVEL") + "名称DICT_NAME="
						+ childItem.get("DICT_NAME"));
				menu.add(delMenuForOneParent(childItem, childItems, allTtems));
				allTtems.add(childItem);
				// parentItem.putAll(childItems.get(i));
			}
		}
		if (menu.size() > 0) {
			for (int i = 0; i < menu.size(); i++) {// 移除已组装过的子节点
				log.info(" ---------------需要删除的级联的菜单**************: "
						+ "DICT_ID=" + (String) menu.get(i).get("DICT_ID")
						+ "编码DICT_CODE=" + menu.get(i).get("DICT_CODE")
						+ "级别DICT_LEVEL=" + menu.get(i).get("DICT_LEVEL")
						+ "名称DICT_NAME=" + menu.get(i).get("DICT_NAME"));

				// allTtems.add(menu.get(i));
				// childItems.remove(menu.get(i));
				// parentItem.putAll(menu.get(i));
			}
		}
		return parentItem;
	}

	@Override
	public RespInfo delPubDictInf(Map<String, String> inf) throws Exception {
		RespInfo info = new RespInfo();
		String DICT_IDS = (String) inf.get("DICT_IDS");
		if (!(DICT_IDS == null) && !(DICT_IDS.equals(""))) {
			String[] IDSArry = DICT_IDS.split(",");
			for (String IDArry : IDSArry) {

				log.info("***********dict_id*****" + IDArry);
				// 将空格去掉
				String dict_id = IDArry.replace("'", "");
				// 根据父级节点查询是否有子节点
				int count = pubDictDao.delQryDictListCount(dict_id);
				if (count > 0) {
					// 查询出来所有的子节点
					List<Map<String, Object>> parentItems = pubDictDao
							.delQryDictList(dict_id);
					// 获取需要获取的参数编码
					String dict_code = parentItems.get(0).get("DICT_CODE")
							.toString();
					List<Map<String, Object>> childItems = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> allItems = new ArrayList<Map<String, Object>>();
					// 查询是否有该编码下的所有级联码表数据
					int count1 = pubDictDao
							.delQryDictAllChildListCount(dict_code);
					if (count1 > 0) {
						childItems = pubDictDao
								.delQryDictAllChildList(dict_code);
					}
					// 获取所有要删除的子级节点
					allItems = delItemTree(parentItems, childItems, allItems);
					// 循环删除所有需要删除的子节点
					for (Map<String, Object> allTtem : allItems) {
						String dict_id_child = (String) allTtem.get("DICT_ID");
						int res_child = pubDictDao.deleteById(dict_id_child);
						if (res_child == 1 || res_child == 0) {
							info.setRspMsg("更新成功");
							info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
						} else {
							info.setRspCode(TpErrorCode.DATABASEERROR);
							info.setRspMsg("数据库异常");
							log.error(info.getRspCode() + " -- "
									+ info.getRspMsg());
							return info;
						}
					}
					log.info("***********获取最后需要删除的树节点*****"
							+ allItems.toString());
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				}
				// 然后再删除选中的节点
				int res = pubDictDao.deleteById(dict_id);
				if (res == 1 || res == 0) {
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				} else {
					info.setRspCode(TpErrorCode.DATABASEERROR);
					info.setRspMsg("数据库异常");
					log.error(info.getRspCode() + " -- " + info.getRspMsg());
					return info;
				}

				int res1 = pubDictDao.deleteByParentId(dict_id);
				if (res1 >= 0) {
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				} else {
					info.setRspCode(TpErrorCode.DATABASEERROR);
					info.setRspMsg("数据库异常");

					return info;
				}

			}

		}
		log.info(info.getRspCode() + " -- " + info.getRspMsg());
		return info;
	}

	@Override
	public RespInfo updatePubDictStatus(Map<String, String> inf)
			throws Exception {
		RespInfo info = new RespInfo();
		String DICT_IDS = (String) inf.get("DICT_IDS");
		if (!(DICT_IDS == null) && !(DICT_IDS.equals(""))) {
			String[] IDSArry = DICT_IDS.split(",");
			for (String IDArry : IDSArry) {
				log.info("***********dict_id*****" + IDArry);
				// 将空格去掉
				String dict_id = IDArry.replace("'", "");
				// 根据父级节点查询是否有子节点
				int count = pubDictDao.delQryDictListCount(dict_id);
				if (count > 0) {
					// 查询出来所有的子节点
					List<Map<String, Object>> parentItems = pubDictDao
							.delQryDictList(dict_id);
					// 获取需要获取的参数编码
					String dict_code = parentItems.get(0).get("DICT_CODE")
							.toString();
					List<Map<String, Object>> childItems = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> allItems = new ArrayList<Map<String, Object>>();
					// 查询是否有该编码下的所有级联码表数据
					int count1 = pubDictDao
							.delQryDictAllChildListCount(dict_code);
					if (count1 > 0) {
						childItems = pubDictDao
								.delQryDictAllChildList(dict_code);
					}
					// 获取所有要删除的子级节点
					allItems = delItemTree(parentItems, childItems, allItems);
					// 循环删除所有需要删除的子节点
					for (Map<String, Object> allTtem : allItems) {

						String dict_id_child = (String) allTtem.get("DICT_ID");
						// 将ID和状态设置到map里面
						Map<String, String> staMap_child = new HashMap<String, String>();
						staMap_child.put("dict_id", dict_id_child);
						staMap_child.put("status", inf.get("STATUS"));
						int res_child = pubDictDao
								.updateStatusById(staMap_child);
						if (res_child == 1 || res_child == 0) {
							info.setRspMsg("更新成功");
							info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
						} else {
							info.setRspCode(TpErrorCode.DATABASEERROR);
							info.setRspMsg("数据库异常");
							log.error(info.getRspCode() + " -- "
									+ info.getRspMsg());
							return info;
						}
					}
					log.info("***********获取最后需要删除的树节点*****"
							+ allItems.toString());
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				}
				// 将ID和状态设置到map里面
				Map<String, String> staMap = new HashMap<String, String>();
				staMap.put("dict_id", dict_id);
				staMap.put("status", inf.get("STATUS"));
				int res = pubDictDao.updateStatusById(staMap);
				if (res == 1 || res == 0) {
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				} else {
					info.setRspCode(TpErrorCode.DATABASEERROR);
					info.setRspMsg("数据库异常");
					log.error(info.getRspCode() + " -- " + info.getRspMsg());
					return info;
				}

				int res1 = pubDictDao.updateStatusByParentId(staMap);
				if (res1 >= 0) {
					info.setRspMsg("更新成功");
					info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
				} else {
					info.setRspCode(TpErrorCode.DATABASEERROR);
					info.setRspMsg("数据库异常");
					log.error(info.getRspCode() + " -- " + info.getRspMsg());
					return info;
				}

			}

		}

		return info;
	}

	/**
	 * 获取开始条数和每页大小
	 *
	 * @param params
	 * @return Map<String,Object>
	 */
	public static Map<String, String> getPageInfo(Map<String, String> params) {
		Map<String, String> pageInfo = new HashMap<String, String>();
		// 分页
		int start = 0;
		int limit = 0;
		// 获取每页多少条
		if (params.get("pageSize") == null
				|| !TdNumberUtil.isNumber((String) params.get("pageSize"))) {
			limit = 10;
		} else {
			limit = Integer.parseInt((String) params.get("pageSize"));
		}
		// 获取开始条
		if (params.get("pageNum") == null
				|| !TdNumberUtil.isNumber((String) params.get("pageNum"))) {
			start = (1 - 1) * limit;
		} else {
			start = (Integer.parseInt((String) params.get("pageNum")) - 1)
					* limit;
		}

		pageInfo.put("start", String.valueOf(start));
		pageInfo.put("limit", String.valueOf(limit));

		return pageInfo;

	}

	@Override
	public RespInfo checkDict(Map<String, String> params) throws Exception {
		int count = pubDictDao.selectPubDictByCodeName(params);
		if (count > 0) {
			return new RespInfo(TpErrorCode.RECORD_EQUAL_ERROR, "参数名称与同级参数名称不能相同");
		}
		return new RespInfo(TpErrorCode.RSPCOD_SUCCESS, "检查成功");
	}

	private Map<String, DictObj> getDictTemp(List<Map<String, Object>> listMap){
		Map<String, DictObj> resMap = new HashMap<String,DictObj>();
		Set<String> keySet  = null;
		Iterator<String> it = null;
		String sKey         = null;
		String sDictType    = null;
		for (Map<String, Object> mapTemp : listMap) {
			keySet = mapTemp.keySet();
			it = keySet.iterator();

			while (it.hasNext()) {
				sKey = it.next().toUpperCase();
				if (null == sKey || sKey.indexOf("_CC_") == -1) {
					continue;
				}
				sDictType = DictUtils.getDictType(sKey);
				resMap.put(sDictType, (DictObj) SpringContext.getBean(TdRedisService.class).getObject(DICT_CODE_PRE + sDictType));
			}
			break;
		}
		return resMap;
	}
}
