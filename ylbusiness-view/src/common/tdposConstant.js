/**
 * 线下收单支付
 */
/**
 * 默认Table的分页信息
 */
export const defaultPage = {
  pageSize: 10,
  pageNum: 1,
};
/**
 * 后台返回信息标识
 */
export const rspInfo = {
  COMM_SUCCESS: '02000000',
  // 对账管理-试算平衡
  RSPCOD_SUCCESS: '000000',
  RSP_NETWORK_ERROR: '网络错误',  // 网络错误标识
  PUB_RSPCOD_SUCCESS: '000000', // 公用的交易返回的成功码
  PUB_PAY_RSPCOD_SUCCESS: '01000000',

  CHK_RSPCOD_SUCCESS: '02030000', // 对账成功标识
};

export const posConstant = {
  // 线下收单-操作成功
  POS_SUCCEESS: '02000000',
  // 线下收单-操作失败
  POS_FAILURE: '02999999',
  // 返回操作结果成功
  RSPCOD_SUCCESS: '000000',
  /** 程序异常返回 */
  PROGRAMMER_ERROR: '02999999',
  RSP_NETWORK_ERROR: '网络错误',  // 网络错误标识
};

/**
 * 后台返回信息标识
 */
export const chnOrgBankRspInfo = {
  CHN_ORG_PAY_RSP_SUCCESS: '01070000',   // 返回成功标识
  RSP_ERROR: '',  // 返回错误标识，一般不需要
  CHN_ORG_PAY_RSP_NETWORK_ERROR: '网络错误',  // 网络错误标识
};
/**
 * 会员常量
 */
export const memConstant = {
  SYS_MEMBER_USR_TYPE: '001', // 用户
  SYS_MEMBER_MER_TYPE: '002', // 商户
};
/**
 * 风控常量
 */
export const rcsConstant = {
  RCS_EXEC_TRAN_STS_SUS: '01', // 可疑交易
  RCS_EXEC_TRAN_STS_EXC: '02', // 异常交易
  RCS_EXEC_TRAN_STS_FREE: '03', // 释放交易
  RCS_IS_NOT_USE: '0', // 是否可用-禁用
};
/**
 * 对账常量
 */
export const chkConstant = {
  SYS_ID: '011',
  CHK_CHN_STS_BM: '02',  // 通道对账状态-业务多账
  CHK_CHN_STS_CHM: '03', // 通道对账状态-通道多账
  CHK_CHN_STS_AMTERR: '04', // 通道对账状态-金额不符
  CHK_ERR_DEAL_STS_FIN: '01', // 错账处理状态-处理完成
};
/**
 * 公共常量
 */
