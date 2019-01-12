package com.sims.service;

import java.util.List;
import java.util.Map;

import com.sims.model.People;

/**
 * 人员Service接口
 * 
 * @author Administrator
 *
 */
public interface PeopleService {

	int insert(People record);

	int deleteByPrimaryKey(String id);

	int updateByPrimaryKey(People record);

	List<People> selectPeopleByPage(Map<String, Object> map);
	
	People findByPeopleName(String name);
}
