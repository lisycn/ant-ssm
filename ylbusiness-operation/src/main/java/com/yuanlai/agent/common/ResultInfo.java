package com.yuanlai.agent.common;
/**
 * 
 * @author chen_yq add 2016.04.27
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结果信息
 * rspcod：响应吗
 * rspmsg: 响应信息
 * rows  ： 结果集（查询时返回的列表）
 * total : 记录数（查询时返回的记录数）
 */
public class ResultInfo {

	private String rspcod;
	private String rspmsg;
	private List rows;
	private int total;
	private Object obj;
	public ResultInfo(){}
	
	public ResultInfo(String rspcod,String rspmsg){
		setRspcod(rspcod);
		setRspmsg(rspmsg);
	}
	
	public ResultInfo(String rspcod,String rspmsg,List rows,int total){
		setRspcod(rspcod);
		setRspmsg(rspmsg);
		setRows(rows);
		setTotal(total);
	}
	
	public Map<String, Object> returnSuccess(String rspcod,String rspmsg){
		setRspcod(rspcod);
		setRspmsg(rspmsg);
		return this.toMap();
	}
	public Map<String, Object> returnSuccess(String rspmsg){
		return returnSuccess(AuthConstants.COMMON_SUCCESSCODE,rspmsg);
	}
	public Map<String, Object> returnSuccess(){
		return returnSuccess(AuthConstants.COMMON_SUCCESSCODE, AuthConstants.COMMON_SUCCESSMSG);
	}
	
	public Map<String, Object> returnFail(String rspcod,String rspmsg){
		setRspcod(rspcod);
		setRspmsg(rspmsg);
		return this.toMap();
	}
	
	public Map<String, Object> returnFailByCode(String errorCode){
		return returnFail(errorCode, AuthPropUtil.getValue(errorCode));
	}

	public Map<String, Object> returnFailByCodeWithLocation(String errorCode,String... locationStr){
		return returnFail(errorCode, AuthPropUtil.getValue(errorCode, locationStr));
	}
	
	public Map<String, Object> returnFail(String rspmsg){
		return returnFail(AuthConstants.COMMON_FAILCODE,rspmsg);
	}
	public Map<String, Object> returnFail(){
		return returnFail(AuthConstants.COMMON_FAILCODE, AuthConstants.COMMON_FAILMSG);
	}
	/**
	 * 转成Map<String, Object>
	 * @return
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(getRspcod()!=null){
			map.put("rspcod", getRspcod());
		}
		if(getRspmsg() != null){
			map.put("rspmsg", getRspmsg());
		}
		if(getRows()!=null){
			map.put("rows", getRows());
		}
		if(getTotal()>0){
			map.put("total", getTotal());
		}if(getObj()!=null){
			map.put("obj", getObj());
		}
		return map;
	}
	
	public String getRspcod() {
		return rspcod;
	}
	public void setRspcod(String rspcod) {
		this.rspcod = rspcod;
	}
	
	public String getRspmsg() {
		return rspmsg;
	}

	public void setRspmsg(String rspmsg) {
		this.rspmsg = rspmsg;
	}

	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