export const pubConstant = {
  RSPCOD_SUCCESS: '01000000',
  RSP_NETWORK_ERROR: '网络错误',
  PUB_EXPORT_TYPE_ORD_TRANS: '01', // 导出类型-转账订单导出
  PUB_EXPORT_TYPE_USR_BASIC: '02', // 导出类型-用户基本信息导出
  PUB_EXPORT_TYPE_USR_ACCOUNT: '03', // 导出类型-用户账户信息导出
  PUB_EXPORT_TYPE_USR_CARD: '04', // 导出类型-用户银行卡绑定信息导出
  PUB_EXPORT_TYPE_STL_PAY: '05', // 导出类型-结算信息导出
  PUB_EXPORT_TYPE_SHR_PAY: '06', // 导出类型-分润信息导出
  PUB_EXPORT_TYPE_RCS_ONEUSERLIMIT: '07', // 导出类型-风控指定用户限额导出
  PUB_EXPORT_TYPE_RCS_ALLUSERLIMIT: '08', // 导出类型-风控分类用户限额导出
  PUB_EXPORT_TYPE_RCS_ONEMERLIMIT: '09', // 导出类型-风控指定商户限额导出
  PUB_EXPORT_TYPE_RCS_ALLMERLIMIT: '10', // 导出类型-风控所有商户限额导出
  PUB_EXPORT_TYPE_RCS_USERLEVEL: '11', // 导出类型-用户等级信息
  PUB_EXPORT_TYPE_RCS_PAYBLACK: '12', // 导出类型-用户黑名单信息
  PUB_EXPORT_TYPE_RCS_TXNRECORDINF: '13', // 导出类型-实时监控记录
  PUB_EXPORT_TYPE_RCS_TXNRECORDHIS: '14', // 导出类型-历史监控记录
  PUB_EXPORT_TYPE_RCS_EXCEPTRULE: '15', // 导出类型-异常规则
  PUB_EXPORT_TYPE_ORD_PRD: '16', // 导出类型-消费订单导出
  PUB_EXPORT_TYPE_ORD_CHA: '17', // 导出类型-充值订单导出
  PUB_EXPORT_TYPE_ORD_WIT: '18', // 导出类型-提现订单导出
  PUB_EXPORT_TYPE_ORD_REF: '19', // 导出类型-退款订单导出
  PUB_EXPORT_TYPE_AGT_EXCEPTRULE: '20', // 导出类型-代理商基本信息导出
  PUB_EXPORT_TYPE_AGT_ACC_EXCEPTRULE: '21', // 导出类型-代理商账户信息导出
  PUB_EXPORT_TYPE_MER_BASIC: '22', // 导出类型-商户信息管理记录导出
  PUB_EXPORT_TYPE_MERACC: '23', // 导出类型-商户账户信息记录导出
  PUB_EXPORT_TYPE_CAS_ACCBALANCEINFO: '24', // 账户平衡查询信息记录导出
  PUB_EXPORT_TYPE_CAS_SUBBALANCEINFO: '25', // 科目平衡查询信息记录导出
  PUB_EXPORT_TYPE_CHK_COREACC: '26', // 导出类型-核心对账信息记录导出
  PUB_EXPORT_TYPE_CHK_ERRORINF: '27', // 导出类型-错账处理列表
  PUB_EXPORT_TYPE_CHK_DOUBT: '28', // 导出类型-疑账处理列表
  PUB_EXPORT_TYPE_CHK_PAYCHN: '29', // 导出类型-支付通道对账单
  PUB_EXPORT_TYPE_REPORT_USRDAILYORD: '30', // 导出类型-用户每日交易统计
  PUB_EXPORT_TYPE_CAS_ACCTXNINFO: '31', // 导出类型-账户流水导出
  PUB_EXPORT_TYPE_CAS_ACCMANAGEINFO: '32', // 导出类型-账户维护导出
  PUB_EXPORT_TYPE_CAS_ACCFROZINFO: '33', // 导出类型-账户冻结导出
  PUB_EXPORT_TYPE_ORD_WIT_UNHANDLE: '34', // 导出类型-提现待汇款单导出
  PUB_EXPORT_TYPE_CAS_TXNJNLINFO: '35', // 导出类型-账务流水查询信息导出
  PUB_EXPORT_TYPE_POS_BRA_BASIC: '40', // 导出类型-收单系统门店信息管理记录导出
  PUB_EXPORT_TYPE_POS_BRA_ACC: '41', // 导出类型-收单系统门店账户信息管理记录导出
  PUB_EXPORT_TYPE_POS_TER_KEY: '42', // 导出类型-收单系统终端主密钥下载记录导出
  PUB_EXPORT_TYPE_POS_TRANS: '50', // 导出类型-收单系统交易信息管理记录导出
  PUB_EXPORT_TYPE_POS_CORE_CHK: '55', // 导出类型-收单系统核心对账记录导出
  PUB_EXPORT_TYPE_POS_AGT_PROFIT: '56', // 导出类型-收单系统代理商分润记录导出
  PUB_EXPORT_TYPE_POS_TRANS_HIS: '51', // 导出类型-收单系统交易信息管理记录导出
  PUB_EXPORT_TYPE_CHK_DOUBT_INF: '52', // 导出类型-疑账信息导出
  PUB_EXPORT_TYPE_CHK_ERR_INF: '53', // 导出类型-错账信息导出
  PUB_EXPORT_TYPE_CLR_PAY: '54', // 导出类型-支付明细导出
  PUB_EXPORT_TYPE_CLR_SUM: '40', // 导出类型-清分汇总信息导出
  PUB_EXPORT_TYPE_CLR_DET: '42', // 导出类型-清分支付明细导出
  PUB_EXPORT_TYPE_CHNMER_TXN_DAY: '60', // 导出类型-渠道商户交易统计日报表
  PUB_EXPORT_TYPE_CHNMER_TXN_MONTH: '61', // 导出类型-渠道商户交易统计月报表
  PUB_EXPORT_TYPE_AGT_APPLY_COUNT: '62', // 导出类型-代理商申请统计报表
  PUB_EXPORT_TYPE_MER_APPLY_COUNT: '63', // 导出类型-商户申请易统计报表
  PUB_EXPORT_TYPE_AGT_TXN_DAY: '64', // 导出类型-代理商交易统计日报表
  PUB_EXPORT_TYPE_AGT_TXN_MONTH: '65', // 导出类型-代理商交易统计月报表
  PUB_EXPORT_TYPE_MER_TXN_DAY: '66', // 导出类型-商户交易统计日报表
  PUB_EXPORT_TYPE_MER_TXN_MONTH: '67', // 导出类型-商户交易统计月报表
  PUB_EXPORT_TYPE_BRA_TXN_DAY: '68', // 导出类型-门店交易统计日报表
  PUB_EXPORT_TYPE_BRA_TXN_MONTH: '69', // 导出类型-门店交易统计月报表
  PUB_EXPORT_TYPE_TERM_TXN_DAY: '70', // 导出类型-终端交易统计日报表
  PUB_EXPORT_TYPE_TERM_TXN_MONTH: '71', // 导出类型-终端交易统计月报表
  PUB_EXPORT_TYPE_CARD_TXN_DAY: '72', // 导出类型-银行卡交易统计日报表
  PUB_EXPORT_TYPE_CARD_TXN_MONTH: '73', // 导出类型-银行卡交易统计月报表
  PUB_EXPORT_TYPE_CHN_TXN_DAY: '74', // 导出类型-渠道交易统计日报表
  PUB_EXPORT_TYPE_CHN_TXN_MONTH: '75', // 导出类型-渠道交易统计月报表
  PUB_AUTH_OBJECT_PERSON: '00', // 可查看对象-个人
  PUB_AUTH_OBJECT_MER: '01', // 可查看对象-商户
  PUB_AUTH_OBJECT_ROLE: '02', // 可查看对象-角色
  PUB_DELETE_FILE_CAN_NOT: '00', // 文件是否可删除-否
  PUB_DELETE_FILE_CAN: '01', // 文件是否可删除-是
  PUB_SAVE_DAYS: '7', // 文件保存天数
};
