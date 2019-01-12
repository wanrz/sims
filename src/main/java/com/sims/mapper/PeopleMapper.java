package com.sims.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.sims.model.People;

public interface PeopleMapper {
    int deleteByPrimaryKey(String id);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);
    
    List<People> selectAllByPage(Map<String, Object> map) throws DataAccessException; // 分页查询用户信息功能\
    
    People findByPeopleName(String name);
}