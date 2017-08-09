package com.yuanlai.agent.service.impl;

import com.tangdi.def.base.seq.TdSequenceService;
import com.tangdi.def.utils.common.TdDateUtil;
import com.tangdi.def.utils.common.TdStringUtil;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.AuthCommonUtil;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.dao.AuthItemDao;
import com.yuanlai.agent.dao.AuthRoleDao;
import com.yuanlai.agent.dao.AuthRoleItemDao;
import com.yuanlai.agent.dao.AuthUsrRoleDao;
import com.yuanlai.agent.entity.AuthRole;
import com.yuanlai.agent.entity.AuthRoleItemKey;
import com.yuanlai.agent.entity.AuthUsrRoleKey;
import com.yuanlai.agent.service.AuthItemService;
import com.yuanlai.agent.service.AuthRoleService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色Service实现
 * @author chen_yq add 2016.04.29
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

	@Autowired
	private AuthRoleDao authRoleDao;
	@Autowired
	private AuthRoleItemDao authRoleItemDao;
	@Autowired
	private AuthItemDao authItemDao;
	@Autowired
	private AuthUsrRoleDao authUsrRoleDao;
	@Autowired
	@Qualifier("authLogNoService")
	private TdSequenceService authLogNoService;
	@Autowired
	private AuthItemService authItemService;
