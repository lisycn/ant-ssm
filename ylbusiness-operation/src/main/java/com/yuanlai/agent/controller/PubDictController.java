package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.TdCommonUtil;
import com.yuanlai.agent.constants.TpConstant;
import com.yuanlai.agent.constants.TpErrorCode;
import com.yuanlai.agent.entity.Dict;
import com.yuanlai.agent.entity.DictObj;
import com.yuanlai.agent.entity.RespInfo;
import com.yuanlai.agent.service.IPubDictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict/")
public class PubDictController extends TdBaseController {

	@Autowired
	private IPubDictService pubDictService;

	/**
	 * 查询码表值列表
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 *             TdRuntimeException
	 */
	@RequestMapping("/qryDictList")
	@ResponseBody
	public Map<String, Object> qryDictList(HttpServletRequest req) throws Exception {
		Map<String, Object> reqMap = getReqMap(req);
		String sType = (String) reqMap.get("TYPE");
		RespInfo respInfo = new RespInfo();
		respInfo.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
		respInfo.putVal("DATA", pubDictService.getDictCodeList(sType));
		return respInfo.toMap();
	}

	/**
	 * 查询码表值列表
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 *             TdRuntimeException
	 */
	@RequestMapping("/qryDictMutiList")
	@ResponseBody
	public Map<String, Object> qryDictMutiList(HttpServletRequest req) throws Exception {
		Map<String, Object> reqMap = getReqMap(req);

		log.info("传入的参数reqMap:" + reqMap);

		String sTypes    = (String) reqMap.get("TYPES");
		String sLanguage = (String) reqMap.get(TpConstant.SYS_LANGUAGE_NAME);
		if (null != sTypes && sTypes.endsWith(",")) {
			sTypes = sTypes.substring(0, sTypes.length() - 1);
		}

		String[] arr = sTypes.split(",");
		Map<String, Object> typeMap = new HashMap<String, Object>();
		RespInfo respInfo = new RespInfo();
		List<DictObj> tempList = null;
		for (String sTemp : arr) {
			try {
				log.info("码表类型：" + sTemp);
				tempList = pubDictService.getDictCodeList(sTemp);
				//设置如果前端为英文语言环境，则将DICT_NAME字段内容更新为ABR的字段信息
				if(TpConstant.SYS_LANGUAGE_EN.equalsIgnoreCase(sLanguage)){
					for(DictObj dictObj:tempList){
						dictObj.setAttrMap("DICT_NAME", dictObj.getAttrMap("ABR"));
					}
				}
				typeMap.put(sTemp, tempList);
			} catch (Exception e) {
				log.error("{}",e);
				log.info("码表类型：%s未找到！", sTemp);
			}
		}
		log.info("返回Map：" + typeMap);
		respInfo.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
		respInfo.putVal("DATA", typeMap);
		return respInfo.toMap();
	}

