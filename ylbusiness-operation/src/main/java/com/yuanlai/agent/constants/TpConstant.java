package com.yuanlai.agent.constants;

/**
 * 互联网支付静态常量类
 * 
 * @author
 * @version 1.0.0 2016-04-21
 */
public class TpConstant {
	/** 互联网支付系统ID */
	public static final String SYS_ID_TDPAY = "010";
	/** 收单系统ID */
	public static final String SYS_ID_TDPOS = "011";
	/** 会员类型-商户 */
	public static final String SYS_MEMBER_MER_TYPE = "002";
	/** 会员类型-用户 */
	public static final String SYS_MEMBER_USR_TYPE = "001";
	
	/** 国际化类型名称 */
	public static final String SYS_LANGUAGE_NAME = "language";
	/** 国际化类型名称 */
	public static final String SYS_LANGUAGE_ZH = "zh";
	/** 国际化类型名称 */
	public static final String SYS_LANGUAGE_EN = "en";
	
	/** 支付方式-全部 */
	public static final String PAY_TYPE_ALL = "00";
	/** 支付方式-虚拟账户支付 */
	public static final String PAY_TYPE_VIRTUAL = "01";
	/** 支付方式-网银支付 */
	public static final String PAY_TYPE_ONLINE = "02";
	/** 支付方式-快捷支付 */
	public static final String PAY_TYPE_QUICK = "03";
	/** 支付方式-终端支付 */
	public static final String PAY_TYPE_TERMINAL = "04";
	/** 支付方式-代付 */
	public static final String PAY_TYPE_OTHER_PAY = "05";

	// *************ADD BY RCS START********************//

	/** 密码类型-登录密码 */
	public static final String RCS_PWD_TYPE_SIGN = "0";
	/** 密码类型-支付密码 */
	public static final String RCS_PWD_TYPE_PAY = "1";
	/** 支付密码正确 */
	public static final String RCS_PWD_PAY_SUC = "06";
	/** 支付密码错误 */
	public static final String RCS_PWD_PAY_ERR = "05";
	/** 支付解锁 */
	public static final String RCS_PWD_PAY_UNFREEZE = "12";
	/** 登录密码正确 */
	public static final String RCS_PWD_SIGN_SUC = "00";
	/** 登录密码错误 */
	public static final String RCS_PWD_SIGN_ERR = "01";
	/** 登录解锁 */
	public static final String RCS_PWD_SIGN_UNFREEZE = "11";
	/** 默认密码冻结次数 */
	public static final String RCS_PWD_DEF_ERR_TIMES = "6";
	/** 默认密码冻结时间 单位：分钟 */
	public static final String RCS_PWD_DEF_FREEZE_TIME = "120";
	/** 面对面认证 */
	public static final String RCS_IS_F2F = "1";
	/** 非面对面认证 */
	public static final String RCS_IS_NOT_F2F = "0";
	/** 限额检查类型-全部 */
	public static final String RCS_LIMIT_CHECK_TYPE_ALL = "3";
	/** 限额检查类型-接收方 */
	public static final String RCS_LIMIT_CHECK_TYPE_REC = "2";
	/** 限额检查类型-发起方 */
	public static final String RCS_LIMIT_CHECK_TYPE_SPONSOR = "1";
	/** 用户名单状态-黑名单 */
	public static final String RCS_MEM_STATUS_BLACK = "01";
	/** 用户名单状态-非黑名单 */
	public static final String RCS_MEM_STATUS_NON_BLACK = "00";
	/** 是否可用-禁用 */
	public static final String RCS_IS_NOT_USE = "0";
	/** 是否可用-可用 */
	public static final String RCS_IS_USE = "1";
	/** 异常规则类型-拦截规则 */
	public static final String RCS_RULE_TYPE_INTERCEPT = "01";
	/** 异常规则类型-分析规则 */
	public static final String RCS_RULE_TYPE_ANALYSIS = "02";
	/** 异常规则执行频率-准实时 */
	public static final String RCS_EXEC_RATE_REALTIME = "01";
	/** 异常规则执行频率-当天 */
	public static final String RCS_EXEC_RATE_DAY = "02";
	/** 异常规则执行频率-当月 */
	public static final String RCS_EXEC_RATE_MOUTH = "03";
	/** 异常规则警告类型-邮件 */
	public static final String RCS_WARN_LEVEL_EMAIL = "01";
	/** 异常规则警告类型-短信 */
	public static final String RCS_WARN_LEVEL_MESSAGE = "02";
	/** 异常规则邮件标题 */
	public static final String RCS_EXEC_MAIL_TITLE = "棠棣支付-风险预警";
	/** 操作结果 00:成功 01:失败 */
	public static final String RCS_EXEC_OPRA_EORRO = "01";
	/** 操作异常判别码 -限额规则 */
	public static final String RCS_EXEC_LIMIT_ERROR_CODE = "04";
	/** 操作异常判别码 -异常规则 */
	public static final String RCS_EXEC_RULE_ERROR_CODE = "07";
	/** 异常交易状态-可疑 */
	public static final String RCS_EXEC_TRAN_STS_SUS = "01";
	/** 异常交易状态-异常 */
	public static final String RCS_EXEC_TRAN_STS_EXC = "02";
	/** 异常交易状态-释放 */
	public static final String RCS_EXEC_TRAN_STS_FREE = "03";

	/** 用户认证等级-未认证 */
	public static final String USER_CERT_LEVET_0 = "0";
	/** 用户认证等级-Ⅰ类 */
	public static final String USER_CERT_LEVET_1 = "1";
	/** 用户认证等级-Ⅱ类 */
	public static final String USER_CERT_LEVET_2 = "2";
	/** 用户认证等级-Ⅲ类 */
	public static final String USER_CERT_LEVET_3 = "3";

	/** 用户认证方式 00-面对面认证 */
	public static final String USER_CERT_TYP_0 = "00";
	/** 用户认证方式 01-手机认证 */
	public static final String USER_CERT_TYP_1 = "01";
	/** 用户认证方式 02-邮箱认证 */
	public static final String USER_CERT_TYP_2 = "02";
	/** 用户认证方式 03-实名认证 */
	public static final String USER_CERT_TYP_3 = "03";
	/** 用户认证方式 04-证件认证 */
	public static final String USER_CERT_TYP_4 = "04";
	/** 用户认证方式 05-手持证件认证 */
	public static final String USER_CERT_TYP_5 = "05";

	/** 用户认证状态 0-未认证 */
	public static final String USER_IDENTIFY_STS_0 = "0";
	/** 用户认证状态 1-已认证 */
	public static final String USER_IDENTIFY_STS_1 = "1";
	/** 用户认证状态 2-认证不通过 */
	public static final String USER_IDENTIFY_STS_2 = "2";

	/** 黑名单类型-邮箱 */
	public static final String RCS_BLACK_TYPE_EMAIL = "03";
	/** 黑名单类型-手机号 */
	public static final String RCS_BLACK_TYPE_PHONE = "04";
	/** 黑名单类型-用户Id */
	public static final String RCS_BLACK_TYPE_USR_ID = "06";
	/** 黑名单类型-商户Id */
	public static final String RCS_BLACK_TYPE_MER_ID = "07";

	// *************ADD BY RCS END**********************//

	// ****************账户账务*****************
	/** 冻结 **/
	public static final String NORMALACC = "0";
	/** 解冻 **/
	public static final String DISABLEACC = "1";
	/** 账户类型--用户 **/
	public static final String ACCTYPEUSER = "001";// 用户
	/** 账户类型--商户 **/
	public static final String ACCTYPEMEM = "002";
	/** 账户类型--代理商 **/
	public static final String ACCTYPEAGENT = "003";
	/** 账户类型--预付卡 **/
	public static final String ACCTYPEPREP = "004";
	/** 账户类型--平台账户 **/
	public static final String ACCTYPEPLAT = "005";
	/** 账户类型--收入账户 **/
	public static final String ACCTYPEPYE = "006";
	/** 账户类型--支付通道账户 **/
	public static final String ACCTYPECHN = "007";
	/** 账户类型--成本账户 **/
	public static final String ACCTYPECOST = "008";

