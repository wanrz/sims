package com.sims.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.sims.model.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer stuid);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer stuid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
    
    List<Student> selectAllByPage(Map<String, Object> map) throws DataAccessException; 
    // 分页查询用户信息功能
}