package com.yuanlai.agent.service.impl;

import com.tangdi.def.base.exec.TdSyncExecService;
import com.tangdi.def.base.freemarker.TdFreeMarkerService;
import com.tangdi.def.utils.common.*;
import com.tangdi.tdcomm.ext.utils.SpringContext;
import com.yuanlai.agent.common.DownloadUtil;
import com.yuanlai.agent.common.EmptyUtil;
import com.yuanlai.agent.common.TdCommonUtil;
import com.yuanlai.agent.dao.PubDownloadInfDao;
import com.yuanlai.agent.entity.PubAttachment;
import com.yuanlai.agent.entity.PubDownloadInf;
import com.yuanlai.agent.service.IDownloadService;
import com.yuanlai.agent.service.IFileDownloadService;
import com.yuanlai.agent.service.IPubAttachmentService;
import com.yuanlai.agent.service.IPubDictService;
import com.yuanlai.agent.util.ConvertUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 下载中心接口实现
 * 
 * @version 1.0.0,2016年9月27日
 * @author xuxf
 */
@Service
public class DownloadServiceImpl implements IDownloadService {

	private static final Logger log = LoggerFactory.getLogger(DownloadServiceImpl.class);

	@Autowired
	private PubDownloadInfDao pubDownloadInfDao;
	@Autowired
	private TdFreeMarkerService tdFreeMarkerService;
	@Autowired
	private IPubDictService pubDictService;
	@Autowired
	private IPubAttachmentService iPubAttachmentService;

