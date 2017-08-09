import MD5 from 'crypto-js/md5';
import BASE64 from 'crypto-js/enc-base64';
import ENC_UTF8 from 'crypto-js/enc-utf8';
import { openNotice } from '../common/antdUtil';
import { rspInfo } from '../common/authConstant';
import { ylagent } from '../config/server';
import cityJson from '../data/cityData.json';
import { invalidCharacters, continueRepeatCharacters, constinueRepeatCharacterReg, specialCharacters, operationFlags } from './tdpayConstant.js';
import { logoutApiUrl } from '../config/url';
/**
 * 检测是否已登录(判断sessionStorage中loginInfo是否为null)
 *
 * 返回: boolean
 */
export function checkLogin() {
  return sessionStorage.getItem('loginInfo') === null ? false : true;
}

/**
 * 设置登录信息(将登录信息loginInfo设置到sessionStorage中)
 *
 * 参数：
 * info: 登录信息对象
 */
export function setLoginInfo(info) {
  let str = '';
  if (Object.prototype.toString.call(info) !== '[object String]') {
    str = JSON.stringify(info);
  } else {
    str = info;
  }
  sessionStorage.removeItem('loginInfo');
  sessionStorage.setItem('loginInfo', BASE64.stringify(ENC_UTF8.parse(str)));
}

/**
 * 获取登录信息(从sessionStorage中获取loginInfo)
 *
 * 返回: Object
 */
export function getLoginInfo() {
  let info = {};
  if (checkLogin() === true) {
    // 获取loginInfo后需解密
    info = JSON.parse(BASE64.parse(sessionStorage.getItem('loginInfo')).toString(ENC_UTF8));
  }
  return info;
}

/**
 * 移除登录信息(从localStorage中移除loginInfo,同时移除appState)
 */
export function removeLoginInfo() {
  sessionStorage.clear();
  localStorage.clear();
}

export function setLocalItem(k, v) {
  localStorage.removeItem(k);
  if (v !== null) {
    localStorage.setItem(k, BASE64.stringify(ENC_UTF8.parse(v.toString())));
  }
}

export function getLocalItem(k) {
  const item = localStorage.getItem(k);
  try {
    return item === null ? null : BASE64.parse(item).toString(ENC_UTF8);
  }catch (e) {
    console.error(e, item);
    localStorage.removeItem(k);
    return null;
  }
}

export function removeLocalItem(k) {
  localStorage.removeItem(k);
}

/**
 * 在localStorage中存登录token信息
 */
export function setLocalToken(token) {
  localStorage.removeItem('td_user_token');
  if (token !== null) {
    localStorage.setItem('td_user_token', BASE64.stringify(ENC_UTF8.parse(token.toString())));
  }
}

/**
 * 在localStorage中取登录token信息
 */
export function getLocalToken() {
  const tk = localStorage.getItem('td_user_token');
  try {
    return tk === null ? null : BASE64.parse(tk).toString(ENC_UTF8);
  } catch (e) {
    console.error(e, 'td_user_token');
    localStorage.removeItem('td_user_token');
    return null;
  }
}

/**
 * 移除在localStorage中登录token信息
 */
export function removeLocalToken() {
  localStorage.removeItem('td_user_token');
}

export function getCookie(name) {
  let value = '; ' + document.cookie;
  let parts = value.split('; ' + name + '=');
  if (parts.length == 2)
    return parts.pop().split(';').shift();
}

/**
 * 获取url中指定key的value
 */
export function getUrlVal(url, key) {
  const idx = url.indexOf(key + '=');
  if (idx === -1) {
    return '';
  } else {
    const nurl = url.substring(idx), idx2 = nurl.indexOf('&');
    return nurl.substring(1 + key.length, idx2 === -1 ? url.length : idx2);
  }
}

/**
 * 刷新session中的loginInfo信息
 */
