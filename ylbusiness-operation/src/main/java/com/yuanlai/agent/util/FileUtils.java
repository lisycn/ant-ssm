package com.yuanlai.agent.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 
	 * @param sFolder
	 * @throws Exception
	 */
	public static void createFolder(String sFolder) throws Exception {
		File fDir = new File(sFolder);
		if (((fDir.exists()) && (fDir.isDirectory())) || (fDir.mkdirs()))
			return;
		throw new IOException("Can't create Folder" + sFolder);
	}

	public static File createFile(String sFile) throws IOException {
		File f = new File(sFile);
		if ((!f.exists()) && (!f.createNewFile())) {
			throw new IOException("Can't create file" + sFile);
		}

		return f;
	}

	/**
	 * 
	 * @param dirName
	 * @return
	 */
	public static String getFileName(String dirName) {
		File dir = new File(dirName);
		String fileName = null;
		if (dir.isDirectory()) {
			String[] fileList = dir.list();
			if ((fileList != null) && (fileList.length > 0)) {
				fileName = fileList[0];
			}
		}
		return fileName;
	}

	/**
	 * 
	 * @param folder
	 */
	public static void deleteFolder(File folder) {
		String[] childs = folder.list();
		if ((childs == null) || (childs.length <= 0)) {
			folder.delete();
		} else {
			for (int i = 0; i < childs.length; ++i) {
				String childName = childs[i];
				String childPath = folder.getPath() + File.separator + childName;
				File filePath = new File(childPath);
				if ((filePath.exists()) && (filePath.isFile()))
					filePath.delete();
				else if ((filePath.exists()) && (filePath.isDirectory())) {
					deleteFolder(filePath);
				}
			}
			folder.delete();
		}
	}

	/**
	 * 
	 * @param folder
	 */
	public static void deleteChildFolder(File folder) {
		String[] childs = folder.list();
		if ((childs == null) || (childs.length <= 0)) {
			return;
		}

		for (int i = 0; i < childs.length; ++i) {
			String childName = childs[i];
			String childPath = folder.getPath() + File.separator + childName;
			File filePath = new File(childPath);
			if ((filePath.exists()) && (filePath.isFile()))
				filePath.delete();
			else if ((filePath.exists()) && (filePath.isDirectory()))
				deleteFolder(filePath);
		}
	}

	/**
	 * @param sFile
	 * @return
	 */
	public static String getFileType(String sFile) {
		String sRes = "";
		if (sFile.indexOf(".") != -1) {
			String[] arr = sFile.split("\\.");
			if (arr.length != 0) {
				sRes = arr[(arr.length - 1)];
			}
		}
		return sRes;
	}

	/**
	 * @param sFile
	 * @return
	 */
	public static String getCutFileName(String sFileName) {
		String sFileType = getFileType(sFileName);
		String sRes = sFileName.replaceAll("." + sFileType, "");
		int iCutLen = 20;
		if (sRes.length() >= iCutLen) {
			sRes = sRes.substring(0, iCutLen);
		}
		return sRes + "." + sFileType;
	}

	/**
	 * 生成缩略图
	 * 
	 * @param origUrl
	 * @param afterUrl
	 * @param nw
	 */
	public static void getSmallImage(String origUrl, String afterUrl, int nw) {
		try {
			File fi = new File(origUrl); // 大图文件
			File fo = new File(afterUrl); // 将要转换出的小图文件

			/*
			 * AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D 坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系
			 * 列平移、缩放、翻转、旋转和剪切来构造仿射变换。
			 */
			AffineTransform transform = new AffineTransform();
			BufferedImage bis = ImageIO.read(fi); // 读取图片
			int w = bis.getWidth();
			int h = bis.getHeight();
			// double scale = (double)w/h;
			int nh = (nw * h) / w;
			double sx = (double) nw / w;
			double sy = (double) nh / h;
			transform.setToScale(sx, sy); // setToScale(double sx, double sy)
											// 将此变换设置为缩放变换。
			System.out.println(w + " " + h);
			/*
			 * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 Raster 中 2D
			 * 坐标的线性映射。所使用的插值类型由构造方法通过 一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。 如果在构造方法中指定了
			 * RenderingHints 对象，则使用插值提示和呈现 的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色 呈现提示和抖动提示。
			 * 注意，务必要满足以下约束：源图像与目标图像 必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中 的 band 数。
			 */
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
			/*
			 * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像， 对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存 储的
			 * Blue、Green 和 Red 三种颜色。
			 */
			ato.filter(bis, bid);
			ImageIO.write(bid, "jpeg", fo);
		} catch (Exception e) {
			log.error("{}", e);
		}
	}

	/**
	 * 压缩文件
	 * 
	 * @param listFiles
	 *            所有待压缩文件列表
	 */
	public static String zip(List<String> listFile, String targetZipPath) {
		try {
			byte[] buffer = new byte[1024];
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetZipPath));
			for (String sFileName : listFile) {
				File file = new File(sFileName);
				FileInputStream fis = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				int len = 0;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
		} catch (Exception e) {
			log.error("{}", e);
		}
		return targetZipPath;
	}

	public static File doZip(String sourceDir, String zipFilePath) throws IOException {
		File file = new File(sourceDir);
		File zipFile = new File(zipFilePath);
		ZipOutputStream zos = null;
		try {
			// 创建写出流操作
			OutputStream os = new FileOutputStream(zipFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);

			String basePath = null;

			// 获取目录
			if (file.isDirectory()) {
				basePath = file.getPath();
			} else {
				basePath = file.getParent();
			}

			zipFile(file, basePath, zos);
		} finally {
			if (zos != null) {
				zos.closeEntry();
				zos.close();
			}
		}

		return zipFile;
	}

	/**
	 * @param source
	 *            源文件
	 * @param basePath
	 * @param zos
	 */
	private static void zipFile(File source, String basePath, ZipOutputStream zos) throws IOException {
		File[] files = null;
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}

		InputStream is = null;
		String pathName;
		byte[] buf = new byte[1024];
		int length = 0;
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					pathName = file.getPath().substring(basePath.length() + 1) + "/";
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} else {
					pathName = file.getPath().substring(basePath.length() + 1);
					is = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					while ((length = bis.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
				}
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("F:/1.txt");
		list.add("F:/2.txt");
		list.add("F:/3.txt");
		list.add("F:/4.txt");

		File file = new File("F:/temp/73349978.jpg");
		System.out.println(file.getParent());
	}
}
