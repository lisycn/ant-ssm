import { callAjax, getLoginInfo, getLocalItem } from '../../common/util';
import { ylagent } from "../../config/server";
import { openNotice } from "../../common/antdUtil";
import { rspInfo } from "../../common/authConstant";

export const TOGGLE_MENU = "TOGGLE_MENU";
export const CLICK_MENU = "CLICK_MENU";
export const BUILD_MENU = "BUILD_MENU";

export function clickMenuAction(item) {
  return {
    type: CLICK_MENU,
    item: item
  }
}

//异步action 基于中间件thunkMiddleware (详见configureStore)
export function buildMenuAction(data) {
  const info = getLoginInfo(),
    sysId = info.sysId ? info.sysId : getLocalItem("sys");
	if (!isNaN(_IEVersion) && _IEVersion < 9) {  //ie8同步action
		let menus = [];
    callAjax({
      url: ylagent.authUsr.getMenuBySys,
      async: false,
      type: "POST",
      dataType: "json",
      data: { sysId: sysId ? sysId : "009" }  //  权限系统id默认009
    }, (result) => {
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        menus = result.rspData.usrLoginAuthList
      } else {
        openNotice("error", result.rspMsg, "登录失败");
      }
    }, () => {
      openNotice("error", "请求发送失败", "错误");
    });
    return { type: BUILD_MENU, data: menus }
	}else {
		return (dispatch, getState) => {
			callAjax({
				url: ylagent.authUsr.getMenuBySys,
				type: "POST",
				dataType: "json",
				data: { sysId: sysId ? sysId : "009" }  //  权限系统id默认009
			}, (result) => {
				let menus = []
				if (result.rspCod === rspInfo.RSP_SUCCESS) {
					menus = result.rspData.usrLoginAuthList
				} else {
					openNotice("error", result.rspMsg, "登录失败");
				}
				dispatch({ type: BUILD_MENU, data: menus });
			}, () => {
				dispatch({ type: BUILD_MENU, data: [] });
				openNotice("error", "请求发送失败", "错误");
			});
		}
	}
}

export function toggleMenuAction(item) {
  return {
    type: TOGGLE_MENU,
    item: item
  }
}
