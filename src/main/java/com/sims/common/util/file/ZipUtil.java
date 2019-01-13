/**
 * Project Name:cloudwalk-common
 * File Name:ZipUtil.java
 * Package Name:cn.cloudwalk.common.file.zip
 * Date:2016年8月12日上午10:11:30
 * Copyright @ 2010-2016 重庆中科云丛科技有限公司  All Rights Reserved.
 *
*/

package com.sims.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:ZipUtil <br/>
 * Description: 提供压缩和解压等常用操作. <br/>
 * Date: 2016年8月12日 上午10:11:30 <br/>
 * 
 * @author 李强
 * @version
 * @since JDK 1.7
 * @see
 */
public class ZipUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);
	
	private ZipUtil() {
	}

	static final int BUFFER = 2048;

	/**
	 * 
	 * zip:压缩文件夹. <br/>
	 *
	 * @author:李强 Date: 2016年8月12日 上午10:21:54
	 * @param folder
	 * @param zipFile
	 * @since JDK 1.7
	 */
	public static void zip(File folder, File zipFile) {

//		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		DataOutputStream out2 = null;
		ZipOutputStream out = null;
//		FileInputStream fi = null;
		try {
			dest = new FileOutputStream(zipFile);
			out2 = new DataOutputStream(dest);
			out = new ZipOutputStream(out2);
//			byte[] data = new byte[BUFFER];
			File f = folder;
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(files[i]);
//				BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i].getName());
				out.putNextEntry(entry);
//				int count;
//				while ((count = origin.read(data, 0, BUFFER)) != -1) {
//					out.write(data, 0, count);
//				}
				IOUtils.copy(fi, out);
//				IOUtils.closeQuietly(origin);
				IOUtils.closeQuietly(fi);
			}
		} catch (Exception e) {
			logger.error("zip", e);
		}finally{
			try {
				out.closeEntry();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			IOUtils.closeQuietly(origin);
//			IOUtils.closeQuietly(fi);
			IOUtils.closeQuietly(dest);
			IOUtils.closeQuietly(out);
//			IOUtils.closeQuietly(out2);
		}
	}
	/**
	 * 
	 * unzip:解压文件夹. <br/>
	 *
	 * @author:李强 Date: 2016年8月12日 上午10:22:25
	 * @param zip
	 * @param folder
	 * @since JDK 1.7
	 */
	public static void unzip2(File zip, File folder) {
		ZipFile zipFile = null;
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		FileOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			zipFile = new ZipFile(zip);
			Enumeration<? extends ZipEntry> emu = zipFile.entries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(folder, entry.getName()).mkdirs();
					continue;
				}
				inputStream = zipFile.getInputStream(entry);
				bis = new BufferedInputStream(inputStream);
				File file = new File(folder, entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				out = new FileOutputStream(file);
				bos = new BufferedOutputStream(out, BUFFER);
				int count;
				byte[] data = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				IOUtils.closeQuietly(bos);
				IOUtils.closeQuietly(bis);
				IOUtils.closeQuietly(out);
				IOUtils.closeQuietly(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("unzip", e);
		} finally {
//			IOUtils.closeQuietly(zipFile);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(inputStream);
		}
	}
	/**
	 * 
	 * unzip:解压文件夹. <br/>
	 *
	 * @author:李强 Date: 2016年8月12日 上午10:22:25
	 * @param zip
	 * @param folder
	 * @since JDK 1.7
	 */
	public static void unzip(File zip, File folder) {
		ZipFile zipFile = null;
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		FileOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			zipFile = new ZipFile(zip,Charset.forName("GBK"));
			Enumeration<? extends ZipEntry> emu = zipFile.entries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(folder, entry.getName()).mkdirs();
					continue;
				}
				inputStream = zipFile.getInputStream(entry);
				bis = new BufferedInputStream(inputStream);
				File file = new File(folder, entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				out = new FileOutputStream(file);
				bos = new BufferedOutputStream(out, BUFFER);
				int count;
				byte[] data = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				IOUtils.closeQuietly(bos);
				IOUtils.closeQuietly(bis);
				IOUtils.closeQuietly(out);
				IOUtils.closeQuietly(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("unzip", e);
		} finally {
//			IOUtils.closeQuietly(zipFile);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * http://www.codingyun.com/article/81.html java.util.zip递归压缩带目录结构的文件/文件夹
	 * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
	 * 
	 * @param filepath
	 *          文件所在目录
	 * @param zippath
	 *          压缩后zip文件名称
	 * @param dirFlag
	 *          zip文件中第一层是否包含一级目录，true包含；false没有 2015年6月9日
	 */
	public static void zipMultiFile(String filepath, String zippath, boolean dirFlag) {
		FileOutputStream out = null;
		ZipOutputStream zipOut = null;
		try {
			File file = new File(filepath);// 要被压缩的文件夹
			File zipFile = new File(zippath);
			out = new FileOutputStream(zipFile);
			zipOut = new ZipOutputStream(out);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File fileSec : files) {
					if (dirFlag) {
						recursionZip(zipOut, fileSec, file.getName() + File.separator);
					} else {
						recursionZip(zipOut, fileSec, "");
					}
				}
			}
			zipOut.close();
		} catch (Exception e) {
			logger.error("zipMultiFile", e);
		} finally{
			IOUtils.closeQuietly(zipOut);
			IOUtils.closeQuietly(out);
		}
	}

	private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileSec : files) {
				recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
			}
		} else {
			byte[] buf = new byte[1024];
			InputStream input = null;
			try {
				input = new FileInputStream(file);
				zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
				int len;
				while ((len = input.read(buf)) != -1) {
					zipOut.write(buf, 0, len);
				}
			} catch (Exception e) {
				logger.error("recursionZip", e);
			} finally {
				IOUtils.closeQuietly(input);
			}
		}
	}
	
	public static void main(String[] args) {
		ZipUtil.unzip(new File("F:/upload/2.zip"), new File("F:/upload/aaa"));
	}
}