export function refreshLoginInfo(tk, asyn, sys = null) {
  if (tk === null) {
    openNotice('error', '登录检查失败，即将跳转到登录页面');
    setTimeout(() => {
      document.getElementById('hiddenLogoutBtn').click();
    }, 3200);
  } else {
    const dat = { 'token': tk };
    $.support.cors = true;  // IE8下cors跨域访问
    $.ajax({
      type: 'POST',
      url: ylagent.authUsr.getLoginInfoByToken,
      data: dat,
      dataType: 'json',
      contentType: 'application/x-www-form-urlencoded',
      async: asyn,
      crossDomain: true,
      cache: false,
      beforeSend (req) {
        req.setRequestHeader('req-auth', buildReqSign(dat, tk));
      },
      success: (result, status, xhr) => {
        validResSign(result.rspCod, result.rspData, xhr.getResponseHeader('rsp-auth'), tk);
        if (result.rspCod === rspInfo.RSP_SUCCESS) {
          const info = result.rspData.usrInfo;
          setLoginInfo({
            usrName: info.usrName,
            token: tk,
            isFirstLogin: info.isFirstLogin,
          });
          // asyn为false时，代表直接从浏览器输入地址同步查询loginInfo
          if (asyn.toString() === 'true') {
            openNotice('info', '您可能已使用其他账户登录，3秒后将更新页面');
            setTimeout(() => { location.reload(); }, 3000);
          }
        } else if (result.rspCod === '_SSO_ERR') {
          openNotice('warning', result.rspMsg);
          setTimeout(() => {
            removeLoginInfo();
            window.location.href = logoutApiUrl;
          }, 3200);
        } else {
          window.location.href = logoutApiUrl;
        }
      },
      error: (req, info, opt) => {
        openNotice('warning', '请求发送失败');
      },
    });
  }
}

/**
 * 发送ajax请求(使用jQuery发送ajax)
 *
 * 参数:
 * opt: 请求参数对象
 *      url: 请求地址, 必选
 *      type: 请求类型, 可选(默认POST)
 *      data: 请求参数, 可选(默认{})
 *      dataType: 参数类型, 可选(默认json)
 *      async: 请求模式, 可选(默认true 异步)
 *      contentType: 发送编码类型, 可选(默认application/x-www-form-urlencoded)
 * successCallback: 发送成功回调方法
 * errorCallback: 发送失败回调方法
 */
export function callAjax(opt, successCallback, errorCallback) {
  let type = 'POST', data = {}, dataType = 'json', async = true, skip = false, contentType = 'application/x-www-form-urlencoded';
  if (opt) {
    type = opt.type ? opt.type : type;
    data = opt.data ? opt.data : data;
    dataType = opt.dataType ? opt.dataType : dataType;
    async = opt.async ? async : opt.async;
    contentType = opt.contentType ? opt.contentType : contentType;
    skip = data.skip === true ? true : false;
  }
  const info = getLoginInfo(), lTk = getLocalToken();
  if (skip === false) {
    if (lTk !== info.token) {
      refreshLoginInfo(lTk, true, info.sysId);
    } else {
      if (info) {
        data.token = info.token;
        data.systemno = info.sysId;
      }
      doNormalRequest(info.sysId, type, opt, data, dataType, contentType, async, successCallback, errorCallback);
    }
  } else {
    doNormalRequest(info.sysId, type, opt, data, dataType, contentType, async, successCallback, errorCallback);
  }
}

// inner function
function doNormalRequest(sysId, type, opt, data, dataType, contentType, async, successCallback, errorCallback) {
  const tk = data.token ? data.token : null;
  $.support.cors = true;  // IE8下cors跨域访问
  $.ajax({
    type,
    url: opt.url,
    data,
    dataType,
    contentType,
    async,
    crossDomain: true,
    cache: false,
    beforeSend (req) {
      req.setRequestHeader('req-auth', buildReqSign(data, tk));
    },
    success: (result, status, xhr) => {
      const cod = result.rspCod;
      validResSign(cod, result.rspData, xhr.getResponseHeader('rsp-auth'), cod === '_SSO_ERR' ? null : tk);
      if (cod === '_SSO_ERR') {
        openNotice('error', result.rspMsg);
        setTimeout(() => {
          document.getElementById('hiddenLogoutBtn').click();
        }, 3200);
      } else {
        if (typeof successCallback === 'function') {
          successCallback(result);
        }
      }
    },
    error: (req, info, opt) => {
      if (typeof errorCallback === 'function') {
        errorCallback(req, info, opt);
      } else {
        openNotice('warning', '请求发送失败');
      }
    },
  });
}