	/** 普通注册(商户) **/
	public static final String OPENACCTYPE01 = "01";//
	/** 理财注册 **/
	public static final String OPENACCTYPE02 = "02";//
	/** T0注册 **/
	public static final String OPENACCTYPE03 = "03";//
	/** 代理商注册 **/
	public static final String OPENACCTYPE04 = "04";//
	/** 预付卡注册 **/
	public static final String OPENACCTYPE05 = "05";//
	/** 普通注册(用户) **/
	public static final String OPENACCTYPE06 = "06";//
	/** 用户注册卖家 **/
	public static final String OPENACCTYPE07 = "07";//

	/** 记账类型-账户 */
	public static final String CAS_ACC_TYPE_ACCOUNT = "0";
	/** 记账类型-科目 */
	public static final String CAS_ACC_TYPE_SUBJECT = "1";
	/** 科目级别-非最后一级 */
	public static final String CAS_SUBJECT_NOT_LAST_LEVEL = "0";
	/** 科目级别-最后一级 */
	public static final String CAS_SUBJECT_IS_LAST_LEVEL = "1";
	/** 账务流水插入失败不删除任务 */
	public static final String CAS_INSERT_ERROR_NOTDEL = "1";
	/** 借贷标志 D-借 */
	public static final String CAS_DC_FLAG_D = "D";
	/** 借贷标志 C-贷 */
	public static final String CAS_DC_FLAG_C = "C";
	/** 记账状态 U-预计 */
	public static final String CAS_JNL_BOK_STS_U = "U";
	/** 记账状态 S-成功 */
	public static final String CAS_JNL_BOK_STS_S = "S";
	/** 记账状态 F-失败 */
	public static final String CAS_JNL_BOK_STS_F = "F";
	/** 币种 **/
	public static final String CCYTYP = "CNY";
	/** 币种常量 **/
	public static final String CCYTYPNUM = "01";
	/** 记账流水序号-账户左补 */
	public static final String CAS_ACC_LEFT = "A";
	/** 记账流水序号-科目左补 */
	public static final String CAS_SUBJECT_LEFT = "S";
	/** 借方账户A **/
	public static final String DACTNOA = "DACTNOA";
	/** 借方账户A记账规则 **/
	public static final String DACTNOADRULA = "DRULA";
	/** 借方账户B **/
	public static final String DACTNOB = "DACTNOB";
	/** 借方账户B记账规则 **/
	public static final String DACTNOADRULB = "DRULB";
	/** 借方账户C **/
	public static final String DACTNOC = "DACTNOC";
	/** 借方账户C记账规则 **/
	public static final String DACTNOADRULC = "DRULC";
	/** 借方账户D **/
	public static final String DACTNOD = "DACTNOD";
	/** 借方账户D记账规则 **/
	public static final String DACTNOADRULD = "DRULD";
	/** 借方账户E **/
	public static final String DACTNOE = "DACTNOE";
	/** 借方账户E记账规则 **/
	public static final String DACTNOADRULE = "DRULE";

	/** 贷方账户A **/
	public static final String CACTNOA = "CACTNOA";
	/** 贷方账户A记账规则 **/
	public static final String CACTNOADRULA = "CRULA";
	/** 贷方账户B **/
	public static final String CACTNOB = "CACTNOB";
	/** 贷方账户B记账规则 **/
	public static final String CACTNOADRULB = "CRULB";
	/** 贷方账户C **/
	public static final String CACTNOC = "CACTNOC";
	/** 贷方账户C记账规则 **/
	public static final String CACTNOADRULC = "CRULC";
	/** 贷方账户D **/
	public static final String CACTNOD = "CACTNOD";
	/** 贷方账户D记账规则 **/
	public static final String CACTNOADRULD = "CRULD";
	/** 贷方账户E **/
	public static final String CACTNOE = "CACTNOE";
	/** 贷方账户E记账规则 **/
	public static final String CACTNOADRULE = "CRULE";

	/** 借方账户A 借贷方向 **/
	public static final String DACDFLGA = "DACDFLGA";
	/** 借方账户B 借贷方向 **/
	public static final String DACDFLGB = "DACDFLGB";
	/** 借方账户C 借贷方向 **/
	public static final String DACDFLGC = "DACDFLGC";
	/** 借方账户D 借贷方向 **/
	public static final String DACDFLGD = "DACDFLGD";
	/** 借方账户E 借贷方向 **/
	public static final String DACDFLGE = "DACDFLGE";

	/** 贷方账户A 借贷方向 **/
	public static final String CACDFLGA = "CACDFLGA";
	/** 贷方账户B 借贷方向 **/
	public static final String CACDFLGB = "CACDFLGB";
	/** 贷方账户C 借贷方向 **/
	public static final String CACDFLGC = "CACDFLGC";
	/** 贷方账户D 借贷方向 **/
	public static final String CACDFLGD = "CACDFLGD";
	/** 贷方账户E 借贷方向 **/
	public static final String CACDFLGE = "CACDFLGE";

	/** 账务调整方向-加 **/
	public static final String IMPROVEFLGADD = "ADD";
	/** 账务调整方向-减 **/
	public static final String IMPROVEFLGSUB = "SUB";

	/** 平台账户账户固定值 **/
	public static final String PLATFIXEDVALUE16 = "95999999999999999999";
	/** 收入账户账户固定值 **/
	public static final String PYEFIXEDVALUE20 = "96999999999999999999";
	/** 支付通道账户固定值 **/
	public static final String CHNFIXEDVALUE30 = "97999999999999999999";
	/** 支付通道账户固定值 **/
	public static final String CHNFIXEDVALUE40 = "98999999999999999999";

	/** 科目类别--301平台活期 **/
	public static final String SUBJECTTYP301 = "301";
	/** 科目类别--201手续费收入 **/
	public static final String SUBJECTTYP201 = "201";
	/** 科目类别--401手续费支出 **/
	public static final String SUBJECTTYP401 = "401";
	/** 科目类别--161担保过渡 **/
	public static final String SUBJECTTYP161 = "161";
	/** 科目类别--162提现过渡款项 **/
	public static final String SUBJECTTYP162 = "162";
	/** 科目类别--163转账银行卡过渡款项 **/
	public static final String SUBJECTTYP163 = "163";
	/** 科目类别--164结算过渡款项 **/
	public static final String SUBJECTTYP164 = "164";
	/** 科目类别--165平台T0预存款 **/
	public static final String SUBJECTTYP165 = "165";
	/** 科目类别--166平台T0待还款 **/
	public static final String SUBJECTTYP166 = "166";
	/** 科目类别--167营销推广 **/
	public static final String SUBJECTTYP167 = "167";
	/** 科目类别--168平台垫款户 **/
	public static final String SUBJECTTYP168 = "168";
	/** 科目类别--169平台营销推广 **/
	public static final String SUBJECTTYP169 = "169";
	/** 科目类别--101会员普通存款 **/
	public static final String SUBJECTTYP101 = "101";
	/** 科目类别--102会员待结算款 **/
	public static final String SUBJECTTYP102 = "102";
	/** 科目类别--103会员应结算款 **/
	public static final String SUBJECTTYP103 = "103";
	/** 科目类别--104会员理财款项 **/
	public static final String SUBJECTTYP104 = "104";
	/** 科目类别--105会员保证金 **/
	public static final String SUBJECTTYP105 = "105";
	/** 科目类别--106代理商T0预存款 **/
	public static final String SUBJECTTYP106 = "106";
	/** 科目类别--107代理商T0待还款 **/
	public static final String SUBJECTTYP107 = "107";
	/** 科目类别--108预付卡 **/
	public static final String SUBJECTTYP108 = "108";
	/** 科目类别--109体验金 **/
	public static final String SUBJECTTYP109 = "109";

	/** 会计类别--1-负债类账户 2-收入类账户 3-资产类账户 4-费用成本类账户 **/
	public static final String ACCTYPFLGFZ = "1";
	/** 会计类别--1-负债类账户 2-收入类账户 3-资产类账户 4-费用成本类账户 **/
	public static final String ACCTYPFLGSR = "2";
	/** 会计类别--1-负债类账户 2-收入类账户 3-资产类账户 4-费用成本类账户 **/
	public static final String ACCTYPFLGZC = "3";
	/** 会计类别--1-负债类账户 2-收入类账户 3-资产类账户 4-费成本类账户 **/
	public static final String ACCTYPFLGCB = "4";

