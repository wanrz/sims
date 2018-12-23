package com.sims.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.mapper.StudentMapper;
import com.sims.model.Student;
import com.sims.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentMapper studentMapper;

	@Override
	public List<Student> selectStudentByPage(Map<String, Object> map) {
		return studentMapper.selectAllByPage(map);
	}

}
