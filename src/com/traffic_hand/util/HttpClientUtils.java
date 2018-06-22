package com.traffic_hand.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.traffic_hand.common.Constants;


public class HttpClientUtils {
	private static Logger logger = Logger.getLogger(HttpClientUtils.class);
	private HttpClientUtils(){
	}
	
	/**
	 * 执行HTTP GET请求
	 * @param url 请求地址
	 * @param headers 请求头
	 * @return 
	 */
	public static String doGet(String url, Map<String, String> headers){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		RequestConfig requestConfig = 
				RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(15000).build();
		httpGet.setConfig(requestConfig);
		//设置请求头
		if(headers != null && !headers.isEmpty()){
			for(Entry<String, String> entry : headers.entrySet()){
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			// 获取响应实体    
            int statusCode = response.getStatusLine().getStatusCode();
            if(Constants.HTTP_SUCCESS_STATUS_CODE == statusCode){
            	HttpEntity entity = response.getEntity();
            	if(entity != null){
            		return EntityUtils.toString(entity, Constants.CHARSET_UTF8);
            	}
            }
		} catch (Exception e) {
			logger.error("执行HTTP GET 请求失败！", e);
		} finally{
			try {
				if(response != null)
					response.close();
				
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 执行HTTP GET请求
	 * @param url
	 * @param headers
	 * @param timeout
	 * @return
	 */
	public static String doGet(String url, Map<String, String> headers,int timeout){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		RequestConfig requestConfig = 
				RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
		httpGet.setConfig(requestConfig);
		//设置请求头
		if(headers != null && !headers.isEmpty()){
			for(Entry<String, String> entry : headers.entrySet()){
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			// 获取响应实体    
            int statusCode = response.getStatusLine().getStatusCode();
            if(Constants.HTTP_SUCCESS_STATUS_CODE == statusCode){
            	HttpEntity entity = response.getEntity();
            	if(entity != null){
            		return EntityUtils.toString(entity, Constants.CHARSET_UTF8);
            	}
            }
		} catch (Exception e) {
			logger.error("执行HTTP GET 请求失败！", e);
		} finally{
			try {
				if(response != null)
					response.close();
				
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 执行HTTP GET请求  octet stream 数据流
	 * @param url 请求地址
	 * @return 
	 */
	public static byte[] doGet(String url){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		RequestConfig requestConfig = 
				RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).build();
		httpGet.setConfig(requestConfig);
		
		CloseableHttpResponse response = null;
		InputStream inputStream = null;
        byte[] bytes = null;
        
		try {
			response = httpclient.execute(httpGet);
			// 获取响应实体
            int statusCode = response.getStatusLine().getStatusCode();
            if(Constants.HTTP_SUCCESS_STATUS_CODE == statusCode){
            	HttpEntity entity = response.getEntity();
            	if(entity != null){
            		inputStream = entity.getContent();
                    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
                    byte[] buff = new byte[128];  
                    int rc = 0;  
                    while ((rc = inputStream.read(buff, 0, 128)) > 0) {  
                        swapStream.write(buff, 0, rc);  
                    }
                    bytes = swapStream.toByteArray();
            	}
            }
		} catch (Exception e) {
			logger.error("执行HTTP GET 请求失败！", e);
		} finally{
			try {
				if(response != null)
					response.close();
				
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
	
	/**
	 * 执行HTTP POST请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param headers 请求头
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> params, Map<String, String> headers){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		
		RequestConfig requestConfig = 
				RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(15000).build();
		httpPost.setConfig(requestConfig);
		//设置请求头
		if(headers != null && !headers.isEmpty()){
			for(Entry<String, String> entry : headers.entrySet()){
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse response = null;
		try {
			uefEntity = new UrlEncodedFormEntity(params, Constants.CHARSET_UTF8);
			httpPost.setEntity(uefEntity);
		
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if(Constants.HTTP_SUCCESS_STATUS_CODE == statusCode){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					return EntityUtils.toString(entity, Constants.CHARSET_UTF8);
				}
			}
		} catch (Exception e) {
			logger.error("执行HTTP POST 请求失败！", e);
		} finally{
			try {
				if(response != null)
					response.close();
				
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 执行HTTP POST请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param headers 请求头
	 * @return
	 */
	public static String doPost(String url, String params, Map<String, String> headers){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		
		RequestConfig requestConfig = 
				RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(15000).build();
		httpPost.setConfig(requestConfig);
		//设置请求头
		if(headers != null && !headers.isEmpty()){
			for(Entry<String, String> entry : headers.entrySet()){
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(params, Constants.CHARSET_UTF8);
			httpPost.setEntity(stringEntity);
		
			response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if(Constants.HTTP_SUCCESS_STATUS_CODE == statusCode){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					return EntityUtils.toString(entity, Constants.CHARSET_UTF8);
				}
			}
		} catch (Exception e) {
			logger.error("执行HTTP POST 请求失败！", e);
		} finally{
			try {
				if(response != null)
					response.close();
				
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
