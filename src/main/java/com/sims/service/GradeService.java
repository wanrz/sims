package com.sims.service;

import java.util.List;
import java.util.Map;

import com.sims.model.Grade;

public interface GradeService {
	List<Grade> selectGradeByPage(Map<String, Object> map);
}	
