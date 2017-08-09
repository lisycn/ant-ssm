package com.yuanlai.agent.dao;


import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.AuthItem;

import java.util.List;
import java.util.Map;

public interface AuthItemDao extends BaseDao<AuthItem, String> {
	/**
	 * 禁用/启用菜单
	 * @param isUse 0-禁用/1-启用
	 * @param itmId 菜单id
	 * @return
	 */
	int disOrEnable(String isUse, String itmId, String updObj, String updTim);
	
	/**
	 * 按照parentItmId查询菜单，按itemId降序排列 ，取第一个
	 * @param parentItmId
	 * @return
	 */
	AuthItem selectMaxByParentItemId(String parentItmId);
	List<AuthItem> selectByParentItemId(String parentItmId);
	
	/**
	 * 返回菜单信息及关联的系统信息
	 * @param itmId
	 * @return
	 */
	Map<String, Object> selectByItmId(String itmId);
	/**
	 * 更新菜单的 isLeaf字段
	 * @param itemId
	 * @param lsLeaf
	 * @return
	 */
	int updateItemIsLeaf(String itmId, String isLeaf);
	List<Map<String, Object>> selectItemTreeForTable_parent(Map<String, Object> map);
	List<Map<String, Object>> selectItemTreeForTable_child(Map<String, Object> map);
	int countItemTreeForTable_child(Map<String, Object> map);
	/**
	 * 查询重复
	 * @param authItem
	 * @param sysId 系统编号
	 * @param itmName 菜单名
	 * @param itmId 菜单id
	 * @return
	 */
	List<AuthItem> selectRepeat(AuthItem authItem);
}