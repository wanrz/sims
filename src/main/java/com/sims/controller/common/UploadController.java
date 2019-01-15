package com.sims.controller.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sims.common.base.controller.BaseController;
import com.sims.common.util.Constants;
import com.sims.common.util.UUIDUtil;
import com.sims.model.Picture;
import com.sims.service.PictureService;

/**
 * <p>ClassName: UploadController</p>
 * Description:单文件上传<br/>
 * @date 2019年1月13日 下午12:30:16 
 * @author wanrz
 * @version 1.0
 * @since JDK 1.7
 */ 
@Controller
@RequestMapping("/upload") 
public class UploadController extends BaseController {
	Logger logger = LoggerFactory.getLogger(UploadController.class);

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = Constants.Config.IBIS_FILE_PATH;
    
    @Resource(name = "pictureService")
	private PictureService pictureService;

    @RequestMapping("/index")
    public String index() {
        return "jsp/file/singleUpload";
    }

    @RequestMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
    		Model model,HttpServletRequest request) {
    	System.out.println(request.getParameter("username"));
        if (file.isEmpty()) {
        	model.addAttribute("message", "Please select a file to upload");
            return "jsp/file/singleUpload";
        }
        String peopleId="";
        String  fileName="";
        String  fileType="";
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            
            fileName=UUIDUtil.getUUID();
            String filePath=com.sims.common.util.file.FileUtils.getDateFilePath(fileName, fileType);
            Path path = Paths.get(UPLOADED_FOLDER+filePath);
            Files.write(path, bytes);
            
            if(StringUtils.isNotEmpty(request.getParameter("peopleId"))){
            	peopleId=request.getParameter("peopleId");
            	Picture record=new Picture();
            	record.setId(UUIDUtil.getUUID());
            	record.setName(fileName);
            	record.setStyle(file.getContentType());
            	record.setSize(file.getSize());
            	record.setPath(filePath);
            	record.setPeopleId(peopleId);
            	record.setCreateTime(new Date());
            	pictureService.insert(record);
            }
            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "jsp/file/singleUpload";
    }

	/**
	 * fileUpload:(文件上传). <br/>
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return 上传文件的路径
	 */
	@ResponseBody
	@RequestMapping(value = "/upload2", method = { RequestMethod.POST }, consumes = "multipart/form-data")
	public void upload2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			// 判断是否是文件
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 得到所有的表单域，它们目前都被当作FileItem
				List<FileItem> fileItems = upload.parseRequest(request);
				// 文件名称
				String fileMD5 = null;
				// 切片坐标
				int chunk = 0;
				// 切片文件
				FileItem chunkFile = null;

				for (FileItem fileItem : fileItems) {
					System.err.println(fileItem.getFieldName());
					if (fileItem.getFieldName().equals("md5value")) {
						fileMD5 = fileItem.getString();
					} else if (fileItem.getFieldName().equals("chunk")) {
						chunk = Integer.parseInt(fileItem.getString());
					} else if (fileItem.getFieldName().equals("file")) {
						chunkFile = fileItem;
					}
				}

				// 临时目录用来存放所有分片文件
				String tempFileDirPath = UPLOADED_FOLDER + "/temp/" + fileMD5;
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
				if (null != chunkFile) {
					FileUtils.copyInputStreamToFile(chunkFile.getInputStream(), tempChunkFile);
				}
			} else {
				throw new RuntimeException("请求类型出错！");
			}
		} catch (Exception e) {
			logger.warn("上传文件出现异常：" + e.getMessage());
		}
	}
}