	/**
	 * 预下载数据保存
	 * 
	 * @param params
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> preDownLoad(Map<String, Object> params) {

		Map<String,Object> respInfo = new HashMap<String,Object>();
		if (EmptyUtil.isNull(params.get("EXPORT_TYPE"))) {
			respInfo.put("rspMsg","导出类型不能为空");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		if (EmptyUtil.isNull(params.get("EXPORT_PARAM"))) {
			respInfo.put("rspMsg","导出条件不能为空");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		// 把参数放入实体类中
		PubDownloadInf pubDownloadInf = new PubDownloadInf();
		try {
			Map<String, Object> humpMap = ConvertUtil.getCamelMap(params);
			pubDownloadInf = TdBeanUtil.toObject(humpMap, PubDownloadInf.class);
		} catch (Exception e) {
			log.info(e.toString());
			respInfo.put("rspMsg","系统异常");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		// 生成主键
		String id = IdWorker.getWorkerId();
		String fileName = IdWorker.getWorkerId();
		params.put("FILE_NAME", fileName);
		params.put("ID", id);

		pubDownloadInf.setId(id);
		pubDownloadInf.setFileName(fileName);
		pubDownloadInf.setFileStatus("####");// 文件状态
		pubDownloadInf.setCreateT(TdDateUtil.getDateTime());
		pubDownloadInf.setSysId("010");
		// 插入下载中心表
		try {
			pubDownloadInfDao.insert(pubDownloadInf);//插入下载中心表
		} catch (Exception e) {
			log.info(e.toString());
			respInfo.put("rspMsg","系统异常");
			respInfo.put("rspCode","999999");
			return respInfo;
		}
		
		String sVersion=String.valueOf(params.get("sVersion"));
		TdSyncExecService.asyncExec(DownloadServiceImpl.class,"createDownload", sVersion, params);
		try {
			createDownload(sVersion, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respInfo;
	}

	/**
	 * 把文件下载到服务器
	 * 
	 * @param params
	 * @return RespInfo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createDownload(String sVersion, Map<String, Object> params) throws Exception {
		log.info("传入的参数为：" + params.toString());
		Map<String,Object> respInfo = new HashMap<String,Object>();

		try {
			Map<String, Object> qryMap = null;
			// 把传入的参数解析出来 解析之后调查询接口
			if (!EmptyUtil.isNull(params.get("EXPORT_PARAM"))) {
				qryMap = DownloadUtil.setExportParam(String.valueOf(params.get("EXPORT_PARAM")));
			} else {
				respInfo.put("rspMsg","数据库中的导出条件为空");
				throw new Exception();
			}

			String service = TdCommUtil.getProperty(params.get("SERVICE").toString());
			// 去数据库中取数据
			List<Map<String, Object>> downloadMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> extraParams = new HashMap<String, Object>();
			Map model = new HashMap();

			// 判断导出类型，去查导出数据
			try {
				service = service.replaceFirst(service.substring(0, 1), service.substring(0, 1).toLowerCase());
				IFileDownloadService fileDownloadService = SpringContext.getBean(service, IFileDownloadService.class);

				downloadMapList = fileDownloadService.download(qryMap);

				if (EmptyUtil.isNotNull(params.get("resultMap"))) {
					extraParams = downloadMapList.get(downloadMapList.size() - 1);
					downloadMapList.remove(downloadMapList.size() - 1);
					model.putAll(extraParams);
				}

			} catch (Exception e) {
				log.info(e.toString());
				respInfo.put("rspMsg","系统异常");
				respInfo.put("rspCode","999999");
				e.printStackTrace();
				throw new Exception();
			}

			// 把返回数据为空的字段设置成空字符串
			if(downloadMapList!=null&&downloadMapList.size()>0){
				for (int i = 0; i < downloadMapList.size(); i++) {
					Map<String, Object> downloadMap = downloadMapList.get(i);
					for (Map.Entry<String, Object> entry : downloadMap.entrySet()) {
						if (entry.getValue() == null || entry.getValue().equals("null")) {
							entry.setValue("");
						}
					}
				}

				// 取字典表
				pubDictService.formatDictList(downloadMapList);
			}
			// 下载所需的参数
			String templateName = String.valueOf(params.get("TEMPLATENAME"));
			model.put("downloadMapList", downloadMapList);
			String filtName = String.valueOf(params.get("FILE_NAME")) + ".xls";

			// 调用下载方法
			String filePath = "";
			try {
				filePath = tdFreeMarkerService.createTemplateXlsByFileName(templateName, model, filtName);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
			
			//把excel的信息存在附件表里面，并上传到文件服务器上
			params.put("FILE_NAME", filtName);
			params.put("filePath", filePath);
			this.addAttachment(params);

			//把文件转移到文件服务器上
//			String sSaveType = "0";
//			try {
//				sSaveType = TdCommUtil.getProperty("ATTACHMENT.SAVE.TYPE");
//			} catch (Exception e) {
//				sSaveType = "0";
//			}
//			
//			//如果是存储在fastDFS上，首先上传fastDFS，然后移除本地的上传文件
//			if("1".equals(sSaveType)){
//				log.info("将文件保存至fastDFS start");
//				String sFjFastId = TdCommUtil.fdfsUpload(filePath);
//				log.info("将文件保存至fastDFS end");
//				
//				//删除本地服务器上文件
//				TdFileUtil.deleteFile(filePath);//删除文件
//				
//				filePath = sFjFastId;//附件信息表中的FJPATH 存储内容为fastId
//
//			}
			
			params.put("FILE_STATUS", "01");
		} catch (Exception e) {
			log.info("异步失败：" + e.toString());
			e.printStackTrace();
			params.put("FILE_STATUS", "02");
		} finally {
			// 更新下载状态、文件路径
			updFileStaus(params);
		}
	}

	/**
	 * 更新下载状态、文件路径
	 * 
	 * @param params
	 *            void
	 */
	public void updFileStaus(Map<String, Object> params) {
		try {
			pubDownloadInfDao.updateFileStatus(params);
		} catch (Exception e) {
			log.info(e.toString());
		}

	}

