package com.yuanlai.agent.controller;

import com.tangdi.def.base.controller.TdBaseController;
import com.tangdi.def.base.redis.TdRedisService;
import com.tangdi.def.constant.Constant;
import com.tangdi.def.utils.common.TdCommUtil;
import com.tangdi.def.utils.common.TdDateUtil;
import com.tangdi.def.utils.common.TdNumberUtil;
import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.entity.AuthUsr;
import com.yuanlai.agent.service.AuthUsrService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 *
 * @author chen_yq add 2016.04.27
 */
@RequestMapping(value = "/authUsr/")
@Controller
public class AuthUsrController extends TdBaseController {
    private final AuthUsrService authUsrService;
    private final TdRedisService tdRedisService;

    @Autowired
    public AuthUsrController(AuthUsrService authUsrService, TdRedisService tdRedisService) {
        this.authUsrService = authUsrService;
        this.tdRedisService = tdRedisService;
    }

    @RequestMapping(value = "selectByPage")
    @ResponseBody
    public Map<String, Object> selectByPage(AuthUsr authUsr, int pageSize, int pageNum) throws TdRuntimeException {
        Map<String, Object> map = authUsrService.selectByPage(authUsr, pageSize, pageNum);
        return genSuccessResult(map);
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(AuthUsr authUsr, HttpServletRequest request) throws TdRuntimeException {
        logInfo("添加用户");
        Map<String, Object> usrInfo = (Map<String, Object>) request.getAttribute(Constant.USR_INFO);
        authUsr.setCreObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
        try {
            String pwd = authUsrService.add(authUsr);
            return genSuccessResult(pwd);
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }


    }

    @RequestMapping(value = "update")
    @ResponseBody
    public Map<String, Object> update(AuthUsr authUsr, HttpServletRequest request) throws TdRuntimeException {
        Map<String, Object> usrInfo = (Map<String, Object>) request.getAttribute(Constant.USR_INFO);
        authUsr.setUpdObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
        try {
            authUsrService.update(authUsr);
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "delByUsrId")
    @ResponseBody
    public Map<String, Object> delByUsrId(HttpServletRequest request, @RequestParam("usrId") String usrId) throws TdRuntimeException {
        try {
            Map<String, Object> usrInfo = (Map<String, Object>) request.getAttribute(Constant.USR_INFO);
            String currUsrId = String.valueOf(usrInfo.get(AuthConstants.USR_USRID));
            if (currUsrId.equals(usrId)) {
                return genErrorResult("不能删除自己");
            }
            authUsrService.delByUsrId(usrId);
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "delByUsrIds")
    @ResponseBody
    public Map<String, Object> delByUsrIds(@RequestParam("usrIds") String usrIds) throws TdRuntimeException {
        try {
            authUsrService.delByUsrIds(usrIds);
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "selectByUsrId")
    @ResponseBody
    public Map<String, Object> selectByUsrId(@RequestParam("usrId") String usrId) throws TdRuntimeException {
        try {
            Map<String, Object> map = authUsrService.selectByUsrId(usrId);
            return genSuccessResult(map);
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "disOrEnable")
    @ResponseBody
    public Map<String, Object> disOrEnable(HttpServletRequest request, @RequestParam("usrStatus") String usrStatus, @RequestParam("usrId") String usrId) throws TdRuntimeException {
        try {
            Map<String, Object> usrInfo = (Map<String, Object>) request.getAttribute(Constant.USR_INFO);
            if (AuthConstants.STRING_0.equals(usrStatus)) {
                String currUsrId = String.valueOf(usrInfo.get(AuthConstants.USR_USRID));
                if (currUsrId.equals(usrId)) {
                    return genErrorResult("不能禁用自己");
                }
            }
            String currUsername = String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME));
            authUsrService.disOrEnable(usrStatus, usrId, currUsername);
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "getLoginInfoByToken")
    @ResponseBody
    public Map<String, Object> getLoginInfoByToken(@RequestParam("token") String token) throws TdRuntimeException {
        log.error("根据token获取用户登录信息,token=" + token);
        try {
            Map<String, Object> usrInfo = (Map<String, Object>) tdRedisService.getObject(token);
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("usrInfo", new HashMap<String, Object>());
            if (usrInfo != null) {
                retMap.put("usrInfo", usrInfo);
            }
            return genSuccessResult(retMap);
        } catch (Exception e) {
            return genErrorResult();
        }
    }

    @RequestMapping(value = "logout")
    @ResponseBody
    public Map<String, Object> logout(@RequestParam("token") String token) throws TdRuntimeException {
        log.error("logout,token=" + token);
        try {
            Map<String, Object> map = (Map<String, Object>) tdRedisService.getObject(token);
            String usrId = map.get("usrId").toString();
            log.error("logout,usrId=" + usrId);
            tdRedisService.deleteObject(usrId);//移除usrId=token
            tdRedisService.deleteObject(token);//移除token=usrInfo
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult();
        }
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, @RequestParam("usrName") String usrName, @RequestParam("usrPsw") String usrPsw) throws TdRuntimeException {
        AuthUsr usr;
        try {
            usr = authUsrService.login(usrName, usrPsw);
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
        int isFirstLogin = 0;
        String token = AuthConstants.COMMON_PREFIX +
                TdCommUtil.md5(usrName.getBytes()) +
                TdDateUtil.getDateTime("ddHHmmssSSS") +
                TdNumberUtil.randomNumber(6);
        log.debug("token=" + token);
        log.debug("usrId=" + usr.getUsrId());
        Map<String, Object> map = AuthBeanUtil.toMap(usr);
        String ip = request.getRemoteAddr();
        map.put("remote_ip", ip);
        String usrRole = authUsrService.selectUsrRole(usr.getUsrId());
        map.put(AuthConstants.COMMON_USRCURRROLEIDS, usrRole);
        if (TdCommUtil.md5(AuthConstants.STRING_td888888, AuthConstants.COMMON_CHARSET_UTF8).equals(usr.getUsrPsw())) {
            isFirstLogin = 1;
        }
        map.put(AuthConstants.USR_ISFIRSTLOGIN, isFirstLogin);
        log.info("map=" + map);
        tdRedisService.saveObject(token, map, 30 * 60);
        tdRedisService.saveObject(usr.getUsrId(), token, 30 * 60);
        Map<String, Object> retMap = new HashMap<>();
        retMap.put(AuthConstants.COMMON_TOKEN, token);
        retMap.put(AuthConstants.USR_ISFIRSTLOGIN, isFirstLogin);
        return genSuccessResult(retMap);
    }

    @RequestMapping(value = "getSysInfByUsr")
    @ResponseBody
    public Map<String, Object> getSysInfByUsr(HttpServletRequest request) throws TdRuntimeException {
        Map usrInfo = (Map) request.getAttribute(Constant.USR_INFO);
        try {
        	Map<String, Object> respMap = new HashMap<>();
            List list = authUsrService.getSysInfByUsr(usrInfo);
            respMap.put("list", list);
            
            Map<String, Object> menuItemsMapFinder = new HashMap<>();
            menuItemsMapFinder.put("title", "Finder");
            Map<String, Object> menuItemsMapAppstore = new HashMap<>();
            menuItemsMapAppstore.put("title", "Appstore");
            Map<String, Object> menuItemsMapMail = new HashMap<>();
            menuItemsMapMail.put("title", "Mail");
            Map<String, Object> menuItemsMapSafari = new HashMap<>();
            menuItemsMapSafari.put("title", "Safari");
            Map<String, Object> menuItemsMapFaceTime = new HashMap<>();
            menuItemsMapFaceTime.put("title", "FaceTime");
            Map<String, Object> menuItemsMapAddressBook = new HashMap<>();
            menuItemsMapAddressBook.put("title", "AddressBook");
            Map<String, Object> menuItemsMapiCalendar = new HashMap<>();
            menuItemsMapiCalendar.put("title", "iCalendar");
            Map<String, Object> menuItemsMapiTunes = new HashMap<>();
            menuItemsMapiTunes.put("title", "iTunes");
            Map<String, Object> menuItemsMapPhotoBooth = new HashMap<>();
            menuItemsMapPhotoBooth.put("title", "PhotoBooth");
            Map<String, Object> menuItemsMapiPhoto = new HashMap<>();
            menuItemsMapiPhoto.put("title", "iPhoto");
            Object[]  menuItems = {menuItemsMapFinder,menuItemsMapAppstore,menuItemsMapMail,menuItemsMapSafari,menuItemsMapFaceTime,menuItemsMapAddressBook,
            		menuItemsMapiCalendar,menuItemsMapiTunes,menuItemsMapPhotoBooth,menuItemsMapiPhoto};
            
            List<Object> menuItemslist = Arrays.asList(menuItems);
            respMap.put("menuItems", menuItemslist);
            log.info("返回的参数为{}",respMap);
            return genSuccessResult(respMap);
        } catch (Exception e) {
            log.error("查询系统信息失败", e);
            return genErrorResult("查询系统信息失败");
        }
    }

    @RequestMapping(value = "getMenuBySys")
    @ResponseBody
    public Map<String, Object> getMenuBySys(HttpServletRequest request, @RequestParam("sysId") String sysId) throws TdRuntimeException {
        Map usrInfo = (Map) request.getAttribute(Constant.USR_INFO);
        try {
            String usrName = usrInfo.get(AuthConstants.USR_USRNAME).toString();
            Map<String, Object> map = authUsrService.getMenuBySys(usrName, sysId);
            log.info("map=" + map);
            return genSuccessResult(map);
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "resetUsrPwd")
    @ResponseBody
    public Map<String, Object> resetUsrPwd(HttpServletRequest request, AuthUsr authUsr) throws TdRuntimeException {
        Map usrInfo = (Map) request.getAttribute(Constant.USR_INFO);
        authUsr.setUpdObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
        try {
            String newPwd = authUsrService.resetUsrPwd(authUsr);
            return genSuccessResult(newPwd);
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "updatePwd")
    @ResponseBody
    public Map<String, Object> updatePwd(HttpServletRequest request, AuthUsr authUsr) throws TdRuntimeException {
        Map usrInfo = (Map) request.getAttribute(Constant.USR_INFO);
        authUsr.setUsrId(String.valueOf(usrInfo.get(AuthConstants.USR_USRID)));
        authUsr.setUpdObj(String.valueOf(usrInfo.get(AuthConstants.USR_USRNAME)));
        String oldPwd = request.getParameter("oldPwd");
        log.info("oldPwd=" + oldPwd);
        log.info("authUsr=" + authUsr.toString());
        try {
            if (StringUtils.isBlank(oldPwd)) {
                authUsrService.updatePwdForFirstLogin(authUsr);
            } else {
                authUsrService.updatePwd(authUsr, oldPwd);
            }
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "unLock")
    @ResponseBody
    public Map<String, Object> unLock(String usrId) throws TdRuntimeException {
        try {
            authUsrService.unLock(usrId);
            return genSuccessResult();
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    //测试，先注释掉spring-mvc.xml中的拦截器
    @RequestMapping(value = "getMenuBySysTest")
    @ResponseBody
    public Map<String, Object> getMenuBySysTest(String usrName, String sysId) throws TdRuntimeException {
        try {
            Map<String, Object> map = authUsrService.getMenuBySys(usrName, sysId);
            log.info("map=" + map);
            return genSuccessResult(map);
        } catch (Exception e) {
            return genErrorResult(e.getMessage());
        }
    }

    @RequestMapping(value = "testAction")
    @ResponseBody
    public Map<String, Object> testAction() throws TdRuntimeException {
        log.info("--------------->测试交易");
        return genSuccessResult();
    }
}
