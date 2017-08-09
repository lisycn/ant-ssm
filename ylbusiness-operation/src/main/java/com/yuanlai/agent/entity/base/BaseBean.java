package com.yuanlai.agent.entity.base;

/**
 * 分页模型, �?要分页的实体类可继承
 * 
 * @author Lee
 *
 */
public class BaseBean {
	
	// 分页页码
	private Integer pageNum;
	
	// 分页大小
    private Integer pageSize;
	
	// �?始时�?
	private String startTime;
	
	// 结束时间
	private String endTime;
    

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
