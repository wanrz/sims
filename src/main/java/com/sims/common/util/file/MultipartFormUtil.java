package com.sims.common.util.file;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
/**
 * https://blog.csdn.net/johnson_moon/article/details/51874007
 * @author John.Lee
 *
 */
public class MultipartFormUtil {
	/**
	 * @author 李强
	 * @method singleFileUploadWithParameters
	 * @description 集上传单个文件与传递参数于一体的方法
	 * @param actionURL 上传文件的URL地址包括URL
     * @param fileId 文件属性名
	 * @param uploadFile 上传文件
	 * @param parameters 跟文件一起传输的参数(HashMap)
	 * @return String("" if no response get)
	 * @attention 上传文件name为file(服务器解析)
	 * */
	public static String singleFileUploadWithParameters(String actionURL,String fileId, File uploadFile, HashMap<String, String> parameters){
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "---------------------------7e0dd540448";
		String response = "";
		try{
			URL url = new URL(actionURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			//发送post请求需要下面两行
			connection.setDoInput(true);
			connection.setDoOutput(true);
			//设置请求参数
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			//获取请求内容输出流                        
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			String fileName = uploadFile.getName();
			//开始写表单格式内容
			//写参数
			Set<String> keys = parameters.keySet();
			for(String key : keys){
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; name=\"");
				ds.write(key.getBytes());
				ds.writeBytes("\"" + end);
				ds.writeBytes(end);
				ds.write(parameters.get(key).getBytes());
				ds.writeBytes(end);
			}
			//写文件
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\"; " + "filename=\"");
			//防止中文乱码
			ds.write(fileName.getBytes());
			ds.writeBytes("\"" + end);
			ds.writeBytes("Content-Type: application/octet-stream" + end);
			ds.writeBytes(end);
			//根据路径读取文件
			FileInputStream fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int length = -1;
			while((length = fis.read(buffer)) != -1){
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			fis.close();
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			ds.writeBytes(end);
			ds.flush();
			try{
				//获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while((temp = reader.readLine()) != null){
					s += temp;
				}
				response = s;
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
				System.out.println("No response get!!!");
			}
			ds.close();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}
}
