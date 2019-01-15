package com.sims.controller.common;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sims.common.ResultEntity;
import com.sims.common.enums.GlobalMessage;
import com.sims.common.util.Constants;

@Controller
@RequestMapping("/bigfile")
public class WebuploaderChunkedController {
	Logger logger = LoggerFactory.getLogger(WebuploaderChunkedController.class);

	private static String basePath = Constants.Config.IBIS_FILE_PATH+File.separator;
	private static final String IN = "/";
	private static final String CHUNK = "chunk";
	private static final String TEMP = "temp/";
	private static final String PART = "part";
	private static final String FILE_MD5 = "md5";
	private static final String FILE_NAME = "fileName";
	
	@RequestMapping("/bigFileUpload")
	public String detail(HttpServletResponse response,Model model,String id) throws Exception {
		model.addAttribute("outkeyId", id);
		return "jsp/file/bigFileUpload";
	}
	
	/**
	 * 获取多部分信息
	 *
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
	 *
	 * @param request
	 * @return
	 */
	private CommonsMultipartResolver getMultipartResolver(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		return new CommonsMultipartResolver(context);
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
	 * 
	 * @param file
	 * @param request  参数file,md5value,chunk
	 * @param response
	 * @param session
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = { RequestMethod.POST }, consumes = "multipart/form-data")
	public void upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
//			Thread.sleep(2000);
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				// 文件MD5值
				String fileMD5 = request.getParameter("md5value");
				// 切片下标
				int chunk = 0;
				if (StringUtils.hasText(request.getParameter("chunk"))) {
					chunk = Integer.parseInt(request.getParameter("chunk"));
				}
				// 临时目录用来存放所有分片文件
				String tempFileDirPath = basePath + "temp/" + fileMD5;
				File tempFileDir = new File(tempFileDirPath);
				if (!tempFileDir.exists()) {
					tempFileDir.mkdirs();
				}
				// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
				File tempChunkFile = new File(tempFileDir, chunk + ".part");
				// 如果文件已经存在，则删除该文件
				if (tempChunkFile.exists()) {
					FileUtils.forceDelete(tempChunkFile);
				}
				// 切片文件
				if (null != file) {
					FileUtils.copyInputStreamToFile(file.getInputStream(), tempChunkFile);
				}
			} else {
				throw new RuntimeException("请求类型出错！");
			}
		} catch (Exception e) {
			logger.warn("上传文件出现异常：" + e.getMessage());
		}
	}

	/**
	 * 查询已经存在的分片
	 * 
	 * @param params	md5
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	public ResultEntity check(Map<String, Object> params, HttpSession session) {
		ResultEntity resultEntity = new ResultEntity().buildGlobalMessage(GlobalMessage.RESP_FAIL);
		try {
			String fileMD5 = String.valueOf(params.get(FILE_MD5));
			if (StringUtils.hasText(fileMD5)) {
				String chunkFilePath = basePath + TEMP + fileMD5 + IN;
				File f = new File(chunkFilePath);
				if (f.exists()) {
					String[] files = f.list(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(PART);
						}
					});
					resultEntity.setData(files);
				} else {
					resultEntity.setData(new String[] {});
				}
			}
		} catch (Exception ex) {
			logger.error("检查切片是否存在", ex);
		}
		return resultEntity;
	}

	@RequestMapping("checksingle")
	@ResponseBody
	public ResultEntity checksingle(@RequestParam Map<String, Object> params, HttpSession session) {
		ResultEntity resultEntity = new ResultEntity().buildGlobalMessage(GlobalMessage.RESP_FAIL);
		try {
			String fileMD5 = String.valueOf(params.get(FILE_MD5));
			String chunkIndex = String.valueOf(params.get(CHUNK));
			String chunkSize = String.valueOf(params.get("chunkSize"));
			if (StringUtils.hasText(fileMD5) && StringUtils.hasText(chunkSize) && StringUtils.hasText(chunkIndex)) {
				String chunkFilePath = basePath + TEMP + fileMD5 + IN + chunkIndex + PART;
				File f = new File(chunkFilePath);
				if (f.exists() && f.length() == Long.valueOf(chunkSize)) {
					resultEntity.buildGlobalMessage(GlobalMessage.RESP_SUCESS);
				}
			}
		} catch (Exception ex) {
			logger.error("检查切片是否存在", ex);
		}
		return resultEntity;
	}

	/**
	 * merge:(合并文件). <br/>
	 * 
	 * @param params 请求参数 md5,fileName
	 */
	@RequestMapping(value = "merge", method = RequestMethod.POST)
	@ResponseBody
	public ResultEntity merge(@RequestParam Map<String, String> params, HttpSession session) {
		ResultEntity resultEntity = new ResultEntity().buildGlobalMessage(GlobalMessage.RESP_FAIL);
		try {
			String fileMD5 = params.get(FILE_MD5);
			String fileName = params.get(FILE_NAME);
			String fileExtName = FilenameUtils.getExtension(fileName);
			String targetFile = "temp/" + fileMD5 + "." + fileExtName;
			mergeChunkFile(fileMD5, basePath + targetFile);
			resultEntity.buildData(targetFile).buildGlobalMessage(GlobalMessage.RESP_SUCESS);
		} catch (Exception ex) {
			logger.error("合并文件出现异常", ex);
			resultEntity.buildRespDesc("合并文件出现异常！");
		}
		return resultEntity;
	}

	/**
	 * 合并分块文件
	 *
	 * @param fileMd5
	 * @param targetFilePath
	 */
	private void mergeChunkFile(String fileMd5, String targetFilePath) {
		Assert.notNull(fileMd5, "md5值不能为空");
		FileChannel outChannel = null;
		FileOutputStream fos = null;
		FileInputStream in = null;
		String tempFilePath = basePath + TEMP;

		String chunkFileDir = tempFilePath + fileMd5;
		File dir = new File(chunkFileDir);
		if (!dir.exists()) {
			throw new RuntimeException("分块文件夹不存在,md5:" + fileMd5);
		}
		File targetFile = new File(targetFilePath);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		try {
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile() && pathname.getName().endsWith(PART);
				}
			});
			List<File> chunkFiles = Arrays.asList(files);
			Collections.sort(chunkFiles, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					String chunk1 = FilenameUtils.getBaseName(o1.getName());
					String chunk2 = FilenameUtils.getBaseName(o2.getName());
					return Integer.valueOf(chunk1) - Integer.valueOf(chunk2);
				}
			});
			FileChannel inChannel = null;
			fos = new FileOutputStream(targetFilePath);
			outChannel = fos.getChannel();
			for (File chunk : chunkFiles) {
				in = new FileInputStream(chunk);
				inChannel = in.getChannel();
				inChannel.transferTo(0, inChannel.size(), outChannel);
				IOUtils.closeQuietly(inChannel);
				IOUtils.closeQuietly(in);
			}
		} catch (Exception e) {
			throw new RuntimeException("合并分块文件异常", e);
		} finally {
			if (in != null) {
				IOUtils.closeQuietly(in);
			}
			IOUtils.closeQuietly(outChannel);
			IOUtils.closeQuietly(fos);
			if (dir.exists()) {
				FileUtils.deleteQuietly(dir);
			}
		}
	}
}
