/**
 * Project Name:cloudwalk-common
 * File Name:RequestUtil.java
 * Package Name:cn.cloudwalk.common.net
 * Date:2016年3月19日下午3:28:33
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd  All Rights Reserved.
 *
*/

package com.sims.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ClassName:RequestUtil <br/>
 * Description: Request工具类. <br/>
 * Date: 2016年3月19日 下午3:28:33 <br/>
 *
 * @author 陈腾
 * @version 1.0.0
 * @since JDK 1.7
 */
public class RequestUtil {
	
	private RequestUtil(){
	}

  private static final Logger logger = Logger.getLogger(RequestUtil.class);
  /**
   * 读取requestBody
   * @param request
   * @return
   */
  public static String getRequestBody(HttpServletRequest request) {
		String str, wholeStr = "";
		try {
			BufferedReader br = request.getReader();
			while((str = br.readLine()) != null){
			wholeStr += str;
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return wholeStr;
	}
  /**
   * getRequestData:解析http请求参数. <br/>
   *
   * @param request HttpServletRequest请求
   * @return 返回字符串
   * @since JDK 1.7
   */
  public static String getRequestData(HttpServletRequest request) {
    StringBuilder buffer = new StringBuilder();
    try {
      InputStream inputStream = request.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
    } catch (IOException e) {
      logger.info("解析http请求参数出错");
      logger.info(e);
    }
    logger.info("接收到的参数 ===== >>> " + buffer.toString());
    return buffer.toString();
  }

  /**
   * 判断是否是ajax
   * 
   * @param req
   * @return
   */
  public static boolean isAjax(HttpServletRequest req) {
    String requestType = req.getHeader("X-Requested-With");
    return StringUtils.isNotBlank(requestType) && "XMLHttpRequest".equalsIgnoreCase(requestType);
  }
}