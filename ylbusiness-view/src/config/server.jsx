import { baseAuthApiUrl, baseJobApiUrl, dubboApiUrl, baseLogUrl, logoutAuthApiUrl, baseAgentApiUrl } from './url';
export const apiPath = baseAgentApiUrl + 'ylbusiness-web/'; // 权限系统
export const jobPath = baseJobApiUrl + 'xxl-job-admin/';  // JOB接口地址
export const dubboPath = dubboApiUrl + 'dubbo-admin/governance/';         // dubbo管理平台
export const logPath = baseLogUrl + 'tddef-log/logManager/';  // 日志查看平台
export const agentPath = baseAgentApiUrl + 'ylbusiness-web/'; // 线下收单系统
export const logoutPath = logoutAuthApiUrl + 'ylbusiness-web/';  // 日志查看平台

export const ylagent = {
  authUsr: {
    userLogin: apiPath + 'authUsr/login.do',
    userLogout: apiPath + 'authUsr/logout.do',
    getLoginInfoByToken: apiPath + 'authUsr/getLoginInfoByToken.do',
    getSysInfByUsr: apiPath + 'authUsr/getSysInfByUsr.do',
    getMenuBySys: apiPath + 'authUsr/getMenuBySys.do',
    queryUserList: apiPath + 'authUsr/selectByPage.do',
    addUser: apiPath + 'authUsr/add.do',
    editUser: apiPath + 'authUsr/update.do',
    deleteUser: apiPath + 'authUsr/delByUsrId.do',
    enableUser: apiPath + 'authUsr/disOrEnable.do',
    resetUsrPwd: apiPath + 'authUsr/resetUsrPwd.do',
    updatePwd: apiPath + 'authUsr/updatePwd.do',
    unLock: apiPath + 'authUsr/unLock.do',
  },
  //  记录查询
  record: {
    queryCallbackRecord: apiPath + 'channelController/callbackRecord/query.do',
    queryChangerateRecord: apiPath + 'channelController/changerateRecord/query.do',
    queryCodepayRecord: apiPath + 'channelController/codepayRecord/query.do',
    queryDownloadkeysRecord: apiPath + 'channelController/downloadkeysRecord/query.do',
    queryRegesitorRecord: apiPath + 'channelController/regesitorRecord/query.do',
    queryValidcardRecord: apiPath + 'channelController/validcardRecord/query.do',
    userinfoRecord: apiPath + 'channelController/userinfoRecord/query.do',
  },
  //  码表
  pubDict: {
    qryPubDictList: apiPath + 'dict/qryPubDictList.do',
    updDictRediu: apiPath + 'dict/updDictRediu.do',
  },
  // 交易报表
  report: {
  //  codePayReport: apiPath + 'report/codePayReport.do',
    dailyReport: apiPath + 'report/dailyReport.do',
    monthReport: apiPath + 'report/monthReport.do',
    // queryTransactionList: agentPath+'transactionManageController/queryTransactionList.do',
    queryTransactionList: `${agentPath}transactionManageController/queryTransactionList.do`,


  //  queryTransactionList: agentPath+'transactionManageController/queryTransactionList.do',
  },
  authRole: {
    queryRoleList: apiPath + 'authRole/selectByPage.do',
    addRole: apiPath + 'authRole/add.do',
    editRole: apiPath + 'authRole/update.do',
    deleteRole: apiPath + 'authRole/delByRoleId.do',
    enableRole: apiPath + 'authRole/disOrEnable.do',
    disRole: apiPath + 'authRole/assignRolePre.do',
    assignUsrRole: apiPath + 'authRole/assignRole.do',
    assignRole: apiPath + 'authRole/assignAuthPre.do',
    assignAuth: apiPath + 'authRole/assignAuth.do',
  },
  // 菜单管理
  authItem: {
    queryItemList: apiPath + 'authItem/selectItemTreeTable.do',
    addItem: apiPath + 'authItem/add.do',
    editItem: apiPath + 'authItem/update.do',
    deleteItem: apiPath + 'authItem/delByItmId.do',
    enableItem: apiPath + 'authItem/disOrEnable.do',

  },
  // 机构管理
  authOrg: {
    queryOrgList: apiPath + 'authOrg/selectOrgTreeTable.do',
    addOrg: apiPath + 'authOrg/add.do',
    ediOrg: apiPath + 'authOrg/update.do',
    deleteOrg: apiPath + 'authOrg/delByOrgId.do',
    enableOrg: apiPath + 'authOrg/disOrEnable.do',
    selectDropdownListTree: apiPath + 'authOrg/selectDropdownListTree.do',
    selectByOrgId: apiPath + 'authOrg/selectByOrgId.do',
  },
  // 代理商管理
  agent: {
    queryAgentList: `${agentPath}agentManage/queryAgentList.do`,
    querySingleProfitSharList: `${agentPath}singleProfitSharManageController/querySingleProfitSharList.do`,
    querydayProfitSharList: `${agentPath}dayProfitSharManageController/querydayProfitSharList.do`,
    queryMonthProfitSharList: `${agentPath}monthProfitSharManageController/queryMonthProfitSharList.do`,
    queryChildAgent: `${agentPath}agent/queryChildAgent.do`,
    // 文件下载
    fileDownloadList: `${agentPath}system/fileDownload/fileDownloadList.do`,
    fileDown: `${agentPath}system/fileDownload/fileDown.do`,
    noticeList: `${agentPath}system/notice/noticeList.do`,
  },
  mer: {
    selectUsrNameByUserId: `${agentPath}tdpos/merManage/selectUsrNameByUserId.do`,
    updateMerStatus: `${agentPath}merManageController/updateMerStatus.do`,
    updateMerRateInfo: `${agentPath}merManageController/updateMerRateInfo.do`,
    bindAgent: `${agentPath}merManageController/bindAgent.do`,
    qryMerInfList: `${agentPath}merManageController/queryList.do`,
  },

  logout: {
    logoutPath: logoutPath + 'authUsr/logout.do',
   // http://monitor.tombot.cn/tombot-authWeb/authUsr/logout.do
  },
  business: {
    merchandiseTypeList: `${agentPath}business/merchandiseTypeList.do`,
    addMerchandiseType: `${agentPath}business/addMerchandiseType.do`,
    deleteMerchandiseType: `${agentPath}business/deleteMerchandiseType.do`,
    queryMerchandiseTypeById: `${agentPath}business/queryMerchandiseTypeById.do`,
    updateMerchandiseById: `${agentPath}business/updateMerchandiseById.do`,
    queryMerchandiseList: `${agentPath}business/queryList.do`,
    deleteEnerty: `${agentPath}business/deleteEnerty.do`,
    insertentity: `${agentPath}business/insertEntity.do`,
  },

  // 工作流程配置管理
  workflow: {
    modelMain: {
      queryModelMainList: apiPath + 'workflow/findModelMainByPage.do',
      addModelMain: apiPath + 'workflow/addModelMain.do',
      updateModelMain: apiPath + 'workflow/updateModelMain.do',
      batchEnableModelMain: apiPath + 'workflow/batchEnableModelMain.do',
      disableModelMain: apiPath + 'workflow/disableModelMain.do',
      deleteModelMainByNos: apiPath + 'workflow/deleteModelMainByNo.do',
      findUnfinishedFlowStatusEntity: apiPath + 'workflow/findUnfinishedFlowStatusEntity.do',
      saveModelPath: apiPath + 'workflow/buildModelPath.do',
      findProcessCfg: apiPath + 'workflow/findProcessCfg.do',
      queryModelNode: apiPath + 'workflow/buildModelPathPre.do',
    },
    modelNode: {
      queryNodeList: apiPath + 'workflow/findModelNodeByPage.do',
      addModelNode: apiPath + 'workflow/addModelNode.do',
      queryNodeDictMenu: apiPath + 'workflow/findDDLForAddNode.do',
      updateModelNode: apiPath + 'workflow/updateModelNode.do',
      deleteModelNodeByNo: apiPath + 'workflow/deleteModelNodeByNo.do',
      batchEnableModelNode: apiPath + 'workflow/batchEnableModelNode.do',
      disableModelNode: apiPath + 'workflow/disableModelNode.do',
      findNodeListByPositionno: apiPath + 'workflow/findNodeListByPositionno.do',
    },
    modelPosition: {
      queryPositionList: apiPath + 'workflow/findModelPositionByPage.do',
      addPosition: apiPath + 'workflow/addModelPosition.do',
      editPosition: apiPath + 'workflow/updateModelPosition.do',
      disableModelPosition: apiPath + 'workflow/disableModelPosition.do',
      batchEnableModelPosition: apiPath + 'workflow/batchEnableModelPosition.do',
      deleteModelPositionByNo: apiPath + 'workflow/deleteModelPositionByNo.do',
      addModelPositionRolesPre: apiPath + 'workflow/addModelPositionRolesPre.do',
      addModelPositionRoles: apiPath + 'workflow/addModelPositionRoles.do',
      queryPositionMappingList: apiPath + 'workflow/findModelPositionMappingByPage.do',
      deleteModelPositionMappingByNos: apiPath + 'workflow/deleteModelPositionMappingByNos.do',
      findPositionMappingListByPositionno: apiPath + 'workflow/findPositionMappingListByPositionno.do',
    },
    taskMonitor: {
      findTaskByPage: apiPath + 'workflow/findTaskByPage.do',
      findTrace: apiPath + 'workflow/findTrace.do',
      findTaskByPagePre: apiPath + 'workflow/findTaskByPagePre.do',
    },
  },
  dubbo: {
    services: {
      getAllServices: dubboPath + 'getAllServices.do',
    },
    applications: {
      getAllApplications: dubboPath + 'getAllApplications.do',
    },
    addresses: {
      getAllAddresses: dubboPath + 'getAllAddresses.do',
    },
    providers: {
      getAllProviders: dubboPath + 'getAllProviders.do',
    },
    consumers: {
      getAllComsumers: dubboPath + 'getAllConsumers.do',
    },
    loadbalances: {
      getAllLoadBalances: dubboPath + 'getAllLoadbalances.do',
      getServiceList: dubboPath + 'getServiceList.do',
      createLoadbalance: dubboPath + 'createLoadbalance.do',
      editLoadbalance: dubboPath + 'editLoadbalance.do',
      deleteLoadbalance: dubboPath + 'deleteLoadbalance.do',
    },
  },
  log: {
    logInfo: {
      getAll: logPath + 'findAll',
    },
  },
};
// 考虑将通用部分码表移至权限系统
export const tdpub = {
  // 码表
  dict: {
    qryDictList: agentPath + 'dict/qryDictList.do',
    qryDictMutiList: agentPath + 'dict/qryDictMutiList.do',
  },
  attachment: {
    upload: agentPath + 'attachment/commonFileUpload.do',
  },
  // 下载中心
  download: {
    downloadFile: `${agentPath}tdposPub/download/downloadFile.do`,
    qryDownloadList: `${agentPath}tdposPub/download/qryDownloadList.do`,
    delCurrRecord: `${agentPath}tdposPub/download/delCurrRecord.do`,
    getStreamFile: `${agentPath}tdposPub/download/getStreamFile.do`,
    downloadTemplate: `${agentPath}tdposPub/download/downloadTemplate.do`,
  },
};

export const tdJobUrl = {
  jobInfo: {
    pageList: jobPath + 'jobinfo/pageList.do',
    jobAdd: jobPath + 'jobinfo/add.do',
    reschedule: jobPath + 'jobinfo/reschedule.do',
    remove: jobPath + 'jobinfo/remove.do',
    pause: jobPath + 'jobinfo/pause.do',
    resume: jobPath + 'jobinfo/resume.do',
    trigger: jobPath + 'jobinfo/trigger.do',
  },
  jobLog: {
    pageList: jobPath + 'joblog/pageList.do',
    logDetail: jobPath + 'joblog/logDetail.do',
    logKill: jobPath + 'joblog/logKill.do',
  },
};
