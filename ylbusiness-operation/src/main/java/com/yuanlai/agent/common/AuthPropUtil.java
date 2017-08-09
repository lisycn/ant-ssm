package com.yuanlai.agent.common;

import java.io.*;
import java.util.Properties;

/**
 * 暂未用到
 * @author Administrator
 *
 */
public class AuthPropUtil {
	private static Properties props;
    static{
    	props = new Properties();
    	String url = AuthPropUtil.class.getClassLoader().getResource("/").getPath().replaceAll("%20", "");
		//截取字符串得到WEB-INF路径然后拼接
		String file = url.substring(0, url.indexOf("WEB-INF")) +"WEB-INF/classes/conf/message/message_zh_CN.properties"; 
		InputStream in;
		try {
			in = new BufferedInputStream (new FileInputStream(file));
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 获取key对应的value
     * @param key 错误码
     * @return
     */
    public static String getValue(String key){
    	String val = props.getProperty(key);
    	if(val == null){
    		return "";
    	}
    	return val;
    }
    /**
     * 替换key对应的value中的#
     * @param key 错误码
     * @param locationStr 位置
     * @return
     */
    public static String getValue(String key,String... locationStr){
    	String val = props.getProperty(key);
    	if(val == null){
    		return "";
    	}
    	for(String str : locationStr){
    		val = val.replaceFirst(AuthConstants.COMMON_HASHTAG, str);
    	}
    	return val;
    }
}
