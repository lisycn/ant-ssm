package com.yuanlai.agent.dao;

import java.util.List;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.CallbackRecord;
import com.yuanlai.agent.entity.CodePayDailyReport;

public interface CodePayDailyReportDao extends BaseDao<CallbackRecord, Exception> {

	// 写入日报表
    public void addDailyReport(CodePayDailyReport report) throws Exception;

	// 查询月报表
	public List<CodePayDailyReport> selectMonthReport(String month) throws Exception;

	// 查询总报表
	public List<CodePayDailyReport> selectTotalReportList(CodePayDailyReport report) throws Exception;
    
}