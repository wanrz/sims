package com.sims.common.util.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * HTTP请求代理类
 * 
 * @author 何春节
 * @version 1.0
 * @since 1.6
 *
 */
public class HttpRequestProxy {

	private static final Logger logger = Logger.getLogger(HttpRequestProxy.class);

	/**
	 * 连接超时
	 */
	private static int CONNECT_TIME_OUT = 3000;

	/**
	 * 读取数据超时
	 */
	private static int READ_TIME_OUT = 30000;

	/**
	 * 将参数组装成字符串
	 * 
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String buildQueryStr(Map<String, String> paramMap, String encoding)
			throws UnsupportedEncodingException {
		StringBuilder params = new StringBuilder("");

		if (paramMap != null && !paramMap.isEmpty()) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				if (StringUtils.hasText(params)) {
					params.append("&");
				}
				String val = entry.getValue();
				if (StringUtils.hasText(val)) {
					val = URLEncoder.encode(val, encoding);
				}
				params.append(entry.getKey()).append("=").append(val);
			}
		}

		return params.toString();
	}

	/**
	 * 将参数组装成字符串
	 * 
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
	 * @param reqUrl     HTTP请求URL
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
			if (conn != null) {
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setInstanceFollowRedirects(false);
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
				if (is != null && bs != null) {
					while ((len = is.read(bytes)) != -1) {
						bs.write(bytes, 0, len);
					}
					if (bs != null) {
						return new String(bs.toByteArray(), encoding);
					}
				}
			}
			return "";
		} catch (IOException e) {
			logger.error("发送POST请求异常，原因：", e);
		} finally {

			if (bs != null)
				IOUtils.closeQuietly(bs);
			if (is != null)
				IOUtils.closeQuietly(is);
			if (os != null)
				IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}

		return null;
	}

	/**
	 * 发送带参数的GET的HTTP请求
	 * 
	 * @param url
	 * @param paramMap
	 * @param encoding
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
			if (conn != null) {
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setConnectTimeout(CONNECT_TIME_OUT);
				conn.setReadTimeout(READ_TIME_OUT);

				is = conn.getInputStream();
				bs = new ByteArrayOutputStream();

				int len = 0;
				if (is != null && bs != null) {
					while ((len = is.read(bytes)) != -1) {
						bs.write(bytes, 0, len);
					}
				}
				return new String(bs.toByteArray(), encoding);
			}
			return "";
		} catch (IOException e) {
			logger.error("发送GET请求异常，原因：", e);
		} finally {

			if (bs != null)
				IOUtils.closeQuietly(bs);

			if (is != null)
				IOUtils.closeQuietly(is);

			if (conn != null) {
				conn.disconnect();
			}
		}

		return null;
	}

	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl     HTTP请求URL
	 * @param parameters 参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String postJson(String reqUrl, String json, String encoding) {
		return postStr(reqUrl, json, "json", encoding);
	}

	public static String postXml(String reqUrl, String xml, String encoding) {
		return postStr(reqUrl, xml, "xml", encoding);
	}

	/**
	 * 发送报文
	 * 
	 * @param reqUrl   请求url
	 * @param reqData  报文数据
	 * @param bodyType 报文类型 xml，json
	 * @param encoding 报文编码
	 * @return
	 */
	private static String postStr(String reqUrl, String reqData, String bodyType, String encoding) {

		HttpURLConnection conn = null;
		ByteArrayOutputStream bs = null;
		OutputStream os = null;
		InputStream is = null;

		byte[] bytes = new byte[1024];
		try {
			conn = (HttpURLConnection) new URL(reqUrl).openConnection();
			if (conn != null) {
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setInstanceFollowRedirects(false);
				conn.setConnectTimeout(CONNECT_TIME_OUT);
				conn.setReadTimeout(READ_TIME_OUT);
				conn.setRequestProperty("Content-Type", "application/" + bodyType);
				conn.connect();

				byte[] b = reqData.getBytes(encoding);
				if (b != null) {
					os = conn.getOutputStream();
					os.write(b, 0, b.length);
					os.flush();
				}

				is = conn.getInputStream();
				bs = new ByteArrayOutputStream();

				int len = 0;
				if (is != null) {
					while ((len = is.read(bytes)) != -1) {
						if (bs != null) {
							bs.write(bytes, 0, len);
						}
					}
				}

				return new String(bs.toByteArray(), encoding);
			}
			return "";
		} catch (Exception e) {
			logger.error("发送POST DATA请求异常，原因：", e);
		} finally {
			IOUtils.closeQuietly(bs);
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * 测试地址是否连通
	 * 
	 * @param url
	 * @return
	 */
	public static boolean checkHttpReachable(String url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			if (conn != null) {
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setInstanceFollowRedirects(false);
				conn.setConnectTimeout(5000);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.connect();
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * @author 李强
	 * @param reqUrl      下载路径
	 * @param downloadDir 下载存放目录
	 * @return 返回下载文件
	 */
	public static File downloadFile(String reqUrl, String downloadDir) {
		File file = null;
		try {
			// 统一资源
			URL url = new URL(reqUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();

			// 文件大小
			int fileLength = httpURLConnection.getContentLength();

			// 文件名
			String filePathUrl = httpURLConnection.getURL().getFile();
			String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

			System.out.println("file length---->" + fileLength);

			URLConnection con = url.openConnection();

			BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

			String path = downloadDir + File.separatorChar + fileFullName;
			file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			int size = 0;
			int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				len += size;
				out.write(buf, 0, size);
				// 打印下载百分比
				System.out.println("下载了-------> " + len * 100 / fileLength + "%\n");
			}
			bin.close();
			out.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return file;
	}

	/**
	 * @author 李强
	 * @param reqUrl
	 * @param files
	 * @param parameters
	 * @return
	 */
	public static String uploadFile(String reqUrl, Map<String, File> files,
			Map<String, String> parameters) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "---------------------------7e0dd540448";
		String response = "";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 发送post请求需要下面两行
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// 设置请求参数
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			// 获取请求内容输出流
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			// 开始写表单格式内容
			// 写参数
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; name=\"");
				ds.write(key.getBytes());
				ds.writeBytes("\"" + end);
				ds.writeBytes(end);
				ds.write((parameters.get(key)==null?"":parameters.get(key)).getBytes());
				ds.writeBytes(end);
			}
			Set<String> fileKeys = files.keySet();
			for (String key : fileKeys) {
				// 写文件
				File uploadFile = files.get(key);
				if(uploadFile==null) {
					continue;
				}
				String fileName = uploadFile.getName();
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; " + "name=\""+key+"\"; " + "filename=\"");
				// 防止中文乱码
				ds.write(fileName.getBytes());
				ds.writeBytes("\"" + end);
				ds.writeBytes("Content-Type: application/octet-stream" + end);
				ds.writeBytes(end);
				// 根据路径读取文件
				FileInputStream fis = new FileInputStream(uploadFile);
				byte[] buffer = new byte[1024];
				int length = -1;
				while ((length = fis.read(buffer)) != -1) {
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				fis.close();
			}
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			ds.writeBytes(end);
			ds.flush();
			try {
				// 获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					s += temp;
				}
				response = s;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("No response get!!!");
			}
			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}

	public static void main(String[] args) {
		// 下载文件测试
		File f = downloadFile("http://www.houhong.net/images/close.gif", "D://");
		System.out.println(f.getAbsolutePath());
	}
}
