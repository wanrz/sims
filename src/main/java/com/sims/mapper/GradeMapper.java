package com.sims.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.sims.model.Grade;

public interface GradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);
    
    List<Grade> selectAllByPage(Map<String, Object> map) throws DataAccessException; // 分页查询用户信息功能
}