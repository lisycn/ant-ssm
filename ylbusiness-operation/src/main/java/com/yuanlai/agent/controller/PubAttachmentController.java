package com.yuanlai.agent.controller;

import com.tangdi.def.utils.common.TdJsonUtil;
import com.yuanlai.agent.common.TdCommonUtil;
import com.yuanlai.agent.entity.PubAttachment;
import com.yuanlai.agent.service.IPubAttachmentService;
import com.yuanlai.agent.util.FileUtils;
import com.yuanlai.agent.util.ImgAndString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/attachment/")
public class PubAttachmentController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("pubAttachmentServiceImpl")
	private IPubAttachmentService attachmentService;
	@Value("#{operationConfig}")
	private Properties prop;

	/**
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("commonFileUpload")
	public void fileUploadHandler(@RequestParam(value = "file", required = false) CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info("文件上传请求：[{}]", file);
		if (request.getMethod().equalsIgnoreCase("options")) {
			return;
		}
		try {
			Map<String, String> reqMap = TdCommonUtil.getReqMap(request);
			log.info("文件上传请求参数：[{}]", reqMap);
			String sId = TdCommonUtil.getSeqId("FJ_ID");
			String sTableName = (String) reqMap.get("TABLENAME");
			String sLx = (String) reqMap.get("LX");
			String sOrderNum = (String) reqMap.get("ORDERNUM");
			String sFilePath = null;
			String sOldFileName = null;
			String sNewFileName = null;
			String sFileType = null;
			String sTransferFileType = null;
			String sFileDir = prop.getProperty("picPath") +"/";
            sFileDir += TdCommonUtil.getDateTime().substring(0, 8) + "/";
            log.info("文件上传 sFileDir：[{}]", sFileDir);
			sOldFileName = file.getOriginalFilename();
			sOldFileName = FileUtils.getCutFileName(sOldFileName);
			sFileType    = FileUtils.getFileType(sOldFileName);
			if(sFileType.toLowerCase().equals("gif")||sFileType.toLowerCase().equals("bmp")){
				sTransferFileType =  "jpg";
			}else{
				sTransferFileType = sFileType;
			}
			String  prefixPicture = TdCommonUtil.getDateTime();
			sNewFileName = prefixPicture + "." + sTransferFileType;
			sFilePath    = sFileDir + sNewFileName;

			PubAttachment attachment = new PubAttachment();
			attachment.setId(sId);
			attachment.setTableName(sTableName);
			attachment.setPkId("");
			attachment.setLx(sLx);
			attachment.setOrderNum(sOrderNum);
			attachment.setFjPath(sFilePath);
			attachment.setFjName(sOldFileName);
			attachment.setFjt(TdCommonUtil.getDateTime());
			attachment.setFjo("");
			attachment.setSfsx("0");

			log.info("文件上传 attachment：[{}]", attachment);

			this.attachmentService.saveFile(file, sFileDir, sNewFileName, attachment,sFileType, prefixPicture);

			String sFjShow = TdCommonUtil.getRelPath(sFilePath);
			if (null == sFjShow) {
				sFjShow = "";
			}

			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("FJNAME_" + sLx + "_" + sOrderNum, sOldFileName);
			retMap.put("FJID_"   + sLx + "_" + sOrderNum, sId);
			retMap.put("FJ_PATH", sFilePath);
			// 判断相应的图片的格式
			if (null != sFileType && ImgAndString.checkWhetherPicture(sFileType)) {
				String[] urlArr;
				if (sFilePath.indexOf("/") != -1) {
					urlArr = sFilePath.split("/");
				} else {
					urlArr = sFilePath.split("\\\\");
				}

				String imgName = urlArr[urlArr.length - 1];
				String afterUrl = sFilePath.replace(imgName, "S_" + imgName);
				FileUtils.getSmallImage(sFilePath, afterUrl, 200);
				retMap.put("FJSRC_"  + sLx + "_" + sOrderNum, ImgAndString.ImgtoStr(sFilePath));
				retMap.put("FJSRCS_" + sLx + "_" + sOrderNum, ImgAndString.ImgtoStr(afterUrl));
			}
			if (!"".equals(sFjShow)) {
				retMap.put("FJSHOW_" + sLx + "_" + sOrderNum, sFjShow);
			}
			log.info("文件上传信息处理结果:{}", retMap);
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(TdJsonUtil.jsonFromObject(retMap));
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			log.error("文件上传处理异常：{}", e);
		}
	}
}
