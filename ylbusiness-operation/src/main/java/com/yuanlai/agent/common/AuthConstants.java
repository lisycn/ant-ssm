package com.yuanlai.agent.common;
/**
 * 
 * @author chen_yq add 2016.04.27
 */
public class AuthConstants {

	//****************AuthItem实体类字段*******************
	/**菜单实体类 authItem**/
	public static String ITEM_AUTHITEM = "authItem";
	
	/**菜单ID itmId**/
	public static String ITEM_ITMID = "itmId";
	
	/**菜单名 itmName**/
	public static String ITEM_ITMNAME = "itmName";
	
	/**上级菜单ID parentItmId**/
	public static String ITEM_PARENTITMID = "parentItmId";
	/**菜单url itmUrl**/
	public static String ITEM_ITMURL = "itmUrl";	
	/**菜单类型 itmTyp**/
	public static String ITEM_ITMTYP = "itmTyp";
	
	/**菜单所属系统ID sysId**/
	public static String ITEM_SYSID = "sysId";	
	
	/**是否叶子节点 isLeaf**/
	public static String ITEM_ISLEAF = "isLeaf";	
	
	/**是否有父菜单 */
	public static String ITEM_HAVE_PARENT = "hasPrt";
	
	/**顶级菜单id 000**/
	public static String ITEM_ROOT_ITMID = "000";
	//****************AuthRole实体类字段*******************
	/**角色 authRole**/
	public static String ROLE_AUTHROLE = "authRole";
	
	/**角色名 roleName**/
	public static String ROLE_ROLENAME = "roleName";	
	/**角色id roleId**/
	public static String ROLE_ROLEID = "roleId";
	
	//****************AuthUsr实体类字段*******************
	/**用户 authUsr**/
	public static String USR_AUTHUSR = "authUsr";
	
	/**用户名 usrName**/
	public static String USR_USRNAME = "usrName";
	
	/**用户id usrId**/
	public static String USR_USRID = "usrId";
	
	/**用户密码 usrPsw**/
	public static String USR_USRPSW = "usrPsw";
	
	/**用户旧密码 oldPwd**/
	public static String USR_OLDPWD = "oldPwd";	
	
	
	/**用户真实名 usrRealName**/
	public static String USR_USRREALNAME = "usrRealName";
	
	/**用户状态 usrStatus**/
	public static String USR_USRSTATUS = "usrStatus";

	/**是否首次登录 isFirstLogin**/
	public static String USR_ISFIRSTLOGIN = "isFirstLogin";
	
	/**上次登录时间**/
	public static String USR_LASTLOGINTIME = "lastLoginTime";
	
	/**登录失败次数**/
	public static String USR_FAILLOGINTIMES = "failLoginTimes";
	//****************AuthOrg实体类字段*******************
	/**机构 authOrg**/
	public static String ORG_AUTHORG = "authOrg";
	
	/**机构id orgId**/
	public static String ORG_ORGID = "orgId";
	
	/**上级机构id parentOrgId**/
	public static String ORG_PARENTORGID = "parentOrgId";	
	
	/**机构名 orgName**/
	public static String ORG_ORGNAME = "orgName";
	
	/**顶级机构id : sysOrg**/
	public static String ORG_ROOT_ORGID = "sysOrg";
	
	//****************系统信息*******************
	/**系统实体类 authSysInf**/
	public static String AUTHSYSINF = "authSysInf";
	
	//****************通用字段*******************
	/**通用字段 是否使用 isUse**/
	public static String COMMON_ISUSE = "isUse";
	
	/**用户当前角色列表 usrCurrRoleList**/
	public static String COMMON_USRCURRROLELIST = "usrCurrRoleList";
	
	/**角色当前的菜单id集合 roleCurrItemIdList**/
	public static String COMMON_ROLECURRITEMIDLIST = "roleCurrItemIdList";
	
	/**用户当前角色ID集合 usrCurrRoleIdList**/
	public static String COMMON_USRCURRROLEIDLIST = "usrCurrRoleIdList";

	/**用户当前角色ID字符串  usrCurrRoleIds**/
	public static String COMMON_USRCURRROLEIDS = "usrCurrRoleIds";
	
	/**用户当前的权限（菜单）集合  usrCurrItemList**/
	public static String COMMON_USRCURRITEMLIST = "usrCurrItemList";
	
	/**全部角色信息 allRoleList**/
	public static String COMMON_ALLROLELIST = "allRoleList";	
	
	/**用户登录时的权限列表 usrLoginAuthList**/
	public static String COMMON_USRLOGINAUTHLIST = "usrLoginAuthList";		
	
	/**菜单树 itemTree**/
	public static String COMMON_ITEMTREE = "itemTree";
	
	/**list**/
	public static String COMMON_LIST = "list";
	
	/**start**/
	public static String COMMON_START = "start";
	
	/**limit**/
	public static String COMMON_LIMIT = "limit";
	
	/**英文逗号 , **/
	public static String COMMON_COMMA = ",";
	
	/**#号**/
	public static String COMMON_HASHTAG = "#";
	
	/**key**/
	public static String COMMON_KEY = "key";
	
	/**children**/
	public static String COMMON_CHILDREN = "children";
	
	/**成功交易码:000000**/
	public static String COMMON_SUCCESSCODE = "000000";
	
	/**成功交易信息:交易成功**/
	public static String COMMON_SUCCESSMSG = "交易成功";
	
	/**失败错误码:999999**/
	public static String COMMON_FAILCODE = "999999";
	
	/**失败错误信息:交易失败**/
	public static String COMMON_FAILMSG = "交易失败";

	/**rspcod**/
	public static String COMMON_RSPCOD = "rspcod";
	
	/**rspmsg**/
	public static String COMMON_RSPMSG = "rspmsg";
	
	/**rows**/
	public static String COMMON_ROWS = "rows";
	
	/**total**/
	public static String COMMON_TOTAL = "total";
	
	/**obj**/
	public static String COMMON_OBJ = "obj";
	
	/**系统id sysId**/
	public static String COMMON_SYSID = "sysId";	
	/**UTF-8 字符集编码**/
	public static String COMMON_CHARSET_UTF8 = "UTF-8";
	
	/**系统编号 009 **/
	public static String COMMON_SYSTEMID = "009";
	/**前缀  auth**/
	public static String COMMON_PREFIX = "auth";
	/**token**/
	public static String COMMON_TOKEN = "token";	
	public static final String COMMON_USRINFO = "usrInfo";
	//****************int常量*******************
	/**int 0**/
	public static int INT_0 = 0;
	
	/**int 1**/
	public static int INT_1 = 1;
	
	/**int 10**/
	public static int INT_10 = 10;
	
	/**int 30**/
	public static int INT_30 = 30;
	/**最大登录失败次数 5**/
	public static int MAX_LOGIN_TIMES = 5;
	
	//****************String常量*******************
	/**String 0**/
	public static String STRING_0 = "0";
	/**String 1**/
	public static String STRING_1 = "1";
	/**String 101**/
	public static String STRING_101 = "101";
	/**td888888**/
	public static String STRING_td888888 = "yl888888";
	//****************其他常量*******************
	/**时间类型:yyyyMMddHHmmss**/
	public static String TIMESTYLE_yyyymmddhhmmss = "yyyyMMddHHmmss";
}
