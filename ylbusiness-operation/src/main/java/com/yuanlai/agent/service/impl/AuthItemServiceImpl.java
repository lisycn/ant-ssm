package com.yuanlai.agent.service.impl;

import com.tangdi.def.utils.common.TdDateUtil;
import com.tangdi.def.utils.common.TdStringUtil;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.AuthCommonUtil;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.dao.AuthItemDao;
import com.yuanlai.agent.dao.AuthRoleItemDao;
import com.yuanlai.agent.entity.AuthItem;
import com.yuanlai.agent.service.AuthItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author chen_yq add 2016.04.26
 */
@Service
public class AuthItemServiceImpl implements AuthItemService {
	@Autowired
	private AuthItemDao authItemDao;
	@Autowired
	private AuthRoleItemDao authRoleItemDao;
	
	private static Logger log = LoggerFactory.getLogger(AuthItemServiceImpl.class);
	@Override
	public Map<String, Object> selectByPage(AuthItem authItem, int pageSize, int pageNum) throws TdRuntimeException {
		log.info("分页查询菜单信息");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(authItem);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		log.info("查询条件paraMap:"+paraMap.toString());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String,Object>> retList = authItemDao.selectByPager(paraMap);
			int total = authItemDao.countByCondition(paraMap);
			map.put(AuthConstants.COMMON_LIST, retList);
			map.put(AuthConstants.COMMON_TOTAL, total);
			return map;
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}
	@Override
	public void add(AuthItem authItem) throws TdRuntimeException {
		authItem.setItmId(null);//itemId,由系统生成
		log.info("添加菜单："+authItem.toString());
		Map<String,Object> paraMap = AuthBeanUtil.toMap(authItem);
		TdStringUtil.chkRequiredParam(paraMap, AuthConstants.ITEM_PARENTITMID,AuthConstants.ITEM_ITMTYP,AuthConstants.ITEM_ITMNAME);
		
		String parentItmId = authItem.getParentItmId();
		if(AuthConstants.ITEM_ROOT_ITMID.equals(parentItmId)){
			throw new TdRuntimeException("不能添加顶级菜单");
		}
		log.info("查询上级菜单获取系统编号");
		AuthItem parentItem = authItemDao.selectByPrimaryKey(parentItmId);
		if(parentItem == null){//未查询到ItemId=ParentItmId的菜单信息
			throw new TdRuntimeException("未查询到上级菜单 "+parentItmId+" 的信息");
		}
		authItem.setSysId(parentItem.getSysId());
		List<AuthItem> list = authItemDao.selectRepeat(authItem);
		if(!CollectionUtils.isEmpty(list)){
			log.info("--->菜单名重复");
			throw new TdRuntimeException("菜单名称重复");
		}
		log.info("根据parentItmId "+parentItmId+" 获取编号最大的itmId");
		AuthItem bigItem = authItemDao.selectMaxByParentItemId(parentItmId);
		if(bigItem == null){//当前parentItmId下没有子级菜单，新建
			authItem.setItmId(parentItmId+AuthConstants.STRING_101);//新一级菜单从101开始
		}else{//当前parentItmId下有子级菜单，降序排列，itemId+1
			String tmpItemId = ""+(Long.valueOf(bigItem.getItmId())+1);
			if(tmpItemId.contains(AuthConstants.ITEM_ROOT_ITMID)){
				throw new TdRuntimeException("菜单编号已达到最大值");
			}
			authItem.setItmId(tmpItemId);
		}
		if(authItem.getItmId().length() == AuthConstants.INT_30){
			throw new TdRuntimeException("菜单长度已达到最大值");
		}
		authItem.setCreTim(TdDateUtil.getDateTime());
		try {
			log.info("添加菜单："+authItem.toString());
			authItemDao.insertSelective(authItem);
			if(AuthConstants.STRING_1.equals(parentItem.getIsLeaf())){//上级菜单更新为非叶子节点
				authItemDao.updateItemIsLeaf(authItem.getParentItmId(), AuthConstants.STRING_0);
			}
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}
	@Override
	public void update(AuthItem authItem) throws TdRuntimeException {
		Map<String,Object> paraMap = AuthBeanUtil.toMap(authItem);
		TdStringUtil.chkRequiredParam(paraMap, AuthConstants.ITEM_ITMID,AuthConstants.ITEM_ITMNAME);

		AuthItem oldItem = authItemDao.selectByPrimaryKey(authItem.getItmId());
		if(oldItem == null){
			throw new TdRuntimeException("未查询到菜单信息");
		}
		if(AuthConstants.ITEM_ROOT_ITMID.equals(oldItem.getParentItmId())){
			throw new TdRuntimeException("不能更新顶级菜单");
		}
		authItem.setSysId(oldItem.getSysId());
		List<AuthItem> list = authItemDao.selectRepeat(authItem);
		if(!CollectionUtils.isEmpty(list)){
			throw new TdRuntimeException("菜单名称重复");
		}
		authItem.setUpdTim(TdDateUtil.getDateTime());
		log.info("修改菜单："+authItem.toString());
		try {
			authItemDao.updateByPrimaryKeySelective(authItem);
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}
	
	@Override
	public void disOrEnable(String isUse,String itmId,String updObj)throws TdRuntimeException{
		log.info("禁用/启用菜单isUse={}，itmId=",isUse,itmId);
		if(AuthConstants.STRING_1.equals(isUse)){
			enable(itmId,updObj);
		}else{
			disable(itmId,updObj);
		}
	}
	
	private void enable(String itmId,String updObj) throws TdRuntimeException {
		log.info("启用菜单 {}",itmId);
		AuthItem item = authItemDao.selectByPrimaryKey(itmId);
		if(item == null){
			throw new TdRuntimeException("未查询到菜单信息");
		}
		if(AuthConstants.ITEM_ROOT_ITMID.equals(item.getParentItmId())){
			throw new TdRuntimeException("不能操作顶级菜单");
		}
		
		log.info("查询父级菜单");
		AuthItem parentItem = authItemDao.selectByPrimaryKey(item.getParentItmId());
		if(parentItem == null){
			throw new TdRuntimeException("未查询到父级菜单信息");
		}
		if(AuthConstants.STRING_0.equals(parentItem.getIsUse())){
			throw new TdRuntimeException("请先启用父级菜单");
		}
		
		try {
			log.info("修改父级菜单为非叶子节点");
			authItemDao.updateItemIsLeaf(parentItem.getItmId(), AuthConstants.STRING_0);
			log.info("启用菜单，并设置为叶子节点");
			item.setIsUse(AuthConstants.STRING_1);
			item.setIsLeaf(AuthConstants.STRING_1);
			item.setUpdObj(updObj);
			item.setUpdTim(TdDateUtil.getDateTime());
			authItemDao.updateByPrimaryKeySelective(item);
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}
	private void disable(String itmId,String updObj) throws TdRuntimeException {
		log.info("禁用菜单 {}",itmId);
		AuthItem item = authItemDao.selectByPrimaryKey(itmId);
		if(item == null){
			throw new TdRuntimeException("未查询到菜单信息");
		}
		if(item.getParentItmId().equals(AuthConstants.ITEM_ROOT_ITMID)){
			throw new TdRuntimeException("不能操作顶级菜单");
		}
		log.info("查询子菜单是否有启用");
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put(AuthConstants.ITEM_PARENTITMID, item.getItmId());
		paraMap.put(AuthConstants.COMMON_ISUSE, AuthConstants.STRING_1);
		List childList = authItemDao.selectByCondition(paraMap);
		if(!CollectionUtils.isEmpty(childList)){
			throw new TdRuntimeException("请先禁用子菜单");
		}
		try {
			log.info("禁用菜单");
			authItemDao.disOrEnable(AuthConstants.STRING_0, itmId,updObj,TdDateUtil.getDateTime());
			paraMap.put(AuthConstants.ITEM_PARENTITMID, item.getParentItmId());
			List brotherList = authItemDao.selectByCondition(paraMap);
			if(CollectionUtils.isEmpty(brotherList)){
				log.info("设置父菜单为叶子节点");
				authItemDao.updateItemIsLeaf(item.getParentItmId(), AuthConstants.STRING_1);
			}
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	public Map<String, Object> selectByItmId(String itmId) throws TdRuntimeException {
		Map<String, Object> mapItem = authItemDao.selectByItmId(itmId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AuthConstants.ITEM_AUTHITEM, mapItem);
		return map;
	}
	@Override
	public void delByItmId(String itmId) throws TdRuntimeException {
		log.info("删除菜单itmId="+itmId);
		AuthItem item = authItemDao.selectByPrimaryKey(itmId);
		if(item == null){
			throw new TdRuntimeException("未查询到菜单信息");
		}
		if(item.getParentItmId().equals(AuthConstants.ITEM_ROOT_ITMID)){
			throw new TdRuntimeException("不能操作顶级菜单");
		}
		if(AuthConstants.STRING_1.equals(item.getIsUse())){
			throw new TdRuntimeException("菜单状态为启用，请禁用后删除");
		}
		log.info("查询子菜单");
		List<AuthItem> childList = authItemDao.selectByParentItemId(item.getItmId());
		if(!CollectionUtils.isEmpty(childList)){
			throw new TdRuntimeException("请先删除子菜单");
		}
		try{
			authItemDao.deleteByPrimaryKey(itmId);
			log.info("删除角色与菜单关联表");
			authRoleItemDao.deleteByItemId(itmId);
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}
	
	@Override
	public Map<String, Object> selectItemTreeTable(AuthItem authItem) throws TdRuntimeException{
		log.info("查询菜单树状表格:"+authItem.toString());
		Map<String, Object> map = AuthBeanUtil.toMap(authItem);
		log.info("查询全部顶级菜单");
		List<Map<String, Object>> parentItems = authItemDao.selectItemTreeForTable_parent(map);
		log.info("查询非顶级菜单总数");
		int count = authItemDao.countItemTreeForTable_child(map);
		map.put(AuthConstants.COMMON_LIMIT, count);
		log.info("查询非顶级菜单");
		List<Map<String, Object>> childItems = authItemDao.selectItemTreeForTable_child(map);
		List<Map<String,Object>> itemTree = getItemTree(parentItems,childItems);
		Map<String, Object> retmap = new HashMap<String, Object>();
		retmap.put(AuthConstants.COMMON_LIST, itemTree);
		return retmap;
	}
	/**
	 * 循环父级菜单,组装所有父菜单的菜单树
	 * @param parentItems
	 * @param childItems
	 * @return
	 * @throws TdRuntimeException
	 */
	public List<Map<String, Object>> getItemTree(List<Map<String, Object>> parentItems,List<Map<String, Object>> childItems)throws TdRuntimeException{
		List<Map<String, Object>> menu = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> parentItem : parentItems){
			menu.add(getMenuForOneParent(parentItem,childItems));
		}
		return menu;
	}
	/**
	 * 组装一个父菜单的菜单树
	 * @param parentItem
	 * @param childItems
	 * @return
	 */
	private Map<String, Object> getMenuForOneParent(Map<String,Object> parentItem,List<Map<String, Object>> childItems){
		List<Map<String, Object>> menu = new ArrayList<Map<String,Object>>();
		for(int i=0; i<childItems.size() ;i++){
			Map<String,Object> childItem = childItems.get(i);
			if(parentItem.get(AuthConstants.ITEM_ITMID).equals(childItem.get(AuthConstants.ITEM_PARENTITMID))){
				menu.add(getMenuForOneParent(childItem,childItems));
			}
		}
		if (menu.size() > 0) {
			parentItem.put(AuthConstants.COMMON_CHILDREN, menu);//有子节点设置children
			for (int i = 0; i < menu.size(); i++) {//移除已组装过的子节点
				childItems.remove(menu.get(i));
			}
		}
		return parentItem;
	}
}
