package com.yuanlai.agent.dao;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.CallbackRecord;
import com.yuanlai.agent.entity.CodePayMonthReport;

public interface CodePayMonthReportDao extends BaseDao<CallbackRecord, Exception> {
	
	// 写入月报表
	public void addMonthReport(CodePayMonthReport report) throws Exception;

}