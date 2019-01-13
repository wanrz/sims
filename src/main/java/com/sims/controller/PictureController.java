package com.sims.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sims.common.JsonListResult;
import com.sims.common.JsonResult;
import com.sims.common.base.controller.BaseController;
import com.sims.common.enums.EnumClass;
import com.sims.common.util.Constants;
import com.sims.common.util.ControllerUtil;
import com.sims.common.util.ImgConvertByteArray;
import com.sims.common.util.JsonUtil;
import com.sims.common.util.UUIDUtil;
import com.sims.common.util.file.FileUtils;
import com.sims.model.Picture;
import com.sims.service.PictureService;

@Controller
@RequestMapping("/picture")
public class PictureController extends BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String FILE_SEPARATOR = "/";
	
	@Resource(name = "pictureService")
	private PictureService pictureService;

	@RequestMapping("/pictureList")
	public void pictureList(HttpServletRequest request, HttpServletResponse response, Picture record) {
		JsonListResult<Picture> result = new JsonListResult<Picture>();
		// 返回结果
		String retJson = "";
		// 设置参数信息
		if(record.getName()==null && record.getPeopleId()==null){
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", record);
//		map.put("page", pageInfo);
		
		// 设置分页查询
		PageHelper.startPage(Integer.parseInt(request.getParameter("page")), Integer.parseInt(request.getParameter("rows")));//currentPage 当前页码，rows 每页量
		PageHelper.orderBy("id desc");//排序
		List<Picture> list = this.pictureService.selectPictureByPage(map);
		// 设置返回结果
		result.setRows(list);
        PageInfo<Picture> pageInfo1 = new PageInfo<>(list);
		result.setTotal(pageInfo1.getTotal());
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/pictureComboList")
	public void pictureComboList(HttpServletRequest request, HttpServletResponse response, Picture record) {
		// 返回结果
		String retJson = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", record);
		// 设置分页查询
		List<Picture> list = this.pictureService.selectPictureByPage(map);
		Picture picture=new Picture();
		picture.setId("0");
		picture.setName("请选择...");
		list.add(picture);
//		Collections.sort(list); // 按年龄排序
		// 设置返回结果
		retJson = JsonUtil.toJSON(list);
		this.getPrintWriter(response, retJson);
	}
	
	
	@RequestMapping("/pictureSave")
	public void pictureSave(HttpServletRequest request, HttpServletResponse response, Picture record) {
		// 返回结果
		JSONObject result=new JSONObject();
		String base64Img = "";
		// 保存图片
		base64Img = request.getParameter("picture_feature_file");
		if (StringUtils.isNotEmpty(base64Img)) {
			String imgPath = saveImg(base64Img);
			record.setPath(imgPath);
		}
		record.setId(UUIDUtil.getUUID());
		record.setCreateTime(new Date());
		int res=this.pictureService.insert(record);
		if(res<0){
			result.put("success", "true");
			result.put("errorMsg", "保存失败");
		}else{
			result.put("success", "true");
		}
		
		this.getPrintWriter(response, result);
	}
	
	@RequestMapping("/pictureUpdate")
	public void pictureUpdate(HttpServletRequest request, HttpServletResponse response, Picture record) {
		JsonListResult<Picture> result = new JsonListResult<Picture>();
		// 返回结果
		String retJson = "";
		String base64Img = "";
		String id=request.getParameter("id");
		record.setId(id);
		// 保存图片
		base64Img = request.getParameter("picture_feature_file");
		if (StringUtils.isNotEmpty(base64Img)) {
			String imgPath = saveImg(base64Img);
			record.setPath(imgPath);
		}
		int res=this.pictureService.updateByPrimaryKey(record);
		if(res<0){
			result.setMessage("更新失败");
		}
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/pictureDelete")
	public void pictureDelete(HttpServletRequest request, HttpServletResponse response, String delIds) {
		JsonListResult<Picture> result = new JsonListResult<Picture>();
		// 返回结果
		String retJson = "";
		String str[]=delIds.split(",");
		long delNums = 0;
		for(String id:str){
//			boolean f=studentDao.getStudentBypictureId(con, str[i]);
//			if(f){
//				result.put("errorIndex", i);
//				result.put("errorMsg", "班级下面有学生，不能删除！");
//				ResponseUtil.write(response, result);
//				return;
//			}
			delNums=delNums+this.pictureService.deleteByPrimaryKey(id);
		}
		result.setSuccess(true);
		result.setTotal(delNums);
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	/**
	 * 根据文件相对路径获取文件信息
	 * @param requestData
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getFile")
	public void getFile(String fileUrl, HttpServletRequest request,
			HttpServletResponse response) {
		
		//文件数据		
		byte[] binaryData = null;
		//文件格式
		if(!StringUtils.isBlank(fileUrl)) {
			String fileName = FilenameUtils.getName(fileUrl);
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ fileName + "\"");
		}
		
		//文件存储方式
		String fileStoreway = Constants.Config.FILE_STORAGE_WAY;
		try {
			if(EnumClass.FileAccessEnum.LOCAL.getValue().equals(fileStoreway)) {
				//本地获取时，路径为全路径
				binaryData = FileUtils.getFileDataByPath(Constants.Config.IBIS_FILE_PATH+fileUrl);
			} 
			
			else if(EnumClass.FileAccessEnum.HTTP.getValue().equals(fileStoreway)) {
				binaryData = FileUtils.getFileDataByHttp(fileUrl);
			} else if(EnumClass.FileAccessEnum.HTTPS.getValue().equals(fileStoreway)) {
				binaryData = FileUtils.getFileDataByHttps(fileUrl);
			}
			//返回文件流
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(binaryData);
			outputStream.flush();
			
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	/**
	 * 获取图片base64字符串
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadImage")
	public void uploadImage(HttpServletRequest request, HttpServletResponse response)  {
		JsonResult resultEntity = ControllerUtil.uploadImage(request, response);	
		getPrintWriter(response, JsonUtil.toJSON(resultEntity));
	}
	
	
	/**
	 * 保存图片
	 * 
	 * @param imgData
	 * @param path
	 * @param imgName
	 * @return
	 */
	public String saveImg(String base64Img) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String path = FILE_SEPARATOR + year + FILE_SEPARATOR + month + FILE_SEPARATOR + day + FILE_SEPARATOR
				+ UUIDUtil.getUUID() + ".jpg";
		String imgUrl = Constants.Config.IBIS_FILE_PATH + path;
		ImgConvertByteArray.byteArray2Img(decodeBase64(base64Img), imgUrl);
		return path;
	}
	
	/**
	 * 对base64编码后的字符串进行反解
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] decodeBase64(String str) {
		return Base64.decodeBase64(clearBase64String(str));
	}
	
	/**
	 * 将字节数组进行base64编码，然后替换换行和回车等字符
	 * 
	 * @param str
	 * @return
	 */
	public static String clearBase64String(String str) {
		return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\s", "+")
				.replaceAll("\u003d", "=");
	}
}