/**
 * 构建请求签名
 * 构建方式：
 * 1. 请求参数按key的升序排序，拼接参数值(去除token、Object和Array等参数值)，组成原始字符串
 * 2. 对原始字符串进行base64转换
 * 3. 转换后的base64的值拼接token(如果不为空)进行md5计算
 * 4. 构建完成后的签名放在请求的header中，属性为req-auth
 * 参数：
 * dat：请求参数对象
 * tk：当前token(可为null)
 */
export function buildReqSign(dat, tk) {
  let str = buildSignString('', dat);
  if (str !== '') {
    str = BASE64.stringify(ENC_UTF8.parse(str));
  }
  return MD5(tk !== null ? (str + tk) : str).toString();
}

/**
 * 校验响应签名
 * 校验方式：
 * 1. 从响应信息的header中取出rsp-auth作为签名
 * 2. 响应信息签名的构建方式为repCode拼接repData(key升序排序拼接值)作为原始字符串
 * 3. 对原始字符串进行base64转换
 * 4. 转换后的base64的值拼接token(如果不为空)进行md5计算
 * 5. 与header中的rsp-auth对比
 * 参数：
 * code：响应码(一般为result.rspCod)
 * dat：响应数据(一般为result.rspData)
 * sign：响应签名
 * tk：当前token(可为null)
 */
export function validResSign(code, dat, sign, tk) {
  let str = buildSignString(code, dat);
  str = BASE64.stringify(ENC_UTF8.parse(str));
  str = MD5(tk !== null ? (str + tk) : str).toString();
  const result = str === sign;
  if (result === false) {
    console.log('响应签名校验失败');
    console.log('响应签名', sign);
    console.log('计算签名', str);
  }
  return result;
}

/**
 * 构建签名用原始字符串
 */
function buildSignString(ori, dat) {
  let str = ori;
  if (dat !== null && dat !== undefined) {
    const keys = Object.keys(dat).sort();  // key的升序排序
    for (let i = 0; i < keys.length; i++) {
      const v = dat[keys[i]];
      if (keys[i] === 'token' || v === undefined || v === null || isArray(v) || isObject(v) || v.toString().trim === '') {
        continue;
      }
      str += v.toString();
    }
  }
  return str;
}

/**
 * 判断是否为数组
 */
export function isArray(v) {
  return Object.prototype.toString.call(v) === '[object Array]';
}

/**
 * 判断是否为对象(包括函数和数组)
 */
export function isObject(obj) {
  const type = typeof obj;
  return type === 'function' || type === 'object' && !!obj;
}

/**
 * 判断{}对象空不空
 */
export function isEmptyObject(e) {
  let t;
  for (t in e)
    return !1;
  return !0;
}

/**
 * 判断是否支持promise
 *
 * 参数:
 * 被判断对象
 *
 * 返回: Boolean
 */
export function isPromise(value) {
  if (value !== null && typeof value === 'object') {
    return value.promise && typeof value.promise.then === 'function';
  }
}

/**
 * 过滤对象属性(去掉值为null,undefined的属性)
 *
 * 参数：
 * obj：待过滤的对象
 *
 * 返回：
 * 过滤后的对象
 */
export function filterObject(obj) {
  let o = {};
  for (let k in obj) {
    if (obj[k] || obj[k] === 0) {
      o[k] = obj[k];
    }
  }
  return o;
}

/**
 * 格式化日期
 *
 * 参数：
 * date：带格式化日期(Date类型)
 * fmt：格式(yyyy-MM-dd hh:mm:ss.S 等)
 */