	/**
	 * 将请求中的参数封装到一个Map中，仅适用于字段为字符串类型
	 *
	 * @param req
	 * @return Map 包含所有的请求参数
	 */
	public static Map<String, Object> getReqMap(HttpServletRequest req) {
		Map<String, Object> mapRes = new HashMap<String, Object>();
		if (null == req) {
			return mapRes;
		}

		// 循环遍历请求的参数
		Map<String, String[]> tmp = req.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				// 将循环的参数添加到Map中
				mapRes.put(key, values.length >= 1 ? values[0].trim() : null);
			}
		}

		return mapRes;
	}

	@RequestMapping("qryPubDictList")
	@ResponseBody
	public Map<String, Object> qryPubDictList(HttpServletRequest request) throws TdRuntimeException {
		log.info("start qryPubDictList");
		RespInfo info = null;
		Map<String, String> params = TdCommonUtil.getReqMap(request);
		log.info("start qryPubDictList=========" + params.toString());
		info = pubDictService.selectDictList(params);
		try {
			log.info("qryPubDictList");
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
		return info.toMap();
	}

	@RequestMapping("updDictInf")
	@ResponseBody
	public Map<String, Object> updDictInf(HttpServletRequest request) throws Exception {
		log.info("开始执行修改参数updPubDict");
		RespInfo info = null;
		Map<String, String> params = TdCommonUtil.getReqMap(request);
		params.put("dict_id", params.get("DICT_ID"));
		params.put("dict_name", params.get("DICT_NAME"));
		params.put("dict_value", params.get("DICT_VALUE"));
		params.put("dict_code", params.get("DICT_CODE"));
		params.put("dict_abr", params.get("DICT_ABR"));
		params.put("dict_level", params.get("DICT_LEVEL"));
		log.info("start qryPubDictList=========" + params.toString());
		info = pubDictService.updatePubDictInf(params);
		try {
			log.info("updPubDict");
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
		return info.toMap();
	}

	/**
	 * 实现增加一条码表数据
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addDictInf")
	@ResponseBody
	public Map<String, Object> addDictInf(HttpServletRequest request) throws Exception {
		log.info("start addPubDict");
		RespInfo info = null;
		Map<String, String> params = TdCommonUtil.getReqMap(request);
		params.put("dict_level", params.get("DICT_LEVEL"));
		params.put("parent_id", params.get("PARENT_ID"));
		params.put("dict_code", params.get("DICT_CODE"));
		params.put("dict_name", params.get("DICT_NAME"));
		params.put("dict_value", params.get("DICT_VALUE"));
		params.put("abr", params.get("DICT_ABR"));
		log.info("参数名称 dict_level=========" + params.get("dict_level"));
		log.info("参数名称 parent_id=========" + params.get("parent_id"));
		log.info("参数名称 dict_code=========" + params.get("dict_code"));
		log.info("参数名称 dict_value=========" + params.get("dict_value"));
		log.info("参数名称 dict_abr=========" + params.get("abr"));

		log.info("start qryPubDictList=========" + params.toString());
		info = pubDictService.addPubDictInf(params);
		try {
			log.info("addPubDict");
		} catch (Exception e) {
			return genErrorResult(e.getMessage());
		}
		return info.toMap();
	}

	@RequestMapping("delDictInf")
	@ResponseBody
	public Map<String, Object> delDictInf(HttpServletRequest request) throws Exception {
		log.info("start delDictInf");
		RespInfo info = null;
		log.info("************接收到的请求参数" + TdCommonUtil.getReqMap(request).toString());
		Map<String, String> params = TdCommonUtil.getReqMap(request);
		info = pubDictService.delPubDictInf(params);
		return info.toMap();
	}

	@RequestMapping("updDictStatus")
	@ResponseBody
	public Map<String, Object> updDictStatus(HttpServletRequest request) throws Exception {
		log.info("start updDictStatus");
		RespInfo info = null;
		log.info("************接收到的请求参数" + TdCommonUtil.getReqMap(request).toString());
		Map<String, String> params = TdCommonUtil.getReqMap(request);
		info = pubDictService.updatePubDictStatus(params);
		return info.toMap();
	}

	@RequestMapping("updDictRediu")
	@ResponseBody
	public Map<String, Object> updDictRediu(HttpServletRequest request) throws Exception {
		log.info("start updDictStatus");
		RespInfo info = new RespInfo();
		log.info("************接收到的请求参数" + TdCommonUtil.getReqMap(request).toString());
		List<Dict> dictList = new ArrayList<Dict>();
		dictList = pubDictService.getList();
		log.info("dictList={}", dictList);
		info.setRspMsg("刷新成功");
		info.setRspCode(TpErrorCode.RSPCOD_SUCCESS);
		return info.toMap();
	}



	@RequestMapping("checkDict")
	@ResponseBody
	public Map<String, Object> checkDict(HttpServletRequest request) throws Exception {
		log.info("start checkDict");
		RespInfo info = new RespInfo();
		Map<String, String> params = TdCommonUtil.getReqMap(request);
		params.put("dict_code", params.get("DICT_CODE"));
		params.put("dict_name", params.get("DICT_NAME"));
		log.info("************接收到的请求参数" + TdCommonUtil.getReqMap(request).toString());
		info=pubDictService.checkDict(params);
		return info.toMap();
	}

}
