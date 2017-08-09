package com.yuanlai.agent.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanlai.agent.service.ReportService;

/**
 * @author Lee
 *
 */
public class CodePayReportTask {
	
	private static Logger log = LoggerFactory.getLogger(CodePayReportTask.class);

	@Autowired
	private ReportService reportService;

	public void run() {
		log.debug("T+1日生成交易报表   Task Start");

		try {
			reportService.codePayReportTask();
		} catch (Exception e) {
			log.error("T+1日生成交易报表 Exception:  " + e.getMessage());
			e.printStackTrace();
		}
		log.debug("T+1日生成交易报表   Task End");

	}

}
