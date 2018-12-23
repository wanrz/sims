package com.sims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sims.common.JsonListResult;
import com.sims.common.base.controller.BaseController;
import com.sims.common.util.JsonUtil;
import com.sims.model.Grade;
import com.sims.service.GradeService;

@Controller
@RequestMapping("/grade")
public class GradeController extends BaseController {
	@Resource(name = "gradeService")
	private GradeService gradeService;

	@RequestMapping("/gradeList")
	public void gradeList(HttpServletRequest request, HttpServletResponse response, Grade record) {
		JsonListResult<Grade> result = new JsonListResult<Grade>();
		// 返回结果
		String retJson = "";
		// 设置参数信息
		String gradename = request.getParameter("gradeName");
		if (gradename == null) {
			gradename = "";
		}
		record.setGradename(gradename);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", record);
//		map.put("page", pageInfo);
		
		// 设置分页查询
		PageHelper.startPage(Integer.parseInt(request.getParameter("page")), Integer.parseInt(request.getParameter("rows")));//currentPage 当前页码，rows 每页量
		PageHelper.orderBy("id desc");//排序
		List<Grade> list = this.gradeService.selectGradeByPage(map);
		// 设置返回结果
		result.setRows(list);
        PageInfo<Grade> pageInfo1 = new PageInfo<>(list);
		result.setTotal(pageInfo1.getTotal());
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/gradeSave")
	public void gradeSave(HttpServletRequest request, HttpServletResponse response, Grade record) {
		JsonListResult<Grade> result = new JsonListResult<Grade>();
		// 返回结果
		String retJson = "";
		int res=this.gradeService.insert(record);
		if(res<0){
			result.setMessage("添加失败");
		}
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/gradeUpdate")
	public void gradeUpdate(HttpServletRequest request, HttpServletResponse response, Grade record) {
		JsonListResult<Grade> result = new JsonListResult<Grade>();
		// 返回结果
		String retJson = "";
		String id=request.getParameter("id");
		record.setId(Integer.parseInt(id));
		int res=this.gradeService.updateByPrimaryKey(record);
		if(res<0){
			result.setMessage("更新失败");
		}
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
	
	@RequestMapping("/gradeDelete")
	public void gradeDelete(HttpServletRequest request, HttpServletResponse response, String delIds) {
		JsonListResult<Grade> result = new JsonListResult<Grade>();
		// 返回结果
		String retJson = "";
		String str[]=delIds.split(",");
		long delNums = 0;
		for(String id:str){
//			boolean f=studentDao.getStudentByGradeId(con, str[i]);
//			if(f){
//				result.put("errorIndex", i);
//				result.put("errorMsg", "班级下面有学生，不能删除！");
//				ResponseUtil.write(response, result);
//				return;
//			}
			delNums=delNums+this.gradeService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		result.setSuccess(true);
		result.setTotal(delNums);
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
}
