package com.yuanlai.agent.dao;


import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 数据字典接口
 * @author
 *
 */
public interface PubDictDao extends BaseDao<Dict, Exception> {
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Dict> selectPageList(Map<String, Object> con) throws Exception;


	public List<Dict> selectList(Dict dict) throws Exception;

	/**
	 * 获取根节点SeqNum最大的记录。
	 * @return
	 * @throws Exception
	 */
	public int selectDictBySeqNumMax(Dict dict)  throws Exception;

	public int selectDictByDictIdMax()  throws Exception;

	/**
	 * 批量修改数据字典状态
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int updateDictStatus(Map<String, Object> con) throws Exception;

	public int addList(List<Dict> dict) throws Exception;



	/**
	 * add by liuyanwei 以下全为运营管理需要用的码表管理的交易
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectDictList(Map<String, String> map);

	public int  selectDictListCount(Map<String, String> map);


	List<Map<String,Object>> selectDictsList(Map<String, String> map);

	public int updatePubDictInf(Map<String, String> inf) throws Exception;


	public int addPubDictInf(Map<String, String> inf) throws Exception;


	public int  selectPubDictByDictIdMax() throws Exception;

	public int  selectPubDictBySeqNumMax(String parent_id) throws Exception;


	public int  selectPubDictBySeqNumCount(String parent_id) throws Exception;


	public int  selectPubDictByCode(String dict_code)  throws Exception;

	public int  selectPubDictByCodeValue(Map<String, String> inf)  throws Exception;


	public int  selectPubDictByCode_upd(Map<String, String> inf)  throws Exception;

	public int  selectPubDictByCodeValue_upd(Map<String, String> inf)  throws Exception;

	//删除
	//需要递归查询出来所有的子级，然后判断删除
	public int delQryDictListCount(String parent_id)  throws Exception;
	List<Map<String,Object>> delQryDictList(String parent_id);
	public int delQryDictAllChildListCount(String dict_code)  throws Exception;
	List<Map<String,Object>> delQryDictAllChildList(String dict_code);


	public int  deleteById(String dict_id)  throws Exception;

	public int  deleteByParentId(String parent_id)  throws Exception;

	//修改状态
	public int  updateStatusById(Map<String, String> inf)  throws Exception;

	public int  updateStatusByParentId(Map<String, String> inf)  throws Exception;


	public int  selectPubDictByCodeName_upd(Map<String, String> inf)  throws Exception;

	public int  selectPubDictByCodeName(Map<String, String> inf)  throws Exception;

}