	/** 交易类型--正常 **/
	public static final String TXNFLGNORMAL = "N";
	/** 交易类型--调账 **/
	public static final String TXNFLGIMPROVE = "T";
	/** 交易类型--抹帐 **/
	public static final String TXNFLGCANLE = "C";

	/** 交易种类--充值 **/
	public static final String TXNTYPUSERDEPOSIT = "USERDEPOSIT";
	/** 交易种类--订单支付 **/
	public static final String TXNTYPORDERPAYMENT = "ORDERPAYMENT";
	/** 交易种类--担保支付 **/
	public static final String TXNTYPGUARANTEEPAYMENT = "GUARANTEEPAYMENT";
	/** 交易种类--担保支付确认 **/
	public static final String GUARANTEEPAYMENTCONFIRM = "GUARANTEEPAYMENTCONFIRM";
	/** 交易种类--提现 **/
	public static final String TXNTYPUSERWITHDRAW = "USERWITHDRAW";
	/** 交易种类--提现确认 **/
	public static final String USERWITHDRAWCONFIRM = "USERWITHDRAWCONFIRM";
	/** 交易种类--转账 **/
	public static final String TXNTYPTRANSFERACCOUNTS = "TRANSFERACCOUNTS";
	/** 交易种类--退款 **/
	public static final String TXNTYPUSERREFUND = "USERREFUND";
	/** 交易种类--手续费支出 **/
	public static final String RECONCILIATIONS = "RECONCILIATIONS";
	/** 交易种类--清算 **/
	public static final String CLEARACCOUNTS = "CLEARACCOUNTS";
	/** 交易种类--结算 **/
	public static final String SETTLEACCOUNTS = "SETTLEACCOUNTS";
	/** 交易种类--分润 **/
	public static final String SHAREPAYMENT = "SHAREPAYMENT";
	/** 交易种类--T0垫款 (T0预存款项充值) **/
	public static final String TXNTYPT0ADVANCES = "T0ADVANCES";
	/** 交易种类--T0结算 **/
	public static final String TXNTYPT0SETTLEMENT = "T0SETTLEMENT";
	/** 交易种类--T0结算确认 **/
	public static final String T0SETTLEMENTCONFIRM = "SETTLEACCOUNTCONFIRM";
	/** 交易种类--T0还款 **/
	public static final String T0REIMBURSEMENT = "T0REIMBURSEMENT";
	/** 交易种类--重单/错账处理 **/
	public static final String TODEALWITHREPEAT = "TODEALWITHREPEAT";
	/** 交易种类--补单 **/
	public static final String ADDITIONALORDER = "ADDITIONALORDER";
	/** 交易种类--抹帐 **/
	public static final String CHARGEITCORRECT = "CHARGEITCORRECT";
	/** 交易种类--综合记账 **/
	public static final String COMPREHENSIVEACCOUNTING = "COMPREHENSIVEACCOUNTING";
	/** 交易种类--错账垫资 **/
	public static final String ERRORACCOUNTADVANCES = "ERRORACCOUNTADVANCES";
	/** 交易种类--保证金 **/
	public static final String MARGINPAYMENT = "MARGINPAYMENT";
	/** 交易种类--营销推广 **/
	public static final String MARKETINGPROMOTION = "MARKETINGPROMOTION";
	/** 交易种类--理财购买 **/
	public static final String WEALTHMANAGEMENTPRODUCTS = "WEALTHMANAGEMENTPRODUCTS";
	/** 交易种类--理财赎回 **/
	public static final String FINANCIALBENEFITS = "FINANCIALBENEFITS";
	/** 交易种类--理财派息 **/
	public static final String FINANCIALPAYOUT = "FINANCIALPAYOUT";
	/** 交易种类--批量支付 **/
	public static final String BATCHORDERPAYMENT = "ORDERBATCHPAYMENT";
	/** 交易种类--冻结 **/
	public static final String ACCOUNTFROZEN = "ACCOUNTFROZEN";
	/** 交易种类--解冻 **/
	public static final String ACCOUNTTHAW = "ACCOUNTTHAW";
	/** 交易种类--调账 **/
	public static final String IMPROVEAMT = "IMPROVEAMT";
	/** 交易种类--批量垫款 **/
	public static final String BATCHACCOUNTADVANCES = "BATCHACCOUNTADVANCES";
	/** 交易种类--批量抹帐 **/
	public static final String BATCHCHARGEITCORRECT = "BATCHCHARGEITCORRECT";
	/** 交易种类--平台账户充值 **/
	public static final String PLATFORMDESPOSIT = "PLATFORMDESPOSIT";
	/** 交易种类--红包充值 **/
	public static final String REDPACKETSDESPOSIT = "REDPACKETSDESPOSIT";
	/** 交易种类--积分充值 **/
	public static final String POINTSDESPOSIT = "POINTSDESPOSIT";
	/** 交易种类--积分兑换**/
	public static final String POINTSEXCHANGE = "POINTSEXCHANGE";
	/** 交易种类--积分兑换**/
	public static final String EXPERIENCEGRANT = "EXPERIENCEGRANT";

	/** 科目级别--一级科目 **/
	public static final String SUBJECTLEV1 = "1";
	/** 科目级别--二级科目 **/
	public static final String SUBJECTLEV2 = "2";
	/** 科目级别--三级科目 **/
	public static final String SUBJECTLEV3 = "3";
	/** 科目级别--存在上一级 **/
	public static final String IS_LAST_LEV = "1";
	/** 科目级别--不存在上一级 **/
	public static final String NOT_LAST_LEV = "0";
	/** 审核状态--待审核 **/
	public static final String AUT_STS0 = "0";
	/** 审核状态--审核不通过 **/
	public static final String AUT_STS1 = "1";
	/** 审核状态--审核通过 **/
	public static final String AUT_STS2 = "2";
	/** 审核状态--审核中 **/
	public static final String AUT_STS3 = "3";

	/** 支持标志--是 **/
	public static final String CAS_DEF_FLG_Y = "Y";
	/** 支持标志--否 **/
	public static final String CAS_DEF_FLG_N = "N";

	/** 手续费类型--1 实时扣手续费 **/
	public static final String CAS_FEE_TYP = "1";

	/** 会计分录 交易子码--001 商户申请T0结算 **/
	public static final String ACC_ENTRY_SUB_COD_001 = "001";
	/** 会计分录 交易子码--002 代理商下属商户申请T0结算 **/
	public static final String ACC_ENTRY_SUB_COD_002 = "002";

	/** 导出 导出类别码--24 账户平衡查询导出 **/
	public static final String PUB_EXPORT_TYPE_CAS_ACCBALANCEINFO = "24";
	/** 导出 导出类别码--25 科目平衡查询导出 **/
	public static final String PUB_EXPORT_TYPE_CAS_SUBBALANCEINFO = "25";
	/** 导出 导出类别码--31 账户流水导出 **/
	public static final String PUB_EXPORT_TYPE_CAS_ACCTXNINFO = "31";
	/** 导出 导出类别码--32账户维护导出 **/
	public static final String PUB_EXPORT_TYPE_CAS_ACCMANAGEINFO = "32";
	/** 导出 导出类别码--33 账户冻结导出 **/
	public static final String PUB_EXPORT_TYPE_CAS_ACCFROZINFO = "33";

	/** 导出 导出类别码--34 账务流水查询信息导出 **/
	public static final String PUB_EXPORT_TYPE_CAS_TXNJNLINFO = "35";

	/** 试算平衡 查询模式码--01 实时查询 **/
	public static final String QRY_MODE_TIM = "01";
	/** 试算平衡 查询模式码--02 历史查询 **/
	public static final String QRY_MODE_HIS = "02";

	/** 渠道维护,支付通道代码表- 系统自有标志--0 系统自有 **/
	public static final String CAS_CHN_IS_SYSTEM = "0";
	/** 渠道维护,支付通道代码表- 系统自有标志--1 运营维护 **/
	public static final String CAS_CHN_NOT_SYSTEM = "1";

	/** 批量支付-mongo表名 **/
	public static final String BATCHMONGOTABLE = "cas_kkbatchPay_info";

	// ****************账户账务*****************

	// ********************************对账模块
	// start********************************//

