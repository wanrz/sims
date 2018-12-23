package com.sims.service;

import java.util.List;
import java.util.Map;

import com.sims.model.Student;

public interface StudentService {
	List<Student> selectStudentByPage(Map<String, Object> map);
}	
