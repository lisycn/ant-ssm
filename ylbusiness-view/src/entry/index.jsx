import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, IndexRedirect, hashHistory } from 'react-router';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import configureStore from '../redux/store/configureStore';
import { checkLogin, getLoginInfo, getLocalToken, getLocalItem, refreshLoginInfo, getUrlVal } from '../common/util';
import App from '../view/app/App';
import Login from '../view/login/Login';
import SysInf from '../view/sys/SysInf';
import GetTkn from '../view/sys/GetTkn';
import Main from '../view/main/Main';
import Home from '../view/home/Home';
import Error401 from '../view/error/401/Error401';
import Error404 from '../view/error/404/Error404';
import Error410 from '../view/error/410/Error410';
// 权限管理
import AuthUserManage from '../view/auth/userManage/AuthUserManage';
import AuthRoleManage from '../view/auth/roleManage/AuthRoleManage';
import AuthOrgManage from '../view/auth/orgManage/AuthOrgManage';
import AuthMenuManage from '../view/auth/menuManage/AuthMenuManage';
import AuthUpdPwd from '../view/auth/updPwd/AuthUpdPwd';
//  记录管理
import CallbackRecordManage from '../view/record/callbackRecord/CallbackRecordManage';
import ChangerateRecordManage from '../view/record/changerateRecord/ChangerateRecordManage';
import CodepayRecordManage from '../view/record/codepayRecord/CodepayRecordManage';
import DownloadkeysRecordManage from '../view/record/downloadkeysRecord/DownloadkeysRecordManage';
import RegesitorRecordManage from '../view/record/regesitorRecord/RegesitorRecordManage';
import ValidcardRecordManage from '../view/record/validcardRecord/ValidcardRecordManage';
import UserInfoManage from '../view/record/userinfoRecord/UserInfoManage';
//  码表管理
import PubDictManage from '../view/pub/pubDict/PubDictManage';
// 交易报表
// import CodePayReportManage from '../view/report/codePayReport/CodePayReportManage';
// import DailyReportManage from '../view/report/dailyReport/DailyReportManage';
// import MonthReportManage from '../view/report/monthReport/MonthReportManage';
// 模板管理
import WorkflowModelMainManage from '../view/workflow/main/WorkflowMainManage';
// 岗位管理
import WorkflowModelPositionManage from '../view/workflow/position/WorkflowPositionManage';
// 节点管理
import ModelNodeManage from '../view/workflow/procedure/ModelProcedureManage';
// 任务监控
import TaskMonitorManage from '../view/workflow/taskMonitor/TaskMonitorManage';
// 任务调度
import JobInfoManage from '../view/job/jobInfo/JobInfoManage';
import JobLogManage from '../view/job/jobLog/JobLogManage';
import JobLogDetail from '../view/job/jobLogDetail/JobLogDetail';

// 日志管理
import LogManage from '../view/log/logInfo/LogManage';

// dubbo
import DubboServicesManage from '../view/dubbo/services/DubboServicesManage';
import AdderssesManage from '../view/dubbo/addresses/AddressesManage';
import ApplicationsManage from '../view/dubbo/applications/ApplicationsManage';
import ConsumersManage from '../view/dubbo/consumers/ConsumersManage';
import ProvidersManage from '../view/dubbo/providers/ProvidersManage';
import LoadbalancesManage from '../view/dubbo/loadbalances/LoadbalancesManage';

// 代理商管理
import AgentManage from '../view/agent/AgentManager/AgentManage';
import DayProfitSharReport from '../view/agent/DayProfitSharReports/DayProfitSharReport';
import MonthProfitSharReport from '../view/agent/MonthProfitSharReports/MonthProfitSharReport';
import SingleProfitSharReport from '../view/agent/SingleProfitSharReports/SingleProfitSharReport';
import OverviewManager from '../view/agent/OverviewManager/OverviewManage';
import FileDownloadManage from '../view/agent/FileDownload/FileDownloadManage';

// 二维码交易
import QRCodetransManage from '../view/transaction/QRCodetransaction/QRCodetransManage';
// 商户管理
import MerchantInfoManage from '../view/merchant/merchantInfo/MerchantInfoManage';



//商城
import MerchandiseTypeManage from '../view/business/merchandisetype/MerchandiseTypeManage';
import MerchandiseManage from '../view/business/merchandise/MerchandiseManage';


import BASE64 from 'crypto-js/enc-base64';
import ENC_UTF8 from 'crypto-js/enc-utf8';
// 针对IE8的补丁(_IEVersion from index.html)
if (!isNaN(_IEVersion) && _IEVersion < 9) {
  require('zrender/lib/vml/vml');  // 此处必须使用es5的写法？
}

const store = configureStore();
// 页面刷新或关闭时保存当前APP状态
$(window).bind('beforeunload', () => {
  sessionStorage.removeItem('appState');
  sessionStorage.setItem('appState', JSON.stringify(store.getState()));
});