	/** 对账模块-系统码 */
	public static final String SYS_IDS = "010|011|012|013";

	/** 核心对账状态-未对账 */
	public static final String CHK_CORE_STS_INIT = "00";
	/** 核心对账状态-已对平 */
	public static final String CHK_CORE_STS_SUCC = "01";
	/** 核心对账状态-业务多账 */
	public static final String CHK_CORE_STS_BM = "02";
	/** 核心对账状态-核心多账 */
	public static final String CHK_CORE_STS_CM = "03";
	/** 通道对账状态-未对账 */
	public static final String CHK_CHN_STS_INIT = "00";
	/** 通道对账状态-已对平 */
	public static final String CHK_CHN_STS_SUCC = "01";
	/** 通道对账状态-业务多账 */
	public static final String CHK_CHN_STS_BM = "02";
	/** 通道对账状态-通道多账 */
	public static final String CHK_CHN_STS_CHM = "03";
	/** 通道对账状态-金额不符 */
	public static final String CHK_CHN_STS_AMTERR = "04";
	/** 未对账 */
	public static final String CHK_CHN_CHK_STS_0 = "0";
	/** 已对账 */
	public static final String CHK_CHN_CHK_STS_1 = "1";
	/** 错账处理状态-未处理 */
	public static final String CHK_ERR_DEAL_STS_INIT = "00";
	/** 错账处理状态-已处理 */
	public static final String CHK_ERR_DEAL_STS_DEAL = "01";
	/** 业务多账处理方式-抹账 */
	public static final String CHK_ERR_BW_DEAL_TYP_MZ = "01";
	/** 业务多账处理方式-错账垫款 */
	public static final String CHK_ERR_BW_DEAL_TYP_DK = "02";
	/** 渠道多账处理方式-补单 */
	public static final String CHK_ERR_CW_DEAL_TYP_BD = "01";
	/** 渠道多账处理方式-退款 */
	public static final String CHK_ERR_CW_DEAL_TYP_TK = "02";
	/** 金额不符处理方式-退款 */
	public static final String CHK_ERR_AE_DEAL_TYP_TK = "01";
	/** 金额不符处理方式-垫资 */
	public static final String CHK_ERR_AE_DEAL_TYP_DK = "02";
	/** 订单类型-担保支付 */
	public static final String ORD_TYPE_IS_GUARANT = "1";
	/** 订单类型-非担保支付 */
	public static final String ORD_TYPE_IS_NOT_GUARANT = "0";
	/** 订单-分页查询总条数标志 **/
	public static final String CHK_SIGN_OF_COUNT_LIST = "countList";

	// ********************************对账模块
	// end********************************//

	// ****************用户模块 START*****************
	/** 认证方式-面对面认证 **/
	public static final String IDE_TYP_FACE = "00";
	/** 认证方式-手机认证 **/
	public static final String IDE_TYP_PHONE = "01";
	/** 认证方式-邮箱认证 **/
	public static final String IDE_TYP_EMAIL = "02";
	/** 认证方式-实名认证 **/
	public static final String IDE_TYP_REAL_NAM = "03";
	/** 认证方式-证件认证 **/
	public static final String IDE_TYP_CERT = "04";
	/** 认证方式-手持证件认证 **/
	public static final String IDE_HOLD_CERT = "05";
	/** 认证状态-未认证 **/
	public static final String IDE_NO = "0";
	/** 认证状态-已认证 **/
	public static final String IDE_SUCESS = "1";
	/** 认证状态-认证不通过 **/
	public static final String IDE_FAIL = "2";
	/** 银行卡绑定操作 **/
	public static final String REL_OPR = "0";
	/** 银行卡解绑操作 **/
	public static final String UNREL_OPR = "1";
	/** 证件审核状态-待审核 **/
	public static final String AUT_NO = "0";
	/** 证件审核状态-审核通过 **/
	public static final String AUT_SUCCESS = "1";
	/** 证件审核状态-审核不通过 **/
	public static final String AUT_FAIL = "2";
	/** 证件审核状态-审核中 **/
	public static final String AUT_ING = "3";
	/** 会计期间日表核对状态--平衡 **/
	public static final String IS_BAL = "0";
	/** 会计期间日表核对状态--不平 **/
	public static final String NOT_BAL = "1";
	/** 起始日期 **/
	public static final String START_DTE = "19700101";
	/** 截止日期 **/
	public static final String END_DTE = "20991231";
	/** 系统码-互联网支付 **/
	public static final String INT_PAY = "010";
	/** 系统码-收单 **/
	public static final String POS = "011";
	/** 系统码-预付卡 **/
	public static final String PRE_CARD = "012";
	/** 系统码-手刷 **/
	public static final String HAND_BRUSH = "013";
	/** 账户种类-用户 **/
	public static final String ACC_USR = "001";
	/** 账户种类-商户 **/
	public static final String ACC_MER = "002";
	/** 账户种类-代理商 **/
	public static final String ACC_AGT = "003";
	/** 账户种类-预付卡账户 **/
	public static final String ACC_PRE_CARD = "004";
	/** 发起方类型-用户 **/
	public static final String SOURCE_TYPE_USR = "001";
	/** 发起方类型-商户 **/
	public static final String SOURCE_TYPE_MER = "002";
	/** 校验码验证类型-邮箱 **/
	public static final String RAND_EMAIL = "0";
	/** 校验码验证类型-短信 **/
	public static final String RAND_PHONE = "1";
	/** 用户认证方式总数 **/
	public static final String USR_CERT_TOTAL = "6";
	/** 用户卖家标识-非卖家 **/
	public static final String USR_NO_SEL = "0";
	/** 用户卖家标识-卖家 **/
	public static final String USR_SEL = "1";
	/** 用户系统-用户状态，禁用 */
	public static final String USR_STATUS_DISABLE = "0";
	/** 用户系统-用户状态，启用 */
	public static final String USR_STATUS_ENABLE = "1";
	/** 用户系统-快捷支付签约标志 ，未签约 */
	public static final String QUICK_PAY_FLAG_CLOSE = "0";
	/** 用户系统-快捷支付签约标志 ，已签约 */
	public static final String QUICK_PAY_FLAG_OPEN = "1";
	/** 用户系统-协议代扣签约标志 ，未签约 */
	public static final String WITHHOLDING_FLAG_CLOSE = "0";
	/** 用户系统-协议代扣签约标志 ，已签约 */
	public static final String WITHHOLDING_FLAG_OPEN = "1";
	/** 用户系统-邮箱类型 ，找回登录密码 */
	public static final String EMAIL_TYPE_LOGIN_PWD = "01";
	/** 用户系统-邮箱类型 ，找回支付密码 */
	public static final String EMAIL_TYPE_PAY_PWD = "02";
	/** 用户系统-邮箱类型 ，修改密保问题 */
	public static final String EMAIL_TYPE_PRO = "03";
	/** 用户系统-应用伙伴状态-禁用 */
	public static final String APP_PAR_STATUS_DIS = "0";
	/** 用户系统-应用伙伴状态 -正常 */
	public static final String APP_PAR_STATUS_NOR = "1";
	// ****************用户模块 END***************

	// ****************会员模块 START*****************
	/** 会员类别-用户 **/
	public static final String MEM_TYP_USER = "001";
	/** 会员类别-商户 **/
	public static final String MEM_TYP_MER = "002";
	/** 会员类别-代理商 **/
	public static final String MEM_TYP_AGT = "003";
	/** 会员是否设置安保问题-设置 **/
	public static final String SET_SECURITY = "0";
	/** 会员是否设置安保问题-无设置 **/
	public static final String NO_SET_SECURITY = "1";
	/** 注册方式-手机 **/
	public static final String REG_WAY_PHONE = "0";
	/** 注册方式-邮箱 **/
	public static final String REG_WAY_EMAIL = "1";
	// ****************会员模块 END***************
	/** Mail setting info start **/
	/** 邮件服务器信息 **/
	public static final String TD_COMM_MAIL_SMTPSERVER = "TD_COMM_MAIL_SMTPSERVER";
	/** 邮件显示名称 **/
	public static final String TD_COMM_MAIL_SHOWNAME = "TD_COMM_MAIL_SHOWNAME";
	/** 邮件发送者地址 **/
	public static final String TD_COMM_MAIL_FROMADDR = "TD_COMM_MAIL_FROMADDR";
	/** 用户密码 **/
	public static final String TD_COMM_MAIL_PASSWORD = "TD_COMM_MAIL_PASSWORD";

