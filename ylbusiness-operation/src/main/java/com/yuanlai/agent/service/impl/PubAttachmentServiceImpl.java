package com.yuanlai.agent.service.impl;

import com.tangdi.def.utils.common.TdCommUtil;
import com.tangdi.def.utils.common.TdDateUtil;
import com.tangdi.def.utils.common.TdFileUtil;
import com.yuanlai.agent.common.TdCommonUtil;
import com.yuanlai.agent.dao.PubAttachmentDao;
import com.yuanlai.agent.entity.PubAttachment;
import com.yuanlai.agent.service.IPubAttachmentService;
import com.yuanlai.agent.util.FileUtils;
import com.yuanlai.agent.util.ImgAndString;
import com.yuanlai.agent.util.TransferBmpOrGifToJpg;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PubAttachmentServiceImpl implements IPubAttachmentService {

	private static final Logger log = LoggerFactory.getLogger(PubAttachmentServiceImpl.class);
	@Autowired
	private PubAttachmentDao attachmentDao;

	public PubAttachment getEntity(PubAttachment entity) {
		return null;
	}

	public List<PubAttachment> getList(PubAttachment entity) {
		return null;
	}

	public int addEntity(PubAttachment attachment) {
		try {
			/** 根据设置判断是否上传附件信息至fast **/
			String sFjPath = attachment.getFjPath();
			sFjPath = saveAttachmentToFast(sFjPath);
			attachment.setFjPath(sFjPath);
			/** 根据设置判断是否上传附件信息至fast **/
		} catch (Exception e) {
			log.error("{}", e);
		}
		return this.attachmentDao.insertEntity(attachment);
	}

	public int addList(List<PubAttachment> list) {
		return 0;
	}

	public int modifyEntity(PubAttachment entity) {
		return 0;
	}

	public int modifyList(List<PubAttachment> list) {
		return 0;
	}

	public int removeEntity(PubAttachment attachment) {
		return this.attachmentDao.deleteEntity(attachment);
	}

	public int removeList(List<PubAttachment> list) {
		return 0;
	}

	public int updateNewAttachment(Map<String, String> paramMap) {
		List<String> idList = TdCommonUtil.getReqIDList(paramMap);
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (null == paramMap.get("PKID")) {
			return -1;
		}

		tempMap.put("PKID", paramMap.get("PKID"));
		tempMap.put("IDS", idList);

		return this.attachmentDao.updateNewEntity(tempMap);
	}

	public int updateHasSaveAttachment(PubAttachment attachment) {
		this.attachmentDao.deleteSameTypeEntity(attachment);
		List<String> idList = new ArrayList<String>();
		idList.add(attachment.getId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PKID", attachment.getPkId());
		paramMap.put("IDS", idList);

		return this.attachmentDao.updateNewEntity(paramMap);
	}

	public List<PubAttachment> getList(Map<String, String> paramMap) {
		log.info("参数：" + paramMap);
		return this.attachmentDao.selectList(paramMap);
	}

	public Map<String, String> queryAttachmentMap(Map<String, String> paramMap) {
		Map<String, String> resMap = new HashMap<String, String>();
		List<PubAttachment> fjList = this.attachmentDao.selectList(paramMap);
		String sId = null;
		String sLx = null;
		String sOrderNum = null;
		String sLx_order = null;
		String sFjName = null;
		String sFjPath = null;
		String sFjShow = null;

		for (PubAttachment fj : fjList) {
			sId = fj.getId();
			sLx = fj.getLx();
			sOrderNum = fj.getOrderNum();
			sLx_order = "_" + sLx + "_" + sOrderNum;
			sFjName = fj.getFjName();
			sFjPath = fj.getFjPath();
			sFjShow = TdCommonUtil.getRelPath(sFjPath);

			resMap.put("FJID" + sLx_order, sId);
			resMap.put("FJNAME" + sLx_order, sFjName);
			resMap.put("FJSHOW" + sLx_order, sFjShow);
		}
		return resMap;
	}
	
	public void saveFile(MultipartFile file, String fileDir, String newFileName, PubAttachment attachment)
			throws Exception {
		log.info("文件上传 attachment：[{},{}]", fileDir, newFileName);
		File targetFile = new File(fileDir, newFileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		file.transferTo(targetFile);

		addEntity(attachment);
	}

	public void saveFile(MultipartFile file, String fileDir, String newFileName, PubAttachment attachment, String sFileType ,String prefixPicture)
			throws Exception {
		log.info("文件上传 attachment：[{},{}]", fileDir, newFileName);
		
		if(sFileType.toLowerCase().indexOf("gif")>=0 || sFileType.toLowerCase().indexOf("bmp")>=0){
			File targetFile = new File(fileDir);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			TransferBmpOrGifToJpg.bmporgifTojpgStream((FileInputStream)file.getInputStream(), fileDir+prefixPicture+".jpg", sFileType);
		}else{
			File targetFile = new File(fileDir,newFileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			file.transferTo(targetFile);
		}
		

		addEntity(attachment);
	}

	@Override
	public int updateNewPkId(Map<String, String> paramMap) {
		return this.attachmentDao.updateNewPkId(paramMap);
	}

	@Override
	public Map<String, Object> queryAttachMap(Map<String, String> paramMap) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<PubAttachment> fjList = this.attachmentDao.selectList(paramMap);
		String sId = null;
		String sLx = null;
		String sOrderNum = null;
		String sLx_order = null;
		String sFjName = null;
		String sFjPath = null;
		// String sFjShow = null;

		for (PubAttachment fj : fjList) {
			sId = fj.getId();
			sLx = fj.getLx();
			sOrderNum = fj.getOrderNum();
			sLx_order = "_" + sLx + "_" + sOrderNum;
			sFjName = fj.getFjName();
			sFjPath = fj.getFjPath();

			resMap.put("FJID" + sLx_order, sId);
			resMap.put("FJNAME" + sLx_order, sFjName);
			String sFileType = FileUtils.getFileType(sFjName);

			/*** 将附件信息从fastDFS下载到本地 start ***/
			sFjPath = getAttachmentFromFast(sFjPath, "upload");
			/*** 将附件信息从fastDFS下载到本地 end ***/

			// 判断图片是否存在
			if (!TdFileUtil.fileExists(sFjPath)) {
				resMap.put("FJSRC" + sLx_order, "");
				resMap.put("FJSRCS" + sLx_order, "");
			} else {
				// 判断相应的图片的格式
				if (null != sFileType && ImgAndString.checkWhetherPicture(sFileType)) {
					String[] urlArr;
					if (sFjPath.indexOf("/") != -1) {
						urlArr = sFjPath.split("/");
					} else {
						urlArr = sFjPath.split("\\\\");
					}
					String imgName = urlArr[urlArr.length - 1];
					String afterUrl = sFjPath.replace(imgName, "S_" + imgName);
					FileUtils.getSmallImage(sFjPath, afterUrl, 200);
					resMap.put("FJSRC" + sLx_order, ImgAndString.ImgtoStr(sFjPath));
					resMap.put("FJSRCS" + sLx_order, ImgAndString.ImgtoStr(afterUrl));
				}
			}
		}
		return resMap;
	}

	@Override
	public Map<String, Object> queryAttachMapNew(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<PubAttachment> fjList = this.attachmentDao.selectListByCondition(paramMap);
		String sId = null;
		String sLx = null;
		String sOrderNum = null;
		String sLx_order = null;
		String sFjName = null;
		String sFjPath = null;
		// String sFjShow = null;

		for (PubAttachment fj : fjList) {
			sId = fj.getId();
			sLx = fj.getLx();
			sOrderNum = fj.getOrderNum();
			sLx_order = "_" + sLx + "_" + sOrderNum;
			sFjName = fj.getFjName();
			sFjPath = fj.getFjPath();
			resMap.put("FJID" + sLx_order, sId);
			resMap.put("FJNAME" + sLx_order, sFjName);
			String sFileType = FileUtils.getFileType(sFjName);

			/*** 将附件信息从fastDFS下载到本地 start ***/
			sFjPath = getAttachmentFromFast(sFjPath, "upload");
			/*** 将附件信息从fastDFS下载到本地 end ***/

			// 判断相应的图片的格式
			if (null != sFileType && ImgAndString.checkWhetherPicture(sFileType)) {
				String[] urlArr;
				if (sFjPath.indexOf("/") != -1) {
					urlArr = sFjPath.split("/");
				} else {
					urlArr = sFjPath.split("\\\\");
				}
				String imgName = urlArr[urlArr.length - 1];
				String afterUrl = sFjPath.replace(imgName, "S_" + imgName);
				FileUtils.getSmallImage(sFjPath, afterUrl, 200);
				resMap.put("FJSRC" + sLx_order, ImgAndString.ImgtoStr(sFjPath));
				resMap.put("FJSRCS" + sLx_order, ImgAndString.ImgtoStr(afterUrl));
			}
		}
		return resMap;
	}

	/**
	 * 清除无效的附件
	 * 
	 * @param params
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> clearInvalidAttach(String sVersion, Map<String, Object> params) throws Exception {
		log.info("清除无效附件开始");
		log.info("传入的参数为：" + params);

		List<Map<String, Object>> allAttachmentList = new ArrayList<Map<String, Object>>();// 存所有的附件信息
		List<Map<String, Object>> midAttachmentList = new ArrayList<Map<String, Object>>();// 存附件信息的中介
		Map<String, Object> parammap = new HashMap<String, Object>();

		parammap.put("END_TIM", params.get("clearTim"));
		parammap.put("SFSX", "0");// 附件无效
		Integer clearDatLength = Integer.valueOf((String) params.get("clearDay"));// 清理多久的

		Integer maxNum = Integer.valueOf(String.valueOf(params.get("maxNum")));// 数据库允许查的最大数
		String countList = String.valueOf(params.get("countList"));// 查总数标志

		// 查询需要清理的附件的总条数
		List<Map<String, Object>> attachList = new ArrayList<Map<String, Object>>();
		Integer totalNum = 0;// 总条数

		params.put("countList", countList);
		attachList = attachmentDao.qryAttachList(params);

		params.put("countList", "");
		totalNum = Integer.valueOf(String.valueOf(attachList.get(0).get("total")));// 总条数

		// 查询附件信息(不用加判断是否需要累加)
		Integer time = totalNum / maxNum;
		if (totalNum > time * maxNum) {
			time = time + 1;
		}
		for (int i = 0; i < time; i++) {
			Integer start = maxNum * i + 1;
			Integer limit = maxNum;
			parammap.put("start", start);
			parammap.put("limit", limit);

			midAttachmentList = attachmentDao.qryAttachList(parammap);// 分段查询的结果

			allAttachmentList.addAll(midAttachmentList);// 添加到总结果里面
		}

		// try {
		// allAttachmentList = attachmentDao.selectListByCondition(parammap);
		// } catch (Exception e) {
		// log.info("查询无效附件列表失败："+e.toString());
		// throw new Exception();
		// }

		String sSaveType = TdCommUtil.getProperty("ATTACHMENT.SAVE.TYPE");// 文件存储方式

		if ("0".equals(sSaveType)) {// 本地服务器
			try {
				for (int i = 0; i < allAttachmentList.size(); i++) {
					String fjPath = String.valueOf(allAttachmentList.get(i).get("fjPath"));// 原图片路径
					String newFjPath = new String();// 缩略图路径
					String dirFileStr = new String();// 附件所在目录

					int lastNum = fjPath.lastIndexOf("\\");
					newFjPath = fjPath.substring(0, lastNum + 1) + "S_"
							+ fjPath.substring(lastNum + 1, fjPath.length());
					dirFileStr = fjPath.substring(0, lastNum);

					TdFileUtil.deleteFile(fjPath);// 删除文件
					TdFileUtil.deleteFile(newFjPath);// 删除文件(缩略图)

					// 判断目录是否为空，为空删除
					File dirFile = new File(dirFileStr);
					if (dirFile.isDirectory()) {
						String[] files = dirFile.list();
						if (files.length == 0) {
							TdFileUtil.deleteFile(dirFileStr);// 删除文件所在的空目录
						}
					}
				}

			} catch (Exception e) {
				log.info(e.toString());
				throw new Exception();
			}
		} else if ("1".equals(sSaveType)) {
			// 删除文件服务器附件
			String sWebRoot = TdCommUtil.getWebHome();
			String osName = System.getProperty("os.name");
			String fileDir = "";// 要删除的文件目录
			String currentDat = TdDateUtil.getDate() + "000000";// 当前日期
			String clearDatStart = TdDateUtil.calTime(currentDat, "d", -clearDatLength);// 两天前的所有文件（假定只有两天的数据）

			if (osName == null) {
				osName = "windows";
			}
			osName = osName.toLowerCase();

			if (osName.indexOf("windows") != -1) {
				if (sWebRoot.startsWith("/")) {
					sWebRoot = sWebRoot.substring(1, sWebRoot.length());
				}
			}

			// 删除附件文件夹
			fileDir = sWebRoot + File.separator + TdCommUtil.getProperty("ATTACHMENT.SAVE.DIR.UPLOAD");

			File dirFile = new File(fileDir);
			if (dirFile.isDirectory()) {
				String[] allFiles = dirFile.list();// 文件夹内所有文件
				for (int i = 1; i <= clearDatLength; i++) {
					String clearDat = TdDateUtil.calTime(clearDatStart, "d", -i);// 要清理的日期
					for (int j = 0; j < allFiles.length; j++) {
						if (allFiles[j].substring(0, 8).equals(clearDat.substring(0, 8))) {
							String clearDir = fileDir + File.separator + allFiles[j];
							TdFileUtil.deleteFile(clearDir);// 删除要清理的目录
						}
					}
				}
			}

			// fastDFS上删除文件
			for (int i = 0; i < allAttachmentList.size(); i++) {
				String fjPath = String.valueOf(allAttachmentList.get(i).get("FJPATH"));// 原图片路径
				try {
					TdCommUtil.fdfsDelete(fjPath);// 删除文件
				} catch (Exception e) {
					log.info("删除文件服务器上的文件出错，文件路径：" + fjPath);
					continue;
				}

			}
		}

		// 删除附件记录
		try {
			attachmentDao.delAttRecord(parammap);
		} catch (Exception e) {
			log.info("删除附件失败：" + e.toString());
			throw new Exception();
		}

		return null;
	}

	@Override
	public int updateHasSaveEntity(PubAttachment paramAttachment) throws Exception {
		try {
			/** 根据设置判断是否上传附件信息至fast **/
			String sFjPath = paramAttachment.getFjPath();
			sFjPath = saveAttachmentToFast(sFjPath);
			paramAttachment.setFjPath(sFjPath);
			/** 根据设置判断是否上传附件信息至fast **/
		} catch (Exception e) {
			log.error("{}", e);
		}
		return this.attachmentDao.updateHasSaveEntity(paramAttachment);
	}

	@Override
	public int insertEntity(PubAttachment paramAttachment) throws Exception {
		return this.attachmentDao.insertEntity(paramAttachment);
	}

	/**
	 * 判断是否从fastDFS中获取文件 如从fastDFS中获取文件，返回下载后的文件绝对地址 如从本地共享磁盘获取文件，则返回传入的附件地址
	 * 
	 * @param sFastId
	 *            fastDFS的id或本地路径
	 * @return
	 */
	public String saveAttachmentToFast(String sFjPath) throws Exception {
		String sSaveType = "0";
		try {
			sSaveType = TdCommUtil.getProperty("ATTACHMENT.SAVE.TYPE");
		} catch (Exception e) {
			sSaveType = "0";
		}

		// 如果是存储在fastDFS上，首先上传fastDFS，然后移除本地的上传文件
		if ("1".equals(sSaveType) && !sFjPath.startsWith("group1")) {
			/*** 将文件保存至fastDFS start ***/
			//log.info("将文件保存至fastDFS start", sFjPath);
			sFjPath = TdCommUtil.fdfsUpload(sFjPath);
			log.info("sFjPath:{}", sFjPath);
			/*** 将文件保存至fastDFS end ***/
		}
		return sFjPath;
	}

	/**
	 * 判断是否从fastDFS中获取文件 如从fastDFS中获取文件，返回下载后的文件绝对地址 如从本地共享磁盘获取文件，则返回传入的附件地址
	 * 
	 * @param sFastId
	 *            fastDFS的id或本地路径
	 * @return
	 */
	public String getAttachmentFromFast(String sFastId, String sRelativePath) throws Exception {
		// 传入为空 或 不是fastDFS信息
		if (StringUtils.isEmpty(sFastId) || !sFastId.startsWith("group1")) {
			return sFastId;
		}
		String sSaveType = "0";
		try {
			sSaveType = TdCommUtil.getProperty("ATTACHMENT.SAVE.TYPE");
		} catch (Exception e) {
			sSaveType = "0";
		}
		if ("1".equals(sSaveType)) {
			String sFileType = FileUtils.getFileType(sFastId);
			String sWebRoot = TdCommUtil.getWebHome();
			String sLocalFile = sWebRoot + "/" + sRelativePath + "/";
			sLocalFile += TdCommonUtil.getSeqId("DIR_ID") + "/";
			sLocalFile += TdCommonUtil.getDateTime() + "." + sFileType;

			// 判断文件是否存在 不存在则先创建文件夹
			File file = new File(sLocalFile);
			File fileDir = new File(file.getParent());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			TdCommUtil.fdfsDownload(sFastId, sLocalFile);
			return sLocalFile;
		} else {
			return sFastId;
		}
	}
	
	/**
	 * 判断是否从fastDFS中获取文件 如从fastDFS中获取文件，返回下载后的文件绝对地址 如从本地共享磁盘获取文件，则返回传入的附件地址
	 * 
	 * @param sFastId
	 *            fastDFS的id或本地路径
	 * @return
	 */
	public String getAttFromFastOfCert(String sFastId, String sRelativePath) throws Exception {
		// 传入为空 或 不是fastDFS信息
		if (StringUtils.isEmpty(sFastId) || !sFastId.startsWith("group1")) {
			return sFastId;
		}
		String sSaveType = "0";
		try {
			sSaveType = TdCommUtil.getProperty("ATTACHMENT.SAVE.TYPE");
		} catch (Exception e) {
			sSaveType = "0";
		}
		if ("1".equals(sSaveType)) {
			File tempFile = new File(sFastId.trim());
			String fileName = tempFile.getName();//证书名称
			
			String path = TdCommUtil.getProperty("certFilePath");
			String pfxFilPath = path + File.separator + fileName;

			// 判断文件是否存在 不存在则先创建文件夹
			File file = new File(pfxFilPath);
			File fileDir = new File(file.getParent());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			
			if (!file.exists()) {
				TdCommUtil.fdfsDownload(sFastId, pfxFilPath);
			}

			return pfxFilPath;
		} else {
			return sFastId;
		}
	}
	
	/**
	 * 查询excel附件信息
	 * 
	 * @param sVersion
	 * @param params
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 */
	@Override
	public Map<String, Object> qryExcelAttach(String sVersion, Map<String, Object> params) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 查询附件表
		List<PubAttachment> fjList = this.attachmentDao.selectListByCondition(params);

		String sFjPath = fjList.get(0).getFjPath();

		/*** 将附件信息从fastDFS下载到本地 start ***/
		sFjPath = getAttachmentFromFast(sFjPath, "download");
		/*** 将附件信息从fastDFS下载到本地 end ***/
		resMap.put("fjPath", sFjPath);

		return resMap;
	}
	
	@Override
	public Map<String,Object> updAttachById(Map<String, Object> paramMap) throws Exception{
		
		if(StringUtils.isEmpty(paramMap.get("attachkey").toString())){
			log.info("更新附件信息失败：附件主键为空");
			throw new Exception();
		}
		attachmentDao.updAttachById(paramMap);
		
		return null;
	}

	@Override
	public Map<String, Object> updateAttachStsById(String sVersion,
			Map<String, Object> params) throws Exception {
		log.info("更新附件信息参数params:"+params);
		if(StringUtils.isEmpty(params.get("ID").toString())){
			log.info("更新附件信息失败：附件主键为空");
			throw new Exception();
		}
		attachmentDao.updateAttachStsById(params);
		return null;
	}
}