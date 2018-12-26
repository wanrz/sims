package com.sims.service;

import java.util.List;
import java.util.Map;

import com.sims.model.User;

/**
 * 用户Service接口
 * 
 * @author Administrator
 *
 */
public interface UserService {

	User login(User user);

	User findById(String id);
	
	User findByUserName(String username);

	int insert(User record);

	int deleteByPrimaryKey(Integer id);

	int updateByPrimaryKey(User record);

	List<User> selectUserByPage(Map<String, Object> map);
}