	/** 短信发送号 **/
	public static final String TD_COMM_PHONE_FROMADDR = "TD_COMM_PHONE_FROMADDR";

	/** 银行卡类别--借记卡 **/
	public static final String CARD_TYPE_DEBIT = "01";
	/** Mail setting info end **/

	/** Sms setting info start **/
	/** 商户号 **/
	public static final String TD_COMM_SMS_MERNO = "TD_COMM_SMS_MERNO";
	/** 商户密钥 **/
	public static final String TD_COMM_SMS_MERKEY = "TD_COMM_SMS_MERKEY";
	/** Sms setting info end **/

	// ****************代理商模块 START*****************//
	/** 代理商ID前缀 **/
	public static final String AGT_ID = "03";
	/** 代理商类型-个人 **/
	public static final String AGT_TYP_PER = "0";
	/** 代理商类型-企业 **/
	public static final String AGT_TYP_ENT = "1";
	/** 代理商状态-不可用 **/
	public static final String AGT_STA_DIA = "0";
	/** 代理商状态-可用，影响登录代理商门户 **/
	public static final String AGT_STA_ABL = "1";
	/** 代理商等级，5最高 **/
	public static final String AGT_LV_1 = "1";
	/** 代理商等级，5最高 **/
	public static final String AGT_LV_2 = "2";
	/** 代理商等级，5最高 **/
	public static final String AGT_LV_3 = "3";
	/** 代理商等级，5最高 **/
	public static final String AGT_LV_4 = "4";
	/** 代理商等级，5最高 **/
	public static final String AGT_LV_5 = "5";
	/** 代理商业务状态(0:关闭 1:开通 影响业务拓展,不影响商户交易) **/
	public static final String AGT_BIZ_STA_CLO = "0";
	/** 代理商业务状态(0:关闭 1:开通 影响业务拓展,不影响商户交易) **/
	public static final String AGT_BIZ_STA_OPN = "1";
	/** 代理商分润信息，区间单位(1:万元) **/
	public static final String AGT_SHA_AREA_UNIT = "1";
	/** 代理商分润信息，第一区间，默认起始交易量 **/
	public static final String AGT_SHA_AREA_FIRST_STR = "0";
	/** 代理商分润信息，第五区间，默认截止交易量 **/
	public static final String AGT_SHA_AREA_FIVE_END = "999999999";

	/** 代理商分润类型 0-按交易手续费 1-按交易金额 **/
	public static final String AGT_SHA_TYP_BY_FEE = "0";
	/** 代理商分润类型 0-按交易手续费 1-按交易金额 **/
	public static final String AGT_SHA_TYP_BY_TXN = "1";
	/** 代理范围，0国代 1省代 2市代 3区代 **/
	public static final String AGT_SCOPE_COUNTRY = "0";
	/** 代理范围，0国代 1省代 2市代 3区代 **/
	public static final String AGT_SCOPE_PROV = "1";
	/** 代理范围，0国代 1省代 2市代 3区代 **/
	public static final String AGT_SCOPE_CITY = "2";
	/** 代理范围，0国代 1省代 2市代 3区代 **/
	public static final String AGT_SCOPE_AREA = "3";
	// ****************用户模块 END***************//

	// ****************公共模块*****************//
	/** 通告发送状态-发送 **/
	public static final String PUB_ISSUE_TRUE = "00";
	/** 通告发送状态-未发送 **/
	public static final String PUB_ISSUE_FALSE = "01";

	public static final String DEFAULT_ENCODING = "UTF-8";

	/** 信息发送状态-发送 **/
	public static final String PUB_MESSAGE_TRUE = "00";
	/** 信息发送状态-未发送 **/
	public static final String PUB_MESSAGE_FALSE = "01";

	/** 发送渠道-邮件 **/
	public static final String PUB_SEND_CHANNEL_MAIL = "00";
	/** 发送渠道-短信 **/
	public static final String PUB_SEND_CHANNEL_PHONE = "01";

	/** 消息是否已读-未读 **/
	public static final String PUB_READ_TRUE = "00";
	/** 消息是否已读-已读 **/
	public static final String PUB_READ_FALSE = "01";

	/** 通告类型01、系统消息；02、单个会员消息； **/
	public static final String PUB_ANNTYPE_SYS = "01";
	/** 通告类型01、系统消息；02、单个会员消息； **/
	public static final String PUB_ANNTYPE_PER = "02";

	/** 通告系统 000001:用户系统;000002:商户系统;000003:代理商系统;000004:后台系统; **/
	public static final String PUB_ANNSYSCODE_USER = "000001";
	/** 通告系统 000001:用户系统;000002:商户系统;000003:代理商系统;000004:后台系统; **/
	public static final String PUB_ANNSYSCODE_MER = "000002";
	/** 通告系统 000001:用户系统;000002:商户系统;000003:代理商系统;000004:后台系统; **/
	public static final String PUB_ANNSYSCODE_AGENT = "000003";
	/** 通告系统 000001:用户系统;000002:商户系统;000003:代理商系统;000004:后台系统; **/
	public static final String PUB_ANNSYSCODE_BACK = "000004";

	/** 卡bin在redis中存储的key **/
	public static final String PUB_UNIONFIT_REDIS_KEY = "UNIONFIT";

	/** 日切间隔时差 秒 **/
	public static final int PUB_CHANGE_TIME = 3600 * 2;

	/** 节假日标志 **/
	public static final String PUB_HOLIDAY_TRUE = "0";
	/** 非节假日标志 **/
	public static final String PUB_HOLIDAY_FALSE = "1";

	/** 验证码类型，0邮箱；1短信；2其他； **/
	public static final String PUB_RAND_TYPE_EMAIL = "0";

	/** 验证码类型，0邮箱；1短信；2其他； **/
	public static final String PUB_RAND_TYPE_MESSAGE = "1";

	/** 验证码业务类型，0注册； **/
	public static final String PUB_RAND_BUS_TYPE_REGISTER = "0";

	/** 验证码业务类型，1密码找回； **/
	public static final String PUB_RAND_BUS_TYPE_PWD_BACK = "1";

	/** 验证码业务类型，2-手机号更换 **/
	public static final String PUB_RAND_BUS_TYPE_PHONE_CHANG = "2";

	/** 验证码业务类型，3-邮箱添加 **/
	public static final String PUB_RAND_BUS_TYPE_MAIL_ADD = "3";

	/** 分页查询 默认每页条数:15 **/
	public static int DEFAULT_PAGESIZE = 15;
	/** 分页查询 默认当前页数:1 **/
	public static int DEFAULT_PAGENUM = 1;
	/** start **/
	public static String PAGESELECT_START = "start";
	/** limit **/
	public static String PAGESELECT_LIMIT = "limit";
	/** 分页查询 返回列表的key值 **/
	public static String PAGESELECT_RETLIST_KEY = "list";
	/** 分页查询 返回列表总数的key值 **/
	public static String PAGESELECT_RETTOTAL_KEY = "total";

	/** 导出类型-转账列表 **/
	public static String PUB_DOWNLOAD_TRANS = "01";
	/** 导出类型-用户基本信息 **/
	public static String PUB_DOWNLOAD_USR_BASIC = "02";
	/** 导出类型-用户账户信息 **/
	public static String PUB_DOWNLOAD_USR_ACCOUNT = "03";
	/** 导出类型-用户银行卡绑定信息 **/
	public static String PUB_DOWNLOAD_USR_CARD = "04";
	/** 导出类型-用户授权信息 **/
	public static String PUB_DOWNLOAD_USR_AUTH = "36";
	/** 导出类型-用户等级信息 **/
	public static String PUB_EXPORT_TYPE_RCS_USERLEVEL = "11";

	/** 导出类型-商户管理基本信息 **/
	public static String PUB_EXPORT_TYPE_MER_BASIC = "22";
	/** 导出类型-商户管理账户类型 **/
	public static String PUB_EXPORT_TYPE_MERACC = "23";

