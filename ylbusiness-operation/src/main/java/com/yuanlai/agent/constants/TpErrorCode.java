package com.yuanlai.agent.constants;

/**
 * 错误码常量
 * 
 * @author
 * @version 2016年4月25日
 */
public class TpErrorCode {
	// 返回操作结果成功
	public static final String RSPCOD_SUCCESS = "01000000";
	/** 程序异常返回 */
	public static final String PROGRAMMER_ERROR = "99999999";
	/** 参数值与同级参数值相同 */
	public static final String RECORD_EQUAL_ERROR = "01009997";
	
	/** 账务-必输参数不能为空 */
	public static final String PARAMSISNOTEXIT = "01010003";
	/** 记录不存在 */
	public static final String RECORD_NOT_EXSIT = "01050002";
	/** 账务-数据库操作错误 */
	public static final String DATABASEERROR = "01010002";
	/** 风控系统-记录已存在 */
	public static final String RCS_EC_RECORD_ISEXIST = "01050003";
	
	/** 用户无此权限 */
	public static final String USR_NO_AUTH = "01009998";
}