// 权限校验(包括菜单权限菜单校验 和 登录token过期校验)
function checkAuth(nextState, replace) {
    // 使用nextState.location.pathname获取路径(如：main/demo/00)
    // const path = nextState.location.pathname;
    // 权限信息应在登录后保存到loginInfo中, 通过getLoginInfo方法获取并校验
    // 若登录token过期, 则：
    // replace("/login");
    // 若权限不匹配, 则：
    // replace("main/error/401");
}

/**
 * 解析token
 */
const parseToken = (url) => {
  const param = BASE64.parse(decodeURI(getUrlVal(url, 'tk'))).toString(ENC_UTF8);
  return param;
};

function pageRedirect() {
    // const url = window.location.href, uSysId = getUrlVal(url, "sys"), uTk = getUrlVal(url, "tk");
    // const isLogin = checkLogin(), lSysId = getLocalItem("sys"), lTk = getLocalToken();
  const info = getLoginInfo(), lTk = getLocalToken(), lSysId = getLocalItem('sys');
  let page = 'login';
    // 如果为权限系统009
    // if (lSysId === null || lSysId === "009") {
  if (lTk !== null) {
    if (info.token === undefined) {
      const param = parseToken(window.location.href);
      refreshLoginInfo(param, false);
    }
    if (lSysId === null) {
            // 此处最好跳转至当前路由
      page = 'sysInf';
    } else {
      page = 'main';
    }
  }
    // }
  return page;
}

ReactDOM.render(
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route path="/" component={App}>
                <IndexRedirect to={ pageRedirect() } />
                <Route path="login" component={Login} />
                <Route path="getTkn" component={GetTkn} />
                <Route path="sysInf" component={SysInf} />
                <Route path="jobLogDetail" component={JobLogDetail} />
                <Route path="main" component={Main}>
                    <IndexRedirect to="home" />
                    <Route path="error/401" component={Error401} />
                    <Route path="error/410" component={Error410} />
                    <Route onEnter={checkAuth} path="home" component={Home} />
                    <Route onEnter={checkAuth} path="auth/userManage" component={AuthUserManage} />
                    <Route onEnter={checkAuth} path="auth/roleManage" component={AuthRoleManage} />
                    <Route onEnter={checkAuth} path="auth/orgManage" component={AuthOrgManage} />
                    <Route onEnter={checkAuth} path="auth/menuManage" component={AuthMenuManage} />
                    <Route onEnter={checkAuth} path="auth/updPwd" component={AuthUpdPwd} />
                    <Route path="workflow/modelMain" component={WorkflowModelMainManage} />
                    <Route path="workflow/modelPosition" component={WorkflowModelPositionManage} />
                    <Route path="workflow/modelNode" component={ModelNodeManage} />
                    <Route path="workflow/taskMonitor" component={TaskMonitorManage} />
                    <Route path="job/jobInfo" component={JobInfoManage} />
                    <Route path="job/jobLog" component={JobLogManage} />

                    <Route path="dubbo/services" component={DubboServicesManage} />
                    <Route path="dubbo/addresses" component={AdderssesManage} />
                    <Route path="dubbo/applications" component={ApplicationsManage} />
                    <Route path="dubbo/consumers" component={ConsumersManage} />
                    <Route path="dubbo/providers" component={ProvidersManage} />
                    <Route path="dubbo/loadbalances" component={LoadbalancesManage} />


                    <Route path="log/logInfo" component={LogManage} />

                    <Route path="record/callbackRecord" component={CallbackRecordManage} />
                    <Route path="record/changerateRecord" component={ChangerateRecordManage} />
                    <Route path="record/codepayRecord" component={CodepayRecordManage} />
                    <Route path="record/downloadkeysRecord" component={DownloadkeysRecordManage} />
                    <Route path="record/regesitorRecord" component={RegesitorRecordManage} />
                    <Route path="record/validcardRecord" component={ValidcardRecordManage} />
                    <Route path="record/userinfoRecord" component={UserInfoManage} />

                    <Route path="pub/pubDict" component={PubDictManage} />
                  {/* <Route path="report/codePayReport" component={CodePayReportManage} />
                    <Route path="report/dailyReport" component={DailyReportManage} />
                    <Route path="report/monthReport" component={MonthReportManage} />*/}

                    <Route path="agent/agentmanager" component={AgentManage} /> 
                    <Route path="agent/dayprofitsharreport" component={DayProfitSharReport} />
                    <Route path="agent/monthprofitsharreport" component={MonthProfitSharReport} />
                    <Route path="agent/singleprofitsharreport" component={SingleProfitSharReport} />
                    <Route path="agent/overviewmanager" component={OverviewManager} /> 
                    <Route path="agent/fileDownload" component={FileDownloadManage} />
                    <Route path = "transaction/QRCodetransaction" component={QRCodetransManage} />
                    <Route path="merchant/merchantInfo" component={MerchantInfoManage} />
                   {/* 商城 */}
                    <Route path="business/merchandiseType" component={MerchandiseTypeManage} />
                    <Route path="business/merchandise" component={MerchandiseManage} />
                    
                    <Route path="*" component={Error404} />
                </Route>
                <Route path="*" component={Error404} />
            </Route>
        </Router>
    </Provider>
    , document.getElementById('root')
);

