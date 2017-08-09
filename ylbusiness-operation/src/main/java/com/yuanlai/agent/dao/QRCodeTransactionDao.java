package com.yuanlai.agent.dao;

import java.util.List;
import java.util.Map;

public interface QRCodeTransactionDao  {
	/**
	 * 查询商品列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> transactionList(Map<String, Object> param);


	/**
	 * 查询商品列表总数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int transactionCount(Map<String, Object> param) throws Exception;

	/**
	 * 查询手续费，总金额，总笔数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCountList(Map<String, Object> param);


}
