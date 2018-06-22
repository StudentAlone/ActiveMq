package com.traffic_hand.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.traffic_hand.common.Constants;

/**
 * 
* <p>Title:PropertiesUtils </p>
* <p>Description: 配置文件处理类</p>
* 
* @author LiuSP
* @date 2017年7月13日
 */
public class PropertiesUtils {
	private static Logger logger = Logger.getLogger(PropertiesUtils.class);
	private PropertiesUtils(){
		loadData();
	}
	
	private String fileName = Constants.CONFIG_FILE_NAME;
	private static Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 根据key获取配置文件内容
	 * @param key
	 * @return
	 */
	public static String getConfigInfo(String key) {
		if(map.isEmpty()){
			new PropertiesUtils();
		}
		return map.get(key);
	}
	
	@SuppressWarnings("unchecked")
	private void loadData() {
		InputStream in = null;
		try {
			Properties props = new Properties();
//			String currentDir = System.getProperty("user.dir");
//			fileName = currentDir + File.separator + fileName;
			in = getClass().getResourceAsStream("/" + fileName);
			
			props.load(in);
			
			Enumeration<String> e = (Enumeration<String>) props.propertyNames();//得到配置文件的名字
	        while(e.hasMoreElements()) {
	              String key = e.nextElement();
	              String value = props.getProperty(key);
	              map.put(key, value);
	         }
		} catch (Exception e) {
			logger.error("读取配置文件出错,配置文件名称: " + fileName, e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