	/** 可查看对象-个人 **/
	public static String PUB_AUTH_OBJECT_PERSON = "00";
	/** 可查看对象-商户 **/
	public static String PUB_AUTH_OBJECT_MER = "01";
	/** 可查看对象-角色 **/
	public static String PUB_AUTH_OBJECT_ROLE = "02";

	/** 文件是否可删除-否 **/
	public static String PUB_DELETE_FILE_CAN_NOT = "00";
	/** 文件是否可删除-是 **/
	public static String PUB_DELETE_FILE_CAN = "01";

	/** 文件是可存储（天） **/
	public static String PUB_FILE_SAVE_DAYS = "7";

	/** 文件状态-正在生成 **/
	public static String PUB_FILE_STATUS_CREATING = "00";
	/** 文件状态-生成失败 **/
	public static String PUB_FILE_STATUS_FAIL = "02";
	/** 文件状态-生成完毕 **/
	public static String PUB_FILE_STATUS_CREATED = "01";

	/** 下载参数分割符 **/
	public static String PUB_FILE_PARAMS_SEPARATE = "####";

	/** 公共-分页查询总条数标志 **/
	public static final String PUB_SIGN_OF_COUNT_LIST = "countList";

	// ****************公共模块 END***************//

	// ****************订单 start*****************//
	/** 订单-转账类型:户转户 **/
	public static final String ORD_ACCOUNT_TRANS_ACCOUNT = "01";
	/** 订单-转账类型:户转卡 **/
	public static final String ORD_ACCOUNT_TRANS_CARD = "02";

	/** 订单类型-01-消费 **/
	public static final String ORD_TYPE_PRD = "01";
	/** 订单类型-02-充值 **/
	public static final String ORD_TYPE_CHARGE = "02";
	/** 订单类型-03-提现 **/
	public static final String ORD_TYPE_WITHDRAW = "03";
	/** 订单类型-04-转账 **/
	public static final String ORD_TYPE_TRANS = "04";
	/** 订单类型-05-退款 **/
	public static final String ORD_TYPE_REF = "05";
	/** 订单类型-06-调账 **/
	public static final String ORD_TYPE_REF_ADJ = "06";
	/** 订单类型-07-重单退款 **/
	public static final String ORD_TYPE_REF_RPT = "07";
	/** 订单类型-08-资金冻结失败退款 **/
	public static final String ORD_TYPE_REF_FAILFRZ = "08";
	/** 订单类型-09-收款 **/
	public static final String ORD_TYPE_RECE = "09";
	/** 订单类型-10-提现确认 **/
	public static final String ORD_TYPE_TXQR = "10";
	/** 业务类型-11-手续费支出 **/
	public static final String ACC_BUS_TYP_FEEOUT = "11";
	/** 业务类型-12-清算 **/
	public static final String ACC_BUS_TYP_CLEARACC = "12";
	/** 业务类型-13-结算 **/
	public static final String ACC_BUS_TYP_STLACC = "13";
	/** 业务类型-14-分润 **/
	public static final String ACC_BUS_TYP_SHAREPAY = "14";
	/** 业务类型-15-T0预存充值 **/
	public static final String ACC_BUS_TYP_T0ADVANCES = "15";
	/** 业务类型-16-T0结算 **/
	public static final String ACC_BUS_TYP_T0STL = "16";
	/** 业务类型-17-T0还款 **/
	public static final String ACC_BUS_TYP_REPAY = "17";
	/** 业务类型-18-错账处理 **/
	public static final String ACC_BUS_TYP_DWREPEAT = "18";
	/** 业务类型-19-补单 **/
	public static final String ACC_BUS_TYP_ADDORD = "19";
	/** 业务类型-20-抹账 **/
	public static final String ACC_BUS_TYP_CHARGEITCOR = "20";
	/** 业务类型-21-综合记账 **/
	public static final String ACC_BUS_TYP_COMPREHENSIVEACC = "21";
	/** 业务类型-22-错账垫资 **/
	public static final String ACC_BUS_TYP_ERRORACCADV = "22";
	/** 业务类型-23-保证金缴纳 **/
	public static final String ACC_BUS_TYP_MARGINPAY = "23";
	/** 业务类型-24-营销推广 **/
	public static final String ACC_BUS_TYP_MARKETPRO = "24";
	/** 业务类型-25-理财购买 **/
	public static final String ACC_BUS_TYP_WEALTHMNGPRO = "25";
	/** 业务类型-26-理财赎回 **/
	public static final String ACC_BUS_TYP_FINANCRESPINFO = "26";
	/** 业务类型-27-T0结算确认 **/
	public static final String ACC_BUS_TYP_T0STLCON = "27";
	/** 业务类型-28-理财派息 **/
	public static final String ACC_BUS_TYP_LCPX= "28";
	/** 业务类型-29-红包充值 **/
	public static final String ACC_BUS_TYP_HBCZ = "29";
	/** 业务类型-30-积分充值 **/
	public static final String ACC_BUS_TYP_JFCZ = "30";
	/** 业务类型-31-积分兑换 **/
	public static final String ACC_BUS_TYP_JFDH = "31";
	/** 业务类型-32-体验金发放 **/
	public static final String ACC_BUS_TYP_TYJF = "32";
	/** 业务类型-33-平台账户充值 **/
	public static final String ACC_BUS_TYP_PTCZ = "33";
	/** 业务类型-34-借款发放 **/
	public static final String ACC_BUS_TYP_JKFF = "34";
	/** 业务类型-35-借款还款 **/
	public static final String ACC_BUS_TYP_JKHK = "35";

	/** 订单状态-成功 **/
	public static final String ORD_STS_SUCC = "00";
	/** 订单状态-失败 **/
	public static final String ORD_STS_FAIL = "01";
	/** 订单状态-等待处理 **/
	public static final String ORD_STS_DEAL = "06";
	/** 订单状态-初始 **/
	public static final String ORD_STS_INIT = "99";
	/** 订单状态-待复核 **/
	public static final String ORD_STS_AUTH = "08";
	/** 订单-订单状态：未付款 **/
	public static final String ORD_ORD_STS_NOT_PAY = "02";
	/** 订单-订单状态：已支付 **/
	public static final String ORD_ORD_STS_ALREADY_PAY = "03";
	/** 订单-订单状态：订单作废 **/
	public static final String ORD_ORD_STS_CANCEL = "04";

	/** 订单-核心记账状态:预计 **/
	public static final String ORD_ACC_STS_PREDICT = "U";
	/** 订单-核心记账状态:成功 **/
	public static final String ORD_ACC_STS_SUCCESS = "S";
	/** 订单-核心记账状态:失败 **/
	public static final String ORD_ACC_STS_FAIL = "F";

	/** 订单-清算状态:未清算 **/
	public static final String ORD_UN_STL = "0";
	/** 订单-清算状态:已清分 **/
	public static final String ORD_ALREADY_CLEAR = "1";
	/** 订单-清算状态:已结算 **/
	public static final String ORD_ALREADY_STL = "2";

	/** 订单-代理商分润状态:已分润 **/
	public static final String ORD_ALREADY_SHR = "1";

	/** 订单-支付状态:成功 **/
	public static final String ORD_PAY_STS_SUCCESS = "00";
	/** 订单-支付状态:失败 **/
	public static final String ORD_PAY_STS_FAIL = "01";
	/** 订单-支付状态:待付款 **/
	public static final String ORD_PAY_STS_NOT_PAY = "02";

	/** 订单-请求类型:按天汇总 **/
	public static final String ORD_QRY_TYPE_DAY = "1";
	/** 订单-请求类型:按周汇总 **/
	public static final String ORD_QRY_TYPE_WEEK = "2";
	/** 订单-请求类型:按月汇总 **/
	public static final String ORD_QRY_TYPE_MONTH = "3";
	/** 订单-分页查询总条数标志 **/
	public static final String ORD_SIGN_OF_COUNT_LIST = "countList";

	/** 订单-核心对账状态：未对账 */
	public static final String ORD_CORE_STS_INIT = "00";
	/** 订单-核心对账状态：已对平 */
	public static final String ORD_CORE_ACC_STS_OK = "01";
	/** 订单-核心对账状态：业务多账 */
	public static final String ORD_CORE_STS_BM = "02";
	/** 订单-核心对账状态：核心多账 */
	public static final String ORD_CORE_STS_CM = "03";

