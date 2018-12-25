package com.sims.common.util.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sims.common.util.BaseConstants;
import com.sims.common.util.http.HttpsRequestProxy;
/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：FileUtils.java  <br/>
 * Package Name：com.cloudwalk.common.util.file  <br/>
 * Description: 文件工具类  <br/>
 * @date: 2018年4月11日 下午4:37:43 
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class FileUtils {
	
	public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	private static final int BUF_SIZE = 8096;

	/**
	 * CheckFolder:检查文件夹
	 *
	 * @author:Jackson He Date: 2015年01月22日 下午20:53:44
	 * @param folder
	 *            目录
	 * @param isDelete
	 *            true:delete, false:create new folder
	 * @return true:success, false:failded
	 */
	public static boolean CheckFolder(String folder, boolean isDelete) {
		boolean bRet = false;

		File file = new File(folder);

		if (isDelete) {
			if (file.exists()) {
				delAllFile(folder); // 删除完里面所有内容

				file.delete(); // 删除空文件夹
			}
		} else {
			// 判断目标文件所在的目录是否存在
			if (!file.exists()) {
				bRet = file.mkdirs();
			}
		}

		return bRet;
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	private static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				CheckFolder(path + "/" + tempList[i], true);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 
	 * fileDownload:(文件下载). <br/>
	 *
	 * @author:lidaiyue Date: 2015年7月9日 上午11:30:53
	 * @param request
	 * @param filePath
	 *            文件的路径 根目录为resources/classes/
	 * @param downloadfileName
	 *            下载的文件名（注意这可以不是真实文件的名称）
	 * @return
	 * @throws IOException
	 * @since JDK 1.7
	 */
	public static ResponseEntity<byte[]> fileDownload(
			HttpServletRequest request, String filePath, String downloadfileName)
			throws IOException {
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		String path = rootPath + BaseConstants.DOWNLOAD_OR_UPLOAD_FILEPATH
				+ filePath;
		File file = new File(filterForXPath(path));
		if (file.exists()) {
			HttpHeaders headers = new HttpHeaders();
			String fileName = new String(downloadfileName.getBytes("UTF-8"),
					"iso-8859-1"); // 解决中文乱码
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(
					org.apache.commons.io.FileUtils.readFileToByteArray(file),
					headers, HttpStatus.CREATED);
		}
		return null;
	}

	/**
	 * 
	 * fileUpload:(文件上传). <br/>
	 *
	 * @author:lidaiyue Date: 2015年7月9日 下午1:17:19
	 * @param request
	 * @param filePath
	 *            文件的路径 根目录为resources/classes/
	 * @param upFileKey
	 *            上传文件的ID
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean fileUpload(HttpServletRequest request,
			String filePath, String fileUpKey) {
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 得到上传的文件
			MultipartFile mFile = multiRequest.getFile(fileNameFilter(fileUpKey));
			// 得到上传文件服务器的路径
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			String path = rootPath + BaseConstants.DOWNLOAD_OR_UPLOAD_FILEPATH;
			// 得到上传文件的文件的文件名
			String fileName = mFile.getOriginalFilename();
			inputStream = mFile.getInputStream();
			byte[] b = new byte[1048576];
			int length = inputStream.read(b);
			path += "\\" + filterForXPath(filePath) + "\\" + fileName;
			// 文件流写到服务器端
			outputStream = new FileOutputStream(path);
			outputStream.write(b, 0, length);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * delFile:(删除文件). <br/>
	 *
	 * @author:lidaiyue Date: 2015年7月9日 下午1:28:13
	 * @param filePath
	 * @return
	 * @since JDK 1.7
	 */
	public boolean delFile(String filePath) {
		File file = new File(filterForXPath(filePath));
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}


	/**
	 * 过滤文件名称的特殊字符
	 * @param fileName 文件名
	 * @return
	 */
	public static String fileNameFilter(String fileName){
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		return fileName.replace("/", "")
			     .replace("\\", "")
			     .replace("%00", "")
			     .replace("\0", "");
	}

	/**
	 * 过滤路径的特殊字符
	 * @param input
	 * @return
	 */
	public  static String filterForXPath(String input) {
		if (StringUtils.isBlank(input)) {
			return null;
		}
		return input.replace("../", "")
				.replace("..\\", "")
				.replace("%00", "")
				.replace("\0", "");
	}
	
	/**
	 * 通过本地文件路径获取文件二进制数据
	 * @return
	 */
	public static byte[] getFileDataByPath(String filePath) {
		FileInputStream fis = null;
		try {			
			File file = new File(filePath);
			if (file.exists()) {
				int size = (int) file.length();
				byte[] filedata = new byte[size];				
				fis = new FileInputStream(file);
				if (fis != null)
				fis.read(filedata, 0, size);
				return filedata;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}		
		return null;
	}
	
	/**
	 * 通过http获取文件二进制数据
	 * @return
	 */
	public static byte[] getFileDataByHttp(String fileurl) {	
		
		if(StringUtils.isBlank(fileurl)){
			return null;
		}
		
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedInputStream bis = null;
			HttpURLConnection httpUrl = null;
			URL url = null;
			byte[] buf = new byte[BUF_SIZE];
			int size = 0;
			
			url = new URL(fileurl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.setConnectTimeout(10000);
	
			// 连接指定的资源
			httpUrl.connect();
			// 获取网络输入流
			bis = new BufferedInputStream(httpUrl.getInputStream());
			
			// 保存文件
			while ((size = bis.read(buf)) != -1) {
				bos.write(buf, 0, size);
			}			
			bos.close();
			bis.close();
			httpUrl.disconnect();
			return bos.toByteArray();
		} catch(Exception e) {
			logger.error("",e);
		}
		return null;
	
	}
	
	/**
	 * 通过https获取文件二进制数据
	 * @return
	 */
	public static byte[] getFileDataByHttps(String fileurl) {
		
		if(StringUtils.isBlank(fileurl)){
			return null;
		}
		
		return HttpsRequestProxy.getData(fileurl, null, null);
	}

}
