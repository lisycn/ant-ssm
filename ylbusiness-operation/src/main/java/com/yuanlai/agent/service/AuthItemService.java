package com.yuanlai.agent.service;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.entity.AuthItem;

import java.util.List;
import java.util.Map;

/**
 * @author chen_yq add 2016.04.27
 */

/**
 * 菜单service
 * 3位一个级别，每一级别从101开始
 * 101顶级菜单
 * 101101、101102二级
 * 101101101、101102101三级，依次类推
 * @author chen_yq add 2016.04.26
 */
public interface AuthItemService {
	
	/**
	 * 分页查询菜单
	 * @param authItem
	 * @param pageSize 每页条数（默认15条）
	 * @param pageNum  当前页数（默认第1页）
	 * @return list:列表<br>total:记录数
	 * @throws Exception
	 */
	Map<String, Object> selectByPage(AuthItem authItem, int pageSize, int pageNum) throws TdRuntimeException;
	/**
	 * 添加菜单
	 * @param authItem parentItmId，itmTyp，itmName必填
	 * @return
	 * @throws Exception
	 */
	void add(AuthItem authItem)throws TdRuntimeException;

	/**
	 * 修改菜单
	 * @param authItem itmId必填
	 * @return
	 * @throws Exception
	 */
	void update(AuthItem authItem)throws TdRuntimeException;
	/**
	 * 按照菜单id查询菜单
	 * @param itmId
	 * @return 菜单信息及关联系统信息
	 * @throws Exception
	 */
	Map<String, Object> selectByItmId(String itmId)throws TdRuntimeException;
	/**
	 * 禁用/启用菜单
	 * @param isUse 0-禁用；1-启用
	 * @param itmId 菜单ID
	 * @return
	 * @throws TdRuntimeException
	 */
	void disOrEnable(String isUse, String itmId, String updObj)throws TdRuntimeException;
	/**
	 * 删除菜单
	 * @param itmId 菜单id
	 * @return
	 * @throws TdRuntimeException
	 */
	void delByItmId(String itmId)throws TdRuntimeException;
	/**
	 * 查询菜单表格树
	 * @param authItem itmId（默认000,顶级菜单），sysId可选
	 * @return
	 */
	Map<String, Object> selectItemTreeTable(AuthItem authItem)throws TdRuntimeException;
	/**
	 * 获取菜单树
	 * @param parentItems
	 * @param childItems
	 * @return
	 * @throws TdRuntimeException
	 */
	List<Map<String, Object>> getItemTree(List<Map<String, Object>> parentItems, List<Map<String, Object>> childItems)throws TdRuntimeException;
}
