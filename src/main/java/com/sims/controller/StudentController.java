package com.sims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sims.common.JsonListResult;
import com.sims.common.base.controller.BaseController;
import com.sims.common.util.JsonUtil;
import com.sims.model.Student;
import com.sims.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {
	@Resource(name = "studentService")
	private StudentService studentService;

	@RequestMapping("/studentList")
	public void studentList(HttpServletRequest request, HttpServletResponse response, Student record) {
		JsonListResult<Student> result = new JsonListResult<Student>();
		// 返回结果
		String retJson = "";
		// 设置参数信息
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String sex=request.getParameter("sex");
		String bbirthday=request.getParameter("bbirthday");
		String ebirthday=request.getParameter("ebirthday");
		String gradeId=request.getParameter("gradeId");
		
		if(stuNo!=null){
			record.setStuno(stuNo);
			record.setStuname(stuName);
			record.setSex(sex);
			if(StringUtils.isNotEmpty(gradeId)){
				record.setGradeid(Integer.parseInt(gradeId));
			}
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", record);
//		map.put("page", pageInfo);
		
		// 设置分页查询
		PageHelper.startPage(Integer.parseInt(request.getParameter("page")), Integer.parseInt(request.getParameter("rows")));//currentPage 当前页码，rows 每页量
		PageHelper.orderBy("stuId asc");//排序
		List<Student> list = this.studentService.selectStudentByPage(map);
		// 设置返回结果
		result.setRows(list);
        PageInfo<Student> pageInfo1 = new PageInfo<>(list);
		result.setTotal(pageInfo1.getTotal());
		retJson = JsonUtil.toJSON(result);
		this.getPrintWriter(response, retJson);
	}
}
