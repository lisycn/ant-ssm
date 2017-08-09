package com.yuanlai.agent.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载中心参数格式化
 * @version 1.0.0,2016年5月18日
 * @author yaorp
 */
public class DownloadUtil {
    private static final Logger log = LoggerFactory.getLogger(DownloadUtil.class);

    /**
     * 把从下载中心表中取出的数据格式化
     * @param str
     * @return Map<String,Object>
     */
    public static Map<String, Object> setExportParam(String str) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (!str.isEmpty()) {
            String[] li = str.split("####");
            for (int i = 0; i < li.length; ++i) {
                String[] arr2 = li[i].split("=");
                if (arr2.length == 1) {
                    returnMap.put(arr2[0], "");
                } else if (arr2.length == 2) {
                    returnMap.put(arr2[0].toString(), arr2[1]);
                }
            }
        }
        return returnMap;
    }

    /**
     * 把要存入下载中心表中的数据格式化
     * @param map
     * @return String
     */
    public static String getExportParam(Map<String, Object> map) {
        String returnStr = "";
        for (String key : map.keySet()) {
            if (null == map.get(key)) {
                map.put(key, "");
            }
            returnStr = returnStr + key + "=" + map.get(key) + "####";
        }
        int num = returnStr.lastIndexOf("####");

        returnStr = returnStr.substring(0, num);
        return returnStr;
    }

    /**
     * 把文件转成流的形式输出
     * @param filePath
     * @param response
     *            void
     */
    public static void getStreamFile(String filePath, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            // 取得文件名。
            String filename = file.getName();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("multipart/form-data");

            response.addHeader("Access-Control-Allow-Origin", "*");// 允许哪些url可以跨域请求到本域
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");// 允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
            response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,accept");// 允许哪些请求头可以跨域

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.error("{}", ex);
        }
    }

    /**
     * 把文件转成流的形式输出
     * @param filePath
     * @param response
     *            void
     */
    public static void getStreamFile(Map<String, Object> resparams, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            String filePath = (String)resparams.get("fjPath");
            File file = new File(filePath);
            // 取得文件名。
            String filename = (String) resparams.get("fileName");

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("multipart/form-data");

            response.addHeader("Access-Control-Allow-Origin", "*");// 允许哪些url可以跨域请求到本域
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");// 允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
            response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,accept");// 允许哪些请求头可以跨域

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.error("{}", ex);
        }
    }
}