	/** 订单-第三方/银行对账状态：未对账 **/
	public static final String ORD_CHN_CHK_STS_INIT = "00";
	/** 订单-第三方/银行对账状态：已对平 **/
	public static final String ORD_CHN_CHK_STS_OK = "01";

	/** 订单-是否抹账：抹账 **/
	public static final String ORD_WIPE_SIGN = "00";

	/** 订单-请求来源 01：PC **/
	public static final String ORD_REQ_CHN_PC = "01";
	/** 订单-请求来源 02：手机 **/
	public static final String ORD_REQ_CHN_MOBILE = "02";

	/** 订单-会员类型：用户 */
	public static final String ORD_MEMBER_USR_TYPE = "001";
	/** 订单-会员类型：商户 */
	public static final String ORD_MEMBER_MER_TYPE = "002";
	/** 订单-会员类型：代理商 */
	public static final String ORD_MEMBER_AGE_TYPE = "003";

	/** 订单-买方 */
	public static final String ORD_BUY = "1";
	/** 订单-卖方 */
	public static final String ORD_SELLER = "2";

	/** 订单-查询最大总条数 */
	public static final String ORD_QRY_IN_MAX_SIZE = "100";
	/** 订单-提现类型-正常提现 */
	public static final String ORD_WITHDRAW_TYPE_NOR = "01";

	/** 订单-支付方式:支付账户 */
	public static final String ORD_PAY_TYPE_ACCOUNT = "01";
	/** 订单-支付方式:网上银行 */
	public static final String ORD_PAY_TYPE_E_BANK = "02";
	/** 订单-支付方式:快捷 */
	public static final String ORD_PAY_TYPE_QUICK_PAY = "03";
	/** 订单-支付方式:积分 */
	public static final String ORD_PAY_TYPE_POINT = "04";

	/** 订单-是否担保支付:是 */
	public static final String ORD_IS_GUARANT = "1";
	/** 订单-是否担保支付:不是 */
	public static final String ORD_IS_NOT_GUARANT = "0";

	/** 订单-冻结标志:冻结 */
	public static final String ORD_FROZ_SIGN_ALREADY_FROZ = "0";
	/** 订单-冻结标志:解冻 */
	public static final String ORD_FROZ_SIGN_UN_FROZ = "1";

	/** 订单-是否当天到账:是 */
	public static final String ORD_IS_DAYTOACCOUNT = "1";
	/** 订单-是否当天到账:否 */
	public static final String ORD_IS_NOT_DAYTOACCOUNT = "0";
	/** 订单-手续费类型：实时 */
	public static final String ORD_FEE_TYP_REAL_TIME = "1";

	/** 订单-支付渠道记账流水号：存在 */
	public static final String ORD_PAY_CHN_LOG_EXIST = "1";

	/** 订单-请求类型：后台 */
	public static final String ORD_REQ_TYPE_BACK = "1";
	/** 订单-请求类型：门户 */
	public static final String ORD_REQ_TYPE_PORTALS = "2";

	/** 订单-是否需要平台清算：是 */
	public static final String ORD_IS_CLEAR_IS = "1";
	/** 订单-是否需要平台清算：否 */
	public static final String ORD_IS_CLEAR_NO = "0";

	// *****************订单 end*****************//

	// ****************清结算 start*****************//
	/** 结算类型-T1结算 正常清结算 **/
	public static final String STL_TYP_T1 = "0";
	/** 结算类型-T0结算 **/
	public static final String STL_TYP_T0 = "1";

	public static final String STL_TYP_D0 = "2";
	/** 结算类型－代理商分润 */
	public static final String STL_TYP_SR = "2";

	/** 结算状态- 未结算 **/
	public static final String STL_STS_NO = "0";
	/** 结算状态- 已结算 **/
	public static final String STL_STS_SUCESS = "1";

	/** 结算审核状态-0-结算待审 **/
	public static final String STL_STS_AUDIT_0 = "0";
	/** 结算审核状态 1-结算确认 **/
	public static final String STL_STS_AUDIT_1 = "1";
	/** 结算审核状态-2-审核不通过 **/
	public static final String STL_STS_AUDIT_2 = "2";

	public static final String STL_STS_AUDIT_3 = "3";

	/** 分润状态 － 已分润 **/
	public static final String SHR_STS_SUCESS = "1";

	/** 清分类型-0会员 **/
	public static final String CLR_TYP_MEM = "0";
	/** 清分类型-1网点 */
	public static final String CLR_TYP_NET = "1";
	/** 清分类型-2终端 **/
	public static final String CLR_TYP_TER = "2";

	/** 一级分润类型 0-按交易手续费 */
	public static final String FST_SHR_TYP_FEE = "0";
	/** 一级分润类型 1-按交易金额 ' */
	public static final String FST_SHR_TYP_AMT = "1";
	/** 分润区间单位(1:万元) */
	public static final String AREA_UNIT_W = "1";
	/** 分润区间单位 分 1万=1000000 */
	public static final String AREA_UNIT_C = "1000000";

	/** 分润结算方式 到支付账户 */
	public static final String STL_WAY_TO_ACCOUNT = "1";
	/** 分润结算方式 到银行账户 */
	public static final String STL_WAY_TO_BANK = "0";
	// 清分类型

	// 结算周期1:日结 2:周结 3:月结,默认1
	/** 结算周期-1日结 **/
	public static final String STL_HZ_DAY = "1";
	/** 结算周期-2周结 **/
	public static final String STL_HZ_WEEK = "2";
	/** 结算周期-3月结 **/
	public static final String STL_HZ_MONTH = "3";

	// 结算目标(0:网点结算 1:商户结算)
	/** 结算周期-0网点结算 **/
	public static final String STL_OBJ_NOD = "0";
	/** 结算周期-1商户结算 **/
	public static final String STL_OBJ_MER = "1";

	// 结算方式(0:自然日结算D 1:工作日结算T)
	/** 结算方式-0自然日结算D **/
	public static final String STL_TYPE_D = "0";
	/** 结算方式-1工作日结算T **/
	public static final String STL_TYPE_T = "1";

	// 是否支持T0结算(0:否 1:是)
	/** 是否支持T0结算-0否 **/
	public static final String STL_T0_OFF = "0";
	/** 是否支持T0结算-1是 **/
	public static final String STL_T0_ON = "1";

	// 是否支持D0结算(0:否 1:是)
	/** 是否支持D0结算-0否 **/
	public static final String STL_D0_OFF = "0";
	/** 是否支持D0结算-1是 **/
	public static final String STL_D0_ON = "1";

	/** 清结算-核心对账状态：成功 **/
	public static final String STL_CORE_ACC_STS_OK = "01";
	// *****************清结算 end*****************//

	// ****************对账业务 start*****************//
	/** 核心对账状态 00-未对账 **/
	public static final String CORE_CHK_STS_INIT = "00";
	/** 核心对账状态 01-已对平 **/
	public static final String CORE_CHK_STS_OK = "01";

	/** 核心对账状态 02-业务多账 **/
	public static final String CORE_CHK_STS_B_ERROR = "02";
	/** 核心对账状态 03-核心多账 **/
	public static final String CORE_CHK_STS_C_ERROR = "03";

	/** 通道对账状态 00-未对账 **/
	public static final String CHN_CHK_STS_INIT = "00";
	/** 通道对账状态 01-已对平 **/
	public static final String CHN_CHK_STS_OK = "01";

	/** 通道记账状态 01-成功 **/
	public static final String PAY_CHN_STS_NG = "01";
	/** 通道记账状态 02-失败 **/
	public static final String PAY_CHN_STS_OK = "02";

	// *****************对账业务 end*****************//

	/**************** 任务管理 start *****************/
	/** 工作流业务类型 01-商户开户申请 **/
	public static final String WF_TASK_BUS_TYPE_MER_APPLY = "01";
	/** 工作流业务类型 02-商户变更申请 **/
	public static final String WF_TASK_BUS_TYPE_MER_UPD = "02";
	/** 工作流业务类型 03-用户认证 **/
	public static final String WF_TASK_BUS_TYPE_USR_AUDIT = "03";
	/** 工作流业务类型 04-代理商申请 **/
	public static final String WF_TASK_BUS_TYPE_AGT_APPLY = "04";
	/** 工作流业务类型 05-账务调账 **/
	public static final String WF_TASK_BUS_TYPE_CAS_IMP = "05";
	/** 工作流业务类型 21-商户实名认证申请 **/
	public static final String WF_TASK_BUS_TYPE_MER_ONLINE_UPD = "21";

