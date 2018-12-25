package com.sims.common.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * HTTP请求代理类
 * 
 * @author 何春节
 * @version 1.0
 * @since 1.6
 */
public class HttpRequestProxy {
	private static final Logger LOGGER = Logger.getLogger(HttpRequestProxy.class);
	
	/**
     * 连接超时
     */
	private static final int CONNECT_TIME_OUT = 60 * 1000;
	
	/**
     * 读取数据超时
     */
	private static final int READ_TIME_OUT = 60 * 1000;
	
	/**
	 * 将参数组装成字符串
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static String buildQueryStr(Map<String, String> paramMap, String encoding) 
			throws UnsupportedEncodingException {
		StringBuilder params = new StringBuilder("");
		
		if (paramMap != null && !paramMap.isEmpty()) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				if (!StringUtils.hasText(entry.getValue())) {
					continue;
				}
				if (StringUtils.hasText(params)) {
					params.append("&");
				}
				
				String val = URLEncoder.encode(entry.getValue(), encoding);
				params.append(entry.getKey()).append("=").append(val);
			}
		}
		
		return params.toString();
	}
	
	/**
	 * 将参数组装成字符串
	 * @param paramMap
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] buildParamter(Map<String, String> paramMap, String encoding) 
			throws UnsupportedEncodingException {
		return buildQueryStr(paramMap, encoding).getBytes(encoding);
	}
	
	/**
     * <pre>
     * 发送带参数的POST的HTTP请求
     * </pre>
     * 
     * @param reqUrl HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
    public static String post(String reqUrl, Map<String, String> parameters, String encoding) {
    	
    	HttpURLConnection conn = null;
		ByteArrayOutputStream bs = null;
		OutputStream os = null;
		InputStream is = null;

		byte[] bytes = new byte[1024];
    	try {   
            conn = (HttpURLConnection) new URL(reqUrl).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
               
            byte[] b = buildParamter(parameters, encoding);
            if (b != null) {
            	os = conn.getOutputStream();
            	os.write(b, 0, b.length);
            	os.flush();
            }
            is = conn.getInputStream();
			bs = new ByteArrayOutputStream();
			int len = 0;
			while ((len = is.read(bytes)) != -1) {
				bs.write(bytes, 0, len);
			}
			
			return new String(bs.toByteArray(), encoding);
        }  catch (Exception e) {   
        	LOGGER.error("发送POST请求异常，原因：" + e.getMessage(), e);
        } finally {
        	if (bs != null) {
				try {
					bs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
        	if (os != null) {
        		try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        	if (conn != null) {
        		conn.disconnect();
        	}
        }
    	
    	return null;
    }
	
	/**
	 * 发送带参数的GET的HTTP请求
	 * 
	 * @param reqUrl
	 * @param parameters
	 * @param recvEncoding
	 * @return
	 */
    public static String get(String url, Map<String, String> paramMap, String encoding) {
        
    	HttpURLConnection conn = null;
    	InputStream is = null;
		ByteArrayOutputStream bs = null;

		byte[] bytes = new byte[1024];
        try {
        	
        	if (paramMap != null && !paramMap.isEmpty()) {
				if (url.indexOf('?') == -1) {
					url += "?" + buildQueryStr(paramMap, encoding);
				} else {
					url += "&" + buildQueryStr(paramMap, encoding);
				}
			}
        	
        	conn = (HttpURLConnection) new URL(url).openConnection();
        	conn.setRequestMethod("GET");
        	conn.setDoInput(true);
        	conn.setDoOutput(true);
        	conn.setUseCaches(false);
        	conn.setConnectTimeout(CONNECT_TIME_OUT);
        	conn.setReadTimeout(READ_TIME_OUT);
        	
        	is = conn.getInputStream();
			bs = new ByteArrayOutputStream();
			
			int len = 0;
			while ((len = is.read(bytes)) != -1) {
				bs.write(bytes, 0, len);
			}
			
			return new String(bs.toByteArray(), encoding);
        } catch (IOException e) {
        	LOGGER.error("发送GET请求异常，原因：" + e.getMessage(), e);
        } finally {
        	
        	if (bs != null) {
        		try {
					bs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        	if (is != null) {
        		try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
            if (conn != null) {
            	conn.disconnect();
            }
        }
        
        return null;
    }
}
