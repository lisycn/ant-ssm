package com.yuanlai.agent.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 查询条件
 * @author Lee
 *
 */
public class QueryModel {
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
