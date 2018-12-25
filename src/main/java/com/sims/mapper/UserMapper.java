package com.sims.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.sims.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
	User login(User user);
	
	User findById(String id);
	
	List<User> selectAllByPage(Map<String, Object> map) throws DataAccessException; // 分页查询用户信息功能
}