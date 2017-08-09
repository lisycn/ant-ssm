package com.yuanlai.agent.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class ImgAndString {

	private static final Logger log = LoggerFactory.getLogger(ImgAndString.class);
	static BASE64Encoder encoder = new BASE64Encoder();
	static BASE64Decoder decoder = new BASE64Decoder();
	public static String str = "";

	/**
	 * 图片转换字符串 path:绝对路径
	 * 
	 * @return
	 */
	public static String ImgtoStr(String absolutePath) {
		String aAAString = "";
		try {
			File f = new File(absolutePath);
//			BufferedImage bi = ImageIO.read(f);
			// JAVA处理不正确处理图片ICC信息蒙上红色的问题 放弃ImageIO.read();
			Image src=Toolkit.getDefaultToolkit().getImage(f.getPath());  
			BufferedImage bi = BufferedImageBuilder.toBufferedImage(src);//Image to BufferedImage  
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			aAAString = encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			log.error("{}", e);
		}
		return aAAString;
	}

	/**
	 * 将字符串转为图片
	 * @param imgStr
	 * @return
	 */
	public static int base64StringToImag(String Base64String, String Path, String fileName) {
		int bl = sureDirs(Path);
		if (bl != 0) {
			return bl;
		}
		String fileFullPath = Path + "/" + fileName;
		try {
			byte[] bytes1 = decoder.decodeBuffer(Base64String);
//			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
//			BufferedImage bi1 = ImageIO.read(bais);
			// JAVA处理不正确处理图片ICC信息蒙上红色的问题 放弃ImageIO.read();
			Image imageTookit = Toolkit.getDefaultToolkit().createImage(bytes1);  
			BufferedImage bi1 = BufferedImageBuilder.toBufferedImage(imageTookit);  
			File w2 = new File(fileFullPath);
			ImageIO.write(bi1, "jpg", w2);
			return 0;
		} catch (IOException e) {
			log.error("{}", e);
		}
		return 1;
	}

	public static int sureDirs(String path) {
		File fileDirs = new File(path);
		if (fileDirs.exists()) {
			if (!fileDirs.isDirectory()) {
				if (fileDirs.delete()) {
					if (fileDirs.mkdirs()) {
						return 0;
					}
					log.info("创建目录失败");
					return 2;
				}
				log.info("删除同名文件夹失败");
				return 1;
			}
			return 0;
		}
		if (fileDirs.mkdirs()) {
			return 0;
		}
		log.info("创建目录失败");
		return 2;
	}

	/**
	 * 判断是否符合规定的几种图片格式
	 * @param suffix
	 * @return
	 */
	public static boolean checkWhetherPicture(String suffix) {
		suffix = suffix.toLowerCase();
		if ("bmp".equals(suffix) || "gif".equals(suffix) || "jpeg".equals(suffix) || "png".equals(suffix)
				|| "jpg".equals(suffix)) {
			return true;
		}
		return false;
	}
}
