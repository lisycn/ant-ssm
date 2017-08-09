package com.yuanlai.agent.common;

/**
 * 错误码分类
 * @author chen_yq add 2016.04.28
 *
 */
public class AuthErrorCode {
	//=======用户模块：USR_******=======
	public static String USR_000001="USR_000001";
	
	//=======角色模块：ROLE_******=======
	public static String ROLE_000001="ROLE_000001";	

	//=======菜单模块：ITEM_******=======
	/**菜单编号已达到最大值**/
	public static String ITEM_000001="ITEM_000001";
	/**未查询到 # 的菜单信息**/
	public static String ITEM_000002="ITEM_000002";
	/**菜单的parentItmId不能是 #**/
	public static String ITEM_000003="ITEM_000003";
	/**# 不能为空**/
	public static String ITEM_000004="ITEM_000004";
	//=======系统信息模块：SYSINF_******=======
	public static String SYSINF_0001="SYSINF_0001";	

	//=======机构信息模块：ORG_******=======
	public static String ORG_000001="ORG_000001";	

	
	//=======通用=======
	/**000000:交易成功**/
	public static String COMM_000000 = "COMM_000000";
	/**999999:交易失败**/
	public static String COMM_999999 = "COMM_999999";
	/**999998:数据库错误**/
	public static String COMM_999998 = "COMM_999998";
	/**999997:系统异常**/
	public static String COMM_999997 = "COMM_999997";
}