//	@Autowired
//	private WorkFlowConfigService workFlowConfigService;
	private static Logger log = LoggerFactory.getLogger(AuthItemServiceImpl.class);
	
	@Override
	public Map<String, Object> selectByPage(AuthRole authRole, int pageSize, int pageNum) throws TdRuntimeException {
		log.info("分页查询角色信息");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(authRole);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		log.info("查询条件paraMap:"+paraMap.toString());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String,Object>> retList = authRoleDao.selectByPager(paraMap);
			int total = authRoleDao.countByPager(paraMap);
			map.put(AuthConstants.COMMON_LIST, retList);
			map.put(AuthConstants.COMMON_TOTAL, total);
			return map;
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	public void add(AuthRole authRole) throws TdRuntimeException {
		log.info("添加角色:"+authRole.toString());
		Map<String,Object> paraMap = AuthBeanUtil.toMap(authRole);
		TdStringUtil.chkRequiredParam(paraMap, AuthConstants.ROLE_ROLENAME,AuthConstants.COMMON_SYSID);
		chkRepeat(authRole);
		authRole.setCreTim(TdDateUtil.getDateTime());
		authRole.setRoleId(authLogNoService.create());
		try {
			log.info("入库前的角色:"+authRole.toString());
			authRoleDao.insertSelective(authRole);
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	/**
	 * 校验角色名是否重复
	 * @param authRole
	 * @param sysId 系统编号
	 * @param roleName 角色名
	 * @param roleId 角色id
	 */
	private void chkRepeat(AuthRole authRole){
		List<AuthRole> repeatList = authRoleDao.selectRepeat(authRole);
		if(CollectionUtils.isNotEmpty(repeatList)){
			throw new TdRuntimeException("角色名重复");
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(AuthRole authRole) throws TdRuntimeException {
		log.info("更新前的角色:"+authRole.toString());
		Map<String,Object> paraMap = AuthBeanUtil.toMap(authRole);
		TdStringUtil.chkRequiredParam(paraMap, AuthConstants.ROLE_ROLEID,AuthConstants.ROLE_ROLENAME,AuthConstants.COMMON_SYSID);
		chkRepeat(authRole);
		authRole.setUpdTim(TdDateUtil.getDateTime());
		try {
			authRoleDao.updateByPrimaryKeySelective(authRole);
			Map<String, Object> map = authRoleDao.selectByRoleId(authRole.getRoleId());
			log.info("更新工作流的角色名");
//			workFlowConfigService.updateSystemrolename(String.valueOf(map.get("sysId")), String.valueOf(map.get("roleId")), String.valueOf(map.get("roleName")));
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delByRoleId(String roleId) throws TdRuntimeException {
		log.info("删除角色,roleId:"+roleId);
		AuthRole roleTmp = authRoleDao.selectByPrimaryKey(roleId);
		if(roleTmp == null){
			throw new TdRuntimeException("未查询到角色信息");
		}
		if(AuthConstants.STRING_1.equals(roleTmp.getIsUse())){
			throw new TdRuntimeException("角色状态为启用，请禁用后删除");
		}
		try {
			authRoleDao.deleteByPrimaryKey(roleId);
			log.info("删除工作流岗位与角色对应关系");
//			workFlowConfigService.delPositionMappingByRolecode(roleId);
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void disOrEnable(String isUse, String roleId,String updObj) throws TdRuntimeException {
		log.info("禁用/启用角色isUse={}，roleId={}",isUse,roleId);
		if(AuthConstants.STRING_1.equals(isUse)){
			isUse = AuthConstants.STRING_1;
		}else{
			isUse = AuthConstants.STRING_0;
		}
		try {
			authRoleDao.disOrEnable(isUse, roleId,updObj,TdDateUtil.getDateTime());
			log.info("禁用/启用工作流岗位与角色对应关系");
//			workFlowConfigService.disOrEnablePositionMappingByRolecode(roleId, isUse);
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	public Map<String, Object> selectByRoleId(String roleId) throws TdRuntimeException {
		Map<String, Object> authRoleMap = authRoleDao.selectByRoleId(roleId);
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put(AuthConstants.ROLE_AUTHROLE, authRoleMap);
		return map;
	}

	@Override
	public Map<String, Object> assignAuthPre(String roleId) throws TdRuntimeException {
		log.info("权限分配预处理,查询角色信息，roleId="+roleId);
		AuthRole role = authRoleDao.selectByPrimaryKey(roleId);
		if(role == null){
			throw new TdRuntimeException("未查询到角色 "+roleId+" 的信息");
		}
		String sysId = role.getSysId();
		log.info("角色所属系统id："+sysId);
		if(StringUtils.isEmpty(sysId)){
			throw new TdRuntimeException("角色 "+roleId+" 的系统id为空");
		}
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put(AuthConstants.COMMON_SYSID, sysId);
		log.info("查询角色 {} 的当前菜单id集合(叶子节点)",roleId);
		List<String> roleCurrItemIdList = authRoleItemDao.selectLeafItmIdByRoleId(roleId);
		log.info("查询系统 {} 的顶级菜单",sysId);
		List<Map<String, Object>> parentItems = authItemDao.selectItemTreeForTable_parent(paraMap);
		log.info("查询系统 {} 的非顶级菜单总数 ",sysId);
		int count = authItemDao.countItemTreeForTable_child(paraMap);
		log.info("查询系统 {} 的非顶级菜单",sysId);
		paraMap.put(AuthConstants.COMMON_LIMIT, count);
		List<Map<String, Object>> childItems = authItemDao.selectItemTreeForTable_child(paraMap);
		List<Map<String,Object>> itemTree = authItemService.getItemTree(parentItems,childItems);
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put(AuthConstants.COMMON_CHILDREN, itemTree);
		Map<String, Object> retmap = new HashMap<String, Object>();
		retmap.put(AuthConstants.COMMON_ROLECURRITEMIDLIST, roleCurrItemIdList);//显示已有菜单
		retmap.put(AuthConstants.COMMON_ITEMTREE, tmp);
		return retmap;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void assignAuth(String roleId, String itmIds) throws TdRuntimeException {
		log.info("分配权限,角色id:{}，菜单id:{}",roleId,itmIds);
		if(itmIds==null || "".equals(itmIds=itmIds.trim())){
			authRoleItemDao.deleteByRoleId(roleId);
			return;
		}
		String[] ary = itmIds.split(AuthConstants.COMMON_COMMA);
		Map<String,String> itemIdMap = new HashMap<String,String>();
		for(String itemId : ary){
			itemIdMap.putAll(getItemIdMap(itemId));
		}
		Set<String> set = itemIdMap.keySet();
		List<AuthRoleItemKey> authRoleItemList = new ArrayList<AuthRoleItemKey>();
		for(String itemId : set){
			AuthRoleItemKey tmp = new AuthRoleItemKey();
			tmp.setRoleId(roleId);
			tmp.setItmId(itemId);
			authRoleItemList.add(tmp);
		}
		try {
			log.info("删除角色 {} 之前的权限",roleId);
			authRoleItemDao.deleteByRoleId(roleId);
			authRoleItemDao.insertMulti(authRoleItemList);
		} catch (Exception e) {
			log.info("数据库异常：{}",e.getMessage());
			throw new TdRuntimeException("数据库错误");
		}
	}
	/**
	 * 根据菜单id获取本级菜单id及其父级菜单id
	 * @param itemId
	 * @return
	 */
	public Map<String,String> getItemIdMap(String itemId){
		Map<String,String> map = new HashMap<String,String>();
		if(itemId==null || "".equals(itemId = itemId.trim())){
			return map;
		}
		map.put(itemId, itemId);//放入本级菜单id
		while(true){//循环放入父级菜单id
			if(itemId.length()<=3){
				map.put(itemId, itemId);
				break;
			}else{
				itemId = itemId.substring(0, itemId.length()-3);
				map.put(itemId, itemId);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> assignRolePre(String usrId) throws TdRuntimeException {
		log.info("查询用户 {} 当前有效角色",usrId);
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> usrCurrRoleIdList = authUsrRoleDao.selectUsrCurrValidRoleId(usrId);
		log.info("查询全部有效角色总数");
		int count = authRoleDao.countAllValidRole();
		log.info("查询全部有效角色");
		List<Map<String,Object>> allRoleList = authRoleDao.selectAllValidRole(count);
		map.put(AuthConstants.COMMON_USRCURRROLEIDLIST, usrCurrRoleIdList);//用户当前角色ID集合
		map.put(AuthConstants.COMMON_ALLROLELIST, allRoleList);
		if(usrCurrRoleIdList==null || usrCurrRoleIdList.size()<=0){
			map.put(AuthConstants.COMMON_USRCURRROLEIDLIST, new ArrayList<String>());
		}
		if(allRoleList == null || allRoleList.size()<=0){
			map.put(AuthConstants.COMMON_ALLROLELIST, new ArrayList<String>());
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void assignRole(String usrId, String roleIds) throws TdRuntimeException {
		log.info("用户id:{}，角色id:{}",usrId,roleIds);
		if(roleIds==null || "".equals(roleIds=roleIds.trim())){
			log.info("删除用户 {} 之前的角色",usrId);
			authUsrRoleDao.deleteByUsrId(usrId);
			return;
		}
		String[] ary = roleIds.split(AuthConstants.COMMON_COMMA);
		List<AuthUsrRoleKey> authUsrRoleList = new ArrayList<AuthUsrRoleKey>();
		for(String roleId : ary){
			AuthUsrRoleKey key = new AuthUsrRoleKey();
			key.setUsrId(usrId);
			key.setRoleId(roleId);
			authUsrRoleList.add(key);
		}
		try {
			log.info("删除用户 {} 之前的角色",usrId);
			authUsrRoleDao.deleteByUsrId(usrId);
			authUsrRoleDao.insertMulti(authUsrRoleList);
		} catch (Exception e) {
			log.info("数据库异常：{}",e.getMessage());
			throw new TdRuntimeException("数据库错误");
		}
	}
	@Override
	public List<Map<String, Object>> selectValidBySysId(String sysId) throws TdRuntimeException {
		log.info("查询角色,sysId="+sysId);
		log.info("查询角色总数");
		int count = authRoleDao.countValidBySysId(sysId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(AuthConstants.COMMON_LIMIT, count);
		map.put(AuthConstants.COMMON_SYSID, sysId);
		log.info("查询全部角色");
		List<Map<String, Object>> list = authRoleDao.selectValidBySysId(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectByMap(Map<String,Object> map) {
		log.info("查询角色，map="+map.toString());
		int count = authRoleDao.countByMap(map);
		map.put(AuthConstants.COMMON_LIMIT, count);
		List<Map<String, Object>> list = authRoleDao.selectByMap(map);
		return list;
	}
//	@Override
//	public void delByRoleIds(String roleIds) throws TdRuntimeException {
//		log.info("查询是否有角色状态为启用，roleIds={}",roleIds);
//		List<String> idList = AuthCommonUtil.handleIds(roleIds, AuthConstants.COMMON_COMMA);
//		Map<String,Object> paraMap = new HashMap<String,Object>();
//		paraMap.put(AuthConstants.COMMON_ISUSE, AuthConstants.STRING_1);//1启用0禁用
//		paraMap.put(AuthConstants.COMMON_LIST, idList);
//		List<AuthRole> lst = authRoleDao.selectStatusRole(paraMap);
//		if(lst!=null && lst.size()>0){
//			throw new TdRuntimeException("有 "+lst.size()+" 个角色状态为启用");
//		}
//		try {
//			authRoleDao.delByRoleIds(idList);
//		} catch (Exception e) {
//			throw new TdRuntimeException("数据库错误");
//		}
//	}
}
