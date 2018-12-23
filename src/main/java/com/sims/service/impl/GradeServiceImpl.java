package com.sims.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.mapper.GradeMapper;
import com.sims.model.Grade;
import com.sims.service.GradeService;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {
	@Autowired
	private GradeMapper gradeMapper;

	@Override
	public List<Grade> selectGradeByPage(Map<String, Object> map) {
		return gradeMapper.selectAllByPage(map);
	}

	@Override
	public int insert(Grade record) {
		return gradeMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return gradeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Grade record) {
		return gradeMapper.updateByPrimaryKey(record);
	}

}
