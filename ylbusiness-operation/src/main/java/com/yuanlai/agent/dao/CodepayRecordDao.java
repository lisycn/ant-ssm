package com.yuanlai.agent.dao;

import java.util.List;

import com.tangdi.def.toolkit.mybatis.data.BaseDao;
import com.yuanlai.agent.entity.CodepayRecord;

public interface CodepayRecordDao extends BaseDao<CodepayRecord, Exception> {
	
	
	/**
	 * 查询指定日期交易记录
	 * 
	 * @param String date
	 * @return
	 * @throws Exception
	 */
    public List<CodepayRecord> selectDailyRecordList(String date) throws Exception;
    
}