package com.yuanlai.agent.service.impl;

import com.tangdi.def.base.redis.TdRedisService;
import com.tangdi.def.base.seq.TdSequenceService;
import com.tangdi.def.utils.common.TdCommUtil;
import com.tangdi.def.utils.common.TdDateUtil;
import com.tangdi.def.utils.common.TdStringUtil;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.AuthCommonUtil;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.dao.AuthSysInfDao;
import com.yuanlai.agent.dao.AuthUsrDao;
import com.yuanlai.agent.dao.AuthUsrRoleDao;
import com.yuanlai.agent.entity.AuthSysInf;
import com.yuanlai.agent.entity.AuthUsr;
import com.yuanlai.agent.service.AuthUsrService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户管理Service实现
 *
 * @author chen_yq add 2016.04.27
 */
@Service
public class AuthUsrServiceImpl implements AuthUsrService {

    @Autowired
    private AuthUsrDao authUsrDao;
    @Autowired
    private AuthSysInfDao authSysInfDao;
    @Autowired
    private AuthUsrRoleDao authUsrRoleDao;
    @Autowired
    @Qualifier("authLogNoService")
    private TdSequenceService authLogNoService;
    @Autowired
    private TdRedisService tdRedisService;
    private static Logger log = LoggerFactory.getLogger(AuthUsrServiceImpl.class);

    public AuthUsr login(String usrName, String usrPwd) throws TdRuntimeException {
        log.info("用户 {} 登录", usrName);
        AuthUsr usr = authUsrDao.selectByUsrName(usrName);
        if (usr == null) {
            throw new TdRuntimeException("用户名不存在");
        }
        if (AuthConstants.STRING_0.equals(usr.getUsrStatus())) {
            throw new TdRuntimeException("用户被禁用");
        }
        checkLogin(usr, usrPwd);
        usr.setLastLoginTime(TdDateUtil.getDateTime());
        usr.setFailLoginTimes("" + 0);//登录成功，重置
        authUsrDao.updateByPrimaryKeySelective(usr);
        return usr;
    }

    private void checkLogin(AuthUsr usr, String usrPwd) throws TdRuntimeException {
        log.info("校验用户登录:usr=" + usr.toString());
        int failLoginTimes = Integer.valueOf(StringUtils.isBlank(usr.getFailLoginTimes()) ? "0" : usr.getFailLoginTimes());
        String lastLoginTimeStr = usr.getLastLoginTime();
        if (StringUtils.isBlank(lastLoginTimeStr)) {
            lastLoginTimeStr = TdDateUtil.getDateTime();
        }

        //跨天  重记登录失败次数
        if (isCrossDay(lastLoginTimeStr)) {
            failLoginTimes = 0;
        }
        if (!usrPwd.equals(usr.getUsrPsw())) {
            failLoginTimes += 1;
            usr.setFailLoginTimes("" + failLoginTimes);
            usr.setLastLoginTime(TdDateUtil.getDateTime());
            authUsrDao.updateByPrimaryKeySelective(usr);
            if (failLoginTimes >= AuthConstants.MAX_LOGIN_TIMES) {//超过最大登录次数，30分钟后再登录
                long diffmin = diffmin(lastLoginTimeStr);
                if (diffmin > 0) {
                    throw new TdRuntimeException("登录密码已被锁定，请 " + diffmin + " 分钟之后再尝试");
                }
            }
            throw new TdRuntimeException("密码错误，您还可以输入 " + (AuthConstants.MAX_LOGIN_TIMES - failLoginTimes) + " 次");
        }
    }

    private long diffmin(String dateTime) {//
        if (StringUtils.isBlank(dateTime)) {
            return 0;
        }
        Date lastLoginTime = TdDateUtil.parseDate(dateTime, AuthConstants.TIMESTYLE_yyyymmddhhmmss);
        long diff = System.currentTimeMillis() - lastLoginTime.getTime();
        return (1800000L - diff) / 60000l;
    }

    private boolean isCrossDay(String currentDate) {
        if (StringUtils.isBlank(currentDate)) {
            return true;
        }
        int lastDay = Integer.valueOf(currentDate.substring(0, 8));
        int nowDay = Integer.valueOf(TdDateUtil.getDateTime().substring(0, 8));
        return (nowDay - lastDay) >= 1;
    }

    public List getSysInfByUsr(Map map) throws TdRuntimeException {
        return authUsrDao.getSysInfByUsr(map);
    }

