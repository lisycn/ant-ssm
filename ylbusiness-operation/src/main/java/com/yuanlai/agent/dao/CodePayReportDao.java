package com.yuanlai.agent.dao;

import java.util.List;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.CodePayReport;

public interface CodePayReportDao extends BaseDao<CodePayReport, Exception> {
	
	// 写入交易报表
    public void addCodePayReport(CodePayReport report) throws Exception;

	// 查询日报表
	public List<CodePayReport> selectDailyReport(String day) throws Exception;
}