export function formatDate(date, fmt) {
  var o = {
    'M+': date.getMonth() + 1, // 月份
    'd+': date.getDate(), // 日
    'h+': date.getHours(), // 小时
    'm+': date.getMinutes(), // 分
    's+': date.getSeconds(), // 秒
    'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
    'S': date.getMilliseconds(), // 毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
  return fmt;
}

/**
 * 格式化日期字符串，只能格式化形如yyyyMMddHHmmss为yyyy-MM-dd HH:mm:ss
 * 参数：
 * dataStr：日期字符串(String类型)
 */
export function parseDate(dataStr) {
  if (dataStr && dataStr !== null && dataStr.length == 8) {
    return dataStr.substr(0, 4) + '-' + dataStr.substr(4, 2) + '-' + dataStr.substr(6, 2);
  } else if (dataStr && dataStr !== null && dataStr.length >= 14) {
    return dataStr.substr(0, 4) + '-' + dataStr.substr(4, 2) + '-' +
      dataStr.substr(6, 2) + ' ' + dataStr.substr(8, 2) + ':' +
      dataStr.substr(10, 2) + ':' + dataStr.substr(12, 2);
  } else {
    return dataStr;
  }
}

/**
 * 格式化交易类型
 * 参数：
 * dat(String类型)
 */
export function handleTranstatus(dat) {
  let renName = "";
  if (dat === "01"){
    renName = "未上传";
  } else if (dat === "02") {
    renName = "未交易";
  } else if (dat === "03") {
    renName = "交易成功";
  } else if (dat === "04") {
    renName = "交易失败";
  } else if (dat === "05") {
    renName = "可疑";
  }
  return renName;
}

export function setDict(dictData) {
  localStorage.setItem('DICT', JSON.stringify(dictData));
}

export function getSelectOption(key) {
  let DICT_DATA_JSON = JSON.parse(localStorage.getItem('DICT'));
  if (DICT_DATA_JSON[key] == undefined || DICT_DATA_JSON[key] === '') {
    return null;
  }
  return DICT_DATA_JSON[key];
}

/**
 * 将查询的码表信息放到本地
 * type    : 码表类型
 * dictData: 码表列表
 */
export function setDictType(type, dictData) {
  sessionStorage.setItem('DICT_SELECT_' + type, JSON.stringify(dictData));
}

/**
 * 通过值获取名
 */
export function getDict(key, val) {
  let sRes = sessionStorage.getItem('DICT_SELECT_' + key);

  if (null === sRes && undefined === sRes) {
    return '';
  }

  let arrTemp = JSON.parse(sRes);
  if (arrTemp == null || arrTemp.length <= 0) {
    return '';
  }
  let sname = '';
  for (let j = 0; j < arrTemp.length; j++) {
    let oTemp = arrTemp[j];
    if (oTemp.attrMap.DICT_VALUE == val) {
      sname = oTemp.attrMap.DICT_NAME;
      break;
    }
  }
  if (sname == '')
    return val;
  else
    return sname;
}

/**
 * 查询本地的码表信息
 * type : 码表类型
 */
export function getDictByType(type) {
  let sRes = sessionStorage.getItem('DICT_SELECT_' + type);
  if (null === sRes && undefined === sRes) {
    return null;
  }
  return JSON.parse(sRes);
}

/**
 * 查询远程的下拉选项信息
 * url   : 请求地址
 * param ：请求的参数
 *     -type    : ["STATUS","SEX"...] 请求后台的码表类型
 * reload  : true|false 是否直接从远程查询
 * callback: 回调函数
 */
export function requestSelectData(url, param, reload, callback) {
  let arrType = param.type;
  let sRemoteParams = '';
  let oRes = {};
  let sType = '';
  let oTemp = {};
  // reload 默认为false
  if (typeof (reload) == undefined) {
    reload = false;
  }
  for (let i = 0; i < arrType.length; i++) {
    sType = arrType[i];
    // 设置重新加载 或 本地未找到 都需要从服务器获取
    if (reload || null == getDictByType(sType)) {
      sRemoteParams += sType + ',';
      continue;
    }
  }

  if ('' == sRemoteParams) {
    oRes = getDictCallbackObj(arrType);
    callback(oRes);
    return;
  } else {
    param = { 'TYPES': sRemoteParams };
    // 此处发送ajax请求获取数据更新
    let opt = {
      url,
      type: 'GET',
      dataType: 'json',
      data: param,
    };

    let oTypeTemp = null;
    callAjax(opt, function (result) {
      oTypeTemp = result.rspData.DATA;
      for (let sKey in oTypeTemp) {
        // 本地不存在，后台查询成功，更新本地存储
        setDictType(sKey, oTypeTemp[sKey]);
      }
      oRes = getDictCallbackObj(arrType);
      callback(oRes);
    }, function (req, info, opt) {
      console.log(info);
    });
  }
}

function getDictCallbackObj(arrType) {
  let oRes = {};
  let arrTemp = null;
  let sTemp = null;
  for (let i = 0; i < arrType.length; i++) {
    sTemp = arrType[i];
    arrTemp = getDictByType(sTemp);
    oRes[sTemp] = [];

    if (null == arrTemp) {
      continue;
    }

    for (let j = 0; j < arrTemp.length; j++) {
      let oTemp = arrTemp[j];
      oRes[sTemp][oRes[sTemp].length] = { 'value': oTemp.attrMap.DICT_VALUE, 'text': oTemp.attrMap.DICT_NAME };
    }
  }
  return oRes;
}


/**
 * 解析省市Json
 * 2016.5.26
 * authour lishouyong
 */
export function buildAreaCode(text) {
  let areaCode = text.split(',');
  let provinceName = text;
  cityJson.map(item => {
    if (item.value === areaCode[0]) {
      provinceName = item.label;
      if (item.children) {
        let subCity = item.children;
        subCity.map(city => {
          if (city.value === areaCode[1]) {
            provinceName += '-' + city.label;
            if (city.children) {
              let subArea = city.children;
              subArea.map(area => {
                if (area.value === areaCode[2]) {
                  provinceName += '-' + area.label;
                }
              });
            }
          }
        });
      }
    }
  });
  return provinceName;
}

/**
 * 元转分
 */
export function cent2Yuan(cent) {
  if (cent == null || cent == '' || isNaN(cent)) {
    return '0.00';
  }
  return TDFormatMoney(cent / 100, 2);
//  return yuan.replace(/[0]+$/, '').replace(/[.]+$/, '');
}

/**
 * 格式化金额:  12345.67格式化为 12,345.67
 * @param s
 * @param n
 * @returns {String}
 */
function TDFormatMoney(s, n) {
  n = n > 0 && n <= 20 ? n : 2;
  s = parseFloat((s + '').replace(/[^\d\.-]/g, '')).toFixed(n) + '';
  var l = s.split('.')[0].split('').reverse(),
    r = s.split('.')[1];
  var t = '';
  for (var i = 0; i < l.length; i++) {
    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : '');
  }
  return t.split('').reverse().join('') + '.' + r;
}