    public Map<String, Object> getMenuBySys(String usrName, String sysId) throws TdRuntimeException {
        log.info("获取用户名 {} 系统 {} 的菜单", usrName, sysId);
        AuthUsr usr = authUsrDao.selectByUsrName(usrName);
        if (usr == null) {
            throw new TdRuntimeException("未查询到用户");
        }
        log.info("用户id: {} ,用户状态: {}", usr.getOrgId(), usr.getUsrStatus());
        if (!AuthConstants.STRING_1.equals(usr.getUsrStatus())) {
            throw new TdRuntimeException("用户未启用");
        }
        log.info("查询系统信息");
        AuthSysInf sysInf = authSysInfDao.selectByPrimaryKey(sysId);
        if (sysInf == null) {
            throw new TdRuntimeException("未查询到系统信息");
        }
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put(AuthConstants.ITEM_SYSID, sysId);
        paraMap.put(AuthConstants.USR_USRID, usr.getUsrId());
        List<Map<String, Object>> parents = authUsrDao.selectUsrAuthForLogin(paraMap); //展示的一级菜单
        paraMap.put(AuthConstants.ITEM_HAVE_PARENT, AuthConstants.ITEM_HAVE_PARENT);
        List<Map<String, Object>> items = authUsrDao.selectUsrAuthForLogin(paraMap); //子菜单
        List<Map<String, Object>> usrLoginAuthList = prtMenu(parents, items);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usrLoginAuthList", usrLoginAuthList);
        map.put(AuthConstants.COMMON_USRCURRITEMLIST, items);
        return map;
    }

    public static List<Map<String, Object>> prtMenu(List<Map<String, Object>> prts, List<Map<String, Object>> chds) {
        List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < prts.size(); i++) {
            Map<String, Object> prt = prts.get(i);
            menu.add(paseOnePrt(prt, chds));
        }
        return menu;
    }

    public static Map<String, Object> paseOnePrt(Map<String, Object> prt, List<Map<String, Object>> chds) {
        List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < chds.size(); i++) {
            Map<String, Object> chd = chds.get(i);
            if (prt.get("key").equals(chd.get("parentItmId"))) {
                menu.add(paseOnePrt(chd, chds));
            }
        }
