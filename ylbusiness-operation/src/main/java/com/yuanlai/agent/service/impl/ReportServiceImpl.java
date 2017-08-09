package com.yuanlai.agent.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.def.utils.exception.TdRuntimeException;
import com.yuanlai.agent.common.AuthBeanUtil;
import com.yuanlai.agent.common.AuthCommonUtil;
import com.yuanlai.agent.common.AuthConstants;
import com.yuanlai.agent.constants.SourceConstants;
import com.yuanlai.agent.dao.AuthOrgDao;
import com.yuanlai.agent.dao.CodePayDailyReportDao;
import com.yuanlai.agent.dao.CodePayMonthReportDao;
import com.yuanlai.agent.dao.CodePayReportDao;
import com.yuanlai.agent.dao.CodepayRecordDao;
import com.yuanlai.agent.entity.AuthOrg;
import com.yuanlai.agent.entity.CodePayDailyReport;
import com.yuanlai.agent.entity.CodePayMonthReport;
import com.yuanlai.agent.entity.CodePayReport;
import com.yuanlai.agent.entity.CodepayRecord;
import com.yuanlai.agent.service.ReportService;

/**
 * 报表服务类
 * 
 * @author Lee
 *
 */
@Service
public class ReportServiceImpl implements ReportService {

	private static Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private CodepayRecordDao codepayRecordDao;

	@Autowired
	private AuthOrgDao authOrgDao;

	@Autowired
	private CodePayReportDao codePayReportDao;

	@Autowired
	private CodePayDailyReportDao codePayDailyReportDao;

	@Autowired
	private CodePayMonthReportDao codePayMonthReportDao;

	@Override
	public void codePayReportTask() throws Exception {

		// 昨天日期yyyyMMdd
		Date yestoday = DateUtils.addDays(new Date(), -1);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(yestoday);

		SimpleDateFormat dateParse = new SimpleDateFormat("yyyyMMddHHmmss");

		log.info("生成{}交易报表开始", date);

		// 接入商列表
		List<AuthOrg> orgList = authOrgDao.selectByParentOrgId(SourceConstants.ROOT_ORG_ID);

		// 昨天交易记录
		List<CodepayRecord> recordList = codepayRecordDao.selectDailyRecordList(date);

		CodePayReport report = new CodePayReport();

		// 不是设置机构费率就默认0.003
		BigDecimal wxRate = new BigDecimal(SourceConstants.ORG_DEFAULT_RATE);
		BigDecimal aliRate = new BigDecimal(SourceConstants.ORG_DEFAULT_RATE);
		// 恒丰费率
		BigDecimal hfRate = new BigDecimal(SourceConstants.HENGFENG_RATE);
		// 分机构报表
		for (AuthOrg org : orgList) {

			report.setAccessPart(org.getOrgId());

			// 获取机构微信费率
			if (null != org.getWxRate() && !"".equals(org.getWxRate())) {
				wxRate = new BigDecimal(org.getWxRate());
			}
			// 获取机构支付宝费率
			if (null != org.getAliRate() && !"".equals(org.getAliRate())) {
				aliRate = new BigDecimal(org.getAliRate());
			}

			for (CodepayRecord record : recordList) {
				if (org.getOrgId().equals(record.getAccesspart())) {
					String channelCode = record.getChannelCode();
					BigDecimal amount = new BigDecimal(record.getAmount());
					BigDecimal fee = new BigDecimal(record.getFee());
					// 恒丰手续费,最低1分
					BigDecimal hfFee = amount.multiply(hfRate).setScale(0, BigDecimal.ROUND_UP);
					// 平台手续费,最低1分
					BigDecimal sourceProfit = BigDecimal.ZERO;
					// 微信支付宝分别计算
					if ("ALIPAY".equals(channelCode)) {
						sourceProfit = amount.multiply(aliRate.subtract(hfRate)).setScale(0, BigDecimal.ROUND_UP);
					}
					if ("WXPAY".equals(channelCode)) {
						sourceProfit = amount.multiply(wxRate.subtract(hfRate)).setScale(0, BigDecimal.ROUND_UP);
					}
					// 交易手续费等于恒丰手续费, 平台无分润
					if (BigDecimal.ZERO.equals(fee.subtract(hfFee))) {
						sourceProfit = BigDecimal.ZERO;
					}
					// 接入商分润
					BigDecimal accessProfit = fee.subtract(hfFee).subtract(sourceProfit);
					
					report.setOrderId(record.getOrderid());
					report.setAccount(record.getAccount());
					report.setChannelCode(channelCode);
					report.setAmount(amount.doubleValue());
					report.setFee(fee.doubleValue());
					report.setHfFee(hfFee.doubleValue());
					report.setSourceProfit(sourceProfit.doubleValue());
					report.setAccessProfit(accessProfit.doubleValue());
					
					// 交易时间
					Date payTime = dateParse.parse(record.getPaytime());
					report.setTransTime(payTime);
					
					// 分别写入报表
					codePayReportDao.addCodePayReport(report);
				}
			}
		}
		log.info("生成{}交易报表完成", date);
	}

