package com.sims.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sims.common.JsonListResult;
import com.sims.common.base.controller.BaseController;
import com.sims.common.enums.EnumClass;
import com.sims.common.util.Constants;
import com.sims.common.util.JsonUtil;
import com.sims.common.util.file.FileUtils;
import com.sims.model.User;
import com.sims.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping("/userList")
	public void userList(HttpServletRequest request, HttpServletResponse response, User record) {
		JsonListResult<User> result = new JsonListResult<User>();
		// 返回结果
		String retJson = "";
		// 设置参数信息
		String username = request.getParameter("username");
		if (username == null) {
			username = "";
		}
		record.setUsername(username);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", record);
//		map.put("page", pageInfo);
		
		// 设置分页查询
		PageHelper.startPage(Integer.parseInt(request.getParameter("page")), Integer.parseInt(request.getParameter("rows")));//currentPage 当前页码，rows 每页量
		PageHelper.orderBy("id desc");//排序
		List<User> list = this.userService.selectUserByPage(map);
		// 设置返回结果
		result.setRows(list);
        PageInfo<User> pageInfo1 = new PageInfo<>(list);
		result.setTotal(pageInfo1.getTotal());
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/userComboList")
	public void userComboList(HttpServletRequest request, HttpServletResponse response, User record) {
		// 返回结果
		String retJson = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", record);
		// 设置分页查询
		List<User> list = this.userService.selectUserByPage(map);
		User user=new User();
		user.setId(0);
		user.setUsername("请选择...");
		list.add(user);
		Collections.sort(list); // 按年龄排序
		// 设置返回结果
		retJson = JsonUtil.toJSON(list);
		this.getPrintWriter(response, retJson);
	}
	
	
	@RequestMapping("/userSave")
	public void userSave(HttpServletRequest request, HttpServletResponse response, User record) {
		JsonListResult<User> result = new JsonListResult<User>();
		// 返回结果
		String retJson = "";
		int res=this.userService.insert(record);
		if(res<0){
			result.setMessage("添加失败");
		}
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/userUpdate")
	public void userUpdate(HttpServletRequest request, HttpServletResponse response, User record) {
		JsonListResult<User> result = new JsonListResult<User>();
		// 返回结果
		String retJson = "";
		String id=request.getParameter("id");
		record.setId(Integer.parseInt(id));
		int res=this.userService.updateByPrimaryKey(record);
		if(res<0){
			result.setMessage("更新失败");
		}
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/userDelete")
	public void userDelete(HttpServletRequest request, HttpServletResponse response, String delIds) {
		JsonListResult<User> result = new JsonListResult<User>();
		// 返回结果
		String retJson = "";
		String str[]=delIds.split(",");
		long delNums = 0;
		for(String id:str){
//			boolean f=studentDao.getStudentByuserId(con, str[i]);
//			if(f){
//				result.put("errorIndex", i);
//				result.put("errorMsg", "班级下面有学生，不能删除！");
//				ResponseUtil.write(response, result);
//				return;
//			}
			delNums=delNums+this.userService.deleteByPrimaryKey(Integer.parseInt(id));
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
}
