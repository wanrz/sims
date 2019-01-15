package com.sims.common.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.sims.common.JsonResult;
import com.sims.common.base.model.PageInfo;
import com.sims.common.exception.BaseServiceException;
import com.sims.common.exception.CheckException;
import com.sims.common.exception.ServiceException;
import com.sims.common.util.ObjectUtils;
import com.sims.common.util.RequestUtil;
import com.sims.model.User;

public class BaseController {
	
	protected final Logger log = Logger.getLogger(this.getClass());

	/** 基于@ExceptionHandler异常处理 
	 * @throws Exception */
	@ExceptionHandler
	public void exp(HttpServletRequest request, Exception ex, HttpServletResponse response) {
		this.log.error(ex);
		try {
			request.setAttribute("ex", ex);
			if (RequestUtil.isAjax(request)) {
				JsonResult result = new JsonResult();
				result.setSuccess(false);
				if (ex instanceof BaseServiceException || ex instanceof CheckException
						|| ex instanceof ServiceException) {
					result.setMessage(ex.getMessage());
				} else {
					result.setMessage("出错了，请联系系统管理员！");
				}

				String contentType = "application/json";
				response.setContentType(contentType);
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.print(JSON.toJSONString(result));
				writer.flush();
				writer.close();
			} else {
				// 转发到错误页面
				request.getRequestDispatcher("error").forward(request, response);
			}
		} catch (Exception e) {
			this.log.error(e);
		}
	}

	/**
	 * <p>
	 * getPrintWriter:将对象传到前台 <br/>
	 * 适用条件：AJAX请求<br/>
	 * 执行流程：AJAX调用的方法里调用该方法<br/>
	 * 使用方法：传递response和object参数<br/>
	 * 注意事项:只有需要向前台传递参数的方法里才可以调用该方法<br/>
	 * </p>
	 *
	 * @author:焦少平 Date: 2014年11月9日 下午7:11:05
	 * @param response
	 *            响应
	 * @param object
	 *            对象
	 * @since JDK 1.7
	 */
	public void getPrintWriter(HttpServletResponse response, Object object) {
		PrintWriter out = null;
		try {
			// 得到输出流
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();

			// 输出对象
			out.print(object);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (null != out) {
				// 刷新流
				out.flush();
				// 关闭流
				out.close();
			}
		}
	}
	
	/**
	 * 对上面的方法进行从命名
	 * @param response
	 * @param object
	 */
	public void writer(HttpServletResponse response, Object object) {
		this.getPrintWriter(response, object);
	}

	/*
	 * 返回空记录集 Author: Jackson He
	 */
	public void responseEmptyRecord(HttpServletResponse response,
			String otherRowsName, String message) {
		StringBuilder jsonResult = new StringBuilder();
		if (message != null && message.length() > 0) {
			jsonResult.append("{ \"rows\":[], \"total\":0, \"message\": \""
					+ message + "\" ");
		} else {
			jsonResult.append("{ \"rows\":[], \"total\":0 ");
		}

		if (otherRowsName != null && otherRowsName.length() > 0) {
			jsonResult.append(", \" " + otherRowsName + "\":[]");
		}

		jsonResult.append(" }");

		getPrintWriter(response, jsonResult.toString());
	}

	/*
	 * 返回操作结果 Author: Jackson He
	 */
	public void responseOperateResult(HttpServletResponse response,
			Integer result, String message) {
		StringBuilder jsonResult = new StringBuilder();
		if (message != null && message.length() > 0) {
			jsonResult.append("{ \"value\":" + result.toString()
					+ ",  \"message\": \"" + message + "\" }");
		} else {
			jsonResult.append("{ \"value\":" + result.toString() + "}");
		}

		getPrintWriter(response, jsonResult.toString());
	}
	
	

	/**
	 *
	 * setPageInfo:设置分页的数据. <br/>
	 *
	 * @author:朱云飞 Date: 2015年8月19日 下午2:14:10
	 * @param request
	 * @param pageInfo
	 * @since JDK 1.7
	 */
	public void setPageInfo(HttpServletRequest request, PageInfo pageInfo) {
		if (pageInfo == null) {
			pageInfo = new PageInfo();
		}
		int page = ObjectUtils.objToInt(request.getParameter("page"), 1);
		int rows = ObjectUtils.objToInt(request.getParameter("rows"), 20);
		pageInfo.setPageSize(rows);
		pageInfo.setCurrentPage(page);
	}
	
	/**
	 * getCurrentUser:获取系统当前登录用户信息 <br/>
	 * 适用条件：获取当前用户信息<br/>
	 * 执行流程：类调用<br/>
	 * 适用条件：获取当前用户信息<br/>
	 *
	 * @param request http请求
	 * @return User 用户信息
	 */

	public User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("curruser");
	}

	/**
	 * setCurrentUser:将当前登录用户信息放入session中 <br/>
	 * 适用条件：将当前用户信息放入session中<br/>
	 * 执行流程：类调用<br/>
	 * 适用条件：存入当前用户信息<br/>
	 *
	 * @param request
	 *            http请求
	 * @return User 用户信息
	 */
	public void setCurrentUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute("curruser", user);
	}
	
	/**
	 * 读取session的属性值
	 * @param request
	 * @param attrName
	 * @return
	 */
	public String getSessionAttr(HttpServletRequest request,String attrName) {
		HttpSession session = request.getSession();
		String attrValue = (String)session.getAttribute(attrName);
		return attrValue;
	}
	
	/**
	 * 从请求对象中获取文件对象数组
	 */
	protected List<MultipartFile> getImgMultipartFileArray(HttpServletRequest request) {
		
		MultipartHttpServletRequest multiRequest = this.getMHttpServletRequest(request);
		if (multiRequest == null) {
			return null;
		}
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		Iterator<String> iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			
			MultipartFile file = multiRequest.getFile(iter.next());
			if (file != null) {
				list.add(file);
			}
		}
		
		return list;
	}
	
	/**
	 * 获取多部分信息
	 * @param request
	 * @return
	 */
	protected MultipartHttpServletRequest getMHttpServletRequest(HttpServletRequest request) {
		CommonsMultipartResolver mrt = this.getMultipartResolver(request);
		// 判断 request 是否有文件上传,即多部分请求
		if (!mrt.isMultipart(request)) {
			return null;
		}
		
		return (MultipartHttpServletRequest) request;
	}
	
	
	/**
	 * 获取请求多部分Resolver
	 * @param request
	 * @return
	 */
	private CommonsMultipartResolver getMultipartResolver(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		return new CommonsMultipartResolver(context);
	}
	
    /**
     * 转发请求参数
     *
     * @param request
     * @param model
     */
	protected void forwardRequestParam(HttpServletRequest request, ModelMap model){
		Map<String, String[]> map = request.getParameterMap();
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					String[] values = (String[]) value;
					if (values.length == 1) {
						model.addAttribute(entry.getKey(), values[0]);
					}
				}
			}
		}
	}

}