/**
 * 判断非法字符
 */
function invalidCharacterSCheck(opt) {
  let checkFlag = false;
  /** 根据url初步判断是查询还是修改，删除，新增，修改、删除、新增不做拦截 */
  if (opt.url) {
    for (let i = 0; i < operationFlags.length; i++) {
      if (opt.url.indexOf(operationFlags[i]) >= 0) {
        console.log('url初步判断为相关修改操作，不做拦截' + operationFlags[i], opt.url);
        return checkFlag;
      }
    }
  }
  console.log('url初步判断不为相关修改操作拦截未通过，进行下面的判断。。。。。。');
  /** 非法字符的判断 */
  if (opt && opt.data) {
    for (let dat in opt.data) {
      let content = opt.data[dat] + '';
      console.log('content', content);
      invalidCharacters.map((item, index) => {
        console.log('(opt.indexOf', content.indexOf(item) >= 0);
        let arrayCheck = [];
        if (content.indexOf(item) >= 0) {
          console.log('specialCharacters.indexOf(item)', continueRepeatCharacters.indexOf(item));
          // 不能重复连续输入的特殊字符，只要有两个这样的字符不通过
          if (continueRepeatCharacters.indexOf(item) >= 0) {
            if (constinueRepeatCharacterReg.test(content)) {
              checkFlag = true;
            }
          } else if (specialCharacters.indexOf(item) >= 0) {
            // 特殊字符的判断
            if ((content.indexOf(item) === 0 || content.lastIndexOf(item) === (content.length - 1))) {
              // 如果特殊字符在最开始与最末尾进行拦截，例如_字符，因为请求字典表数据需要将请求字典表参数通过
              checkFlag = true;
            }
          } else {
            // 不是特别的字符就拦截不通过
            checkFlag = true;
          }
        }
      });
      /** 如果已经包含某个非法字符并且判断成功则退出 */
      if (checkFlag) {
        break;
      }
    }
  }
  return checkFlag;
}