	@Override
	public void dailyReportTask() throws Exception {

		// 昨天yyyy-MM-dd
		Date yestody = DateUtils.addDays(new Date(), -1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(yestody);

		log.info("生成{}日报表开始", date);

		List<CodePayReport> reportList = codePayReportDao.selectDailyReport(date);

		CodePayDailyReport dailyReport = new CodePayDailyReport();
		dailyReport.setTransDate(yestody);
		for (CodePayReport report : reportList) {
			dailyReport.setAccessPart(report.getAccessPart());
			dailyReport.setChannelCode(report.getChannelCode());
			dailyReport.setAmount(report.getAmount());
			dailyReport.setTotalFee(report.getFee());
			dailyReport.setHfFee(report.getHfFee());
			dailyReport.setSourceProfit(report.getSourceProfit());
			dailyReport.setAccessProfit(report.getAccessProfit());
			codePayDailyReportDao.addDailyReport(dailyReport);
		}
		log.info("生成{}日报表完成", date);
	}

	@Override
	public void monthReportTask() throws Exception {
		// 上个月yyyy-MM
		Date lastMonth = DateUtils.addMonths(new Date(), -1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String date = format.format(lastMonth);

		log.info("生成{}月报表开始", date);

		List<CodePayDailyReport> reportList = codePayDailyReportDao.selectMonthReport(date);

		CodePayMonthReport monthReport = new CodePayMonthReport();
		monthReport.setTransMonth(date);
		for (CodePayDailyReport report : reportList) {
			monthReport.setAccessPart(report.getAccessPart());
			monthReport.setChannelCode(report.getChannelCode());
			monthReport.setAmount(report.getAmount());
			monthReport.setTotalFee(report.getTotalFee());
			monthReport.setHfFee(report.getHfFee());
			monthReport.setSourceProfit(report.getSourceProfit());
			monthReport.setAccessProfit(report.getAccessProfit());
			codePayMonthReportDao.addMonthReport(monthReport);
		}
		log.info("生成{}月报表完成", date);
	}

	@Override
	public Map<String, Object> getDailyReportList(CodePayDailyReport report, int pageSize, int pageNum)
			throws Exception {
		log.info("分页查询日报表信息");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(report);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		log.info("查询条件paraMap:" + paraMap.toString());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> retList = codePayDailyReportDao.selectByPager(paraMap);
			int total = codePayDailyReportDao.countByCondition(paraMap);
			map.put(AuthConstants.COMMON_LIST, retList);
			map.put(AuthConstants.COMMON_TOTAL, total);
			return map;
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	public Map<String, Object> getMonthReportList(CodePayMonthReport report, int pageSize, int pageNum)
			throws Exception {
		log.info("分页查询月报表信息");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(report);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		log.info("查询条件paraMap:" + paraMap.toString());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> retList = codePayMonthReportDao.selectByPager(paraMap);
			int total = codePayMonthReportDao.countByCondition(paraMap);
			map.put(AuthConstants.COMMON_LIST, retList);
			map.put(AuthConstants.COMMON_TOTAL, total);
			return map;
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	public Map<String, Object> getTotalReport(CodePayDailyReport report) throws Exception {
		log.info("查询总报表信息");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(report);
		log.info("查询条件paraMap:" + paraMap.toString());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<CodePayDailyReport> retList = codePayDailyReportDao.selectTotalReportList(report);
			map.put(AuthConstants.COMMON_LIST, retList);
			map.put(AuthConstants.COMMON_TOTAL, retList.size());
			return map;
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}

	@Override
	public Map<String, Object> getCodePayReportList(CodePayReport report, int pageSize, int pageNum) throws Exception {
		log.info("分页查询交易报表信息");
		Map<String, Object> paraMap = AuthBeanUtil.toMap(report);
		paraMap = AuthCommonUtil.setPageParam(paraMap, pageSize, pageNum);
		log.info("查询条件paraMap:" + paraMap.toString());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> retList = codePayReportDao.selectByPager(paraMap);
			int total = codePayReportDao.countByCondition(paraMap);
			map.put(AuthConstants.COMMON_LIST, retList);
			map.put(AuthConstants.COMMON_TOTAL, total);
			return map;
		} catch (Exception e) {
			throw new TdRuntimeException("数据库错误");
		}
	}	

}
