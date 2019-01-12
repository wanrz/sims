package com.sims.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.mapper.PeopleMapper;
import com.sims.model.People;
import com.sims.service.PeopleService;

@Service("peopleService")
public class PeopleServiceImpl implements PeopleService {
	@Autowired
	private PeopleMapper peopleMapper;

	@Override
	public List<People> selectPeopleByPage(Map<String, Object> map) {
		return peopleMapper.selectAllByPage(map);
	}

	@Override
	public int insert(People record) {
		return peopleMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return peopleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(People record) {
		return peopleMapper.updateByPrimaryKey(record);
	}

	@Override
	public People findByPeopleName(String name) {
		return peopleMapper.findByPeopleName(name);
	}

}
