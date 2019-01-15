package com.sims.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sims.common.base.controller.BaseController;
import com.sims.common.util.CheckUtil;
import com.sims.common.util.Constants;
import com.sims.common.util.UUIDUtil;
import com.sims.common.util.file.ZipUtil;
import com.sims.model.Picture;
import com.sims.service.PictureService;

/**
 * ClassName: ChunkUploadController <br/>
 * Description: 切片上传控制器. <br/>
 */
@Controller
@RequestMapping("/chunkUpload")
public class ChunkUploadController extends BaseController {

	Logger logger = LoggerFactory.getLogger(UploadController.class);

	private static final String PATH_SEPARATOR = "/";

	private static final String TIMESTAMP = "timestamp";
	
	private static String UPLOADED_FOLDER = Constants.Config.IBIS_FILE_PATH;
	
    @Resource(name = "pictureService")
	private PictureService pictureService;

	// @Resource(name = "batchUserService")
	// private BatchUserService batchUserService;

	/**
	 * importBatch:(批量导入). <br/>
	 *
	 * @param params
	 *            请求参数
	 */
	@RequestMapping("importBatch")
	public void importBatch(HttpServletResponse response, Model model, String filePath, String outkeyId) {
		try {
			// zip文件相对路径
			CheckUtil.notEmpty(filePath, "文件路径不能为空");
			// zip文件实际路径
			String zipFilePath = UPLOADED_FOLDER+PATH_SEPARATOR + filePath;
			// 进度条唯一标识
			// final String timestamp =
			// ObjectUtils.toString(params.get(TIMESTAMP));
			// 判断文件是否存在
			File zipFile = new File(zipFilePath);
			if (!zipFile.exists() || !zipFile.isFile()) {
				logger.error("压缩文件错误:{}",zipFilePath);
			}

			String folderPath = UPLOADED_FOLDER+"/temp"+ PATH_SEPARATOR
					+ UUIDUtil.getUUID();
			File folder = new File(folderPath);
			folder.mkdirs();
			
			String fileName="";
			String  fileType="";
			// 解压文件
			try {
				ZipUtil.unzip(zipFile, folder);
				for(File file:folder.listFiles()){
					fileName=UUIDUtil.getUUID();
		            String datePath=com.sims.common.util.file.FileUtils.getDateFilePath(fileName, fileType);
		            File dest = new File(UPLOADED_FOLDER+datePath);
		            copyFileUsingFileChannels(file,dest);
		            
	            	Picture record=new Picture();
	            	record.setId(UUIDUtil.getUUID());
	            	record.setName(fileName);
	            	record.setStyle("image/jpeg");
	            	record.setSize(file.length());
	            	record.setPath(datePath);
	            	record.setPeopleId(outkeyId);
	            	record.setCreateTime(new Date());
	            	pictureService.insert(record);
				}
			} catch (final Exception e) {
				logger.error("解压文件错误：" + zipFilePath, e);
			}
			// 删除解压的文件
			FileUtils.deleteQuietly(folder);
			FileUtils.deleteQuietly(zipFile);
		} catch (Exception ex) {
			logger.error("批量导入出现异常", ex);
		}

	}
	
	private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

}