	/** 工作流审核结果99-流程完毕 **/
	public static final String WF_TASK_STATUS_FINISH = "99";
	/**************** 任务管理 end *****************/

	// ****************商户模块 START*****************
	/**
	 * 商户状态 0 不可用
	 */
	public static final String MER_STATUS_DISABLE = "0";
	/**
	 * 商户状态 1:可用 影响登录商户门户
	 */
	public static final String MER_STATUS_AVAILABLE = "1";
	/**
	 * 商户认证状态 0：未认证
	 */
	public static final String MER_AUTH_STATUS_NOTAUTHEN = "0";
	/**
	 * 商户认证状态 1：已认证
	 */
	public static final String MER_AUTH_STATUS_AUTHEN = "1";
	/**
	 * 商户注册验证码
	 */
	public static final String TD_MER_REGISTER = "TD_MER_REGISTER_";
	/**
	 * 商户注册来源 0：线上(前端注册)
	 */
	public static final String MER_REGISTER_SOURCE_ONLINE = "0";
	/**
	 * 商户注册来源 1：线下(站方后台添加)
	 */
	public static final String MER_REGISTER_SOURCE_OFFLINE = "1";

	/** 商户T0结算0 - 商户支持T0结算 - 1是 */
	public static final String MER_WEB_SUPPORT_T0 = "1";
	/** 商户T0结算1 - 商户不支持T0结算 - 0是 */
	public static final String MER_WEB_NONSUPPORT_T0 = "0";
	/** 商户D0结算0 - 商户支持D0结算 - 1是 */
	public static final String MER_WEB_SUPPORT_D0 = "1";
	/** 商户D0结算1 - 商户不支持D0结算 - 0是 */
	public static final String MER_WEB_NONSUPPORT_D0 = "0";

	/**
	 * 商户签约产品费率，产品代码 备用 默认01-即时支付 02-网关支付 03-担保支付
	 */
	public static final String MER_FEE_PRODUCT_CODE_01 = "01";
	/**
	 * 商户签约产品费率，产品代码 备用 默认01-即时支付 02-网关支付 03-担保支付
	 */
	public static final String MER_FEE_PRODUCT_CODE_02 = "02";
	/**
	 * 商户签约产品费率，产品代码 备用 默认01-即时支付 02-网关支付 03-担保支付
	 */
	public static final String MER_FEE_PRODUCT_CODE_03 = "03";
	/**
	 * 商户状态 0:不可用
	 */
	public static final String MER_STATUS_DISABLED = "0";
	/**
	 * 商户状态 1:可用 影响登录商户门户
	 */
	public static final String MER_STATUS_ABLED = "1";
	/**
	 * 商户认证状态 0:未认证
	 */
	public static final String MER_AUTH_NOT_YET = "0";
	/**
	 * 商户认证状态 1:已认证
	 */
	public static final String MER_AUTH_YET = "1";
	/**
	 * 商户认证等级 0:未认证
	 */
	public static final String MER_AUTH_LV_0 = "0";
	/**
	 * 商户认证等级 1:一级
	 */
	public static final String MER_AUTH_LV_1 = "1";
	/**
	 * 商户认证等级 2：二级
	 */
	public static final String MER_AUTH_LV_2 = "2";
	/**
	 * 商户认证等级 3：三级
	 */
	public static final String MER_AUTH_LV_3 = "3";

	// ****************商户模块 END*****************

	/**************** 银联网银定义的常量 start *****************/

	/** 银联固定填写版本号 */
	public static final String UNIONPAY_VERSION = "5.0.0";
	/** 银联默认取值编码方式 */
	public static final String UNIONPAY_ENCODING = "UTF-8";
	/** 银联签名方法 取值：01（表示采用的是 RSA） 签名方法 目前只支持01-RSA方式证书加密 */
	public static final String UNIONPAY_SIGNMETHOD = "01";
	/** 银联接入类型 0：普通商户直连接入 1：收单机构接入 2：平台类商户接入 */
	public static final String UNIONPAY_ACCESSTYPE = "0";
	/** 银联账号类型01：银行卡02：存折03：IC 卡默认取值：01 */
	public static final String UNIONPAY_ACCTYPE = "01";
	/** 银联交易币种 默认为 156 */
	public static final String UNIONPAY_CURRENCYCODE = "156";

	/** B2C前台通知地址 **/
	public static final String B2CFRONTURL = "unionpayB2CFRONTURL";
	/** B2C后台通知地址 **/
	public static final String B2CBACKURL = "unionpayB2CBACKURL";
	/** B2C退款通知地址 **/
	public static final String B2CREFUNDBACKURL = "unionpayB2CREFUNDBACKURL";

	/** B2B前台通知地址 **/
	public static final String B2BFRONTURL = "unionpayB2BFRONTURL";
	/** B2B后台通知地址 **/
	public static final String B2BBACKURL = "unionpayB2BBACKURL";
	/** B2B退款通知地址 **/
	public static final String B2BREFUNDBACKURL = "unionpayB2BREFUNDBACKURL";

	/** 快捷（代收）前台通知地址 **/
	public static final String QUICKFRONTURL = "unionpayQUICKFRONTURL";
	/** 快捷（代收）后台通知地址 **/
	public static final String QUICKBACKURL = "unionpayQUICKBACKURL";
	/** 快捷（代收）退款通知地址 **/
	public static final String QUICKREFUNDBACKURL = "unionpayQUICKREFUNDBACKURL";

	/** 代付前台通知地址 **/
	public static final String DAIFUBACKURL = "交易币种";

	/**************** 银联网银定义的常量 end *****************/

	/****************** 易宝定义常量 *******************/
	/**
	 * 支付
	 */
	public static final String YEEPAY_CMD = "Buy";// 业务类型
	public static final String YEEPAY_CUR = "CNY";// 交易币种
	public static final String YEEPAY_NEEDRESPONSE = "1";// 应答机制
	public static final String YEEPAY_NET = "NET";
	public static final String YEEPAY_B2C = "B2C";
	public static final String YEEPAY_B2B = "B2B";
	public static final String YEEPAY_BNKCOD = "CCB";
	public static final String YEEPAY_MERID = "p1_MerId";
	public static final String YEEPAY_REQUESTURL = "requestURL";
	public static final String YEEPAY_KEYVALUE = "keyValue";
	public static final String YEEPAY_QUERYURL = "queryURL";
	public static final String YEEPAY_REFUNDURL = "refundURL";
	public static final String YEEPAY_REFUNDQUERYURL = "refundQueryURL";
	public static final String YEEPAY_CANCELORDERURL = "cancelOrderURL";
	/**
	 * 单笔订单查询
	 */
	public static final String YEEPAY_QRY_CMD = "QueryOrdDetail";
	public static final String YEEPAY_PV_VER = "3.0";
	public static final String YEEPAY_SERVICETYPE = "0";
	/**
	 * 退款
	 */
	public static final String PROGRAMMER_REFCMD = "";
	/** 系统默认代理商ID **/
	public final static String SYS_AGENT_ID = "2015000000";

	/**
	 * 系统默认密码
	 */
	public final static String SYS_PWD = "111111";
	/**
	 * 系统默认随机数
	 */
	public final static String SYS_RANDOM = "000000";
	/**
	 * 系统默认登录时间
	 */
	public final static String SYS_LASTLOGINTIME = "00000000000000";
	/**
	 * 需要修改密码
	 */
	public final static int SYS_ISFIRSTLOGINFLAG_0 = 0;

	// --------------------------代理商审核状态：0审核中，1审核通过，2审核不通过-----------------------------------
	/**
	 * 代理商审核状态：0审核中
	 */
	public static final String AUDIT_STATUS_APPLY = "0";
	/**
	 * 代理商审核状态：1审核通过
	 */
	public static final String AUDIT_STATUS_AGREE = "1";
	/**
	 * 代理商审核状态：2审核不通过
	 */
	public static final String AUDIT_STATUS_DISAGREE = "2";

}
