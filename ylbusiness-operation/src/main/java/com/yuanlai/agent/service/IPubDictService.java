package com.yuanlai.agent.service;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.entity.Dict;
import com.yuanlai.agent.entity.DictObj;
import com.yuanlai.agent.entity.RespInfo;

import java.util.List;
import java.util.Map;

public interface IPubDictService {
	/**
	 * 获取并更新最新的数据字典列表
	 * @return
	 */
	public List<Dict> getList();

	/**
	 * 获取并更新最新的数据字典列表
	 * @return
	 */
    public List<Dict> getList(Dict dict);

    /**
	 * 通过数据字典类型那个和值  获取数据字典对应的 中文名称
	 * @param sDictCode
	 * @param sDictVal
	 * @return
	 */
	public String getDictName(String sDictCode, String sDictVal);

	/**
	 * 通过数据字典类型那个和中文名称  获取数据字典对应的值
	 * @param sDictCode
	 * @param sDictName
	 * @return
	 */
	public String getDictVal(String sDictCode, String sDictName);

	/**
	 * 通过数据字典类型那个和中文名称  获取数据字典对应的值
	 * @param sDictCode
	 * @param sDictName
	 * @param sDictLevel
	 * @return
	 */
	public String getDictVal(String sDictCode, String sDictName, String sDictLevel);

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param extMap
	 * 	      language:zh|en
	 * @param listMap
	 * @return
	 */
	public List<Map<String, Object>> formatDictListByLanguage(Map<String, String> extMap, List<Map<String, Object>> listMap);

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param listMap
	 * @return
	 */
	public List<Map<String,Object>> formatDictList(List<Map<String, Object>> listMap);

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param listMap
	 * @return
	 */
	public List<Map<String,Object>> formatMultiDictList(List<Map<String, Object>> listMap, String reg);

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * 将查询的结果集进行码表格式化操作 将_HIDE字段通过隐藏进行替换
	 * @param listMap
	 * @return
	 */
	public List<Map<String,Object>> formatAllList(List<Map<String, Object>> listMap);

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * @param paramMap
	 * @return
	 */
	public Map<String,Object> formatDictMap(Map<String, Object> paramMap);

	/**
	 * 将查询的结果集进行码表格式化操作 将_CC_字段通过码表进行中文替换，并新增_ZH的字段
	 * 将查询的结果集进行码表格式化操作 将_HIDE字段通过隐藏进行替换
	 * @param listMap
	 * @return
	 */
	public List<DictObj> getDictCodeList(String sDictCode);

	//查询出来所有的外层节点 add by liuyanwei 运营管理码表管理的交易
	/**
	 * 码表列表信息查询
	 *
	 * @param params
	 *    DICT_CODE：参数编码
	 *    DICT_NAME：参数名称
	 * @return RespInfo
	 *    rspCod：错误代码，01000000-成功
	 *    rspMsg：错误描述
	 *    total：总记录数
	 *    DATA：返回列表数据
	 *    	  Obj:基础信息
	 *        children：子级节点信息
	 *
	 * @throws Exception
	 */
	public RespInfo selectDictList(Map<String, String> params)throws TdRuntimeException;
	/**
	 * 码表信息修改
	 *
	 * @param params
	 *    DICT_NAME：参数名称
	 *    DICT_ID：参数ID
	 *    DICT_VALUE：参数值【仅适用于子级】
	 *    DICT_ABR:参数描述
	 * @return RespInfo
	 *    rspCod：错误代码，01000000-成功
	 *    rspMsg：错误描述
	 *
	 * @throws Exception
	 */
	public RespInfo updatePubDictInf(Map<String, String> params) throws Exception;
	/**
	 * 码表信息添加
	 *
	 * @param params
	 *    DICT_NAME：参数名称
	 *    DICT_CODE：参数编码
	 *    DICT_ABR:参数描述
	 *    keyarray:添加多个时的数组【仅适用于子级】
	 *    DICT_NAME_N:添加多个时的某个对于那个的参数名称【仅适用于子级】
	 *    DICT_VALUE_N:添加多个时的某个对于那个的参数值【仅适用于子级】
	 * @return RespInfo
	 *    rspCod：错误代码，01000000-成功
	 *    rspMsg：错误描述
	 *
	 * @throws Exception
	 */
	public RespInfo addPubDictInf(Map<String, String> params) throws Exception;
	/**
	 * 码表信息删除
	 *
	 * @param params
	 *    DICT_IDS：格式：'000101','0000102'
	 * @return RespInfo
	 *    rspCod：错误代码，01000000-成功
	 *    rspMsg：错误描述
	 *
	 * @throws Exception
	 */
	public RespInfo delPubDictInf(Map<String, String> params) throws Exception;

	/**
	 * 修改码表信息状态
	 *
	 * @param params
	 *    DICT_IDS：格式：'000101','0000102'
	 *    STATUS: 0 禁用  1 启用
	 * @return RespInfo
	 *    rspCod：错误代码，01000000-成功
	 *    rspMsg：错误描述
	 *
	 * @throws Exception
	 */
	public RespInfo updatePubDictStatus(Map<String, String> params) throws Exception;

	/**
	 * 检查添加单条码表参数名时是否同级已存在信息
	 * @param params
	 *        DICT_NAME：参数名称
	 *        DICT_CODE：参数编码
	 * @return RespInfo
	 *    rspCod：错误代码，01000000-成功
	 *    rspMsg：错误描述
	 *
	 * @throws Exception
	 */
	public RespInfo checkDict(Map<String, String> params) throws Exception;

}