//		if (menu.size() > 0) {
//			for (int i = 0; i < menu.size(); i++) {
//				chds.remove(menu.get(i));
//			}
//		}
        prt.put("children", menu);
        return prt;
    }

    /**
     * 获取用户登录时的菜单树
     *
     * @param parentItmId
     * @param sysId
     * @return
     */
    private List<Map<String, Object>> getUsrLoginItemTree(String usrId, String sysId, String parentItmId) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put(AuthConstants.ITEM_PARENTITMID, parentItmId);
        paraMap.put(AuthConstants.ITEM_SYSID, sysId);
        paraMap.put(AuthConstants.USR_USRID, usrId);
        Map<String, Object> multiItemIdMap = new HashMap<String, Object>();//过滤重复的菜单
        List<Map<String, Object>> itemTree = new ArrayList<Map<String, Object>>();//菜单树
        List<Map<String, Object>> currChildItems = authUsrDao.selectUsrAuthForLogin(paraMap);
        for (Map<String, Object> map : currChildItems) {
            String itmId = AuthConstants.ITEM_ITMID;
            if (multiItemIdMap.containsKey(map.get(itmId))) {
                continue;
            } else {
                multiItemIdMap.put(String.valueOf(map.get(itmId)), map.get(itmId));
            }
            List<Map<String, Object>> children = getUsrLoginItemTree(usrId, sysId, String.valueOf(map.get(itmId)));
            if (children != null && children.size() > 0) {//去除空的子节点(ant-design要求)
                map.put(AuthConstants.COMMON_CHILDREN, children);
            }
            itemTree.add(map);
        }
        return itemTree;
    }

    @Override
    public Map<String, Object> selectByPage(AuthUsr authUsr, int pageSize, int pageNum) throws TdRuntimeException {
        log.info("分页查询用户列表");
        Map<String, Object> paraMap = AuthBeanUtil.toMap(authUsr);
        paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
        try {
            log.info("paraMap:" + paraMap.toString());
            Map<String, Object> data = new HashMap<String, Object>();
            List<Map<String, Object>> retList = authUsrDao.selectByPager(paraMap);
            if (CollectionUtils.isNotEmpty(retList)) {//判断是否被锁定
                for (Map<String, Object> map : retList) {
                    map.put("isLock", 0);//未锁定
                    Object lastLoginTimeObj = map.get(AuthConstants.USR_LASTLOGINTIME);
                    Object failLoginTimesObj = map.get(AuthConstants.USR_FAILLOGINTIMES);
                    if (lastLoginTimeObj != null && !isCrossDay(String.valueOf(lastLoginTimeObj))) {//未跨天
                        if (failLoginTimesObj != null) {
                            int failLoginTimes = Integer.valueOf(String.valueOf(failLoginTimesObj));
                            if (failLoginTimes >= 5 && diffmin(String.valueOf(lastLoginTimeObj)) > 0) {
                                map.put("isLock", 1);//锁定
                            }
                        }
                    }
                }
            }
            int total = authUsrDao.countByCondition(paraMap);
            data.put(AuthConstants.COMMON_LIST, retList);
            data.put(AuthConstants.COMMON_TOTAL, total);
            return data;
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
    }

    @Override
    public String add(AuthUsr authUsr) throws TdRuntimeException {
        log.info("添加用户:" + authUsr.toString());
        Map<String, Object> paraMap = AuthBeanUtil.toMap(authUsr);
        TdStringUtil.chkRequiredParam(paraMap, AuthConstants.USR_USRNAME, AuthConstants.USR_USRREALNAME,AuthConstants.ORG_ORGID);
        AuthUsr usrTmp = authUsrDao.selectByUsrName(authUsr.getUsrName());
        if (usrTmp != null) {
            throw new TdRuntimeException("用户名已存在");
        }
        authUsr.setUsrId(authLogNoService.create());
        authUsr.setCreTim(TdDateUtil.getDateTime());
        authUsr.setUsrPsw(TdCommUtil.md5(AuthConstants.STRING_td888888, AuthConstants.COMMON_CHARSET_UTF8));
        log.info("入库前的authUsr:" + authUsr.toString());
        try {
            authUsrDao.insertSelective(authUsr);
            return AuthConstants.STRING_td888888;
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }

    }

    @Override
    public void update(AuthUsr authUsr) throws TdRuntimeException {
        log.info("更新用户信息:" + authUsr.toString());
        if (authUsr.getUsrId() == null) {
            throw new TdRuntimeException("usrId必填");
        }
        AuthUsr usrOld = authUsrDao.selectByPrimaryKey(authUsr.getUsrId());
        if (usrOld == null) {
            throw new TdRuntimeException("未查询到用户信息");
        }
        String usrName = authUsr.getUsrName();
        if (usrName != null && !usrName.equals(usrOld.getUsrName())) {//更新了用户名
            AuthUsr usrTmp = authUsrDao.selectByUsrName(usrName);
            if (usrTmp != null) {
                throw new TdRuntimeException("用户名已存在");
            }
        }
        authUsr.setUpdTim(TdDateUtil.getDateTime());
        log.info("更新前的authUsr:" + authUsr.toString());
        try {
            authUsrDao.updateByPrimaryKeySelective(authUsr);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }

    }

    @Override
    public void delByUsrId(String usrId) throws TdRuntimeException {
        log.info("删除用户，usrId=" + usrId);
        AuthUsr usr = authUsrDao.selectByPrimaryKey(usrId);
        if (usr == null) {
            throw new TdRuntimeException("未查询到用户信息");
        }
        if (AuthConstants.STRING_1.equals(usr.getUsrStatus())) {//0-禁用，1-启用
            throw new TdRuntimeException("用户状态为启用，请禁用后操作");
        }
        Object tokenKeyObj = tdRedisService.getObject(usrId);
        if (tokenKeyObj != null) {
            String tokenKey = String.valueOf(tokenKeyObj);
            log.info("用户信息从redis移除,token={}", tokenKey);
            tdRedisService.deleteObject(usrId);
            tdRedisService.deleteObject(tokenKey);
        }
        try {
            authUsrDao.deleteByPrimaryKey(usrId);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
    }

    @Override
    public void delByUsrIds(String usrIds) throws TdRuntimeException {
        log.info("批量删除用户，usrIds:" + usrIds);
        List<String> idsList = AuthCommonUtil.handleIds(usrIds, AuthConstants.COMMON_COMMA);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put(AuthConstants.USR_USRSTATUS, AuthConstants.STRING_1);//0-禁用，1-启用
        paraMap.put(AuthConstants.COMMON_LIST, idsList);
        List<AuthUsr> list = authUsrDao.selectStatusUsr(paraMap);
        if (list != null && list.size() > 0) {
            throw new TdRuntimeException("有 " + list.size() + " 个用户状态为启用");
        }
        try {
            authUsrDao.deleteByUsrIds(idsList);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }

    }

    @Override
    public Map<String, Object> selectByUsrId(String usrId) throws TdRuntimeException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> usrMap = authUsrDao.selectByUsrId(usrId);
        map.put(AuthConstants.USR_AUTHUSR, usrMap);
        log.info("查询用户当前角色");
        List<Map<String, Object>> usrCurrRoleList = authUsrDao.selectUsrCurrRole(usrId);
        map.put(AuthConstants.COMMON_USRCURRROLELIST, usrCurrRoleList);
        return map;
    }


    @Override
    public void disOrEnable(String usrStatus, String usrId, String updObj) throws TdRuntimeException {
        //usrId-->token,token-->usrInfo
        log.info("禁用/启用用户,usrStatus={} , usrId={} ", usrStatus, usrId);
        if (AuthConstants.STRING_1.equals(usrStatus)) {//启用

        } else if (AuthConstants.STRING_0.equals(usrStatus)) {//禁用
            Object tokenObj = tdRedisService.getObject(usrId);
            if (tokenObj != null) {
                String token = String.valueOf(tokenObj);
                Map map = (Map) tdRedisService.getObject(String.valueOf(token));
                if (map != null) {
                    log.info("修改redis中的用户状态");
                    map.put(AuthConstants.USR_USRSTATUS, usrStatus);
                    tdRedisService.saveObject(token, map, 30 * 60);
                }
            }
        } else {
            throw new TdRuntimeException("禁用/启用标志只能是0或者1");
        }
        try {
            authUsrDao.disOrEnable(usrStatus, usrId, updObj, TdDateUtil.getDateTime());
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
    }

    @Override
    public String resetUsrPwd(AuthUsr authUsr) throws TdRuntimeException {
        log.info("重置用户  {} 的密码", authUsr.getUsrId());
        if (StringUtils.isEmpty(authUsr.getUsrId())) {
            throw new TdRuntimeException("用户id必填");
        }
        authUsr.setUpdTim(TdDateUtil.getDateTime());
        authUsr.setUsrPsw(TdCommUtil.md5(AuthConstants.STRING_td888888, AuthConstants.COMMON_CHARSET_UTF8));
        authUsr.setFailLoginTimes("0");//重置登录失败次数
        try {
            authUsrDao.updateByPrimaryKeySelective(authUsr);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
        return AuthConstants.STRING_td888888;
    }


    @Override
    public void updatePwdForFirstLogin(AuthUsr authUsr) throws TdRuntimeException {
        log.info("用户 {} 修改密码", authUsr.getUsrId());
        Map<String, Object> paraMap = AuthBeanUtil.toMap(authUsr);
        TdStringUtil.chkRequiredParam(paraMap, AuthConstants.USR_USRID, AuthConstants.USR_USRPSW);
        if(authUsr.getUsrPsw().equals(TdCommUtil.md5(AuthConstants.STRING_td888888, AuthConstants.COMMON_CHARSET_UTF8))){
        	throw new TdRuntimeException("新密码不能与初始密码一致");
        }
        authUsr.setUpdTim(TdDateUtil.getDateTime());
        try {
            authUsrDao.updateByPrimaryKeySelective(authUsr);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
    }

    @Override
    public void updatePwd(AuthUsr authUsr, String oldPwd) throws TdRuntimeException {
        log.info("用户 {} 修改密码", authUsr.getUsrId());
        if (StringUtils.isEmpty(oldPwd)) {
            throw new TdRuntimeException("旧密码为空");
        }
        Map<String, Object> paraMap = AuthBeanUtil.toMap(authUsr);
        TdStringUtil.chkRequiredParam(paraMap, AuthConstants.USR_USRID, AuthConstants.USR_USRPSW);
        AuthUsr oldUsr = authUsrDao.selectByPrimaryKey(authUsr.getUsrId());
        if (oldUsr == null) {
            throw new TdRuntimeException("未查询到用户信息");
        }
        if(authUsr.getUsrPsw().equals(TdCommUtil.md5(AuthConstants.STRING_td888888, AuthConstants.COMMON_CHARSET_UTF8))){
        	throw new TdRuntimeException("新密码不能与初始密码一致");
        }
        if (!oldPwd.equals(oldUsr.getUsrPsw())) {
            throw new TdRuntimeException("旧密码错误");
        }
        if (authUsr.getUsrPsw().equals(oldPwd)) {
            throw new TdRuntimeException("新旧密码不能一样");
        }
        authUsr.setUpdTim(TdDateUtil.getDateTime());
        try {
            authUsrDao.updateByPrimaryKeySelective(authUsr);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
    }

    @Override
    public String selectUsrRole(String usrId) throws TdRuntimeException {
        log.info("查询用户 {} 的角色", usrId);
        List<String> roleList = authUsrRoleDao.selectUsrCurrValidRoleId(usrId);
        String roleStr = AuthCommonUtil.handleListForStr(roleList, AuthConstants.COMMON_COMMA);
        return roleStr;
    }

    @Override
    public void unLock(String usrId) throws TdRuntimeException {
        AuthUsr usr = authUsrDao.selectByPrimaryKey(usrId);
        if (usr == null) {
            throw new TdRuntimeException("未查询到用户信息");
        }
        AuthUsr newUsr = new AuthUsr();
        newUsr.setUsrId(usrId);
        newUsr.setFailLoginTimes("0");
        try {
            authUsrDao.updateByPrimaryKeySelective(newUsr);
        } catch (Exception e) {
            throw new TdRuntimeException("数据库错误");
        }
    }
}