	/**
	 * 查询下载列表
	 * 
	 * @param params
	 * @return RespInfo
	 */
	public Map<String,Object> qryDownloadList(Map<String, Object> params) {
		log.info("传入的参数为：" + params.toString());
		Map<String,Object> respInfo = new HashMap<>();
		List<Map<String, Object>> downloadList = null;

		if (EmptyUtil.isNotNull(params.get("BGN_DAT"))) {
			String beginDat = TdDateUtil.fmtDate(String.valueOf(params.get("BGN_DAT")), "yyyy-MM-dd", "yyyyMMdd");
			params.put("BGN_DAT", beginDat);
		}
		if (EmptyUtil.isNotNull(params.get("END_DAT"))) {
			String endDat = TdDateUtil.fmtDate(String.valueOf(params.get("END_DAT")), "yyyy-MM-dd", "yyyyMMdd");
			params.put("END_DAT", endDat);
		}

		// 计算满足条件的总条数
		params.put("countList", "countList");
		try {
			downloadList = pubDownloadInfDao.qryDownloadList(params);
		} catch (Exception e) {
			log.info(e.toString());
			respInfo.put("rspMsg","系统异常");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		params.put("countList", "");
		respInfo.put("total", downloadList.get(0).get("total"));

		// 获取开始条数和每页大小
		params.putAll(getPageInfo(params));

		try {
			downloadList = pubDownloadInfDao.qryDownloadList(params);
		} catch (Exception e) {
			log.info(e.toString());
			respInfo.put("rspMsg","系统异常");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		// 获取字典表的数据
		pubDictService.formatDictList(downloadList);

		respInfo.put("list",downloadList);
		respInfo.put("rspMsg","系统异常");
		respInfo.put("rspCode","000000");
		return respInfo;
	}

	/**
	 * 删除下载中心当前记录
	 * 
	 * @param params
	 * @return RespInfo
	 */
	@Override
	public Map<String,Object> delCurrRecord(Map<String, Object> params) {
		log.info("传入的参数为：" + params.toString());
		Map<String,Object> respInfo = new HashMap<String,Object>();

		if (EmptyUtil.isNull(params.get("IDS"))) {
			respInfo.put("rspMsg","下载主键不能为空");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		String ids = (String) params.get("IDS");// 删除的主键id列表
		String[] idArr = ids.split(",");
		Map<String, Object> reqMap = new HashMap<String, Object>();

		for (int i = 0; i < idArr.length; i++) {
			reqMap.put("ID", idArr[i]);
			// 查询当前记录是否允许删除
			PubDownloadInf pubDownloadInf = null;
			try {
				pubDownloadInf = (PubDownloadInf) pubDownloadInfDao.selectByPrimaryKey(String.valueOf(reqMap.get("ID")));
			} catch (Exception e) {
				log.info(e.toString());
				respInfo.put("rspMsg","系统异常");
				respInfo.put("rspCode","999999");
				return respInfo;
			}

			if (!"00".equals(pubDownloadInf.getIsDel())) {
				respInfo.put("rspMsg","该条记录不允许删除");
				respInfo.put("rspCode","999999");
				return respInfo;
			}

			// 删除当前记录
			try {
				pubDownloadInfDao.deleteByPrimaryKey(String.valueOf(reqMap.get("ID")));
			} catch (Exception e) {
				log.info(e.toString());
				respInfo.put("rspMsg","系统异常");
				respInfo.put("rspCode","999999");
				return respInfo;
			}
		}
		respInfo.put("rspMsg","删除成功");
		respInfo.put("rspCode","000000");
		return respInfo;
	}

	/**
	 * 获取开始条数和每页大小
	 * 
	 * @param params
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> getPageInfo(Map<String, Object> params) {
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		// 分页
		int start = 0;
		int limit = 0;
		// 获取每页多少条
		if (params.get("pageSize") == null || !TdNumberUtil.isNumber((String) params.get("pageSize"))) {
			limit = 15;
		} else {
			limit = Integer.parseInt((String) params.get("pageSize"));
		}
		// 获取开始条
		if (params.get("pageNum") == null || !TdNumberUtil.isNumber((String) params.get("pageNum"))) {
			start = (1 - 1) * limit;
		} else {
			start = (Integer.parseInt((String) params.get("pageNum")) - 1) * limit;
		}

		pageInfo.put("start", start);
		pageInfo.put("limit", limit);

		return pageInfo;

	}

	/**
	 * 下载流文件
	 * 
	 * @param params
	 * @return RespInfo
	 */
	public Map<String,Object> getStreamFile(Map<String, Object> params) {
		log.info("传入的参数为：" + params.toString());
		Map<String,Object> respInfo = new HashMap<String,Object>();
		if (EmptyUtil.isNull(params.get("ID"))) {
			respInfo.put("rspMsg","下载主键不能为空");
			respInfo.put("rspCode","999999");
		}

		PubDownloadInf pubDownloadInf = new PubDownloadInf();

		try {
			pubDownloadInf = (PubDownloadInf) pubDownloadInfDao.selectByPrimaryKey(String.valueOf(params.get("ID")));
		} catch (Exception e) {
			log.info(e.toString());
			respInfo.put("rspMsg","系统异常");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		if (EmptyUtil.isNull(pubDownloadInf)) {
			respInfo.put("rspMsg","记录不存在");
			respInfo.put("rspCode","999999");
			return respInfo;
		}

		respInfo.put("obj",pubDownloadInf);
		return respInfo;
	}
	
	/**
	 * 把excel的信息存在附件表里面，并上传到文件服务器上
	 * @param params
	 * @return 
	 * RespInfo
	 */
	public Map<String,Object> addAttachment(Map<String,Object> params){
		Map<String,Object> respInfo = new HashMap<String,Object>();
		
		String sFilePath = String.valueOf(params.get("filePath"));//附件路径
		String sOldFileName = String.valueOf(params.get("FILE_NAME"));//附件名称
		String sFjo = String.valueOf(params.get("CREATE_O"));//附件创建者
		String pkId = String.valueOf(params.get("ID"));//业务表ID
		String sId = TdCommonUtil.getSeqId("FJ_ID");
		
		PubAttachment attachment = new PubAttachment();
		attachment.setId(sId);//附件ID
		attachment.setTableName("PUB_DOWNLOAD_INF");//表名 用来指定改附件属于哪张表
		attachment.setPkId(pkId);//业务表ID
		attachment.setLx("EXCEL");//类型
		attachment.setOrderNum("01");//序号
		attachment.setFjPath(sFilePath);//附件路径
		attachment.setFjName(sOldFileName);//附件名称
		attachment.setFjt(TdCommonUtil.getDateTime());//附件创建时间
		attachment.setFjo(sFjo);//附件创建者
		attachment.setSfsx("1");//是否生效 1-生效 0-不生效
		
		try {
			iPubAttachmentService.insertEntity(attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		respInfo.put("rspMsg","交易成功");
		respInfo.put("rspCode","000000");
		return respInfo;
		
	}

	/**
	 * 删除下载到本地服务器的附件
	 * @param sVersion
	 * @param params
	 * @return 
	 * RespInfo
	 */
	@Override
	public Map<String,Object> delLocalFile(String sVersion, Map<String, Object> params) {
		log.info("delLocalFile start");
		log.info("传入的参数为："+params.toString());

		Map<String,Object> respInfo = new HashMap<String,Object>();

		String fjPath = String.valueOf(params.get("fjPath"));//原图片路径
		String dirFileStr = new String();//附件所在目录
        
        int lastNum = fjPath.lastIndexOf("/");
		dirFileStr = fjPath.substring(0, lastNum);
		
		try {
			TdFileUtil.deleteFile(fjPath);//删除文件
			
			//判断目录是否为空，为空删除
			File dirFile = new File(dirFileStr);
			if(dirFile.isDirectory()){
				String[] files = dirFile.list();
				if(files.length ==0){
					TdFileUtil.deleteFile(dirFileStr);//删除文件所在的空目录
				}
			}
			
		} catch (Exception e) {
			log.info(e.toString());
			respInfo.put("rspMsg","系统异常");
			respInfo.put("rspCode","999999");
			return respInfo;
			
		}

		respInfo.put("rspMsg","操作成功");
		respInfo.put("rspCode","000000");
		return respInfo;
	}
}
