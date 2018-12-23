package com.sims.service;

import java.util.List;
import java.util.Map;

import com.sims.model.Grade;

public interface GradeService {
	
	int insert(Grade record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(Grade record);

	List<Grade> selectGradeByPage(Map<String, Object> map);
}
